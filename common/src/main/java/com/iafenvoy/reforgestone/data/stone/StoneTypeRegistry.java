package com.iafenvoy.reforgestone.data.stone;

import com.iafenvoy.reforgestone.ReforgeStone;
import com.mojang.datafixers.util.Either;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

public class StoneTypeRegistry {
    public static final RegistryKey<Registry<StoneTypeData>> REGISTRY_KEY = RegistryKey.ofRegistry(Identifier.of(ReforgeStone.MOD_ID, "stone_type"));

    private static List<Item> collectItem(Either<Item, TagKey<Item>> e) {
        return e.map(List::of, t -> Registries.ITEM.streamTagsAndEntries().filter(p -> p.getFirst().equals(t)).collect(LinkedList::new, (p, c) -> p.addAll(c.getSecond().stream().map(RegistryEntry::value).toList()), List::addAll));
    }

    @Nullable
    public static StoneTypeData get(DynamicRegistryManager manager, Identifier id) {
        return manager.get(REGISTRY_KEY).get(id);
    }

    @Nullable
    public static StoneTypeData get(DynamicRegistryManager manager, ItemStack stack) {
        return manager.get(REGISTRY_KEY).stream().filter(x -> x.matchIngredient(stack)).findFirst().orElse(null);
    }
}
