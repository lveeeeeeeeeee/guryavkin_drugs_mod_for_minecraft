package net.guryavkin.drugsmodguryavkin.block.custom;

import net.guryavkin.drugsmodguryavkin.DrugsModGuryavkin;
import net.guryavkin.drugsmodguryavkin.item.ModItems;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleGroup;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.lighting.LightEventListener;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.entity.player.BonemealEvent;

import java.util.concurrent.ThreadLocalRandom;

public class WeedBlock extends CropBlock
{
    public static final Item.Properties WEED_FLOWER_PROPERTIES =
            new Item.Properties().tab(DrugsModGuryavkin.DRUGSTAB).stacksTo(16);

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D),
            Block.box(6.0D, 0.0D, 6.0D, 11.0D, 6.0D, 11.0D),
            Block.box(3.0D, 0.0D, 3.0D, 13.0D, 10.0D, 13.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 24.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 32.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 32.0D, 16.0D)};

    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 6);

    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0, 0.0, 0.0, 1.0, 2.0, 1.0), BooleanOp.OR);
        return shape;
    }

    public WeedBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState p_52297_, BlockGetter p_52298_, BlockPos p_52299_, CollisionContext p_52300_) {
        return SHAPE_BY_AGE[p_52297_.getValue(this.getAgeProperty())];
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(!level.isLoaded(blockPos)) return InteractionResult.FAIL;
        if(!level.isClientSide)
        {
            if(state.getValue(AGE) == 6 && player.getItemInHand(InteractionHand.MAIN_HAND).getItem().asItem() != Items.BONE_MEAL)
            {
                level.setBlock(blockPos, state.setValue(AGE, 5), 3);
                player.spawnAtLocation(new ItemStack(ModItems.WEED_BUDS.get().asItem(), 1));
            }
            else if(player.getItemInHand(InteractionHand.MAIN_HAND).getItem().asItem() == Items.BONE_MEAL)
            {
                super.performBonemeal((ServerLevel) level, level.random, blockPos, state);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader reader, BlockPos blockPos)
    {
        if (!reader.isAreaLoaded(blockPos, 1)) return false;
        boolean isAirAbove = isAir(reader.getBlockState(blockPos.above()));
        boolean isLampAbove = (reader.getBlockState(blockPos.above().above()).getBlock() == Blocks.REDSTONE_LAMP);
        boolean isSeaLanternAbove = (reader.getBlockState(blockPos.above().above()).getBlock() == Blocks.SEA_LANTERN);
        boolean isShroomlightAbove = (reader.getBlockState(blockPos.above().above()).getBlock() == Blocks.SHROOMLIGHT);
        return (super.canSurvive(state, reader, blockPos) & isAirAbove) & (isLampAbove | isSeaLanternAbove | isShroomlightAbove);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.WEED_SEEDS.get();
    }


    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 6;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos blockPos, RandomSource randomSource) {
        if (!level.isAreaLoaded(blockPos, 1)) return;
        if (level.getRawBrightness(blockPos, 1) >= 12)
        {
            int i = this.getAge(state);
            if (i < this.getMaxAge())
            {
                float f = getGrowthSpeed(this, level, blockPos) * (level.getRawBrightness(blockPos, 1) - 11) / 2f;
                f = (state.getValue(AGE) == 5) ? f/2 : f;
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(level, blockPos, state, randomSource.nextInt((int)(25.0F / f) + 1) == 0)) {
                    level.setBlock(blockPos, this.getStateForAge(i + 1), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(level, blockPos, state);
                }
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
