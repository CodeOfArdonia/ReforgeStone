package com.iafenvoy.reforgestone.data;

import com.iafenvoy.reforgestone.data.stone.StoneTypeData;
import com.iafenvoy.reforgestone.data.stone.StoneTypeRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class StoneHelper {
    private static final String STONE_TYPE_KEY = "reforged_stone";

    public static Optional<StoneTypeData> getData(DynamicRegistryManager manager, ItemStack stack) {
        return stack.getNbt() == null ? Optional.empty() : Optional.ofNullable(StoneTypeRegistry.get(manager, Identifier.tryParse(stack.getNbt().getString(STONE_TYPE_KEY))));
    }

    public static boolean hasData(ItemStack stack) {
        return stack.getNbt() != null && stack.getNbt().contains(STONE_TYPE_KEY);
    }
}
