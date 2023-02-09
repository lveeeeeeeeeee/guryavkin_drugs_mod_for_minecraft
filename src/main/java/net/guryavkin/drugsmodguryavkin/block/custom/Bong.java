package net.guryavkin.drugsmodguryavkin.block.custom;

import net.guryavkin.drugsmodguryavkin.DrugsModGuryavkin;
import net.guryavkin.drugsmodguryavkin.block.ModBlocks;
import net.guryavkin.drugsmodguryavkin.effect.ModEffects;
import net.guryavkin.drugsmodguryavkin.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.concurrent.ThreadLocalRandom;

public class Bong extends HorizontalDirectionalBlock
{
    public static Item.Properties BONG_ITEM = new Item.Properties().
            tab(DrugsModGuryavkin.DRUGSTAB)
            .stacksTo(1)
            .rarity(Rarity.RARE);

    public static final IntegerProperty BONG_STATE = IntegerProperty.create("bong_state", 0, 1);

    @Override
    public InteractionResult use(BlockState block, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        if (!level.isClientSide())
        {
            if(block.getValue(BONG_STATE) == 0 && player.getItemInHand(hand).getItem() == ModItems.WEED_BUDS.get())
            {
                player.getItemInHand(hand).shrink(1);
                level.setBlock(blockPos, block.setValue(BONG_STATE, 1), 3);
            }
            else if (block.getValue(BONG_STATE) == 1 &&
                    player.getItemInHand(InteractionHand.MAIN_HAND).getItem() != ModItems.WEED_BUDS.get())
            {
                System.out.println(block.getValue(BONG_STATE));
                level.setBlock(blockPos, block.setValue(BONG_STATE, 0), 3);
                level.playSound(null, player.getOnPos(), SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_AMBIENT,
                        SoundSource.BLOCKS, 0.8f, level.random.nextFloat() * 0.1f + 0.75f);
                player.addEffect(new MobEffectInstance(ModEffects.WEED.get(), 2400));
                player.awardStat(Stats.ITEM_USED.get(ModBlocks.BONG.get().asItem()));
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState p_60572_, BlockGetter p_60573_, BlockPos p_60574_, CollisionContext p_60575_) {
        return Bong.SHAPE;
    }

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public Bong(Properties properties)
    {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource source) {
        float chance = 0.6f;
        if (chance < ThreadLocalRandom.current().nextFloat() & state.getValue(Bong.BONG_STATE) >= 1)
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
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getClockWise());
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
        builder.add(BONG_STATE);
    }
}
