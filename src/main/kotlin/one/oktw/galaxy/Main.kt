/*
 * OKTW Galaxy Project
 * Copyright (C) 2018-2021
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package one.oktw.galaxy

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.fabricmc.api.DedicatedServerModInitializer
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.server.dedicated.MinecraftDedicatedServer
import net.minecraft.util.Identifier
import one.oktw.galaxy.block.CustomBlock
import one.oktw.galaxy.block.event.AngelBlock
import one.oktw.galaxy.block.event.BlockEvents
import one.oktw.galaxy.block.event.Elevator
import one.oktw.galaxy.chat.Exchange
import one.oktw.galaxy.command.commands.Admin
import one.oktw.galaxy.command.commands.Home
import one.oktw.galaxy.command.commands.Join
import one.oktw.galaxy.command.commands.Spawn
import one.oktw.galaxy.event.EventManager
import one.oktw.galaxy.event.type.ProxyResponseEvent
import one.oktw.galaxy.item.event.CustomItemEventHandler
import one.oktw.galaxy.player.Harvest
import one.oktw.galaxy.player.Sign
import one.oktw.galaxy.proxy.api.ProxyAPI
import one.oktw.galaxy.recipe.RecipeRegistry
import one.oktw.galaxy.resourcepack.ResourcePack
import java.util.*

@Suppress("unused")
class Main : DedicatedServerModInitializer {
    lateinit var server: MinecraftDedicatedServer
        private set
    lateinit var eventManager: EventManager
        private set

    companion object {
        val PROXY_IDENTIFIER = Identifier("galaxy", "proxy")
        var main: Main? = null
            private set
        val selfUUID by lazy {
            try {
                System.getenv("GALAXY_ID")?.let { UUID.fromString(it) }
            } catch (err: Throwable) {
                null
            } ?: ProxyAPI.dummyUUID
        }
    }

    override fun onInitializeServer() {
        main = this

        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher, _ ->
            listOf(Join(), Admin(), Home(), Spawn()).forEach { dispatcher.let(it::register) }
        })

        // Register CustomBlockEntity
        CustomBlock

        // Recipe
        RecipeRegistry.register()

        ServerLifecycleEvents.SERVER_STARTING.register(ServerLifecycleEvents.ServerStarting {
            server = it as MinecraftDedicatedServer
            eventManager = EventManager(server)

            val resourcePackUrl: String? = System.getenv("resourcePack")
            if (!resourcePackUrl.isNullOrBlank()) {
                GlobalScope.launch {
                    val resourcePack = ResourcePack.new(resourcePackUrl)
                    withContext(server.asCoroutineDispatcher()) {
                        server.setResourcePack(resourcePack.uri.toString(), resourcePack.hash)
                    }
                }
            }

            // Register Proxy packet receiver
            ServerPlayNetworking.registerGlobalReceiver(PROXY_IDENTIFIER) { _, player, _, buf, _ ->
                eventManager.emit(ProxyResponseEvent(player, ProxyAPI.decode(buf.nioBuffer())))
            }

            //Events
            eventManager.register(Exchange())
            eventManager.register(Harvest())
            eventManager.register(BlockEvents())
            eventManager.register(Sign())
            eventManager.register(one.oktw.galaxy.item.event.Wrench())
            eventManager.register(Elevator())
            eventManager.register(AngelBlock())
            eventManager.register(CustomItemEventHandler())
        })

        // server.log("current server id is $selfUID
        println("current server id is $selfUUID")
    }
}
