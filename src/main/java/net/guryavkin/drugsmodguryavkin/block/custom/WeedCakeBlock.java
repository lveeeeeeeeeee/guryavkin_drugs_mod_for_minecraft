package net.guryavkin.drugsmodguryavkin.block.custom;

import net.guryavkin.drugsmodguryavkin.effect.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class WeedCakeBlock extends CakeBlock {
    public WeedCakeBlock(Properties p_51184_) {
        super(p_51184_);
    }

    // copied :(
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if (itemstack.is(ItemTags.CANDLES) && state.getValue(BITES) == 0) {
            Block block = Block.byItem(item);
            if (block instanceof CandleBlock) {
                if (!player.isCreative()) {
                    itemstack.shrink(1);
                }
                level.playSound((Player)null, blockPos, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.setBlockAndUpdate(blockPos, WeedCakeCandleBlock.byCandleWeed(block));
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
                return InteractionResult.SUCCESS;
            }
        }

        if (level.isClientSide) {
            if (eat(level, blockPos, state, player).consumesAction()) {
                return InteractionResult.SUCCESS;
            }
            if (itemstack.isEmpty()) {
                return InteractionResult.CONSUME;
            }
        }
        return WeedCakeBlock.eat(level, blockPos, state, player);
    }

    public static InteractionResult eat(
            Level level,
            BlockPos blockPos,
            BlockState state,
            Player player)
    {
        if (!player.canEat(false)) return  InteractionResult.PASS;
        else
        {
            player.awardStat(Stats.EAT_CAKE_SLICE);
            player.getFoodData().eat(2, 1f);
            int i = state.getValue(BITES);
            level.gameEvent(player, GameEvent.EAT, blockPos);
            if (i < 6)
            {
                level.setBlock(blockPos, state.setValue(BITES, state.getValue(BITES) + 1), 3);
                if(!level.isClientSide())
                {
                    player.addEffect(
                            new MobEffectInstance(ModEffects.WEED.get(),
                                    3000, 1));
                }
            }
            else
            {
                level.removeBlock(blockPos, false);
                level.gameEvent(player, GameEvent.BLOCK_DESTROY, blockPos);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
