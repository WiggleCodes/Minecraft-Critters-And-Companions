package io.github.bonsaistudi0s.crittersandcompanions.datagen.provider;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.*;
import io.github.bonsaistudi0s.crittersandcompanions.common.registry.AnimalTags;
import io.github.bonsaistudi0s.crittersandcompanions.common.registry.CACItems;
import io.github.bonsaistudi0s.crittersandcompanions.common.registry.CACTags;

public final class CACItemTags extends ItemTagsProvider {

    public CACItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, CrittersAndCompanions.MODID);
    }

    private static ResourceKey<Item> key(ItemLike item) {
        return item.asItem().builtInRegistryHolder().key();
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        tag(ItemTags.CAT_FOOD).add(key(CACItems.KOI_FISH.get()));
        tag(ItemTags.OCELOT_FOOD).add(key(CACItems.KOI_FISH.get()));
        tag(ItemTags.FISHES).add(key(CACItems.KOI_FISH.get()));

        tag(DragonflyEntity.TAGS.food()).add(key(Items.SPIDER_EYE));
        temptWithFood(DragonflyEntity.TAGS);

        tag(JumpingSpiderEntity.TAGS.food()).add(key(CACItems.DRAGONFLY_WING.get()));
        temptWithFood(JumpingSpiderEntity.TAGS);

        tag(FerretEntity.TAGS.food()).add(key(Items.CHICKEN));
        tag(FerretEntity.TAGS.tempt()).add(key(Items.RABBIT));

        tag(OtterEntity.TAGS.food())
                .addTag(ItemTags.FISHES)
                .add(key(CACItems.CLAM.get()));

        tag(RedPandaEntity.TAGS.food()).add(key(Items.BAMBOO));
        tag(RedPandaEntity.TAGS.tempt()).add(key(Items.SWEET_BERRIES));

        tag(ShimaEnagaEntity.TAGS.food()).addTag(Tags.Items.SEEDS);
        temptWithFood(ShimaEnagaEntity.TAGS);

        tag(RolyPolyEntity.TAGS.tempt()).addTag(Tags.Items.MUSHROOMS);
        tag(RolyPolyEntity.TAGS.food()).add(key(Items.DEAD_BUSH));

        tag(LadybugEntity.TAGS.food()).addTag(Tags.Items.MUSHROOMS);
        temptWithFood(LadybugEntity.TAGS);

        tag(StagBeetleEntity.TAGS.food()).addTag(Tags.Items.FOODS_BERRY);
        temptWithFood(StagBeetleEntity.TAGS);

        tag(StickBugEntity.TAGS.food()).addTag(ItemTags.LEAVES);
        temptWithFood(StickBugEntity.TAGS);

        tag(SnailEntity.TAGS.tempt()).addTag(ItemTags.LEAVES);
        tag(SnailEntity.TAGS.food()).add(key(Items.CARROT));

        tag(WeevilEntity.TAGS.food()).add(key(CACItems.ACORN.get()));
        temptWithFood(WeevilEntity.TAGS);

        tag(LeafInsectEntity.TAGS.food()).addTag(ItemTags.LEAVES);
        temptWithFood(LeafInsectEntity.TAGS);

        tag(SeaBunnyEntity.TAGS.food()).add(key(Items.SPONGE), key(Items.WET_SPONGE));
        tag(SeaBunnyEntity.TAGS.food()).addOptionalTag(cTag("sponges"));
        temptWithFood(SeaBunnyEntity.TAGS);

        tag(ItemTags.DURABILITY_ENCHANTABLE).add(key(CACItems.GRAPPLING_HOOK.get()));
        tag(CACTags.REPAIRS_ACORN_ARMOR).add(key(CACItems.ACORN.get()));
    }

    private void temptWithFood(AnimalTags tags) {
        tag(tags.tempt()).addTag(tags.food());
    }

    private TagKey<Item> cTag(String tag) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", tag));
    }
}
