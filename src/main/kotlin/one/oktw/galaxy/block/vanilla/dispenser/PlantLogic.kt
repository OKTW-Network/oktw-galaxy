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

package one.oktw.galaxy.block.vanilla.dispenser

import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.Material
import net.minecraft.tag.FluidTags
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

object PlantLogic {
    fun sugarCanePlantCheck(validBlocksToPlantOn: List<Block>, block: Block, world: World, blockPos: BlockPos): Boolean {
        val blockState = world.getBlockState(blockPos)

        if (blockState.block == Blocks.SUGAR_CANE) {
            return true
        } else {
            if (validBlocksToPlantOn.contains(block)) {
                val sugarCanePlant = Direction.Type.HORIZONTAL.iterator()

                while (sugarCanePlant.hasNext()) {
                    val direction = sugarCanePlant.next()
                    val plantState = world.getBlockState(blockPos.offset(direction))
                    val fluidState = world.getFluidState(blockPos.offset(direction))

                    if (fluidState.isIn(FluidTags.WATER) || plantState.isOf(Blocks.FROSTED_ICE)) {
                        return true
                    }
                }
            }

            return false
        }
    }

    fun cactusPlantCheck(validBlocksToPlantOn: List<Block>, block: Block, world: World, blockPos: BlockPos): Boolean {
        val cactusPlant = Direction.Type.HORIZONTAL.iterator()

        var direction: Direction?
        var material: Material
        do {
            if (!cactusPlant.hasNext()) {
                return (validBlocksToPlantOn.contains(block) && !world.getBlockState(blockPos).material.isLiquid)
            }
            direction = cactusPlant.next()
            val blockState = world.getBlockState(blockPos.offset(direction))
            material = blockState.material
        } while (!material.isSolid && !world.getFluidState(blockPos.offset(direction)).isIn(FluidTags.LAVA))

        return false
    }
}
