package com.harleylizard.madscience.common.item;

import com.harleylizard.madscience.common.MadScienceSounds;
import com.harleylizard.madscience.common.Syringe;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class EmptySyringeItem extends Item {

    public EmptySyringeItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity2) {
        return true;
    }

    @Override
    public void postHurtEnemy(ItemStack itemStack, LivingEntity entity, LivingEntity attacker) {
        var level = entity.level();
        if (!level.isClientSide && attacker instanceof Player player) {
            var syringes = Syringe.SYRINGES;

            var type = entity.getType();
            if (syringes.has(type)) {
                var syringe = syringes.get(type);

                var item = syringe.item().getDefaultInstance();

                var position = attacker.position();
                var x = position.x;
                var y = position.y;
                var z = position.z;

                var inventory = player.getInventory();
                var slot = inventory.getFreeSlot();

                if (slot > -1 && inventory.add(item)) {
                    level.playSound(null, x, y, z, MadScienceSounds.STAB, SoundSource.PLAYERS, 1.0f, 1.0f);
                } else {
                    Containers.dropItemStack(level, x, y, z, item);
                }

                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
            }
        }
    }
}
