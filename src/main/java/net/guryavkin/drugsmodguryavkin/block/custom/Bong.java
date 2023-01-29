package net.guryavkin.drugsmodguryavkin.block.custom;

import net.guryavkin.drugsmodguryavkin.DrugsModGuryavkin;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.concurrent.ThreadLocalRandom;

public class Bong extends HorizontalDirectionalBlock
{
    public static Item.Properties BONG_ITEM = new Item.Properties().
            tab(DrugsModGuryavkin.DRUGSTAB)
            .stacksTo(1);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public Bong(Properties properties)
    {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource source) {
        float chance = 0.6f;
        if (chance < ThreadLocalRandom.current().nextFloat())
        {
            level.addParticle(ParticleTypes.BUBBLE,
                    pos.getX()+0.5d+ThreadLocalRandom.current().nextFloat(-0.1f,0.1f),
                    pos.getY()+0.6d+ThreadLocalRandom.current().nextFloat(0.1f),
                    pos.getZ()+0.5d+ThreadLocalRandom.current().nextFloat(-0.1f,0.1f),
                    ThreadLocalRandom.current().nextFloat(0.1f)*0.005d,
                    ThreadLocalRandom.current().nextFloat(0.1f)+0.001d,
                    ThreadLocalRandom.current().nextFloat(0.1f)*0.005d
                    );
            level.addParticle(ParticleTypes.FLAME,
                    pos.getX()+0.5d+ThreadLocalRandom.current().nextFloat(-0.1f,0.1f),
                    pos.getY()+0.6d+ThreadLocalRandom.current().nextFloat(0.1f),
                    pos.getZ()+0.5d+ThreadLocalRandom.current().nextFloat(-0.1f,0.1f),
                    ThreadLocalRandom.current().nextFloat(0.1f)*0.005d,
                    ThreadLocalRandom.current().nextFloat(0.1f)+0.001d,
                    ThreadLocalRandom.current().nextFloat(0.1f)*0.005d
                    );
            level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    pos.getX()+0.5d+ThreadLocalRandom.current().nextFloat(-0.1f,0.1f),
                    pos.getY()+0.6d+ThreadLocalRandom.current().nextFloat(0.1f),
                    pos.getZ()+0.5d+ThreadLocalRandom.current().nextFloat(-0.1f,0.1f),
                    ThreadLocalRandom.current().nextFloat(0.1f)*0.005d,
                    ThreadLocalRandom.current().nextFloat(0.1f)+0.001d,
                    ThreadLocalRandom.current().nextFloat(0.1f)*0.005d
                    );
        }
    }

    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.0, 0.25, 0.6875, 0.69, 0.6875), BooleanOp.OR);
        return shape;
    }

    private static final VoxelShape SHAPE = makeShape();

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
