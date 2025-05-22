package com.iafenvoy.reforgestone.mixin;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.iafenvoy.reforgestone.data.modifier.Modifier;
import com.iafenvoy.reforgestone.data.modifier.builtin.AttributeModifier;
import com.iafenvoy.reforgestone.data.modifier.builtin.MaxDamageModifier;
import com.iafenvoy.reforgestone.data.modifier.builtin.NameModifier;
import com.iafenvoy.reforgestone.data.stone.StoneTypeRegistry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Unique
    private ItemStack reforge_stone$self() {
        return (ItemStack) (Object) this;
    }

    @Inject(method = "getAttributeModifiers", at = @At("TAIL"), cancellable = true)
    private void handleAttributes(EquipmentSlot slot, CallbackInfoReturnable<Multimap<EntityAttribute, EntityAttributeModifier>> cir) {
        if (slot != EquipmentSlot.MAINHAND) return;
        Multimap<EntityAttribute, EntityAttributeModifier> map = HashMultimap.create();
        map.putAll(cir.getReturnValue());
        List<Modifier<?>> modifiers = StoneTypeRegistry.getModifiers(this.reforge_stone$self());
        for (Modifier<?> modifier : modifiers)
            if (modifier instanceof AttributeModifier m)
                map.put(m.attribute(), new EntityAttributeModifier("Modifier", m.value(), m.operation()));
        cir.setReturnValue(map);
    }

    @Inject(method = "getName", at = @At("TAIL"), cancellable = true)
    private void handleName(CallbackInfoReturnable<Text> cir) {
        Text text = cir.getReturnValue();
        List<Modifier<?>> modifiers = StoneTypeRegistry.getModifiers(this.reforge_stone$self());
        for (Modifier<?> modifier : modifiers)
            if (modifier instanceof NameModifier m)
                text = Text.translatable(m.prefix()).append(text).append(Text.translatable(m.suffix())).fillStyle(Style.EMPTY.withColor(m.color()));
        cir.setReturnValue(text);
    }

    @Inject(method = "getMaxDamage", at = @At("TAIL"), cancellable = true)
    private void handleMaxDamage(CallbackInfoReturnable<Integer> cir) {
        int value = cir.getReturnValue();
        List<Modifier<?>> modifiers = StoneTypeRegistry.getModifiers(this.reforge_stone$self());
        for (Modifier<?> modifier : modifiers)
            if (modifier instanceof MaxDamageModifier m)
                value += m.addition();
        cir.setReturnValue(value);
    }
}
