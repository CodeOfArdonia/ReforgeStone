package com.iafenvoy.reforgestone.data.modifier;

public interface Modifier<T extends Modifier<T>> {
    ModifierType<T> getType();
}
