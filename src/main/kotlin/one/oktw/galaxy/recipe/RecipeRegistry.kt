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

package one.oktw.galaxy.recipe

import net.minecraft.recipe.RecipeType
import one.oktw.galaxy.mixin.interfaces.CustomRecipeManager
import one.oktw.galaxy.recipe.blocks.Elevator
import one.oktw.galaxy.recipe.blocks.HTCraftingTable
import one.oktw.galaxy.recipe.easyRecipe.*
import one.oktw.galaxy.recipe.materials.CeramicPlate
import one.oktw.galaxy.recipe.tools.Wrench

object RecipeRegistry {
    fun register() {
        // Recipe
        CustomRecipeManager.addRecipe(RecipeType.CRAFTING, Wrench())
        CustomRecipeManager.addRecipe(RecipeType.CRAFTING, Elevator())
        CustomRecipeManager.addRecipe(RecipeType.CRAFTING, HTCraftingTable())
        CustomRecipeManager.addRecipe(RecipeType.SMELTING, CeramicPlate())
        // Easy Recipe
        CustomRecipeManager.addRecipe(RecipeType.CRAFTING, Ladder())
        CustomRecipeManager.addRecipe(RecipeType.CRAFTING, Chest())
        CustomRecipeManager.addRecipe(RecipeType.CRAFTING, Hopper())
        CustomRecipeManager.addRecipe(RecipeType.CRAFTING, RedstoneLamp())
        CustomRecipeManager.addRecipe(RecipeType.CRAFTING, Dispenser())
        CustomRecipeManager.addRecipe(RecipeType.CRAFTING, DispenserWithBow())
        CustomRecipeManager.addRecipe(RecipeType.CRAFTING, RedstoneRepeater())
        CustomRecipeManager.addRecipe(RecipeType.CRAFTING, TrappedChest())
        CustomRecipeManager.addRecipe(RecipeType.CRAFTING, CarrotOnAStick())
        CustomRecipeManager.addRecipe(RecipeType.CRAFTING, WarpedFungusOnAStick())
        CustomRecipeManager.addRecipe(RecipeType.CRAFTING, BookAndQuill())
        CustomRecipeManager.addRecipe(RecipeType.CRAFTING, Minecart())
        CustomRecipeManager.addRecipe(RecipeType.CRAFTING, Stick())
        CustomRecipeManager.addRecipe(RecipeType.CRAFTING, Glass())
        CustomRecipeManager.addRecipe(RecipeType.CRAFTING, GlassPane())
        CustomRecipeManager.addRecipe(RecipeType.CRAFTING, GlassPaneRestore())
        CustomRecipeManager.addRecipe(RecipeType.CRAFTING, WoodenSlab())
        CustomRecipeManager.addRecipe(RecipeType.CRAFTING, SlabRestore())
    }
}
