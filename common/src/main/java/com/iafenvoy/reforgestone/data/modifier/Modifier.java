package com.iafenvoy.reforgestone.data.modifier;

import net.minecraft.text.Text;

public interface Modifier<T extends Modifier<T>> {
    ModifierType<T> getType();

    Text getTooltip();
}
