package net.guryavkin.drugsmodguryavkin.item;

import net.guryavkin.drugsmodguryavkin.DrugsModGuryavkin;
import net.guryavkin.drugsmodguryavkin.block.ModBlocks;
import net.guryavkin.drugsmodguryavkin.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
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
            ))
    );

    public static final RegistryObject<Item> WEED_BUDS = ITEMS.register(
      "weed_buds",
            () -> new Item(new Item.Properties().tab(
                    DrugsModGuryavkin.DRUGSTAB
            ).stacksTo(16))
    );

    public static final RegistryObject<Item> WEED_SEEDS = ITEMS.register(
            "weed_seeds",
            () -> new ItemNameBlockItem(ModBlocks.WEED_CROP.get(), new Item.Properties().tab(DrugsModGuryavkin.DRUGSTAB)
                    .food(new FoodProperties.Builder().nutrition(1).saturationMod(1f).build()))
    );

    public static final RegistryObject<Item> WEED_CAKE = ITEMS.register("weed_cake",
            () -> new ItemNameBlockItem(ModBlocks.WEED_CAKE.get(),
                    new Item.Properties().tab(DrugsModGuryavkin.DRUGSTAB).stacksTo(1).rarity(Rarity.EPIC)
                            .craftRemainder(Items.BUCKET)));

    public static final RegistryObject<Item> WEED_COOKIE = ITEMS.register("weed_cookie",
            () -> new Item(new Item.Properties().tab(DrugsModGuryavkin.DRUGSTAB).food(
                    new FoodProperties.Builder().nutrition(4).saturationMod(3f)
                            .effect(
            () -> new MobEffectInstance(ModEffects.WEED.get(), 1200, 1, false, false),
                                    0.9f
                            ).build()
            ).stacksTo(16).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> GASHNSH = ITEMS.register(
            "gashnsh",
            () -> new Item(new Item.Properties().tab(DrugsModGuryavkin.DRUGSTAB))
    );
}
