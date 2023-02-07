package net.guryavkin.drugsmodguryavkin.item;

import net.guryavkin.drugsmodguryavkin.DrugsModGuryavkin;
import net.guryavkin.drugsmodguryavkin.block.ModBlocks;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static void Register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }

    public static final DeferredRegister ITEMS = DeferredRegister.create(
            ForgeRegistries.ITEMS,
            DrugsModGuryavkin.MOD_ID
    );

    public static final RegistryObject<Item> WEED = ITEMS.register(
            "weed",
            () -> new Item(new Item.Properties().tab(
                    DrugsModGuryavkin.DRUGSTAB
            ).stacksTo(16)
            )
    );

    public static final RegistryObject<Item> WEED_SEEDS = ITEMS.register(
            "weed_seeds",
            () -> new ItemNameBlockItem(ModBlocks.WEED_CROP.get(), new Item.Properties().tab(DrugsModGuryavkin.DRUGSTAB)
                    .food(new FoodProperties.Builder().nutrition(1).saturationMod(1f).build()))
    );
}
