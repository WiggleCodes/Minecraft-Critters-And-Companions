package io.github.bonsaistudi0s.crittersandcompanions.common.platform.fabric;

import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.stream.Stream;

@SuppressWarnings("unused")
public class PlatformHooksImpl {

    public static boolean canAnimalBeTamed(Animal animal, Player player) {
        return true;
    }

    public static Stream<ItemStack> getAdditionalEquipment(Player player) {
        // Trinkets has no release for this Minecraft version, so there are no extra equipment slots to check.
        return Stream.empty();
    }
}
