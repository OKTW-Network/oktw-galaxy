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

class GlassPane : CraftingRecipe {
    private val dyes = hashMapOf(
        Items.WATER_BUCKET to Items.GLASS_PANE,
        Items.RED_DYE to Items.RED_STAINED_GLASS_PANE,
        Items.GREEN_DYE to Items.GREEN_STAINED_GLASS_PANE,
        Items.PURPLE_DYE to Items.PURPLE_STAINED_GLASS_PANE,
        Items.CYAN_DYE to Items.CYAN_STAINED_GLASS_PANE,
        Items.LIGHT_GRAY_DYE to Items.LIGHT_GRAY_STAINED_GLASS_PANE,
        Items.GRAY_DYE to Items.GRAY_STAINED_GLASS_PANE,
        Items.PINK_DYE to Items.PINK_STAINED_GLASS_PANE,
        Items.LIME_DYE to Items.LIME_STAINED_GLASS_PANE,
        Items.YELLOW_DYE to Items.YELLOW_STAINED_GLASS_PANE,
        Items.LIGHT_BLUE_DYE to Items.LIGHT_BLUE_STAINED_GLASS_PANE,
        Items.MAGENTA_DYE to Items.MAGENTA_STAINED_GLASS_PANE,
        Items.ORANGE_DYE to Items.ORANGE_STAINED_GLASS_PANE,
        Items.BLACK_DYE to Items.BLACK_STAINED_GLASS_PANE,
        Items.BROWN_DYE to Items.BROWN_STAINED_GLASS_PANE,
        Items.BLUE_DYE to Items.BLUE_STAINED_GLASS_PANE,
        Items.WHITE_DYE to Items.WHITE_STAINED_GLASS_PANE
    )

    private val stainedGlassPane = listOf(
        Items.WHITE_STAINED_GLASS_PANE,
        Items.ORANGE_STAINED_GLASS_PANE,
        Items.MAGENTA_STAINED_GLASS_PANE,
        Items.LIGHT_BLUE_STAINED_GLASS_PANE,
        Items.YELLOW_STAINED_GLASS_PANE,
        Items.LIME_STAINED_GLASS_PANE,
        Items.PINK_STAINED_GLASS_PANE,
        Items.GRAY_STAINED_GLASS_PANE,
        Items.LIGHT_GRAY_STAINED_GLASS_PANE,
        Items.CYAN_STAINED_GLASS_PANE,
        Items.PURPLE_STAINED_GLASS_PANE,
        Items.BLUE_STAINED_GLASS_PANE,
        Items.BROWN_STAINED_GLASS_PANE,
        Items.GREEN_STAINED_GLASS_PANE,
        Items.RED_STAINED_GLASS_PANE,
        Items.BLACK_STAINED_GLASS_PANE
    )

    override fun matches(inv: CraftingInventory, world: World): Boolean {
        var match = false
        dyes.forEach { (recipeItem, _) ->
            val glassPane = Ingredient(items = stainedGlassPane)
            val dye = Ingredient(items = listOf(recipeItem))
            val list = listOf(
                glassPane, glassPane, glassPane,
                glassPane, dye, glassPane,
                glassPane, glassPane, glassPane,
            )
            if (RecipeUtils.isItemShapedMatches(inv, 3, 3, list)) {
                match = true
                return@forEach
            }
        }
        return match
    }

    override fun craft(inv: CraftingInventory): ItemStack {
        var item = ItemStack.EMPTY
        dyes.forEach { (recipeItem, result) ->
            val glassPane = Ingredient(items = stainedGlassPane)
            val dye = Ingredient(items = listOf(recipeItem))
            val list = listOf(
                glassPane, glassPane, glassPane,
                glassPane, dye, glassPane,
                glassPane, glassPane, glassPane,
            )
            if (RecipeUtils.isItemShapedMatches(inv, 3, 3, list)) {
                item = result.defaultStack.apply { this.count = 8 }
                return@forEach
            }
        }
        return item
    }

    @Environment(EnvType.CLIENT)
    override fun fits(width: Int, height: Int): Boolean {
        throw NotImplementedError()
    }

    override fun getOutput() = Items.GLASS_PANE.defaultStack.apply { this.count = 8 }

    override fun getId() = Identifier("galaxy", "easy_recipe/glass_pane")

    override fun getSerializer(): RecipeSerializer<*> {
        TODO("Not yet implemented, support client mod.")
    }
}
