/*
 * OKTW Galaxy Project
 * Copyright (C) 2018-2020
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

package one.oktw.galaxy.recipe.utils

import net.minecraft.inventory.CraftingInventory
import net.minecraft.item.ItemStack
import net.minecraft.item.Items

object RecipeUtils {
    fun isItemShapedMatches(inv: CraftingInventory, width: Int, height: Int, list: List<Ingredient>): Boolean {
        for (x in 0..(inv.width - width)) {
            for (y in 0..(inv.height - height)) {
                if (shapeMatchesSmall(inv, width, height, x, y, true, list)) return true
                if (shapeMatchesSmall(inv, width, height, x, y, false, list)) return true
            }
        }

        return false
    }

    private fun shapeMatchesSmall(
        inv: CraftingInventory,
        width: Int,
        height: Int,
        offsetX: Int,
        offsetY: Int,
        widthFromLast: Boolean,
        list: List<Ingredient>
    ): Boolean {
        for (x in 0 until inv.width) {
            for (y in 0 until inv.height) {
                val i = x - offsetX
                val j = y - offsetY
                var input = Ingredient(items = listOf(Items.AIR))
                if (i >= 0 && j >= 0 && i < width && j < height) {
                    input = if (widthFromLast) {
                        list[width - i - 1 + j * width]
                    } else {
                        list[i + j * width]
                    }
                }
                if (!input.matches(inv.getStack(x + y * inv.width))) return false
            }
        }
        return true
    }

    fun isItemShapelessMatches(inv: CraftingInventory, list: List<Ingredient>): Boolean {
        val inputItems = mutableListOf<ItemStack>()

        for (i in 0 until inv.size()) {
            val invItem = inv.getStack(i)
            if (!invItem.isEmpty) {
                inputItems += invItem
            }
        }

        if (inputItems.count() != list.count()) return false
        list.forEach { ingredient ->
            inputItems.removeIf { ingredient.matches(it) }
        }
        return inputItems.isEmpty()
    }
}
