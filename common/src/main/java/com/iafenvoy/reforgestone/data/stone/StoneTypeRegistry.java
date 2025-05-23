package com.iafenvoy.reforgestone.data.stone;

import com.iafenvoy.reforgestone.ReforgeStone;
import com.iafenvoy.reforgestone.data.modifier.Modifier;
import com.iafenvoy.reforgestone.data.modifier.ModifierType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StoneTypeRegistry {
    public static final RegistryKey<Registry<StoneTypeData>> REGISTRY_KEY = RegistryKey.ofRegistry(Identifier.of(ReforgeStone.MOD_ID, "stone_type"));
    private static final String STONE_TYPE_KEY = ReforgeStone.MOD_ID;

    @Nullable
    public static StoneTypeData get(DynamicRegistryManager manager, ItemStack stack) {
        return manager.get(REGISTRY_KEY).stream().filter(x -> x.matchIngredient(stack)).findFirst().orElse(null);
    }

    public static boolean hasData(ItemStack stack) {
        return stack.getNbt() != null && stack.getNbt().contains(STONE_TYPE_KEY, NbtElement.LIST_TYPE);
    }

    public static void addModifiers(StoneTypeData data, ItemStack stack) {
        stack.getOrCreateNbt().put(STONE_TYPE_KEY, ModifierType.CODEC.listOf().encodeStart(NbtOps.INSTANCE, data.modifiers()).resultOrPartial(ReforgeStone.LOGGER::error).orElse(new NbtList()));
    }

    public static List<Modifier<?>> getModifiers(ItemStack stack) {
        if (stack.getNbt() == null || !hasData(stack)) return List.of();
        return ModifierType.CODEC.listOf().parse(NbtOps.INSTANCE, stack.getNbt().get(STONE_TYPE_KEY)).resultOrPartial(ReforgeStone.LOGGER::error).orElse(List.of());
    }
}
