package net.guryavkin.drugsmodguryavkin.block.custom;

import net.guryavkin.drugsmodguryavkin.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.lighting.LightEventListener;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WeedBlock extends CropBlock
{
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D),
            Block.box(6.0D, 0.0D, 6.0D, 11.0D, 6.0D, 11.0D),
            Block.box(3.0D, 0.0D, 3.0D, 13.0D, 10.0D, 13.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 24.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 32.0D, 16.0D)};

    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 5);

    public WeedBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState p_52297_, BlockGetter p_52298_, BlockPos p_52299_, CollisionContext p_52300_) {
        return SHAPE_BY_AGE[p_52297_.getValue(this.getAgeProperty())];
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader reader, BlockPos blockPos) 
    {
        if (!reader.isAreaLoaded(blockPos, 1)) return false;
        boolean isAirAbove = isAir(reader.getBlockState(blockPos.above()));
        return super.canSurvive(state, reader, blockPos) && isAirAbove;
    }

    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0, 0.0, 0.0, 1.0, 2.0, 1.0), BooleanOp.OR);
        return shape;
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
        return 5;
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
