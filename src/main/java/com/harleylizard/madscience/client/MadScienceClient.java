package com.harleylizard.madscience.client;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.harleylizard.madscience.common.MadScience;
import com.harleylizard.madscience.common.MadScienceItems;
import com.mojang.serialization.JsonOps;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientWorldEvents;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.world.item.Item;

import java.io.IOException;

public final class MadScienceClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        var gson = new GsonBuilder().create();
        ClientWorldEvents.AFTER_CLIENT_WORLD_CHANGE.register((minecraft, level) -> {
            minecraft.execute(() -> {
                var colors = Colors.COLORS;
                colors.clear();

                var manager = minecraft.getResourceManager();

                for (var entry : MadScience.listJsons(manager, "colors/item")) {
                    try (var reader = entry.getValue().openAsReader()) {
                        var result = Colors.CODEC.parse(JsonOps.INSTANCE, gson.fromJson(reader, JsonElement.class));

                        if (result.isError()) {
                            continue;
                        }

                        var results = result.getOrThrow();
                        for (var item : results.items()) {
                            colors.put(item, results);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        });

        Item[] items = {
                MadScienceItems.SYRINGE_OF_CHICKEN_DNA,
                MadScienceItems.SYRINGE_OF_COW_DNA,
                MadScienceItems.SYRINGE_OF_PIG_DNA,
                MadScienceItems.SYRINGE_OF_SHEEP_DNA,
                MadScienceItems.SEQUENCED_CHICKEN_GENOME,
                MadScienceItems.SEQUENCED_COW_GENOME,
                MadScienceItems.SEQUENCED_PIG_GENOME,
                MadScienceItems.SEQUENCED_SHEEP_GENOME
        };
        ColorProviderRegistry.ITEM.register((itemStack, i) -> {
            var colors = Colors.COLORS.get(itemStack.getItem()).colors();
            return (i >= colors.size()) ? 0xFFFFFFFF : (colors.getInt(i) | (0xFF << 24));
        }, items);
    }
}
