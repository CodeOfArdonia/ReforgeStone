package com.iafenvoy.reforgestone.data.modifier.builtin;

import com.iafenvoy.reforgestone.data.modifier.Modifier;
import com.iafenvoy.reforgestone.data.modifier.ModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public record NameModifier(String prefix, String suffix) implements Modifier<NameModifier> {
    public static final Codec<NameModifier> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.STRING.fieldOf("prefix").forGetter(NameModifier::prefix),
            Codec.STRING.fieldOf("suffix").forGetter(NameModifier::suffix)
    ).apply(i, NameModifier::new));

    @Override
    public ModifierType<NameModifier> getType() {
        return ModifierType.NAME;
    }

    @Override
    public void apply(ItemStack stack) {
        stack.setCustomName(Text.translatable(this.prefix).append(stack.getName()).append(Text.translatable(this.suffix)));
    }
}
