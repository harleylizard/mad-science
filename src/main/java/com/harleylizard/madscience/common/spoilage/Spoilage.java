package com.harleylizard.madscience.common.spoilage;

import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record Spoilage(int spoilage) {
    public static final Codec<Spoilage> CODEC = Codec.INT.xmap(Spoilage::new, Spoilage::spoilage);

    public static final StreamCodec<FriendlyByteBuf, Spoilage> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.INT, Spoilage::spoilage, Spoilage::new);

    public static final int SPOILAGE = 275;

    public Spoilage tick(ItemStack itemStack, Container container, Item item) {
        var result = spoilage;
        if (result < SPOILAGE) {
            result++;
        } else if (replace(itemStack, container, item)) {
            itemStack.shrink(1);
        }
        return new Spoilage(result);
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
