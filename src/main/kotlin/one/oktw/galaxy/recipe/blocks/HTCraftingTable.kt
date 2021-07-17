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

package one.oktw.galaxy.recipe.blocks

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.inventory.CraftingInventory
import net.minecraft.item.Items
import net.minecraft.recipe.CraftingRecipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.util.Identifier
import net.minecraft.world.World
import one.oktw.galaxy.item.CustomBlockItem
import one.oktw.galaxy.recipe.utils.Ingredient
import one.oktw.galaxy.recipe.utils.RecipeUtils

class HTCraftingTable : CraftingRecipe {
    private val item = CustomBlockItem.HT_CRAFTING_TABLE.createItemStack()

    private val redStone = Ingredient(items = listOf(Items.REDSTONE))
    private val diamond = Ingredient(items = listOf(Items.DIAMOND))
    private val lapisLazuli = Ingredient(items = listOf(Items.LAPIS_LAZULI))
    private val ironIngot = Ingredient(items = listOf(Items.IRON_INGOT))
    private val obsidian = Ingredient(items = listOf(Items.OBSIDIAN))
    private val craftingTable = Ingredient(items = listOf(Items.CRAFTING_TABLE))
    private val list = listOf(
        redStone, diamond, lapisLazuli,
        ironIngot, craftingTable, ironIngot,
        lapisLazuli, obsidian, redStone
    )

    override fun matches(inv: CraftingInventory, world: World): Boolean = RecipeUtils.isItemShapedMatches(inv, 3, 3, list)

    override fun craft(inv: CraftingInventory) = item.copy()

    @Environment(EnvType.CLIENT)
    override fun fits(width: Int, height: Int): Boolean {
        throw NotImplementedError()
    }

    override fun getOutput() = item

    override fun getId() = Identifier("galaxy", "block/htct")

    override fun getSerializer(): RecipeSerializer<*> {
        TODO("Not yet implemented, support client mod.")
    }
}
