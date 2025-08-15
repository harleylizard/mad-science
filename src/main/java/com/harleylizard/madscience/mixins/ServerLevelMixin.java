package com.harleylizard.madscience.mixins;

import com.harleylizard.madscience.common.spoilage.SpoilsContainer;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public final class ServerLevelMixin {

    @Inject(method = "tickChunk", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/LevelChunkSection;isRandomlyTicking()Z", shift = At.Shift.AFTER))
    public void mad_science$tickChunk(LevelChunk chunk, int i, CallbackInfo ci, @Local(ordinal = 3) int m, @Local LevelChunkSection section) {
        if (section.maybeHas(blockState -> blockState.getBlock() instanceof SpoilsContainer)) {
            var pos = chunk.getPos();
            var blockPos = new BlockPos.MutableBlockPos();
            for (var j = 0; j < 16 * 16 * 16; j++) {
                var x = (j % 16) + (pos.x << 4);
                var y = ((j / 16) % 16) + (chunk.getSectionYFromSectionIndex(m) << 4);
                var z = (j / (16 * 16)) + (pos.z << 4);

                if (section.getBlockState(x & 0xF, y & 0xF, z & 0xF).getBlock() instanceof SpoilsContainer container) {
                    container.mad_science$tick((ServerLevel) (Object) this, blockPos.set(x, y, z).immutable());
                }
            }
        }
    }
}
