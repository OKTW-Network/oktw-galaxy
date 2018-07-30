package one.oktw.galaxy.gui.machine

import com.flowpowered.math.vector.Vector3d
import kotlinx.coroutines.experimental.channels.toList
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.reactive.openSubscription
import one.oktw.galaxy.Main
import one.oktw.galaxy.data.DataUUID
import one.oktw.galaxy.galaxy.data.extensions.getPlanet
import one.oktw.galaxy.galaxy.planet.PlanetHelper
import one.oktw.galaxy.galaxy.planet.TeleportHelper
import one.oktw.galaxy.galaxy.planet.enums.PlanetType
import one.oktw.galaxy.gui.GUIHelper
import one.oktw.galaxy.gui.PageGUI
import one.oktw.galaxy.item.enums.ButtonType
import one.oktw.galaxy.item.type.Button
import one.oktw.galaxy.machine.teleporter.TeleporterHelper
import one.oktw.galaxy.machine.teleporter.data.Teleporter
import org.spongepowered.api.data.key.Keys
import org.spongepowered.api.entity.EntityTypes
import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent
import org.spongepowered.api.item.inventory.Inventory
import org.spongepowered.api.item.inventory.InventoryArchetypes
import org.spongepowered.api.item.inventory.ItemStack
import org.spongepowered.api.item.inventory.property.InventoryTitle
import org.spongepowered.api.text.Text
import org.spongepowered.api.text.format.TextColors
import org.spongepowered.api.text.format.TextStyles
import org.spongepowered.api.world.Location
import java.util.*
import kotlin.collections.ArrayList

class Teleporter(private val teleporter: Teleporter) : PageGUI() {
    private val MAX_FRAME = 64

    private val lang = Main.languageService.getDefaultLanguage()
    private val list = TeleporterHelper.getAvailableTargets(teleporter)

    override val token = "BrowserGalaxy-${UUID.randomUUID()}"
    override val inventory: Inventory = Inventory.builder()
        .of(InventoryArchetypes.DOUBLE_CHEST)
        .property(InventoryTitle.of(
            teleporter.crossPlanet
                .let {
                    if (it) {
                        lang["UI.Title.AdvancedTeleporter"]
                    } else {
                        lang["UI.Title.Teleporter"]
                    }
                }
                .format(teleporter.name)
                .let { Text.of(it) }
        ))
        .listener(InteractInventoryEvent::class.java, this::eventProcess)
        .build(Main.main)

    init {
        offerPage(0)

        // register event
        registerEvent(ClickInventoryEvent::class.java, this::clickEvent)
    }

    override suspend fun get(number: Int, skip: Int): List<ItemStack> {
        val res = ArrayList<ItemStack>()

        val teleporters = list
            .skip(skip)
            .limit(number)
            .openSubscription()
            .toList()

        for (targetTransporter in teleporters) {
            // don't tp to itself
            if (teleporter.uuid == targetTransporter.uuid) continue
            val uuid = targetTransporter.position.planet ?: continue
            val planet = Main.galaxyManager.get(null, uuid)?.getPlanet(uuid) ?: continue

            when (planet.type) {
                PlanetType.NORMAL -> Button(ButtonType.PLANET_O)
                PlanetType.NETHER -> Button(ButtonType.PLANET_N)
                PlanetType.END -> Button(ButtonType.PLANET_E)
            }.createItemStack()
                .apply {
                    offer(DataUUID(targetTransporter.uuid))
                    offer(Keys.DISPLAY_NAME, Text.of(TextColors.YELLOW, TextStyles.BOLD, targetTransporter.name))
                    offer(
                        Keys.ITEM_LORE,
                        Arrays.asList(
                            Text.of(
                                TextColors.GREEN,
                                lang["UI.Tip.Target"].format(
                                    "${planet.name} ${targetTransporter.position.x}, ${targetTransporter.position.y}, ${targetTransporter.position.z}"
                                ),
                                TextColors.RESET
                            ),
                            Text.of(
                                TextColors.GREEN,
                                lang["UI.Tip.CanCrossPlanet"].format(
                                    if (targetTransporter.crossPlanet) {
                                        lang["UI.Tip.true"]
                                    } else {
                                        lang["UI.Tip.false"]
                                    }
                                ),
                                TextColors.RESET
                            )
                        )
                    )
                }
                .let { res.add(it) }
        }

        return res
    }

    private fun clickEvent(event: ClickInventoryEvent) {
        event.isCancelled = true

        val player = event.source as Player
        val item = event.cursorTransaction.default
        val uuid = item[DataUUID.key].orElse(null) ?: return

        val (slot) = view.getNameOf(event) ?: return

        if (slot == Companion.Slot.ITEMS) {
            launch {
                val targetTeleporter = TeleporterHelper.get(uuid) ?: return@launch
                val planetId = targetTeleporter.position.planet ?: return@launch
                val targetPlanet = Main.galaxyManager.get(null, planetId)?.getPlanet(planetId) ?: return@launch
                val targetWorld = PlanetHelper.loadPlanet(targetPlanet) ?: return@launch

                val sourceFrames = teleporter.position
                    .let { Location(player.world, it.x, it.y, it.z) }
                    .let { TeleporterHelper.searchTeleporterFrame(it, MAX_FRAME); }

                if (sourceFrames == null) {
                    player.sendMessage(Text.of(lang["Respond.TooMuchFramesThisSide"]))
                    return@launch
                }

                val sourceEntities = player.world.entities.filter {
                    sourceFrames[Triple(it.location.blockX, it.location.blockY - 1, it.location.blockZ)] != null
                }

                val targetFrames = targetTeleporter.position
                    .let { Location(targetWorld, it.x, it.y, it.z) }
                    .let { TeleporterHelper.searchTeleporterFrame(it, MAX_FRAME); }
                    ?.values
                    ?.let { ArrayList(it) }

                if (targetFrames == null) {
                    player.sendMessage(Text.of(lang["Respond.TooMuchFramesThatSide"]))
                    return@launch
                }

                var index = 0

                sourceEntities.forEach {
                    val target = if (targetFrames.size != 0) targetFrames[index % targetFrames.size] else Location(
                        targetWorld,
                        targetTeleporter.position.x,
                        targetTeleporter.position.y,
                        targetTeleporter.position.z
                    )

                    index++

                    if (it.type == EntityTypes.PLAYER) {
                        val currentPlayer = it as Player

                        // we teleport the main player to exact position
                        if (currentPlayer == player) return@forEach

                        TeleportHelper.teleport(
                            it,
                            // offset y by 1, so you are on the block, offset x and z by 0.5, so you are on the center of block
                            Location(targetWorld, target.x + 0.5, target.y + 1, target.z + 0.5)
                        )
                    } else {
                        launch(Main.serverThread) {
                            it.transferToWorld(
                                targetWorld,
                                Vector3d(target.x + 0.5, target.y + 1, target.z + 0.5)
                            )
                        }
                    }
                }

                TeleportHelper.teleport(
                    player,
                    // offset y by 1, so you are on the top of block, offset x and z by 0.5, so you are on the center of block
                    Location(targetWorld, targetTeleporter.position.x + 0.5, targetTeleporter.position.y + 1, targetTeleporter.position.z + 0.5)
                )

                GUIHelper.closeAll(player)
            }
        }
    }
}