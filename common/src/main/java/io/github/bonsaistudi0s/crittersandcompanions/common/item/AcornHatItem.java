package io.github.bonsaistudi0s.crittersandcompanions.common.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;

import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo.AcornArmorRenderer;
import io.github.bonsaistudi0s.crittersandcompanions.common.registry.CACArmorMaterials;
import com.geckolib.animatable.GeoItem;
import com.geckolib.animatable.client.GeoRenderProvider;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.renderer.GeoArmorRenderer;
import com.geckolib.util.GeckoLibUtil;

public class AcornHatItem extends Item implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public AcornHatItem(Properties properties) {
        super(withAttributes(properties.humanoidArmor(CACArmorMaterials.ACORN, ArmorType.HELMET)));
        GeoItem.registerSyncedAnimatable(this);
    }

    private static Properties withAttributes(Properties properties) {
        return properties.attributes(CACArmorMaterials.ACORN.createAttributes(ArmorType.HELMET).withModifierAdded(
                Attributes.MAX_HEALTH,
                new AttributeModifier(
                        CrittersAndCompanions.createId("acorn_hat_health"),
                        4.0,
                        AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.HEAD
        ));
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private @Nullable AcornArmorRenderer renderer;

            @Override
            public GeoArmorRenderer<?, ?> getGeoArmorRenderer(ItemStack itemStack, EquipmentSlot equipmentSlot) {
                if (this.renderer == null)
                    this.renderer = new AcornArmorRenderer();

                return this.renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
