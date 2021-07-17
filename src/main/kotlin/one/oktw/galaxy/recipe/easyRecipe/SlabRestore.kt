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

class SlabRestore : CraftingRecipe {
    private val slabs = hashMapOf(
        Items.OAK_SLAB to Items.OAK_PLANKS,
        Items.SPRUCE_SLAB to Items.SPRUCE_PLANKS,
        Items.BIRCH_SLAB to Items.BIRCH_PLANKS,
        Items.JUNGLE_SLAB to Items.JUNGLE_PLANKS,
        Items.ACACIA_SLAB to Items.ACACIA_PLANKS,
        Items.DARK_OAK_SLAB to Items.DARK_OAK_PLANKS,
        Items.CRIMSON_SLAB to Items.CRIMSON_PLANKS,
        Items.WARPED_SLAB to Items.WARPED_PLANKS,
        Items.STONE_SLAB to Items.STONE,
        Items.SMOOTH_QUARTZ_SLAB to Items.SMOOTH_STONE,
        Items.GRANITE_SLAB to Items.GRANITE,
        Items.POLISHED_GRANITE_SLAB to Items.POLISHED_GRANITE,
        Items.DIORITE_SLAB to Items.DIORITE,
        Items.POLISHED_DIORITE_SLAB to Items.POLISHED_DIORITE,
        Items.ANDESITE_SLAB to Items.ANDESITE,
        Items.POLISHED_ANDESITE_SLAB to Items.POLISHED_ANDESITE,
        Items.COBBLESTONE_SLAB to Items.COBBLESTONE,
        Items.MOSSY_STONE_BRICK_SLAB to Items.MOSSY_STONE_BRICKS,
        Items.STONE_BRICK_SLAB to Items.STONE_BRICKS,
        Items.MOSSY_COBBLESTONE_SLAB to Items.MOSSY_COBBLESTONE,
        Items.BRICK_SLAB to Items.BRICKS,
        Items.END_STONE_BRICK_SLAB to Items.END_STONE_BRICKS,
        Items.NETHER_BRICK_SLAB to Items.NETHER_BRICKS,
        Items.RED_NETHER_BRICK_SLAB to Items.RED_NETHER_BRICKS,
        Items.SANDSTONE_SLAB to Items.SANDSTONE,
        Items.CUT_SANDSTONE_SLAB to Items.CUT_SANDSTONE,
        Items.SMOOTH_SANDSTONE_SLAB to Items.SMOOTH_SANDSTONE,
        Items.RED_SANDSTONE_SLAB to Items.RED_SANDSTONE,
        Items.CUT_RED_SANDSTONE_SLAB to Items.CUT_RED_SANDSTONE,
        Items.SMOOTH_RED_SANDSTONE_SLAB to Items.SMOOTH_RED_SANDSTONE,
        Items.QUARTZ_SLAB to Items.QUARTZ_BLOCK,
        Items.SMOOTH_QUARTZ_SLAB to Items.SMOOTH_QUARTZ,
        Items.PURPUR_SLAB to Items.PURPUR_BLOCK,
        Items.PRISMARINE_SLAB to Items.PRISMARINE,
        Items.PRISMARINE_BRICK_SLAB to Items.PRISMARINE_BRICKS,
        Items.DARK_PRISMARINE_SLAB to Items.DARK_PRISMARINE,
        Items.BLACKSTONE_SLAB to Items.BLACKSTONE,
        Items.POLISHED_BLACKSTONE_SLAB to Items.POLISHED_BLACKSTONE,
        Items.POLISHED_BLACKSTONE_BRICK_SLAB to Items.POLISHED_BLACKSTONE_BRICKS,
        Items.SMOOTH_STONE_SLAB to Items.SMOOTH_STONE
    )

    override fun matches(inv: CraftingInventory, world: World): Boolean {
        var match = false
        slabs.forEach { (recipeItem, _) ->
            val list = listOf(
                Ingredient(items = listOf(recipeItem)), Ingredient(items = listOf(recipeItem))
            )
            if (RecipeUtils.isItemShapedMatches(inv, 2, 1, list)) {
                match = true
                return@forEach
            }
        }
        return match
    }

    override fun craft(inv: CraftingInventory): ItemStack {
        var item = ItemStack.EMPTY
        slabs.forEach { (recipeItem, result) ->
            val list = listOf(
                Ingredient(items = listOf(recipeItem)), Ingredient(items = listOf(recipeItem))
            )
            if (RecipeUtils.isItemShapedMatches(inv, 2, 1, list)) {
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

    override fun getOutput() = Items.OAK_PLANKS.defaultStack

    override fun getId() = Identifier("galaxy", "easy_recipe/slab_restore")

    override fun getSerializer(): RecipeSerializer<*> {
        TODO("Not yet implemented, support client mod.")
    }
}
