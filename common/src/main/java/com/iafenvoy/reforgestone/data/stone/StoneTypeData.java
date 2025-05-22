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
import net.minecraft.registry.tag.TagKey;

import java.util.List;

public record StoneTypeData(String translate, List<Modifier<?>> modifiers,
                            List<Either<Item, TagKey<Item>>> ingredients) {
    private static final Codec<Either<Item, TagKey<Item>>> ITEM_OR_TAG = Codec.either(Registries.ITEM.getCodec(), TagKey.codec(RegistryKeys.ITEM));
    public static final Codec<StoneTypeData> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.STRING.fieldOf("translate").forGetter(StoneTypeData::translate),
            ModifierType.CODEC.listOf().fieldOf("modifiers").forGetter(StoneTypeData::modifiers),
            ITEM_OR_TAG.listOf().fieldOf("ingredients").forGetter(StoneTypeData::ingredients)
    ).apply(i, StoneTypeData::new));

    public boolean matchIngredient(ItemStack stack) {
        return this.ingredients.stream().anyMatch(x -> x.map(stack::isOf, stack::isIn));
    }

    public void apply(ItemStack stack) {
        this.modifiers.forEach(x -> x.apply(stack));
    }
}
