package com.iafenvoy.reforgestone.data.modifier.builtin;

import com.iafenvoy.reforgestone.data.modifier.Modifier;
import com.iafenvoy.reforgestone.data.modifier.ModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.registry.Registries;

public record AttributeModifier(EntityAttribute attribute, double value,
                                EntityAttributeModifier.Operation operation) implements Modifier<AttributeModifier> {
    public static final Codec<AttributeModifier> CODEC = RecordCodecBuilder.create(i -> i.group(
            Registries.ATTRIBUTE.getCodec().fieldOf("attribute").forGetter(AttributeModifier::attribute),
            Codec.DOUBLE.fieldOf("value").forGetter(AttributeModifier::value),
            Codec.STRING.xmap(EntityAttributeModifier.Operation::valueOf, EntityAttributeModifier.Operation::name).optionalFieldOf("operation", EntityAttributeModifier.Operation.ADDITION).forGetter(AttributeModifier::operation)
    ).apply(i, AttributeModifier::new));

    @Override
    public ModifierType<AttributeModifier> getType() {
        return ModifierType.ATTRIBUTE;
    }
}
