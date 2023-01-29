package net.guryavkin.drugsmodguryavkin;

import com.mojang.logging.LogUtils;
import net.guryavkin.drugsmodguryavkin.block.ModBlocks;
import net.guryavkin.drugsmodguryavkin.effect.ModEffects;
import net.guryavkin.drugsmodguryavkin.item.ModItems;
import net.guryavkin.drugsmodguryavkin.networking.ModMessages;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DrugsModGuryavkin.MOD_ID)
public class DrugsModGuryavkin
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "drugsmodguryavkin";
    public static final Logger LOGGER = LogUtils.getLogger();

    public DrugsModGuryavkin()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.Register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEffects.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
                ModMessages.register();
                LOGGER.info("ENQUEUED WORK");
            }
        );
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }

    public static final CreativeModeTab DRUGSTAB = new CreativeModeTab("drugstab") {
        @Override
        @NotNull public ItemStack makeIcon() {
            return ModItems.WEED.get().getDefaultInstance();
        }
    };

    @SubscribeEvent
    public static void CreateTab()
    {

    }
}
