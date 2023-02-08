package net.guryavkin.drugsmodguryavkin.block;

import net.guryavkin.drugsmodguryavkin.DrugsModGuryavkin;
import net.guryavkin.drugsmodguryavkin.block.custom.*;
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

    public static final RegistryObject<Block> WEED_FLOWER = registerBlock("weed_flower_wild",
            () -> new WeedFlowerBlock(
                    () -> new WeedEffect(MobEffectCategory.NEUTRAL, MobEffects.POISON.getColor()),
                    10,
                    BlockBehaviour.Properties.copy(Blocks.TALL_GRASS)),
            WeedBlock.WEED_FLOWER_PROPERTIES
    );

    public static final RegistryObject<Block> WEED_FLOWER_POTTED = BLOCKS.register("weed_flower_potted",
            () -> new WeedFlowerPotBlock(
                    () -> (FlowerPotBlock)Blocks.FLOWER_POT,
                    ModBlocks.WEED_FLOWER,
                    BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)
            )
    );

    public static final RegistryObject<Block> GASHNSH_BLOCK = registerBlock("gashnsh_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.GRASS).sound(SoundType.MOSS).strength(1f)),
                    new Item.Properties().tab(DrugsModGuryavkin.DRUGSTAB)
    );

    public static final RegistryObject<Block> GASHNSH_BRICKS = registerBlock("gashnsh_bricks",
            () -> new Block(BlockBehaviour.Properties.of(Material.GRASS).sound(SoundType.STONE).strength(1f)),
            new Item.Properties().tab(DrugsModGuryavkin.DRUGSTAB)
    );

    public static final RegistryObject<Block> GASHNSH_BRICKS_SMALL = registerBlock("gashnsh_bricks_small",
            () -> new Block(BlockBehaviour.Properties.of(Material.GRASS).sound(SoundType.STONE).strength(1f)),
            new Item.Properties().tab(DrugsModGuryavkin.DRUGSTAB)
    );

    public static final RegistryObject<Block> WEED_CAKE = BLOCKS.register("weed_cake",
            () -> new WeedCakeBlock(BlockBehaviour.Properties.of(Material.CAKE).sound(SoundType.MOSS_CARPET)));

    public static final RegistryObject<Block> WEED_BLACK_CAKE = BLOCKS.register("weed_black_candle_cake",
            () -> new WeedCakeCandleBlock(Blocks.BLACK_CANDLE, BlockBehaviour.Properties.of(Material.CAKE).sound(SoundType.MOSS_CARPET)));

    public static final RegistryObject<Block> WEED_BLUE_CAKE = BLOCKS.register("weed_blue_candle_cake",
            () -> new WeedCakeCandleBlock(Blocks.BLUE_CANDLE, BlockBehaviour.Properties.of(Material.CAKE).sound(SoundType.MOSS_CARPET)));

    public static final RegistryObject<Block> WEED_BROWN_CAKE = BLOCKS.register("weed_brown_candle_cake",
            () -> new WeedCakeCandleBlock(Blocks.BROWN_CANDLE, BlockBehaviour.Properties.of(Material.CAKE).sound(SoundType.MOSS_CARPET)));

    public static final RegistryObject<Block> WEED_CANDLE_CAKE = BLOCKS.register("weed_candle_cake",
            () -> new WeedCakeCandleBlock(Blocks.CANDLE, BlockBehaviour.Properties.of(Material.CAKE).sound(SoundType.MOSS_CARPET)));

    public static final RegistryObject<Block> WEED_CYAN_CAKE = BLOCKS.register("weed_cyan_candle_cake",
            () -> new WeedCakeCandleBlock(Blocks.CYAN_CANDLE, BlockBehaviour.Properties.of(Material.CAKE).sound(SoundType.MOSS_CARPET)));

    public static final RegistryObject<Block> WEED_GREY_CAKE = BLOCKS.register("weed_gray_candle_cake",
            () -> new WeedCakeCandleBlock(Blocks.GRAY_CANDLE, BlockBehaviour.Properties.of(Material.CAKE).sound(SoundType.MOSS_CARPET)));

    public static final RegistryObject<Block> WEED_GREEN_CAKE = BLOCKS.register("weed_green_candle_cake",
            () -> new WeedCakeCandleBlock(Blocks.GREEN_CANDLE, BlockBehaviour.Properties.of(Material.CAKE).sound(SoundType.MOSS_CARPET)));

    public static final RegistryObject<Block> WEED_LIGHTBLUE_CAKE = BLOCKS.register("weed_light_blue_candle_cake",
            () -> new WeedCakeCandleBlock(Blocks.LIGHT_BLUE_CANDLE, BlockBehaviour.Properties.of(Material.CAKE).sound(SoundType.MOSS_CARPET)));

    public static final RegistryObject<Block> WEED_LIGHTGREY_CAKE = BLOCKS.register("weed_light_gray_candle_cake",
            () -> new WeedCakeCandleBlock(Blocks.LIGHT_GRAY_CANDLE, BlockBehaviour.Properties.of(Material.CAKE).sound(SoundType.MOSS_CARPET)));

    public static final RegistryObject<Block> WEED_LIME_CAKE = BLOCKS.register("weed_lime_candle_cake",
            () -> new WeedCakeCandleBlock(Blocks.LIME_CANDLE, BlockBehaviour.Properties.of(Material.CAKE).sound(SoundType.MOSS_CARPET)));

    public static final RegistryObject<Block> WEED_MAGENTA_CAKE = BLOCKS.register("weed_magenta_candle_cake",
            () -> new WeedCakeCandleBlock(Blocks.MAGENTA_CANDLE, BlockBehaviour.Properties.of(Material.CAKE).sound(SoundType.MOSS_CARPET)));

    public static final RegistryObject<Block> WEED_ORANGE_CAKE = BLOCKS.register("weed_orange_candle_cake",
            () -> new WeedCakeCandleBlock(Blocks.ORANGE_CANDLE, BlockBehaviour.Properties.of(Material.CAKE).sound(SoundType.MOSS_CARPET)));

    public static final RegistryObject<Block> WEED_PINK_CAKE = BLOCKS.register("weed_pink_candle_cake",
            () -> new WeedCakeCandleBlock(Blocks.PINK_CANDLE, BlockBehaviour.Properties.of(Material.CAKE).sound(SoundType.MOSS_CARPET)));

    public static final RegistryObject<Block> WEED_PURPLE_CAKE = BLOCKS.register("weed_purple_candle_cake",
            () -> new WeedCakeCandleBlock(Blocks.PURPLE_CANDLE, BlockBehaviour.Properties.of(Material.CAKE).sound(SoundType.MOSS_CARPET)));

    public static final RegistryObject<Block> WEED_RED_CAKE = BLOCKS.register("weed_red_candle_cake",
            () -> new WeedCakeCandleBlock(Blocks.RED_CANDLE, BlockBehaviour.Properties.of(Material.CAKE).sound(SoundType.MOSS_CARPET)));

    public static final RegistryObject<Block> WEED_WHITE_CAKE = BLOCKS.register("weed_white_candle_cake",
            () -> new WeedCakeCandleBlock(Blocks.WHITE_CANDLE, BlockBehaviour.Properties.of(Material.CAKE).sound(SoundType.MOSS_CARPET)));

    public static final RegistryObject<Block> WEED_YELLOW_CAKE = BLOCKS.register("weed_yellow_candle_cake",
            () -> new WeedCakeCandleBlock(Blocks.YELLOW_CANDLE, BlockBehaviour.Properties.of(Material.CAKE).sound(SoundType.MOSS_CARPET)));

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
