package com.iafenvoy.reforgestone.data.modifier.builtin;

import com.iafenvoy.reforgestone.data.modifier.Modifier;
import com.iafenvoy.reforgestone.data.modifier.ModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;

import java.util.UUID;

public record AttributeModifier(EntityAttribute attribute, double value,
                                EntityAttributeModifier.Operation operation) implements Modifier<AttributeModifier> {
    private static final UUID UUID = java.util.UUID.fromString("5641d669-50b5-42a4-8ed2-edee9a7a82bd");
    public static final Codec<AttributeModifier> CODEC = RecordCodecBuilder.create(i -> i.group(
            Registries.ATTRIBUTE.getCodec().fieldOf("attribute").forGetter(AttributeModifier::attribute),
            Codec.DOUBLE.fieldOf("value").forGetter(AttributeModifier::value),
            Codec.STRING.xmap(EntityAttributeModifier.Operation::valueOf, EntityAttributeModifier.Operation::name).fieldOf("operation").forGetter(AttributeModifier::operation)
    ).apply(i, AttributeModifier::new));

    @Override
    public ModifierType<AttributeModifier> getType() {
        return ModifierType.ATTRIBUTE;
    }

    @Override
    public void apply(ItemStack stack) {
        stack.addAttributeModifier(this.attribute, new EntityAttributeModifier(UUID, "attribute_modifier", this.value, this.operation), null);
    }
}
