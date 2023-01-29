package net.guryavkin.drugsmodguryavkin.item;

import net.guryavkin.drugsmodguryavkin.DrugsModGuryavkin;
import net.minecraft.world.item.Item;
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
}
