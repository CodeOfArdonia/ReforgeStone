package com.iafenvoy.reforgestone.data.modifier.builtin;

import com.iafenvoy.reforgestone.data.modifier.Modifier;
import com.iafenvoy.reforgestone.data.modifier.ModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;

import java.util.Map;

public record EnchantmentModifier(Enchantment enchantment, int level) implements Modifier<EnchantmentModifier> {
    public static final Codec<EnchantmentModifier> CODEC = RecordCodecBuilder.create(i -> i.group(
            Registries.ENCHANTMENT.getCodec().fieldOf("enchantment").forGetter(EnchantmentModifier::enchantment),
            Codec.INT.fieldOf("level").forGetter(EnchantmentModifier::level)
    ).apply(i, EnchantmentModifier::new));

    @Override
    public ModifierType<EnchantmentModifier> getType() {
        return ModifierType.ENCHANTMENT;
    }

    @Override
    public void apply(ItemStack stack) {
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(stack);
        enchantments.put(this.enchantment, this.level);
        EnchantmentHelper.set(enchantments, stack);
    }
}
