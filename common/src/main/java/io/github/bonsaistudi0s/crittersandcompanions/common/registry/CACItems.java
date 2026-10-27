package io.github.bonsaistudi0s.crittersandcompanions.common.registry;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.function.Function;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.common.config.CACCommonConfig;
import io.github.bonsaistudi0s.crittersandcompanions.common.item.*;

@SuppressWarnings("unused")
public class CACItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(CrittersAndCompanions.MODID, Registries.ITEM);

    private static Item.Properties baseProperties(String name) {
        return new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM, CrittersAndCompanions.createId(name)))
                .arch$tab(CrittersAndCompanions.CREATIVE_TAB);
    }

    private static RegistrySupplier<Item> register(String name, Function<Item.Properties, Item> factory) {
        return ITEMS.register(name, () -> factory.apply(baseProperties(name)));
    }

    public static final RegistrySupplier<Item> CLAM = register("clam", Item::new);
    public static final RegistrySupplier<Item> DRAGONFLY_WING = register("dragonfly_wing", Item::new);
    public static final RegistrySupplier<Item> KOI_FISH = register("koi_fish", properties -> new Item(properties.food(Foods.TROPICAL_FISH)));
    public static final RegistrySupplier<Item> PEARL = register("pearl", Item::new);
    public static final RegistrySupplier<Item> SILK = register("silk", Item::new);

    public static final RegistrySupplier<Item> SEA_BUNNY_SLIME_BOTTLE = register("sea_bunny_slime_bottle", properties -> new Item(properties.craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));
    public static final RegistrySupplier<Item> SEA_BUNNY_SLIME_BLOCK = register("sea_bunny_slime_block", properties -> new BlockItem(CACBlocks.SEA_BUNNY_SLIME_BLOCK.get(), properties.useBlockDescriptionPrefix()));

    public static final RegistrySupplier<Item> SILK_LEAD = register("silk_lead", SilkLeashItem::new);
    public static final RegistrySupplier<Item> GRAPPLING_HOOK = register("grappling_hook", properties -> {
        properties = properties.stacksTo(1).repairable(SILK.get());

        if (CACCommonConfig.HANDLER.instance().grapplingHook.enableDurability) {
            properties = properties.durability(256).enchantable(1);
        }

        return new GrapplingHookItem(properties);
    });

    public static final RegistrySupplier<Item> PEARL_NECKLACE_1 = register("pearl_necklace_1", properties -> new PearlNecklaceItem(properties.stacksTo(1), 1));
    public static final RegistrySupplier<Item> PEARL_NECKLACE_2 = register("pearl_necklace_2", properties -> new PearlNecklaceItem(properties.stacksTo(1), 2));
    public static final RegistrySupplier<Item> PEARL_NECKLACE_3 = register("pearl_necklace_3", properties -> new PearlNecklaceItem(properties.stacksTo(1), 3));

    public static final RegistrySupplier<Item> DUMBO_OCTOPUS_BUCKET = register("dumbo_octopus_bucket", properties -> new MobBucketItem(CACEntities.DUMBO_OCTOPUS.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties.stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final RegistrySupplier<Item> KOI_FISH_BUCKET = register("koi_fish_bucket", properties -> new MobBucketItem(CACEntities.KOI_FISH.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties.stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final RegistrySupplier<Item> SEA_BUNNY_BUCKET = register("sea_bunny_bucket", properties -> new MobBucketItem(CACEntities.SEA_BUNNY.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties.stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));

    public static final RegistrySupplier<Item> IRON_DRAGONFLY_ARMOR = register("iron_dragonfly_armor", properties -> new DragonflyArmorItem(ArmorMaterials.IRON, "iron", properties.stacksTo(1)));
    public static final RegistrySupplier<Item> GOLD_DRAGONFLY_ARMOR = register("gold_dragonfly_armor", properties -> new DragonflyArmorItem(ArmorMaterials.GOLD, "gold", properties.stacksTo(1)));
    public static final RegistrySupplier<Item> DIAMOND_DRAGONFLY_ARMOR = register("diamond_dragonfly_armor", properties -> new DragonflyArmorItem(ArmorMaterials.DIAMOND, "diamond", properties.stacksTo(1)));
    public static final RegistrySupplier<Item> NETHERITE_DRAGONFLY_ARMOR = register("netherite_dragonfly_armor", properties -> new DragonflyArmorItem(ArmorMaterials.NETHERITE, "netherite", properties.stacksTo(1)));

    public static final RegistrySupplier<Item> DRAGONFLY_SPAWN_EGG = registerSpawnEgg(CACEntities.DRAGONFLY);
    public static final RegistrySupplier<Item> FERRET_SPAWN_EGG = registerSpawnEgg(CACEntities.FERRET);
    public static final RegistrySupplier<Item> DUMBO_OCTOPUS_SPAWN_EGG = registerSpawnEgg(CACEntities.DUMBO_OCTOPUS);
    public static final RegistrySupplier<Item> JUMPING_SPIDER_SPAWN_EGG = registerSpawnEgg(CACEntities.JUMPING_SPIDER);
    public static final RegistrySupplier<Item> KOI_FISH_SPAWN_EGG = registerSpawnEgg(CACEntities.KOI_FISH);
    public static final RegistrySupplier<Item> LEAF_INSECT_SPAWN_EGG = registerSpawnEgg(CACEntities.LEAF_INSECT);
    public static final RegistrySupplier<Item> OTTER_SPAWN_EGG = registerSpawnEgg(CACEntities.OTTER);
    public static final RegistrySupplier<Item> RED_PANDA_SPAWN_EGG = registerSpawnEgg(CACEntities.RED_PANDA);
    public static final RegistrySupplier<Item> SEA_BUNNY_SPAWN_EGG = registerSpawnEgg(CACEntities.SEA_BUNNY);
    public static final RegistrySupplier<Item> SHIMA_ENAGA_SPAWN_EGG = registerSpawnEgg(CACEntities.SHIMA_ENAGA);
    public static final RegistrySupplier<Item> LADYBUG_SPAWN_EGG = registerSpawnEgg(CACEntities.LADYBUG);
    public static final RegistrySupplier<Item> STAG_BEETLE_SPAWN_EGG = registerSpawnEgg(CACEntities.STAG_BEETLE);
    public static final RegistrySupplier<Item> ROLY_POLY_SPAWN_EGG = registerSpawnEgg(CACEntities.ROLY_POLY);
    public static final RegistrySupplier<Item> SNAIL_SPAWN_EGG = registerSpawnEgg(CACEntities.SNAIL);
    public static final RegistrySupplier<Item> STICK_BUG_SPAWN_EGG = registerSpawnEgg(CACEntities.STICK_BUG);
    public static final RegistrySupplier<Item> WEEVIL_SPAWN_EGG = registerSpawnEgg(CACEntities.WEEVIL);

    public static final RegistrySupplier<Item> SILK_COCOON = register("silk_cocoon", properties -> new BlockItem(CACBlocks.SILK_COCOON.get(), properties.useBlockDescriptionPrefix()));

    public static final RegistrySupplier<Item> ACORN = register("acorn", properties -> new AcornItem(properties.compostable(ContextIntProviders.COMPOSTABLE_LOW)));
    public static final RegistrySupplier<Item> ACORN_HAT = register("acorn_hat", AcornHatItem::new);
    public static final RegistrySupplier<Item> SNAIL_SLIME_BOTTLE = register("snail_slime_bottle", properties -> new SnailSlimeBottleItem(properties.craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));

    private static <T extends Mob> RegistrySupplier<Item> registerSpawnEgg(RegistrySupplier<EntityType<T>> entity) {
        return register(entity.getId().getPath() + "_spawn_egg", properties -> new SpawnEggItem(properties.spawnEgg(entity.get())));
    }

    public static void init() {
        ITEMS.register();
    }
}
