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
import net.minecraft.tag.ItemTags
import net.minecraft.util.Identifier
import net.minecraft.world.World
import one.oktw.galaxy.recipe.utils.Ingredient
import one.oktw.galaxy.recipe.utils.RecipeUtils

class WoodenSlab : CraftingRecipe {
    private val slabs = hashMapOf(
        ItemTags.OAK_LOGS to Items.OAK_SLAB,
        ItemTags.SPRUCE_LOGS to Items.SPRUCE_SLAB,
        ItemTags.BIRCH_LOGS to Items.BIRCH_SLAB,
        ItemTags.JUNGLE_LOGS to Items.JUNGLE_SLAB,
        ItemTags.ACACIA_LOGS to Items.ACACIA_SLAB,
        ItemTags.DARK_OAK_LOGS to Items.DARK_OAK_SLAB,
        ItemTags.CRIMSON_STEMS to Items.CRIMSON_SLAB,
        ItemTags.WARPED_STEMS to Items.WARPED_SLAB
    )

    override fun matches(inv: CraftingInventory, world: World): Boolean {
        var match = false
        slabs.forEach { (tag, _) ->
            val list = listOf(
                Ingredient(tag = tag), Ingredient(tag = tag), Ingredient(tag = tag)
            )
            if (RecipeUtils.isItemShapedMatches(inv, 3, 1, list)) {
                match = true
                return@forEach
            }
        }
        return match
    }

    override fun craft(inv: CraftingInventory): ItemStack {
        var item = ItemStack.EMPTY
        slabs.forEach { (tag, result) ->
            val list = listOf(
                Ingredient(tag = tag), Ingredient(tag = tag), Ingredient(tag = tag)
            )
            if (RecipeUtils.isItemShapedMatches(inv, 3, 1, list)) {
                item = result.defaultStack.apply { this.count = 24 }
                return@forEach
            }
        }
        return item
    }

    @Environment(EnvType.CLIENT)
    override fun fits(width: Int, height: Int): Boolean {
        throw NotImplementedError()
    }

    override fun getOutput() = Items.OAK_SLAB.defaultStack.apply { this.count = 24 }

    override fun getId() = Identifier("galaxy", "easy_recipe/wood_slab")

    override fun getSerializer(): RecipeSerializer<*> {
        TODO("Not yet implemented, support client mod.")
    }
}
