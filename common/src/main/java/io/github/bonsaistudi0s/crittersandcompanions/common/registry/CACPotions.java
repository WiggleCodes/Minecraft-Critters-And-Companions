package io.github.bonsaistudi0s.crittersandcompanions.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;

/**
 * Brewing recipes for these potions are data-driven, see {@code data/crittersandcompanions/recipe/brewing}.
 */
public class CACPotions {

    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(CrittersAndCompanions.MODID, Registries.POTION);

    public static final RegistrySupplier<Potion> RESISTANCE = POTIONS.register(
            "resistance",
            () -> new Potion("resistance", new MobEffectInstance(MobEffects.RESISTANCE, 20 * 60 * 3, 0)));

    public static final RegistrySupplier<Potion> LONG_RESISTANCE = POTIONS.register(
            "long_resistance",
            () -> new Potion("resistance", new MobEffectInstance(MobEffects.RESISTANCE, 20 * 60 * 8, 0)));

    public static final RegistrySupplier<Potion> STRONG_RESISTANCE = POTIONS.register(
            "strong_resistance",
            () -> new Potion("resistance", new MobEffectInstance(MobEffects.RESISTANCE, 20 * 60 + 20 * 30, 1)));

    public static void init() {
        POTIONS.register();
    }
}
