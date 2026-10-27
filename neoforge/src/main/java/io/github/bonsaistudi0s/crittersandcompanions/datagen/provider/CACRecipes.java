package io.github.bonsaistudi0s.crittersandcompanions.datagen.provider;

import net.minecraft.advancements.Advancement;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.MultiRegistryBootstrap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.BrewingRecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.Tags;

import java.util.List;
import java.util.Set;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.common.registry.CACBlocks;
import io.github.bonsaistudi0s.crittersandcompanions.common.registry.CACItems;
import io.github.bonsaistudi0s.crittersandcompanions.common.registry.CACPotions;

public final class CACRecipes extends RecipeProvider {

    private CACRecipes(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
    }

    public static MultiRegistryBootstrap create() {
        return new MultiRegistryBootstrap() {
            @Override
            public Set<ResourceKey<? extends Registry<?>>> requestedRegistries() {
                return Set.of(Registries.RECIPE, Registries.ADVANCEMENT);
            }

            @Override
            public void run(BootstrapGetter registries) {
                new CACRecipes(registries.get(Registries.RECIPE), registries.get(Registries.ADVANCEMENT)).buildRecipes();
            }
        };
    }

    private static String id(String path) {
        return CrittersAndCompanions.createId(path).toString();
    }

    @Override
    protected void buildRecipes() {
        shapeless(RecipeCategory.MISC, Items.ENDER_PEARL)
                .requires(CACItems.PEARL.get())
                .requires(Items.POPPED_CHORUS_FRUIT)
                .unlockedBy("has_pearl", has(CACItems.PEARL.get()))
                .save(output, id("ender_pearl_from_pearl"));

        shaped(RecipeCategory.COMBAT, CACItems.IRON_DRAGONFLY_ARMOR.get())
                .pattern("  I")
                .pattern(" I ")
                .pattern("I  ")
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .save(output);

        shaped(RecipeCategory.TOOLS, CACItems.GRAPPLING_HOOK.get())
                .pattern("SS ")
                .pattern("SI ")
                .pattern("  S")
                .define('S', CACItems.SILK.get())
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_silk", has(CACItems.SILK.get()))
                .save(output);

        shaped(RecipeCategory.MISC, CACItems.PEARL_NECKLACE_1.get())
                .pattern(" P ")
                .pattern("P P")
                .pattern(" P ")
                .define('P', CACItems.PEARL.get())
                .unlockedBy("has_pearl", has(CACItems.PEARL.get()))
                .save(output);

        shapeless(RecipeCategory.MISC, Items.STRING, 2)
                .requires(CACItems.SILK.get())
                .unlockedBy("has_silk", has(CACItems.SILK.get()))
                .save(output, id("string_from_silk"));

        shapeless(RecipeCategory.MISC, CACItems.PEARL_NECKLACE_3.get())
                .requires(CACItems.PEARL_NECKLACE_2.get())
                .requires(CACItems.PEARL.get())
                .unlockedBy("has_pearl_necklace_2", has(CACItems.PEARL_NECKLACE_2.get()))
                .save(output);

        shaped(RecipeCategory.COMBAT, CACItems.GOLD_DRAGONFLY_ARMOR.get())
                .pattern("  G")
                .pattern(" G ")
                .pattern("G  ")
                .define('G', Tags.Items.INGOTS_GOLD)
                .unlockedBy("has_gold_ingot", has(Tags.Items.INGOTS_GOLD))
                .save(output);

        shaped(RecipeCategory.MISC, CACItems.ACORN_HAT.get())
                .pattern("AAA")
                .pattern("A A")
                .define('A', CACItems.ACORN.get())
                .unlockedBy("has_acorn", has(CACItems.ACORN.get()))
                .save(output);

        shapeless(RecipeCategory.MISC, CACItems.PEARL_NECKLACE_2.get())
                .requires(CACItems.PEARL_NECKLACE_1.get())
                .requires(CACItems.PEARL.get())
                .unlockedBy("has_pearl_necklace_1", has(CACItems.PEARL_NECKLACE_1.get()))
                .save(output);

        shapeless(RecipeCategory.MISC, Items.SLIME_BALL)
                .requires(CACItems.SEA_BUNNY_SLIME_BOTTLE.get())
                .unlockedBy("has_sea_bunny_slime_bottle", has(CACItems.SEA_BUNNY_SLIME_BOTTLE.get()))
                .save(output, id("slime_ball_from_sea_bunny_slime_bottle"));

        shaped(RecipeCategory.BUILDING_BLOCKS, CACBlocks.SEA_BUNNY_SLIME_BLOCK.get())
                .pattern("SS")
                .pattern("SS")
                .define('S', CACItems.SEA_BUNNY_SLIME_BOTTLE.get())
                .unlockedBy("has_sea_bunny_slime_bottle", has(CACItems.SEA_BUNNY_SLIME_BOTTLE.get()))
                .save(output);

        shaped(RecipeCategory.COMBAT, CACItems.DIAMOND_DRAGONFLY_ARMOR.get())
                .pattern("  D")
                .pattern(" D ")
                .pattern("D  ")
                .define('D', Tags.Items.GEMS_DIAMOND)
                .unlockedBy("has_diamond", has(Tags.Items.GEMS_DIAMOND))
                .save(output);

        shaped(RecipeCategory.TOOLS, CACItems.SILK_LEAD.get())
                .pattern("SS ")
                .pattern("SB ")
                .pattern("  S")
                .define('S', CACItems.SILK.get())
                .define('B', Tags.Items.SLIME_BALLS)
                .unlockedBy("has_silk", has(CACItems.SILK.get()))
                .save(output);

        shapeless(RecipeCategory.MISC, Items.BONE_MEAL, 3)
                .requires(CACItems.CLAM.get())
                .unlockedBy("has_clam", has(CACItems.CLAM.get()))
                .save(output, id("bone_meal_from_clam"));

        shapeless(RecipeCategory.MISC, Items.SLIME_BALL)
                .requires(CACItems.SNAIL_SLIME_BOTTLE.get())
                .unlockedBy("has_snail_slime_bottle", has(CACItems.SNAIL_SLIME_BOTTLE.get()))
                .save(output, id("slime_ball_from_snail_slime_bottle"));

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.of(CACItems.DIAMOND_DRAGONFLY_ARMOR.get()),
                        tag(Tags.Items.INGOTS_NETHERITE),
                        RecipeCategory.COMBAT,
                        CACItems.NETHERITE_DRAGONFLY_ARMOR.get()
                ).unlocks("has_netherite_ingot", has(Tags.Items.INGOTS_NETHERITE))
                .save(output, id("netherite_dragonfly_armor_smithing"));

        buildBrewingRecipes();
    }

    private static final List<Item> POTION_CONTAINERS = List.of(Items.POTION, Items.SPLASH_POTION, Items.LINGERING_POTION);

    private static String brewingId(Item container, Holder<Potion> potion, Item reagent) {
        var containerName = BuiltInRegistries.ITEM.getKey(container).getPath();
        var potionName = potion.unwrapKey().orElseThrow().identifier().getPath();
        var reagentName = BuiltInRegistries.ITEM.getKey(reagent).getPath();
        return id("brewing/" + containerName + "_" + potionName + "_" + reagentName);
    }

    private void brewingMix(Holder<Potion> input, Item reagent, Holder<Potion> result) {
        for (var container : POTION_CONTAINERS) {
            BrewingRecipeBuilder.brewingMix(container, input, reagent, result).save(output, brewingId(container, input, reagent));
        }
    }

    private void brewingContainerTransform(Item container, Holder<Potion> potion, Item reagent, Item result) {
        BrewingRecipeBuilder.brewingContainerTransform(container, potion, reagent, result).save(output, brewingId(container, potion, reagent));
    }

    private void buildBrewingRecipes() {
        var resistance = BuiltInRegistries.POTION.wrapAsHolder(CACPotions.RESISTANCE.get());
        var longResistance = BuiltInRegistries.POTION.wrapAsHolder(CACPotions.LONG_RESISTANCE.get());
        var strongResistance = BuiltInRegistries.POTION.wrapAsHolder(CACPotions.STRONG_RESISTANCE.get());

        brewingMix(Potions.AWKWARD, CACItems.SNAIL_SLIME_BOTTLE.get(), resistance);
        brewingMix(resistance, Items.GLOWSTONE_DUST, strongResistance);
        brewingMix(resistance, Items.REDSTONE, longResistance);

        for (var potion : List.of(resistance, longResistance, strongResistance)) {
            brewingContainerTransform(Items.POTION, potion, Items.GUNPOWDER, Items.SPLASH_POTION);
            brewingContainerTransform(Items.SPLASH_POTION, potion, Items.DRAGON_BREATH, Items.LINGERING_POTION);
        }
    }
}
