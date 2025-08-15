package com.harleylizard.madscience.mixins;

import com.harleylizard.madscience.common.spoilage.Spoils;
import com.harleylizard.madscience.common.spoilage.SpoilsContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BaseEntityBlock.class)
public final class BaseEntityBlockMixin implements SpoilsContainer {

    @Override
    public void mad_science$tick(Level level, BlockPos blockPos) {
        var blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof BaseContainerBlockEntity container) {
            for (var i = 0; i < container.getContainerSize(); i++) {
                var itemStack = container.getItem(i);
                if (itemStack.getItem() instanceof Spoils spoils) {
                    spoils.tick(itemStack, container, level);
                }
            }
        }
    }
}
