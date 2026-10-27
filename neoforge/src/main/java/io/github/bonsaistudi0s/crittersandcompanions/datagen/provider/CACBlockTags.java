package io.github.bonsaistudi0s.crittersandcompanions.datagen.provider;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.FerretEntity;
import io.github.bonsaistudi0s.crittersandcompanions.common.registry.CACTags;

public final class CACBlockTags extends BlockTagsProvider {

    public CACBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, CrittersAndCompanions.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(FerretEntity.DIG_GROUNDS_TAG)
                .addTag(BlockTags.DIRT)
                .addTag(BlockTags.SAND)
                .add(Blocks.GRAVEL.builtInRegistryHolder().key());

        tag(CACTags.BUG_SPAWN_GROUND)
                .add(Blocks.MOSS_BLOCK.builtInRegistryHolder().key())
                .add(Blocks.GRASS_BLOCK.builtInRegistryHolder().key())
                .add(Blocks.PODZOL.builtInRegistryHolder().key())
                .add(Blocks.ROOTED_DIRT.builtInRegistryHolder().key());
    }
}
