package com.iafenvoy.reforgestone.mixin;

import com.iafenvoy.reforgestone.render.GlintLayerManager;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(BufferBuilderStorage.class)
public class BufferBuilderStorageMixin {
    @Inject(method = "assignBufferBuilder", at = @At("HEAD"))
    private static void registerGlints(Object2ObjectLinkedOpenHashMap<RenderLayer, BufferBuilder> builderStorage, RenderLayer layer, CallbackInfo ci) {
        GlintLayerManager.registerAll(builderStorage);
    }
}
