package net.guryavkin.drugsmodguryavkin.effect;

import net.guryavkin.drugsmodguryavkin.DrugsModGuryavkin;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.
            create(ForgeRegistries.MOB_EFFECTS, DrugsModGuryavkin.MOD_ID);

    public static final RegistryObject<MobEffect> WEED = MOB_EFFECTS.register(
            "weed_effect",
            () -> new WeedEffect(MobEffectCategory.NEUTRAL, MobEffects.POISON.getColor())
    );

    public static void register(IEventBus eventBus)
    {
        MOB_EFFECTS.register(eventBus);
    }
}
