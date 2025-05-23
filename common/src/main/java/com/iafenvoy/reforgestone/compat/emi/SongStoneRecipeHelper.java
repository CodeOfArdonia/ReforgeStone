package com.iafenvoy.reforgestone.compat.emi;

import com.iafenvoy.neptune.util.RandomHelper;
import com.iafenvoy.reforgestone.ReforgeStone;
import com.iafenvoy.reforgestone.data.stone.StoneTypeData;
import com.iafenvoy.sow.SongsOfWar;
import com.iafenvoy.sow.item.EnchantmentFragmentItem;
import dev.emi.emi.EmiUtil;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.List;

public class SongStoneRecipeHelper {
    private static final List<Item> ALL_WEAPONS = Registries.ITEM.stream().filter(x -> x instanceof SwordItem || x instanceof AxeItem).toList();
    private static final List<EnchantmentFragmentItem> ALL_STONES = Registries.ITEM.stream().filter(x -> x instanceof EnchantmentFragmentItem).map(x -> (EnchantmentFragmentItem) x).toList();

    public static void register(EmiRegistry registry) {
        for (StoneTypeData fragment : registry.getRecipeManager().) {
            registry.addRecipe(new SowAnvilRecipe(fragment));
            registry.addRecipe(new SowGrindstoneRecipe(fragment));
        }
    }

    private static class SowAnvilRecipe implements EmiRecipe {
        private final Identifier id;
        private final EnchantmentFragmentItem fragment;
        private final int unique = EmiUtil.RANDOM.nextInt();
        private Item lastWeapon = Items.AIR;

        private SowAnvilRecipe(EnchantmentFragmentItem fragment) {
            this.id = Identifier.of(ReforgeStone.MOD_ID, "/song_stone_anvil/" + fragment.getGlint().id());
            this.fragment = fragment;
        }

        @Override
        public EmiRecipeCategory getCategory() {
            return VanillaEmiRecipeCategories.ANVIL_REPAIRING;
        }

        @Override
        public Identifier getId() {
            return this.id;
        }

        @Override
        public List<EmiIngredient> getInputs() {
            return List.of(EmiIngredient.of(ALL_WEAPONS.stream().map(Ingredient::ofItems).map(EmiIngredient::of).toList()), EmiIngredient.of(Ingredient.ofItems(this.fragment)));
        }

        @Override
        public List<EmiStack> getOutputs() {
            return List.of(EmiStack.of(Items.AIR));
        }

        @Override
        public boolean supportsRecipeTree() {
            return false;
        }

        @Override
        public int getDisplayWidth() {
            return 125;
        }

        @Override
        public int getDisplayHeight() {
            return 18;
        }

        @Override
        public void addWidgets(WidgetHolder widgets) {
            widgets.addTexture(EmiTexture.PLUS, 27, 3);
            widgets.addTexture(EmiTexture.EMPTY_ARROW, 75, 1);
            widgets.addGeneratedSlot(r -> EmiIngredient.of(Ingredient.ofItems(this.lastWeapon = RandomHelper.randomOne(r, ALL_WEAPONS))), this.unique, 0, 0);
            widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(this.fragment)), 49, 0);
            widgets.addGeneratedSlot(r -> EmiIngredient.of(Ingredient.ofStacks(this.fragment.applyToStack(new ItemStack(this.lastWeapon)))), this.unique, 107, 0).recipeContext(this);
        }
    }

    private static class SowGrindstoneRecipe implements EmiRecipe {
        private static final Identifier BACKGROUND = Identifier.of("minecraft", "textures/gui/container/grindstone.png");
        private final int unique = EmiUtil.RANDOM.nextInt();
        private final EnchantmentFragmentItem fragment;
        private final Identifier id;
        private Item lastWeapon = Items.AIR;

        public SowGrindstoneRecipe(EnchantmentFragmentItem fragment) {
            this.fragment = fragment;
            this.id = Identifier.of(SongsOfWar.MOD_ID, "/sow_grindstone_" + fragment.getGlint().id());
        }

        @Override
        public EmiRecipeCategory getCategory() {
            return VanillaEmiRecipeCategories.GRINDING;
        }

        @Override
        public Identifier getId() {
            return this.id;
        }

        @Override
        public List<EmiIngredient> getInputs() {
            return List.of();
        }

        @Override
        public List<EmiStack> getOutputs() {
            return List.of();
        }

        @Override
        public boolean supportsRecipeTree() {
            return false;
        }

        @Override
        public int getDisplayWidth() {
            return 116;
        }

        @Override
        public int getDisplayHeight() {
            return 56;
        }

        @Override
        public void addWidgets(WidgetHolder widgets) {
            widgets.addTexture(BACKGROUND, 0, 0, 116, 56, 30, 15);
            widgets.addGeneratedSlot(r -> EmiIngredient.of(Ingredient.ofStacks(this.fragment.applyToStack(new ItemStack(this.lastWeapon = RandomHelper.randomOne(r, ALL_WEAPONS))))), this.unique, 18, 3).drawBack(false);
            widgets.addGeneratedSlot(r -> EmiIngredient.of(Ingredient.ofStacks(new ItemStack(this.lastWeapon))), this.unique, 98, 18).drawBack(false).recipeContext(this);
        }
    }
}
