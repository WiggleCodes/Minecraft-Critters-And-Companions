package io.github.bonsaistudi0s.crittersandcompanions.fabric.common.loot;

import net.fabricmc.fabric.api.loot.v3.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.mixin.loot.LootTableAccessor;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ints.ConstantValue;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import io.github.bonsaistudi0s.crittersandcompanions.common.registry.CACItems;

public class CACLootModifiers {

    private static final ResourceKey<LootTable> OAK_LEAVES =
            ResourceKey.create(Registries.LOOT_TABLE,
                    Identifier.withDefaultNamespace("blocks/oak_leaves"));

    public static void register() {
        // should always reflect the values in crittersandcompanions/loot_modifiers/..

        addEntriesTo(BuiltInLootTables.FISHING_FISH, 0, builder -> {
            builder.accept(5, CACItems.CLAM.get());
            builder.accept(5, CACItems.KOI_FISH.get());
        });

        EntityTypes.DROWNED.getDefaultLootTable().ifPresent(drownedLootTable -> addEntriesTo(drownedLootTable, 0, builder -> {
            builder.accept(1, CACItems.CLAM.get());
        }));

        LootTableEvents.MODIFY.register((key, builder, source, provider) -> {
            if (key.equals(BuiltInLootTables.SHIPWRECK_TREASURE)
                    || key.equals(BuiltInLootTables.UNDERWATER_RUIN_SMALL)
                    || key.equals(BuiltInLootTables.UNDERWATER_RUIN_BIG)
            ) {
                builder.withPool(
                        LootPool.lootPool()
                                .setRolls(Holder.direct(new ConstantValue(1)))
                                .when(LootItemRandomChanceCondition.randomChance(0.1F))
                                .add(LootItem.lootTableItem(CACItems.CLAM.get()))
                );
            }

            if (key.equals(OAK_LEAVES)) {
                var silkTouchHolder = provider.lookupOrThrow(Registries.ENCHANTMENT)
                        .getOrThrow(Enchantments.SILK_TOUCH);
                var fortuneHolder = provider.lookupOrThrow(Registries.ENCHANTMENT)
                        .getOrThrow(Enchantments.FORTUNE);
                var items = provider.lookupOrThrow(Registries.ITEM);

                builder.withPool(
                        LootPool.lootPool()
                                .setRolls(Holder.direct(new ConstantValue(1)))
                                .when(InvertedLootItemCondition.invert(
                                        AnyOfCondition.anyOf(
                                                MatchTool.toolMatches(ItemPredicate.Builder.item().of(items, Items.SHEARS)),
                                                BonusLevelTableCondition.bonusLevelFlatChance(
                                                        silkTouchHolder,
                                                        0.0f,
                                                        1.0f
                                                )
                                        )
                                ))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(
                                        fortuneHolder,
                                        0.05f,
                                        0.0625f,
                                        0.083333336f,
                                        0.1f
                                ))
                                .add(LootItem.lootTableItem(CACItems.ACORN.get()))
                );
            }
        });
    }

    private static void addEntriesTo(ResourceKey<LootTable> key, int index,
                                     Consumer<BiConsumer<Integer, Item>> entries) {
        LootTableEvents.REPLACE.register(((id, table, source, lookupProvider) -> {
            if (!id.equals(key)) return null;
            var accessor = (LootTableAccessor) table;
            var builder = LootTable.lootTable()
                    .setParamSet(table.getParamSet());
            accessor.fabric_getRandomSequence().ifPresent(builder::setRandomSequence);
            var pools = accessor.fabric_getPools();
            for (int i = 0; i < pools.size(); i++) {
                var pool = FabricLootPoolBuilder.copyOf(pools.get(i));
                if (i == index) {
                    entries.accept((weight, item) -> pool.add(LootItem.lootTableItem(item).setWeight(weight)));
                }
                builder.withPool(pool);
            }
            return builder.build();
        }));
    }
}
