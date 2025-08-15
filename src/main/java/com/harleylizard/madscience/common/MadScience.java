package com.harleylizard.madscience.common;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public final class MadScience implements ModInitializer {
    private static final String MOD_ID = "mad-science";

    @Override
    public void onInitialize() {
        MadScienceBlocks.register();
        MadScienceItems.register();
        MadScienceComponents.register();

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
                    output.accept(MadScienceItems.EMPTY_DATA_REEL);
                    output.accept(MadScienceItems.SEQUENCED_CHICKEN_GENOME);
                    output.accept(MadScienceItems.SEQUENCED_COW_GENOME);
                    output.accept(MadScienceItems.SEQUENCED_PIG_GENOME);
                    output.accept(MadScienceItems.SEQUENCED_SHEEP_GENOME);
                })
                .build();
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, resourceLocation(MOD_ID), itemGroup);

        var logger = LoggerFactory.getLogger(MOD_ID);

        var gson = new GsonBuilder().create();
        ServerWorldEvents.LOAD.register((server, level) -> {
            var syringes = Syringe.SYRINGES;
            syringes.clear();

            var manager = server.getResourceManager();
            for (var entry : listJsons(manager, "entities/syringe")) {
                try (var reader = entry.getValue().openAsReader()) {
                    var result = Syringe.CODEC.parse(JsonOps.INSTANCE, gson.fromJson(reader, JsonElement.class));

                    if (result.isError()) {
                        logger.error(result.error().orElseThrow().message());
                        continue;
                    }

                    var syringe = result.getOrThrow();
                    syringes.add(syringe.entity(), syringe);
                } catch (IOException | NullPointerException e) {
                    logger.error("Failed to parse", e);
                }
            }
        });
    }

    public static Set<Map.Entry<ResourceLocation, Resource>> listJsons(ResourceManager manager, String path) {
        return manager.listResources(path, file -> file.toString().endsWith(".json")).entrySet();
    }

    public static ResourceLocation resourceLocation(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
