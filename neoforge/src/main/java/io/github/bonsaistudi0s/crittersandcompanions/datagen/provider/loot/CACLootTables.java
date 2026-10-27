package io.github.bonsaistudi0s.crittersandcompanions.datagen.provider.loot;

import net.minecraft.core.registries.SingleRegistryBootstrap;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public final class CACLootTables {

    private CACLootTables() {
    }

    public static SingleRegistryBootstrap<LootTable> create() {
        return new LootTableProvider(
                Set.of(),
                List.of(
                        new LootTableProvider.SubProviderEntry(CACBlockLoot::new, LootContextParamSets.BLOCK),
                        new LootTableProvider.SubProviderEntry(CACEntityLoot::new, LootContextParamSets.ENTITY),
                        new LootTableProvider.SubProviderEntry(CACGameplayLoot::new, LootContextParamSets.EMPTY)
                )
        );
    }
}
