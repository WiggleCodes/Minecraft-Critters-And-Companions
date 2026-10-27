package io.github.bonsaistudi0s.crittersandcompanions.datagen.provider;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.concurrent.CompletableFuture;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.common.registry.CACTags;

public final class CACBiomeTags extends TagsProvider<Biome> {

    public CACBiomeTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.BIOME, lookupProvider, CrittersAndCompanions.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(CACTags.SILK_COCOON_LUSH_SPAWNS).add(Biomes.LUSH_CAVES);
        tag(CACTags.SILK_COCOON_SPAWNS)
                .addTag(BiomeTags.IS_FOREST)
                .addTag(BiomeTags.IS_JUNGLE);
    }
}
