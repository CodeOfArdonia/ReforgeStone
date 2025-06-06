package com.iafenvoy.reforgestone.data.modifier.builtin;

import com.iafenvoy.reforgestone.data.modifier.Modifier;
import com.iafenvoy.reforgestone.data.modifier.ModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public record NameModifier(String prefix, String suffix, int color) implements Modifier<NameModifier> {
    public static final Codec<NameModifier> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.STRING.optionalFieldOf("prefix", "").forGetter(NameModifier::prefix),
            Codec.STRING.optionalFieldOf("suffix", "").forGetter(NameModifier::suffix),
            Codec.INT.optionalFieldOf("color", -1).forGetter(NameModifier::color)
    ).apply(i, NameModifier::new));

    @Override
    public ModifierType<NameModifier> getType() {
        return ModifierType.NAME;
    }

    @Override
    public Text getTooltip() {
        return Text.translatable("item.reforge_stone.tooltip.name", Text.translatable(this.prefix), Text.translatable(this.suffix)).fillStyle(Style.EMPTY.withColor(this.color));
    }
}
