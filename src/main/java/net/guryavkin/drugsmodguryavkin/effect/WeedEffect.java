package net.guryavkin.drugsmodguryavkin.effect;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class WeedEffect extends DrugEffect implements IDrugEffect
{
    private static String shaderPath = "shaders/post/weedeffect.json";

    public WeedEffect(MobEffectCategory category, int color)
    {
        super(category, color, DrugCategory.DEPRESSANT, 2);
    }

    public WeedEffect()
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
                level.random.nextFloat() * 0.1f + 0.5f
        );
    }

    @Override
    public void Applied(Player player, int duration, int amp) {
        // server acts on level (to properly renew effects) (used in C2S handler)
        if (!player.level.isClientSide())
        {
            // apply the effects
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, duration, amp, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, duration, amp, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, duration, amp, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, duration, amp, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, duration, amp, false, false));
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
        return WeedEffect.shaderPath;
    }
}