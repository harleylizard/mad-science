package com.harleylizard.madscience.common;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;

public final class MadScienceComponents {
    public static final DataComponentType<Integer> SPOILAGE = DataComponentType.<Integer>builder().persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build();

    public static void register() {
        register("spoilage", SPOILAGE);
    }

    private static void register(String name, DataComponentType<?> type) {
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, MadScience.resourceLocation(name), type);
    }
}
