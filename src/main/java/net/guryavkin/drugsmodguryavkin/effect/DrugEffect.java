package net.guryavkin.drugsmodguryavkin.effect;

import net.guryavkin.drugsmodguryavkin.DrugsModGuryavkin;
import net.guryavkin.drugsmodguryavkin.networking.packet.HighS2CPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class DrugEffect extends MobEffect {
    public static DrugCategory category;

    public static int potency;

    public String shaderPath;

    protected DrugEffect(MobEffectCategory category, int color)
    {
        super(category, color);
        DrugEffect.category = DrugCategory.HALLUCINOGEN;
        DrugEffect.potency = 0;
    }

    protected DrugEffect(MobEffectCategory category, int color, DrugCategory _category, int _potency)
    {
        super(category, color);
        DrugEffect.category = _category;
        DrugEffect.potency = _potency;
    }

    public static void ApplyShader(String shader)
    {
        GameRenderer renderer = Minecraft.getInstance().gameRenderer;
        ResourceLocation loc = new ResourceLocation(DrugsModGuryavkin.MOD_ID, shader);
        System.out.println(String.format("WE ARE TURNING ON THE SHADER %1$s", shader));
        renderer.loadEffect(loc);
    }

    public static void ToggleShader(String shader)
    {
        System.out.println(String.format("WE ARE TURNING OFF THE SHADER %1$s", shader));
        GameRenderer renderer = Minecraft.getInstance().gameRenderer;
        renderer.togglePostEffect();
    }
    public void Applied(Player player)
    {

    }
}
