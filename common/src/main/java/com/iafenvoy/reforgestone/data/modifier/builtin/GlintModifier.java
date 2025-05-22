package com.iafenvoy.reforgestone.data.modifier.builtin;

import com.iafenvoy.reforgestone.data.modifier.Modifier;
import com.iafenvoy.reforgestone.data.modifier.ModifierType;
import com.iafenvoy.reforgestone.render.GlintManager;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;

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
    public void apply(ItemStack stack) {
        GlintManager.GlintHolder holder = GlintManager.BY_ID.get(this.color);
        if (holder == null) return;
        holder.apply(stack, this.always);
    }
}
