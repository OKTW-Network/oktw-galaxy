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

package one.oktw.galaxy.recipe.easyRecipe

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.inventory.CraftingInventory
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.recipe.CraftingRecipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.util.Identifier
import net.minecraft.world.World
import one.oktw.galaxy.recipe.utils.Ingredient
import one.oktw.galaxy.recipe.utils.RecipeUtils

class Minecart : CraftingRecipe {
    private val minecartType = hashMapOf(
        Items.CHEST to Items.CHEST_MINECART,
        Items.FURNACE to Items.FURNACE_MINECART,
        Items.HOPPER to Items.HOPPER_MINECART,
        Items.TNT to Items.TNT_MINECART
    )

    override fun matches(inv: CraftingInventory, world: World): Boolean {
        var match = false
        minecartType.forEach { (recipeItem, _) ->
            val ironIngot = Ingredient(items = listOf(Items.IRON_INGOT))
            val type = Ingredient(items = listOf(recipeItem))
            val list = listOf(
                ironIngot, type, ironIngot,
                ironIngot, ironIngot, ironIngot
            )
            if (RecipeUtils.isItemShapedMatches(inv, 3, 2, list)) {
                match = true
                return@forEach
            }
        }
        return match
    }

    override fun craft(inv: CraftingInventory): ItemStack {
        var item = ItemStack.EMPTY
        minecartType.forEach { (recipeItem, result) ->
            val ironIngot = Ingredient(items = listOf(Items.IRON_INGOT))
            val type = Ingredient(items = listOf(recipeItem))
            val list = listOf(
                ironIngot, type, ironIngot,
                ironIngot, ironIngot, ironIngot
            )
            if (RecipeUtils.isItemShapedMatches(inv, 3, 2, list)) {
                item = result.defaultStack
                return@forEach
            }
        }
        return item
    }

    @Environment(EnvType.CLIENT)
    override fun fits(width: Int, height: Int): Boolean {
        throw NotImplementedError()
    }

    override fun getOutput() = Items.MINECART.defaultStack

    override fun getId() = Identifier("galaxy", "easy_recipe/minecart")

    override fun getSerializer(): RecipeSerializer<*> {
        TODO("Not yet implemented, support client mod.")
    }
}
