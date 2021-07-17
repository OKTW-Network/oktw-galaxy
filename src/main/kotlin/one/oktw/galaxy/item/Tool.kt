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

package one.oktw.galaxy.item

import net.minecraft.item.Items.BAT_SPAWN_EGG
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier
import net.minecraft.util.math.MathHelper

class Tool private constructor(id: String, modelData: Int, private val name: String) :
    CustomItem(Identifier("galaxy", "item/tool/$id"), BAT_SPAWN_EGG, modelData) {
    override val cacheable = false

    companion object {
        val WRENCH = registry.register(Tool("wrench", 2010100, "item.Tool.WRENCH"))
    }

    override fun getName(): Text = TranslatableText(name).styled { it.withColor(Formatting.WHITE).withItalic(false) }

    override fun writeCustomNbt(tag: NbtCompound) {
        super.writeCustomNbt(tag)
        tag.put("ToolData", NbtCompound().apply { putUuid("id", MathHelper.randomUuid()) })
    }
}
