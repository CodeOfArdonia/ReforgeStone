package com.iafenvoy.reforgestone.data.modifier.builtin;

import com.iafenvoy.reforgestone.data.modifier.Modifier;
import com.iafenvoy.reforgestone.data.modifier.ModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.text.Text;

public record MaxDamageModifier(int addition) implements Modifier<MaxDamageModifier> {
    public static final Codec<MaxDamageModifier> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.INT.fieldOf("addition").forGetter(MaxDamageModifier::addition)
    ).apply(i, MaxDamageModifier::new));

    @Override
    public ModifierType<MaxDamageModifier> getType() {
        return ModifierType.MAX_DAMAGE;
    }

    @Override
    public Text getTooltip() {
        return Text.translatable("item.reforge_stone.tooltip.max_damage", this.addition);
    }
}
