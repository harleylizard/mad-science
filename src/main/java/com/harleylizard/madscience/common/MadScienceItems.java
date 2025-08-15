package com.harleylizard.madscience.common;

import com.harleylizard.madscience.common.item.EmptySyringeItem;
import com.harleylizard.madscience.common.item.SpoilingItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public final class MadScienceItems {
    public static final Item EMPTY_SYRINGE = new EmptySyringeItem(new Item.Properties());
    public static final Item DIRTY_SYRINGE = new Item(new Item.Properties());
    public static final Item SYRINGE_OF_CHICKEN_DNA = syringeOfDna();
    public static final Item SYRINGE_OF_COW_DNA = syringeOfDna();
    public static final Item SYRINGE_OF_PIG_DNA = syringeOfDna();
    public static final Item SYRINGE_OF_SHEEP_DNA = syringeOfDna();

    public static final Item EMPTY_DATA_REEL = new Item(new Item.Properties());
    public static final Item SEQUENCED_CHICKEN_GENOME = sequencedGenome();
    public static final Item SEQUENCED_COW_GENOME = sequencedGenome();
    public static final Item SEQUENCED_PIG_GENOME = sequencedGenome();
    public static final Item SEQUENCED_SHEEP_GENOME = sequencedGenome();

    public static void register() {
        register("empty_syringe", EMPTY_SYRINGE);
        register("dirty_syringe", DIRTY_SYRINGE);
        register("syringe_of_chicken_dna", SYRINGE_OF_CHICKEN_DNA);
        register("syringe_of_cow_dna", SYRINGE_OF_COW_DNA);
        register("syringe_of_pig_dna", SYRINGE_OF_PIG_DNA);
        register("syringe_of_sheep_dna", SYRINGE_OF_SHEEP_DNA);
        register("empty_data_reel", EMPTY_DATA_REEL);
        register("sequenced_chicken_genome", SEQUENCED_CHICKEN_GENOME);
        register("sequenced_cow_genome", SEQUENCED_COW_GENOME);
        register("sequenced_pig_genome", SEQUENCED_PIG_GENOME);
        register("sequenced_sheep_genome", SEQUENCED_SHEEP_GENOME);
    }

    private static void register(String name, Item item) {
        Registry.register(BuiltInRegistries.ITEM, MadScience.resourceLocation(name), item);
    }

    private static Item syringeOfDna() {
        return new SpoilingItem(new Item.Properties().stacksTo(1), DIRTY_SYRINGE);
    }

    private static Item sequencedGenome() {
        return new Item(new Item.Properties().stacksTo(1));
    }
}
