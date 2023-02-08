package net.guryavkin.drugsmodguryavkin.networking.packet;

import net.guryavkin.drugsmodguryavkin.effect.DrugEffect;
import net.guryavkin.drugsmodguryavkin.effect.IDrugEffect;
import net.guryavkin.drugsmodguryavkin.effect.LSDEffect;
import net.guryavkin.drugsmodguryavkin.networking.ModMessages;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class HighC2SPacket {

    private ServerPlayer player;

    public HighC2SPacket() {

    }

    public HighC2SPacket(ServerPlayer player)
    {
        this.player = player;
    }

    public HighC2SPacket(FriendlyByteBuf buffer)
    {

    }

    public void toBytes(FriendlyByteBuf buffer)
    {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() ->
        {
            // actions of the server
            Collection<MobEffectInstance> effects = player.getActiveEffects();
            Collection<IDrugEffect> drugging = List.of(new IDrugEffect[effects.toArray().length]);
            for(MobEffectInstance effect : effects)
            {
                if (effect.getEffect() instanceof IDrugEffect)
                {
                    IDrugEffect gre = (IDrugEffect)effect.getEffect();
                    drugging.add(gre);
                    gre.Applied(player, effect.getDuration(), effect.getAmplifier());
                }
            }
        }
        );
        return true;
    }
}
