package com.harleylizard.madscience.common;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public final class MadScience implements ModInitializer {
    private static final String MOD_ID = "mad-science";

    @Override
    public void onInitialize() {
        MadScienceBlocks.register();
        MadScienceItems.register();

        var itemGroup = FabricItemGroup.builder()
                .title(Component.translatable("itemGroup.mad-science"))
                .icon(MadScienceItems.EMPTY_SYRINGE::getDefaultInstance)
                .displayItems((parameters, output) -> {
                    output.accept(MadScienceItems.EMPTY_SYRINGE);
                    output.accept(MadScienceItems.DIRTY_SYRINGE);
                    output.accept(MadScienceItems.SYRINGE_OF_CHICKEN_DNA);
                    output.accept(MadScienceItems.SYRINGE_OF_COW_DNA);
                    output.accept(MadScienceItems.SYRINGE_OF_PIG_DNA);
                    output.accept(MadScienceItems.SYRINGE_OF_SHEEP_DNA);
                })
                .build();
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, resourceLocation(MOD_ID), itemGroup);

    }

    public static ResourceLocation resourceLocation(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
