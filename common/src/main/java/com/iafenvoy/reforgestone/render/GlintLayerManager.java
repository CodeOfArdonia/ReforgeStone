package com.iafenvoy.reforgestone.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

@Environment(EnvType.CLIENT)
public class GlintLayerManager extends RenderLayer {
    private static final Map<GlintManager.GlintHolder, RenderLayer> DIRECT_LAYERS = new HashMap<>();
    private static final Map<GlintManager.GlintHolder, RenderLayer> ARMOR_LAYERS = new HashMap<>();

    static {
        for (GlintManager.GlintHolder holder : GlintManager.HOLDERS) {
            if (holder.texture() == null) continue;
            DIRECT_LAYERS.put(holder, RenderLayer.of("direct_glint_" + holder.id(),
                    VertexFormats.POSITION_TEXTURE,
                    VertexFormat.DrawMode.QUADS,
                    256,
                    RenderLayer.MultiPhaseParameters.builder().program(DIRECT_GLINT_PROGRAM)
                            .texture(new Texture(holder.texture(), true, false))
                            .writeMaskState(COLOR_MASK)
                            .cull(DISABLE_CULLING)
                            .depthTest(EQUAL_DEPTH_TEST)
                            .transparency(GLINT_TRANSPARENCY)
                            .texturing(GLINT_TEXTURING)
                            .build(false)));
            ARMOR_LAYERS.put(holder, RenderLayer.of("armor_glint_" + holder.id(),
                    VertexFormats.POSITION_TEXTURE,
                    VertexFormat.DrawMode.QUADS,
                    256,
                    RenderLayer.MultiPhaseParameters.builder().program(ARMOR_ENTITY_GLINT_PROGRAM)
                            .texture(new Texture(holder.texture(), true, false))
                            .writeMaskState(COLOR_MASK)
                            .cull(DISABLE_CULLING)
                            .depthTest(EQUAL_DEPTH_TEST)
                            .transparency(GLINT_TRANSPARENCY)
                            .texturing(ENTITY_GLINT_TEXTURING)
                            .layering(VIEW_OFFSET_Z_LAYERING)
                            .build(false)));
        }
    }

    public GlintLayerManager(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }

    @ApiStatus.Internal
    public static void registerAll(SortedMap<RenderLayer, BufferBuilder> map) {
        for (Map.Entry<GlintManager.GlintHolder, RenderLayer> entry : DIRECT_LAYERS.entrySet())
            if (!map.containsKey(entry.getValue()))
                map.put(entry.getValue(), new BufferBuilder(entry.getValue().getExpectedBufferSize()));
    }

    public static boolean shouldAlwaysGlint(ItemStack stack) {
        return !stack.isEmpty() &&
                stack.getNbt() != null &&
                stack.getNbt().contains(GlintManager.GLINT_KEY, NbtElement.STRING_TYPE) &&
                GlintManager.BY_ID.containsKey(stack.getOrCreateNbt().getString(GlintManager.GLINT_KEY)) &&
                stack.getNbt().getBoolean(GlintManager.GLINT_ALWAYS_KEY);
    }

    public static RenderLayer processStack(RenderLayer origin, ItemStack stack) {
        if (!stack.isEmpty() && stack.getNbt() != null && stack.getNbt().contains(GlintManager.GLINT_KEY, NbtElement.STRING_TYPE)) {
            String id = stack.getOrCreateNbt().getString(GlintManager.GLINT_KEY);
            if (stack.getNbt().getBoolean(GlintManager.GLINT_ALWAYS_KEY))
                return DIRECT_LAYERS.getOrDefault(GlintManager.BY_ID.getOrDefault(id, GlintManager.DEFAULT), RenderLayer.getDirectGlint());
        }
        return origin;
    }

    public static RenderLayer processArmor(RenderLayer origin, ItemStack stack) {
        if (!stack.isEmpty() && stack.getNbt() != null && stack.getNbt().contains(GlintManager.GLINT_KEY, NbtElement.STRING_TYPE)) {
            String id = stack.getOrCreateNbt().getString(GlintManager.GLINT_KEY);
            if (stack.getNbt().getBoolean(GlintManager.GLINT_ALWAYS_KEY))
                return ARMOR_LAYERS.getOrDefault(GlintManager.BY_ID.getOrDefault(id, GlintManager.DEFAULT), RenderLayer.getDirectGlint());
        }
        return origin;
    }
}
