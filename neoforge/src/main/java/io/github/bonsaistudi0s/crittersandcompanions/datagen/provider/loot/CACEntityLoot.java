package io.github.bonsaistudi0s.crittersandcompanions.datagen.provider.loot;

import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import io.github.bonsaistudi0s.crittersandcompanions.common.registry.CACEntities;
import io.github.bonsaistudi0s.crittersandcompanions.common.registry.CACItems;

public final class CACEntityLoot implements LootTableSubProvider {

    private final Context context;

    public CACEntityLoot(Context context) {
        this.context = context;
    }

    private void add(EntityType<?> type, LootTable.Builder table) {
        context.accept(type.getDefaultLootTable().orElseThrow(), table);
    }

    @Override
    public void run() {
        add(
                CACEntities.DRAGONFLY.get(), LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                                .add(LootItem.lootTableItem(CACItems.DRAGONFLY_WING.get())
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2)))))
        );

        add(
                CACEntities.JUMPING_SPIDER.get(), LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                                .add(LootItem.lootTableItem(CACItems.SILK.get())
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2))))
                                .add(LootItem.lootTableItem(Items.SPIDER_EYE)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2))))
                        )
        );

        add(
                CACEntities.KOI_FISH.get(), LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                                .add(LootItem.lootTableItem(CACItems.KOI_FISH.get())
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))))
                        .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                                .when(LootItemRandomChanceCondition.randomChance(0.05F))
                                .add(LootItem.lootTableItem(Items.BONE_MEAL)))
        );

        add(
                CACEntities.SHIMA_ENAGA.get(), LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                                .add(LootItem.lootTableItem(Items.FEATHER)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2)))))
        );
    }
}
