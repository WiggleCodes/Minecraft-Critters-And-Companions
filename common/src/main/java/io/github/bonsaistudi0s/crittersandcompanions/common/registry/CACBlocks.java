package io.github.bonsaistudi0s.crittersandcompanions.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Function;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.common.block.SeaBunnySlimeBlock;
import io.github.bonsaistudi0s.crittersandcompanions.common.block.SilkCocoonBlock;

public class CACBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(CrittersAndCompanions.MODID, Registries.BLOCK);

    public static final RegistrySupplier<Block> SILK_COCOON = register("silk_cocoon", properties -> new SilkCocoonBlock(properties.instabreak().sound(SoundType.WOOL).pushReaction(PushReaction.POPPED)));
    public static final RegistrySupplier<Block> SEA_BUNNY_SLIME_BLOCK = register("sea_bunny_slime_block", properties -> new SeaBunnySlimeBlock(properties.noOcclusion().dynamicShape().sound(SoundType.SLIME_BLOCK)));

    private static RegistrySupplier<Block> register(String name, Function<BlockBehaviour.Properties, Block> factory) {
        return BLOCKS.register(name, () -> factory.apply(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, CrittersAndCompanions.createId(name)))));
    }

    public static void init() {
        BLOCKS.register();
    }
}
