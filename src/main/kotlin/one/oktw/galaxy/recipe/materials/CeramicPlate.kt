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

package one.oktw.galaxy.recipe.materials

import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.SmeltingRecipe
import net.minecraft.util.Identifier
import one.oktw.galaxy.item.Material
import java.util.stream.Stream

class CeramicPlate :
    SmeltingRecipe(
        Identifier("galaxy", "material/ceramic_plate"),
        "",
        Ingredient.ofStacks(Stream.of(Material.RAW_BASE_PLATE.createItemStack())),
        Material.BASE_PLATE.createItemStack(),
        5.0F,
        200
    ) {

    override fun getSerializer(): RecipeSerializer<*> {
        TODO("Not yet implemented, support client mod.")
    }
}
