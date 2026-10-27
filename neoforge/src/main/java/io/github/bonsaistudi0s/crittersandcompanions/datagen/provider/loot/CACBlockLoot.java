package io.github.bonsaistudi0s.crittersandcompanions.datagen.provider.loot;

import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import io.github.bonsaistudi0s.crittersandcompanions.common.registry.CACBlocks;
import io.github.bonsaistudi0s.crittersandcompanions.common.registry.CACItems;

public final class CACBlockLoot implements LootTableSubProvider {

    private final Context context;

    public CACBlockLoot(Context context) {
        this.context = context;
    }

    private void add(Block block, LootTable.Builder table) {
        context.accept(block.getLootTable().orElseThrow(), table);
    }

    @Override
    public void run() {
        add(
                CACBlocks.SEA_BUNNY_SLIME_BLOCK.get(), LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                                .when(ExplosionCondition.survivesExplosion())
                                .add(LootItem.lootTableItem(CACBlocks.SEA_BUNNY_SLIME_BLOCK.get())))
        );

        var items = context.<net.minecraft.world.item.Item>lookup(Registries.ITEM);
        var shearsCondition = MatchTool.toolMatches(ItemPredicate.Builder.item().of(items, Items.SHEARS));
        var noShears = InvertedLootItemCondition.invert(shearsCondition);
        add(
                CACBlocks.SILK_COCOON.get(), LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                                .when(shearsCondition)
                                .add(LootItem.lootTableItem(CACBlocks.SILK_COCOON.get())))
                        .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                                .when(noShears)
                                .add(LootItem.lootTableItem(CACItems.SILK.get())
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 5)))))
                        .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                                .when(noShears)
                                .add(LootItem.lootTableItem(Items.ENDER_PEARL)
                                        .when(LootItemRandomChanceCondition.randomChance(0.1F)))
                                .add(LootItem.lootTableItem(Items.NAME_TAG)
                                        .when(LootItemRandomChanceCondition.randomChance(0.1F))))
                        .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(2))
                                .when(noShears)
                                .add(LootItem.lootTableItem(CACItems.DRAGONFLY_WING.get()))
                                .add(LootItem.lootTableItem(Items.SLIME_BALL)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                .add(LootItem.lootTableItem(Items.RABBIT_HIDE).setWeight(10)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                .add(LootItem.lootTableItem(Items.SPIDER_EYE).setWeight(10)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                .add(LootItem.lootTableItem(Items.COAL).setWeight(10)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(10)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                .add(LootItem.lootTableItem(Items.BONE).setWeight(7)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                        )
        );
    }
}
