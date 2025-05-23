package com.iafenvoy.reforgestone.data.modifier.builtin;

import com.iafenvoy.reforgestone.data.modifier.Modifier;
import com.iafenvoy.reforgestone.data.modifier.ModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.text.Text;

public record GlintModifier(String color, boolean always) implements Modifier<GlintModifier> {
    public static final Codec<GlintModifier> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.STRING.fieldOf("color").forGetter(GlintModifier::color),
            Codec.BOOL.optionalFieldOf("always", true).forGetter(GlintModifier::always)
    ).apply(i, GlintModifier::new));

    @Override
    public ModifierType<GlintModifier> getType() {
        return ModifierType.GLINT;
    }

    @Override
    public Text getTooltip() {
        return Text.translatable("item.reforge_stone.tooltip.glint", this.color, this.always);
    }
}
