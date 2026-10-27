package io.github.bonsaistudi0s.crittersandcompanions.datagen.provider.loot;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;

public final class CACGameplayLoot implements LootTableSubProvider {

    private final Context context;

    public CACGameplayLoot(Context context) {
        this.context = context;
    }

    private static ResourceKey<LootTable> key(String path) {
        return ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(CrittersAndCompanions.MODID, path));
    }

    @Override
    public void run() {
        var common = context.accept(
                key("gameplay/digging/common"), LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                                .add(LootItem.lootTableItem(Items.RABBIT_HIDE))
                                .add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE))
                                .add(LootItem.lootTableItem(Items.GOLDEN_APPLE))
                                .add(LootItem.lootTableItem(Items.NAME_TAG))
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
        );

        var trash = context.accept(
                key("gameplay/digging/trash"), LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                                .add(LootItem.lootTableItem(Items.BONE))
                                .add(LootItem.lootTableItem(Items.ROTTEN_FLESH))
                                .add(LootItem.lootTableItem(Items.STRING))
                                .add(LootItem.lootTableItem(Items.SWEET_BERRIES))
                                .add(LootItem.lootTableItem(Items.GOLD_NUGGET))
                                .add(LootItem.lootTableItem(Items.FLINT))
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
        );

        var treasure = context.accept(
                key("gameplay/digging/treasure"), LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                                .add(LootItem.lootTableItem(Items.TOTEM_OF_UNDYING)))
        );

        context.accept(
                key("gameplay/digging"), LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                                .add(NestedLootTable.lootTableReference(trash).setWeight(80))
                                .add(NestedLootTable.lootTableReference(common).setWeight(18))
                                .add(NestedLootTable.lootTableReference(treasure).setWeight(2)))
        );
    }
}
