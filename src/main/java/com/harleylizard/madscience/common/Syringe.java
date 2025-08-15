package com.harleylizard.madscience.common;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public record Syringe(EntityType<?> entity, Item item, int priority) implements Comparable<Syringe> {
    public static final Codec<Syringe> CODEC = RecordCodecBuilder.create(builder -> builder.group(BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity").forGetter(Syringe::entity), BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(Syringe::item), Codec.INT.optionalFieldOf("priority", 1).forGetter(Syringe::priority)).apply(builder, Syringe::new));

    public static final PriorityMap<EntityType<?>, Syringe> SYRINGES = PriorityMap.of();

    @Override
    public int compareTo(@NotNull Syringe o) {
        return Integer.compare(priority, o.priority);
    }
}
