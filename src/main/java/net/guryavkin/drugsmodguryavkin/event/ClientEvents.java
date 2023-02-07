package net.guryavkin.drugsmodguryavkin.event;

import net.guryavkin.drugsmodguryavkin.DrugsModGuryavkin;
import net.guryavkin.drugsmodguryavkin.effect.IDrugEffect;
import net.guryavkin.drugsmodguryavkin.networking.ModMessages;
import net.guryavkin.drugsmodguryavkin.networking.packet.HighC2SPacket;
import net.guryavkin.drugsmodguryavkin.networking.packet.HighS2CPacket;
import net.guryavkin.drugsmodguryavkin.util.KeyBinding;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEvents
{

    @Mod.EventBusSubscriber(modid = DrugsModGuryavkin.MOD_ID, value = Dist.CLIENT)
    public static class ClientKeyEvents
    {
        @SubscribeEvent
        public static void onAdded(MobEffectEvent.Added event)
        {
            MobEffectInstance effectInstance = event.getEffectInstance();
            MobEffect effect = effectInstance.getEffect();
            LivingEntity entity = event.getEntity();
            Level level = event.getEntity().getLevel();
            if (entity instanceof Player && effect instanceof IDrugEffect)
            {
                IDrugEffect _effect = (IDrugEffect) effect;
                _effect.ServerSideAct(level, (Player) entity);
                _effect.Applied((Player) entity, effectInstance.getDuration());
                System.out.println("TURNING ON THE SHADER");
                ModMessages.sendToServer(new HighC2SPacket((ServerPlayer) entity));
                ModMessages.sendToPlayer(new HighS2CPacket(true, _effect.GetShaderPath()), (ServerPlayer) entity);
            }
        }

        public static void ToggleShader(MobEffectEvent event)
        {
            MobEffect effect = event.getEffectInstance().getEffect();
            LivingEntity player = event.getEntity();
            if (effect instanceof IDrugEffect)
            {
                //turn off the shader
                System.out.println("TURNING OFFF THE SHADER");
                ModMessages.sendToPlayer(new HighS2CPacket(false), (ServerPlayer) player);
            }
        }

        @SubscribeEvent
        public static void onExpired(MobEffectEvent.Expired event)
        {
            ClientKeyEvents.ToggleShader(event);
        }

        @SubscribeEvent
        public static void onRemoved(MobEffectEvent.Remove event)
        {
            ClientKeyEvents.ToggleShader(event);
        }

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event)
        {
            event.register(KeyBinding.FUCK_KEY);
        }

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event)
        {
            try
            {
                if(KeyBinding.FUCK_KEY.consumeClick())
                {
                    ModMessages.sendToServer(new HighC2SPacket());
                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
}
