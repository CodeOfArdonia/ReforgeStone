package com.iafenvoy.reforgestone.compat.emi;

import com.iafenvoy.reforgestone.ReforgeStone;
import com.iafenvoy.reforgestone.Static;
import com.iafenvoy.reforgestone.data.stone.StoneTypeData;
import com.iafenvoy.reforgestone.data.stone.StoneTypeRegistry;
import com.iafenvoy.reforgestone.util.RandomHelper;
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
import java.util.Random;

public class ReforgeStoneRecipeHelper {
    private static final List<Item> ALL_WEAPONS = Registries.ITEM.stream().filter(x -> x instanceof SwordItem || x instanceof AxeItem).toList();

    public static void register(EmiRegistry registry) {
        if (Static.server == null) return;
        for (StoneTypeData data : Static.server.getRegistryManager().get(StoneTypeRegistry.REGISTRY_KEY))
            registry.addRecipe(new AnvilRecipe(data));
    }

    private static class AnvilRecipe implements EmiRecipe {
        private final Identifier id;
        private final StoneTypeData data;
        private final List<Item> targets;
        private final EmiIngredient ingredientsEmi, targetsEmi;
        private final int unique = new Random().nextInt();
        private Item lastTarget = Items.AIR;

        private AnvilRecipe(StoneTypeData data) {
            this.id = Identifier.of(ReforgeStone.MOD_ID, "/anvil/" + data.translate());
            this.data = data;
            this.targets = data.listAllTargets();
            this.ingredientsEmi = EmiIngredient.of(data.listAllIngredients().stream().map(Ingredient::ofItems).map(EmiIngredient::of).toList());
            this.targetsEmi = EmiIngredient.of(this.targets.stream().map(Ingredient::ofItems).map(EmiIngredient::of).toList());
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
            return List.of(this.ingredientsEmi, this.targetsEmi);
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
            widgets.addGeneratedSlot(r -> EmiIngredient.of(Ingredient.ofItems(this.lastTarget = RandomHelper.randomOne(r, this.targets))), this.unique, 0, 0);
            widgets.addSlot(this.ingredientsEmi, 49, 0);
            ItemStack stack = new ItemStack(this.lastTarget);
            StoneTypeRegistry.addModifiers(this.data, stack);
            widgets.addGeneratedSlot(r -> EmiIngredient.of(Ingredient.ofStacks(stack)), this.unique, 107, 0).recipeContext(this);
        }
    }
}
