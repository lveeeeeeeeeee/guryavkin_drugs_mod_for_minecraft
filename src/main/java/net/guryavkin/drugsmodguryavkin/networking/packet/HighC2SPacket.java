package net.guryavkin.drugsmodguryavkin.networking.packet;

import net.guryavkin.drugsmodguryavkin.DrugsModGuryavkin;
import net.guryavkin.drugsmodguryavkin.effect.ModEffects;
import net.guryavkin.drugsmodguryavkin.networking.ModMessages;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class HighC2SPacket {
    public HighC2SPacket() {

    }

    public HighC2SPacket(FriendlyByteBuf buffer)
    {

    }

    public void toBytes(FriendlyByteBuf buffer)
    {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        try
        {
            NetworkEvent.Context context = supplier.get();
            Player player = context.getSender();
            ServerLevel level = context.getSender().getLevel();
            context.enqueueWork(() ->
            {
                // actions of the server
                level.playSound(
                        null,
                        player.getOnPos(),
                        SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_AMBIENT,
                        SoundSource.PLAYERS,
                        0.5f,
                        level.random.nextFloat() * 0.1f + 0.9f
                        );

                // apply "high effect" on player
                player.addEffect(new MobEffectInstance(ModEffects.WEED.get()));

                // apply "high shader" on player (send the S2C packet to the player)
                ModMessages.sendToPlayer(new HighS2CPacket(), (ServerPlayer) player);

            });
            return true;
        }
        catch (Exception e)
        {
            DrugsModGuryavkin.LOGGER.info(e.getMessage());
            return false;
        }
    }
}
