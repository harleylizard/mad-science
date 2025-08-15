package com.harleylizard.madscience.common;

import com.harleylizard.madscience.common.spoilage.Spoilage;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;

public final class MadScienceComponents {
    public static final DataComponentType<Spoilage> SPOILAGE = DataComponentType.<Spoilage>builder().persistent(Spoilage.CODEC).networkSynchronized(Spoilage.STREAM_CODEC).build();

    public static void register() {
        register("spoilage", SPOILAGE);
    }

    private static void register(String name, DataComponentType<?> type) {
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, MadScience.resourceLocation(name), type);
    }
}
