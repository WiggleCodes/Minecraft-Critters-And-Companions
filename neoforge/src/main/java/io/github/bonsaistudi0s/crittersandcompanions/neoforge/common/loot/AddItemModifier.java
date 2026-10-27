package io.github.bonsaistudi0s.crittersandcompanions.neoforge.common.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

public class AddItemModifier extends LootModifier {

    public static final MapCodec<AddItemModifier> CODEC = RecordCodecBuilder.mapCodec(builder ->
            codecStart(builder).and(
                    ItemStackTemplate.CODEC.fieldOf("item").forGetter(it -> it.item)
            ).apply(builder, AddItemModifier::new)
    );

    private final ItemStackTemplate item;

    protected AddItemModifier(Optional<Holder<LootItemCondition>> condition, int priority, ItemStackTemplate item) {
        super(condition, priority);
        this.item = item;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> drops, LootContext context) {
        drops.add(item.create());
        return drops;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
