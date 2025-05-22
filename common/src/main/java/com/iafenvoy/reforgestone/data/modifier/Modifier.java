package com.iafenvoy.reforgestone.data.modifier;

import net.minecraft.item.ItemStack;

public interface Modifier<T extends Modifier<T>> {
    ModifierType<T> getType();

    void apply(ItemStack stack);
}
