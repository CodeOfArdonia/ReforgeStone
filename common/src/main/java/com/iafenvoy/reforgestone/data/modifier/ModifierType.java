package com.iafenvoy.reforgestone.data.modifier;

import com.iafenvoy.reforgestone.ReforgeStone;
import com.iafenvoy.reforgestone.data.modifier.builtin.AttributeModifier;
import com.iafenvoy.reforgestone.data.modifier.builtin.EnchantmentModifier;
import com.iafenvoy.reforgestone.data.modifier.builtin.GlintModifier;
import com.iafenvoy.reforgestone.data.modifier.builtin.NameModifier;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record ModifierType<T extends Modifier<T>>(Identifier id, Codec<T> codec) {
    public static final Map<Identifier, ModifierType<?>> REGISTRY = new HashMap<>();
    public static final Codec<ModifierType<?>> REGISTRY_CODEC = Identifier.CODEC.flatXmap(id -> Optional.ofNullable(REGISTRY.get(id)).map(DataResult::success).orElseGet(() -> DataResult.error(() -> "Unknown registry key")), value -> DataResult.success(value.id));
    public static final Codec<Modifier<?>> CODEC = REGISTRY_CODEC.dispatch("type", Modifier::getType, ModifierType::codec);

    public static final ModifierType<NameModifier> NAME = register(new ModifierType<>(Identifier.of(ReforgeStone.MOD_ID, "name"), NameModifier.CODEC));
    public static final ModifierType<EnchantmentModifier> ENCHANTMENT = register(new ModifierType<>(Identifier.of(ReforgeStone.MOD_ID, "enchantment"), EnchantmentModifier.CODEC));
    public static final ModifierType<AttributeModifier> ATTRIBUTE = register(new ModifierType<>(Identifier.of(ReforgeStone.MOD_ID, "attribute"), AttributeModifier.CODEC));
    public static final ModifierType<GlintModifier> GLINT = register(new ModifierType<>(Identifier.of(ReforgeStone.MOD_ID, "glint"), GlintModifier.CODEC));

    public static <T extends Modifier<T>> ModifierType<T> register(ModifierType<T> type) {
        REGISTRY.put(type.id, type);
        return type;
    }
}
