package io.github.bonsaistudi0s.crittersandcompanions.datagen.provider;

import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.common.block.SilkCocoonBlock;
import io.github.bonsaistudi0s.crittersandcompanions.common.registry.CACBlocks;

public final class CACWorldGenProvider {

    public static final ResourceKey<Feature> SILK_COCOON_CF = ResourceKey.create(
            Registries.FEATURE,
            CrittersAndCompanions.createId("silk_cocoon")
    );

    public static final ResourceKey<Feature> HANGING_SILK_COCOON_CF = ResourceKey.create(
            Registries.FEATURE,
            CrittersAndCompanions.createId("hanging_silk_cocoon")
    );

    public static final ResourceKey<PlacedFeature> SILK_COCOON_PF = ResourceKey.create(
            Registries.PLACED_FEATURE,
            CrittersAndCompanions.createId("silk_cocoon")
    );

    public static final ResourceKey<PlacedFeature> SILK_COCOON_LUSH_PF = ResourceKey.create(
            Registries.PLACED_FEATURE,
            CrittersAndCompanions.createId("silk_cocoon_lush")
    );

    public static final ResourceKey<PlacedFeature> HANGING_SILK_COCOON_PF = ResourceKey.create(
            Registries.PLACED_FEATURE,
            CrittersAndCompanions.createId("hanging_silk_cocoon")
    );

    public static final ResourceKey<PlacedFeature> HANGING_SILK_COCOON_LUSH_PF = ResourceKey.create(
            Registries.PLACED_FEATURE,
            CrittersAndCompanions.createId("hanging_silk_cocoon_lush")
    );

    private CACWorldGenProvider() {
    }

    public static void bootstrapFeatures(BootstrapContext<Feature> context) {
        context.register(
                SILK_COCOON_CF,
                new SimpleBlockFeature(BlockStateProvider.of(CACBlocks.SILK_COCOON.get()))
        );

        context.register(
                HANGING_SILK_COCOON_CF,
                new SimpleBlockFeature(BlockStateProvider.of(CACBlocks.SILK_COCOON.get().defaultBlockState().setValue(
                        SilkCocoonBlock.HANGING,
                        true
                )))
        );
    }

    private static List<PlacementModifier> cocoonPlacement(int count, VerticalAnchor minHeight, boolean hanging) {
        return List.of(
                CountPlacement.of(count),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(minHeight, VerticalAnchor.absolute(140)),
                hanging
                        ? EnvironmentScanPlacement.scanningFor(
                                Direction.UP,
                                BlockPredicate.hasSturdyFace(Direction.DOWN),
                                BlockPredicate.matchesBlocks(Blocks.AIR),
                                12
                        )
                        : EnvironmentScanPlacement.scanningFor(
                                Direction.DOWN,
                                BlockPredicate.alwaysTrue(),
                                BlockPredicate.matchesBlocks(Blocks.AIR),
                                12
                        ),
                OffsetPlacement.vertical(ConstantInt.of(-1)),
                BlockPredicateFilter.forPredicate(BlockPredicate.not(BlockPredicate.matchesBlocks(Blocks.WATER))),
                BiomeFilter.biome()
        );
    }

    public static void bootstrapPlacedFeatures(BootstrapContext<PlacedFeature> context) {
        HolderGetter<Feature> features = context.lookup(Registries.FEATURE);

        context.register(
                SILK_COCOON_PF,
                new PlacedFeature(features.getOrThrow(SILK_COCOON_CF), cocoonPlacement(4, VerticalAnchor.absolute(60), false))
        );

        context.register(
                SILK_COCOON_LUSH_PF,
                new PlacedFeature(features.getOrThrow(SILK_COCOON_CF), cocoonPlacement(8, VerticalAnchor.aboveBottom(32), false))
        );

        context.register(
                HANGING_SILK_COCOON_PF,
                new PlacedFeature(features.getOrThrow(HANGING_SILK_COCOON_CF), cocoonPlacement(4, VerticalAnchor.absolute(60), true))
        );

        context.register(
                HANGING_SILK_COCOON_LUSH_PF,
                new PlacedFeature(features.getOrThrow(HANGING_SILK_COCOON_CF), cocoonPlacement(8, VerticalAnchor.aboveBottom(32), true))
        );
    }
}
