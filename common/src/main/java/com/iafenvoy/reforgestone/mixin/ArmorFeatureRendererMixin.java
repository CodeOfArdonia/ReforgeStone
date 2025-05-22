package com.iafenvoy.reforgestone.mixin;

import com.iafenvoy.reforgestone.render.GlintLayerManager;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(value = ArmorFeatureRenderer.class, priority = 1001)
public class ArmorFeatureRendererMixin<T extends LivingEntity, A extends BipedEntityModel<T>> {
    @Unique
    private static ItemStack nbt_glint$tempStack = ItemStack.EMPTY;

    @Inject(method = "renderArmor", at = @At("HEAD"))
    private void beforeRender(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light, A model, CallbackInfo ci) {
        nbt_glint$tempStack = entity.getEquippedStack(armorSlot);
    }

    @Inject(method = "renderArmor", at = @At("RETURN"))
    private void afterRender(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light, A model, CallbackInfo ci) {
        nbt_glint$tempStack = ItemStack.EMPTY;
    }

    @ModifyExpressionValue(method = "renderArmor", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;hasGlint()Z"))
    private boolean hasGlint(boolean original) {
        return original || GlintLayerManager.shouldAlwaysGlint(nbt_glint$tempStack);
    }

    @ModifyExpressionValue(method = "renderGlint", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getArmorEntityGlint()Lnet/minecraft/client/render/RenderLayer;"))
    private RenderLayer handleGlintRender(RenderLayer original) {
        return GlintLayerManager.processArmor(original, nbt_glint$tempStack);
    }
}
