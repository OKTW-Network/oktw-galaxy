/*
 * OKTW Galaxy Project
 * Copyright (C) 2018-2019
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

package one.oktw.galaxy.command.commands

import com.mojang.authlib.GameProfile
import com.mojang.brigadier.CommandDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.sync.Mutex
import net.minecraft.container.Container
import net.minecraft.container.NameableContainerProvider
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import one.oktw.galaxy.command.Command
import one.oktw.galaxy.gui.TestContainer
import java.util.concurrent.ConcurrentHashMap

class Test : Command, CoroutineScope by CoroutineScope(Dispatchers.Default + SupervisorJob()) {
    private val lock = ConcurrentHashMap<ServerPlayerEntity, Mutex>()

    override fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        dispatcher.register(
            CommandManager.literal("test")
                .executes { context ->
                    execute(context.source, listOf(context.source.player.gameProfile))
                }
        )
    }

    private fun execute(source: ServerCommandSource, collection: Collection<GameProfile>): Int {
        source.player.openContainer(object : NameableContainerProvider {
            override fun createMenu(syncId: Int, playerInventory: PlayerInventory, var3: PlayerEntity): Container? {
                return TestContainer(playerInventory, syncId)
            }

            override fun getDisplayName(): Text {
                return LiteralText("Test")
            }
        })

        return com.mojang.brigadier.Command.SINGLE_SUCCESS
    }
}