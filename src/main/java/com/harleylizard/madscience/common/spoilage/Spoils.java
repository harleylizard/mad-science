package com.harleylizard.madscience.common.spoilage;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface Spoils {

    void tick(ItemStack itemStack, Container container, Level level);
}
