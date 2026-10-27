package io.github.bonsaistudi0s.crittersandcompanions.datagen;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.datagen.provider.*;
import io.github.bonsaistudi0s.crittersandcompanions.datagen.provider.loot.CACLootTables;

@EventBusSubscriber(modid = CrittersAndCompanions.MODID)
public class CrittersAndCompanionsData {

    private static final RegistrySetBuilder WORLD_ENTRIES = new RegistrySetBuilder()
            .add(Registries.FEATURE, CACWorldGenProvider::bootstrapFeatures)
            .add(Registries.PLACED_FEATURE, CACWorldGenProvider::bootstrapPlacedFeatures);

    private static final RegistrySetBuilder RELOADABLE_ENTRIES = new RegistrySetBuilder()
            .add(Registries.LOOT_TABLE, CACLootTables.create())
            .add(CACRecipes.create());

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(CACLang::new);

        event.createProvider(CACBlockTags::new);
        event.createProvider(CACItemTags::new);
        event.createProvider(CACEntityTags::new);
        event.createProvider(CACBiomeTags::new);

        var generator = event.getDefaultPackGenerator();
        generator.createWorldRegistryObjects(WORLD_ENTRIES);
        generator.createReloadableRegistryObjects(RELOADABLE_ENTRIES);
    }

}
