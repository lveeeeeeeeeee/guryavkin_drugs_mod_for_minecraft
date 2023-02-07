package net.guryavkin.drugsmodguryavkin.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class LSDEffect extends DrugEffect implements IDrugEffect
{
    private static String shaderPath = "shaders/post/lsdeffect.json";

    public LSDEffect(MobEffectCategory category, int color)
    {
        super(category, color, DrugCategory.DEPRESSANT, 2);
    }

    public LSDEffect()
    {
        super(MobEffectCategory.NEUTRAL, MobEffects.POISON.getColor(), DrugCategory.DEPRESSANT, 2);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amp)
    {
        //whatever happens on tick
    }

    public void ServerSideAct(Level level, Player player)
    {
        // server acts on level (used in event handler)
        level.playSound(
                null,
                player.getOnPos(),
                SoundEvents.PARROT_IMITATE_VEX,
                SoundSource.PLAYERS,
                0.7f,
                level.random.nextFloat() * 0.1f + 0.8f
        );
    }

    @Override
    public void Applied(Player player, int duration) {
        // server acts on level (to properly renew effects) (used in C2S handler)
        if (!player.level.isClientSide())
        {
            // apply the effects
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, duration, 1, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, duration, 1, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, duration, 1, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, duration, 1, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, duration, 1, false, false));
        }
    }

    public void ClientSideAct() {
        // server acts on client (gui syncing maybe)
    }

    public void Expired() {
        // toggle shader off
    }

    @Override
    public String GetShaderPath()
    {
        return LSDEffect.shaderPath;
    }
}