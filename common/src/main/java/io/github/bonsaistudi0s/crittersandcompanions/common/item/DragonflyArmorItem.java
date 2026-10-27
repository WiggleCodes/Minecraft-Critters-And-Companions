package io.github.bonsaistudi0s.crittersandcompanions.common.item;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.ArmorType;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;

public class DragonflyArmorItem extends Item {

    private final Identifier texture;

    public DragonflyArmorItem(ArmorMaterial material, String tierName, Properties properties) {
        this(
                material,
                CrittersAndCompanions.createId("textures/entity/dragonfly_armor_" + tierName + ".png"),
                properties
        );
    }

    public DragonflyArmorItem(ArmorMaterial material, Identifier texture, Properties properties) {
        super(properties.attributes(createAttributes(material)));
        this.texture = texture;
    }

    private static ItemAttributeModifiers createAttributes(ArmorMaterial material) {
        int defense = material.defense().getOrDefault(ArmorType.CHESTPLATE, 0);
        if (material == ArmorMaterials.NETHERITE) {
            defense = 16;
        }

        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ARMOR,
                        new AttributeModifier(
                                CrittersAndCompanions.createId("armor"),
                                defense,
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.BODY
                )
                .add(
                        Attributes.ARMOR_TOUGHNESS,
                        new AttributeModifier(
                                CrittersAndCompanions.createId("toughness"),
                                material.toughness(),
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.BODY
                )
                .build();
    }

    public Identifier getTexture() {
        return this.texture;
    }
}
