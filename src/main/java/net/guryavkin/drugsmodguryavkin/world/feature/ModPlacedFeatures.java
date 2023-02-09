package net.guryavkin.drugsmodguryavkin.world.feature;

import net.guryavkin.drugsmodguryavkin.DrugsModGuryavkin;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModPlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(
            Registry.PLACED_FEATURE_REGISTRY, DrugsModGuryavkin.MOD_ID
    );

    public static final RegistryObject<PlacedFeature> WEED_PLACEMENT = PLACED_FEATURES.register(
            "weed_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.WEED_PLACEMENT.getHolder().get(),
                    List.of(RarityFilter.onAverageOnceEvery(16),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP,
            BiomeFilter.biome()))
    );

    public static void register(IEventBus eventBus)
    {
        PLACED_FEATURES.register(eventBus);
    }
}
