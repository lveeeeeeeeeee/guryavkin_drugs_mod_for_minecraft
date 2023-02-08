package net.guryavkin.drugsmodguryavkin.block.custom;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import net.guryavkin.drugsmodguryavkin.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class WeedCakeCandleBlock extends AbstractCandleBlock {
    private static final Map<Block, WeedCakeCandleBlock> BY_CANDLE_WEED = Maps.newHashMap();
    public static final BooleanProperty LIT = AbstractCandleBlock.LIT;
    private static final VoxelShape CAKE_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);
    private static final VoxelShape CANDLE_SHAPE = Block.box(7.0D, 8.0D, 7.0D, 9.0D, 14.0D, 9.0D);
    private static final VoxelShape SHAPE = Shapes.or(CAKE_SHAPE, CANDLE_SHAPE);
    private static final Iterable<Vec3> PARTICLE_OFFSETS = ImmutableList.of(new Vec3(0.5D, 1.0D, 0.5D));

    public WeedCakeCandleBlock(Block p_152859_, Properties p_152860_) {
        super(p_152860_);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, Boolean.valueOf(false)));
        BY_CANDLE_WEED.put(p_152859_, this);
    }

    public static BlockState byCandleWeed(Block p_152866_) {
        return BY_CANDLE_WEED.get(p_152866_).defaultBlockState();
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!itemstack.is(Items.FLINT_AND_STEEL) && !itemstack.is(Items.FIRE_CHARGE)) {
            if (candleHit(hitResult) && player.getItemInHand(hand).isEmpty() && blockState.getValue(LIT)) {
                extinguish(player, blockState, level, blockPos);
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                InteractionResult interactionresult = WeedCakeBlock.eat(level, blockPos, ModBlocks.WEED_CAKE.get().defaultBlockState(), player);
                if (interactionresult.consumesAction()) {
                    dropResources(blockState, level, blockPos);
                }
                return interactionresult;
            }
        } else {
            if (candleHit(hitResult) && !blockState.getValue(LIT)) {
                level.setBlock(blockPos, blockState.setValue(LIT, true), 3);
                return  InteractionResult.sidedSuccess(level.isClientSide);
            }
            else return InteractionResult.PASS;
        }
    }

    private static boolean candleHit(BlockHitResult p_152907_) {
        return p_152907_.getLocation().y - (double)p_152907_.getBlockPos().getY() > 0.5D;
    }

    public VoxelShape getShape(BlockState p_152875_, BlockGetter p_152876_, BlockPos p_152877_, CollisionContext p_152878_) {
        return SHAPE;
    }

    @Override
    protected Iterable<Vec3> getParticleOffsets(BlockState p_151927_) {
        return PARTICLE_OFFSETS;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_152905_) {
        p_152905_.add(LIT);
    }

    public static boolean canLight(BlockState p_152911_) {
        return p_152911_.is(BlockTags.CANDLE_CAKES, (p_152896_) -> {
            return p_152896_.hasProperty(LIT) && !p_152911_.getValue(LIT);
        });
    }
}
