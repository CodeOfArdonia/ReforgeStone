package com.iafenvoy.reforgestone.data.stone;

import com.iafenvoy.reforgestone.data.modifier.Modifier;
import com.iafenvoy.reforgestone.data.modifier.ModifierType;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;

import java.util.LinkedList;
import java.util.List;

public record StoneTypeData(String translate, List<Modifier<?>> modifiers,
                            List<Either<Item, TagKey<Item>>> ingredients,
                            List<Either<Item, TagKey<Item>>> targets) {
    private static final Codec<Either<Item, TagKey<Item>>> ITEM_OR_TAG = Codec.either(Registries.ITEM.getCodec(), TagKey.codec(RegistryKeys.ITEM));
    public static final Codec<StoneTypeData> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.STRING.fieldOf("translate").forGetter(StoneTypeData::translate),
            ModifierType.CODEC.listOf().fieldOf("modifiers").forGetter(StoneTypeData::modifiers),
            ITEM_OR_TAG.listOf().fieldOf("ingredients").forGetter(StoneTypeData::ingredients),
            ITEM_OR_TAG.listOf().fieldOf("targets").forGetter(StoneTypeData::targets)
    ).apply(i, StoneTypeData::new));

    public boolean matchIngredient(ItemStack stack) {
        return this.ingredients.stream().anyMatch(x -> x.map(stack::isOf, stack::isIn));
    }

    public boolean matchTarget(ItemStack stack) {
        return this.targets.stream().anyMatch(x -> x.map(stack::isOf, stack::isIn));
    }

    public List<Item> listAllIngredients() {
        return this.ingredients.stream().map(x -> x.map(List::of, y -> Registries.ITEM.getEntryList(y).map(z -> z.stream().map(RegistryEntry::value).toList()).orElse(List.of()))).collect(LinkedList::new, List::addAll, List::addAll);
    }

    public List<Item> listAllTargets() {
        return this.targets.stream().map(x -> x.map(List::of, y -> Registries.ITEM.getEntryList(y).map(z -> z.stream().map(RegistryEntry::value).toList()).orElse(List.of()))).collect(LinkedList::new, List::addAll, List::addAll);
    }
}
