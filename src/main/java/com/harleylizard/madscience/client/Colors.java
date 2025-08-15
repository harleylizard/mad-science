package com.harleylizard.madscience.client;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntLists;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public record Colors(List<Item> items, IntList colors) {
    public static final Codec<Colors> CODEC = RecordCodecBuilder.create(builder -> builder.group(BuiltInRegistries.ITEM.byNameCodec().listOf().fieldOf("items").forGetter(Colors::items), Codec.INT.listOf().xmap(Colors::asIntList, Function.identity()).fieldOf("colors").forGetter(Colors::colors)).apply(builder, Colors::new));

    public static final Map<Item, Colors> COLORS = new HashMap<>();

    public static IntList asIntList(List<Integer> list) {
        return IntLists.unmodifiable(new IntArrayList(list));
    }
}
