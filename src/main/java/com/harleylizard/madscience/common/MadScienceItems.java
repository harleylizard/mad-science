package com.harleylizard.madscience.common;

import com.harleylizard.madscience.common.item.EmptySyringeItem;
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

    public static void register() {
        register("empty_syringe", EMPTY_SYRINGE);
        register("dirty_syringe", DIRTY_SYRINGE);
        register("syringe_of_chicken_dna", SYRINGE_OF_CHICKEN_DNA);
        register("syringe_of_cow_dna", SYRINGE_OF_COW_DNA);
        register("syringe_of_pig_dna", SYRINGE_OF_PIG_DNA);
        register("syringe_of_sheep_dna", SYRINGE_OF_SHEEP_DNA);
    }

    private static void register(String name, Item item) {
        Registry.register(BuiltInRegistries.ITEM, MadScience.resourceLocation(name), item);
    }

    private static Item syringeOfDna() {
        return new Item(new Item.Properties().stacksTo(1));
    }
}
