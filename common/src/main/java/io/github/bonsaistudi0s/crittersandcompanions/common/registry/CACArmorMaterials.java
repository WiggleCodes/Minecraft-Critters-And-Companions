package io.github.bonsaistudi0s.crittersandcompanions.common.registry;

import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.Map;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;

public class CACArmorMaterials {

    public static final ResourceKey<EquipmentAsset> ACORN_ASSET = ResourceKey.create(EquipmentAssets.ROOT_ID, CrittersAndCompanions.createId("acorn"));

    // Durability 5 gives the helmet 55 durability, matching the old item properties.
    public static final ArmorMaterial ACORN = new ArmorMaterial(
            5,
            Map.of(
                    ArmorType.HELMET, 1,
                    ArmorType.CHESTPLATE, 0,
                    ArmorType.LEGGINGS, 0,
                    ArmorType.BOOTS, 0,
                    ArmorType.BODY, 0
            ),
            15,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            0.0F,
            0.0F,
            CACTags.REPAIRS_ACORN_ARMOR,
            ACORN_ASSET
    );
}
