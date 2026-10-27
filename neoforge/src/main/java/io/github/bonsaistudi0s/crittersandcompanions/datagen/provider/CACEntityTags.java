package io.github.bonsaistudi0s.crittersandcompanions.datagen.provider;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.common.registry.CACEntities;

public final class CACEntityTags extends TagsProvider<EntityType<?>> {

    public CACEntityTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.ENTITY_TYPE, lookupProvider, CrittersAndCompanions.MODID);
    }

    @SafeVarargs
    private static Stream<ResourceKey<EntityType<?>>> keys(Supplier<? extends EntityType<?>>... types) {
        return Stream.of(types).map(type -> type.get().builtInRegistryHolder().key());
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        var underWater = keys(
                CACEntities.DUMBO_OCTOPUS,
                CACEntities.KOI_FISH,
                CACEntities.SEA_BUNNY
        ).toList();

        tag(EntityTypeTags.AQUATIC).addAll(underWater);
        tag(EntityTypeTags.AXOLOTL_HUNT_TARGETS).addAll(underWater);
        tag(EntityTypeTags.CAN_BREATHE_UNDER_WATER)
                .addAll(keys(CACEntities.OTTER))
                .addAll(underWater);
        tag(EntityTypeTags.ARTHROPOD)
                .addAll(keys(
                        CACEntities.JUMPING_SPIDER,
                        CACEntities.LEAF_INSECT,
                        CACEntities.LADYBUG,
                        CACEntities.STAG_BEETLE,
                        CACEntities.ROLY_POLY,
                        CACEntities.STICK_BUG,
                        CACEntities.WEEVIL,
                        CACEntities.DRAGONFLY
                ));
    }
}
