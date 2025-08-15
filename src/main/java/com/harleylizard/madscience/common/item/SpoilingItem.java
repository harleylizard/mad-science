package com.harleylizard.madscience.common.item;

import com.harleylizard.madscience.common.MadScienceComponents;
import com.harleylizard.madscience.common.Util;
import com.harleylizard.madscience.common.spoilage.Spoils;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public final class SpoilingItem extends Item implements Spoils {
    public static final int SPOILAGE = 275;

    private final Item spoiled;

    public SpoilingItem(Properties properties, Item spoiled) {
        super(properties.component(MadScienceComponents.SPOILAGE, 0));
        this.spoiled = spoiled;
    }

    @Override
    public void tick(ItemStack itemStack, Container container, Level level) {
        tick(itemStack, container, spoiled, (int) level.getGameTime());
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean bl) {
        if (!level.isClientSide && entity instanceof Player player) {
            tick(itemStack, player.getInventory(), spoiled, entity.tickCount);
        }
    }

    public void tick(ItemStack itemStack, Container container, Item item, int time) {
        if (time % 20 == 0) {
            var component = MadScienceComponents.SPOILAGE;

            var spoilage = itemStack.get(component).intValue();
            if (spoilage < SPOILAGE) {
                spoilage++;
            } else if (replace(itemStack, container, item)) {
                itemStack.shrink(1);
            }
            itemStack.set(component, spoilage);
        }
    }

    @Override
    public boolean isBarVisible(ItemStack itemStack) {
        return spoilage(itemStack) > 0;
    }

    @Override
    public int getBarWidth(ItemStack itemStack) {
        var width = 13.0f;
        return (int) (width - percentage(itemStack) * width);
    }

    @Override
    public int getBarColor(ItemStack itemStack) {
        return Util.blend(0x4F98FF, 0x373A5B, percentage(itemStack)) | (0xFF << 24);
    }

    public float percentage(ItemStack itemStack) {
        return (float) spoilage(itemStack) / (float) SPOILAGE;
    }

    public int spoilage(ItemStack itemStack) {
        return itemStack.get(MadScienceComponents.SPOILAGE).intValue();
    }

    private static boolean replace(ItemStack itemStack, Container container, Item item) {
        for (var i = 0; i < container.getContainerSize(); i++) {
            if (container.getItem(i).equals(itemStack)) {
                container.setItem(i, item.getDefaultInstance());
                return true;
            }
        }
        return false;
    }
}
