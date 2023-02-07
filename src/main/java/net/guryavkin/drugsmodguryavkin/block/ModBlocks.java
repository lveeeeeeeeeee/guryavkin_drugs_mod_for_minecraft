package net.guryavkin.drugsmodguryavkin.block;

import net.guryavkin.drugsmodguryavkin.DrugsModGuryavkin;
import net.guryavkin.drugsmodguryavkin.block.custom.Bong;
import net.guryavkin.drugsmodguryavkin.block.custom.WeedBlock;
import net.guryavkin.drugsmodguryavkin.effect.ModEffects;
import net.guryavkin.drugsmodguryavkin.effect.WeedEffect;
import net.guryavkin.drugsmodguryavkin.item.ModItems;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(
            ForgeRegistries.BLOCKS,
            DrugsModGuryavkin.MOD_ID
            );

    public static final RegistryObject<Block> BONG = registerBlock("bong",
            () -> new Bong(BlockBehaviour.Properties.of(Material.STONE).dynamicShape().
                    noOcclusion().sound(SoundType.GLASS)),
            Bong.BONG_ITEM
    );

    public static final RegistryObject<Block> WEED_CROP = BLOCKS.register("weed_bush",
            () -> new WeedBlock(BlockBehaviour.Properties.of(Blocks.TALL_GRASS.defaultBlockState().getMaterial())
                    .noCollission().instabreak().sound(SoundType.GRASS))
            );

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, Item.Properties properties) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, properties);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            Item.Properties properties) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), properties));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }}
