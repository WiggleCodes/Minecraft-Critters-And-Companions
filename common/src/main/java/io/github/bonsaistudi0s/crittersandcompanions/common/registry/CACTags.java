package io.github.bonsaistudi0s.crittersandcompanions.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;

public class CACTags {

    public static final TagKey<Biome> SILK_COCOON_SPAWNS = TagKey.create(Registries.BIOME, CrittersAndCompanions.createId("silk_cocoon_spawns"));
    public static final TagKey<Biome> SILK_COCOON_LUSH_SPAWNS = TagKey.create(Registries.BIOME, CrittersAndCompanions.createId("silk_cocoon_lush_spawns"));
    public static final TagKey<Biome> IS_LUSH = TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath("c", "is_lush"));

    public static final TagKey<Item> WOODEN_CHESTS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "chests/wooden"));
    public static final TagKey<Item> REPAIRS_ACORN_ARMOR = TagKey.create(Registries.ITEM, CrittersAndCompanions.createId("repairs_acorn_armor"));

    public static final TagKey<Block> BUG_SPAWN_GROUND = TagKey.create(Registries.BLOCK, CrittersAndCompanions.createId("bug_spawn_ground"));

}
