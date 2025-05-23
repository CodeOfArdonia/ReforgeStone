package com.iafenvoy.reforgestone.mixin;

import com.iafenvoy.reforgestone.data.stone.StoneTypeData;
import com.iafenvoy.reforgestone.data.stone.StoneTypeRegistry;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "appendTooltip", at = @At("HEAD"))
    private void appendReforgeStoneTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        if (world == null) return;
        StoneTypeData data = StoneTypeRegistry.get(world.getRegistryManager(), stack);
        if (data != null) {
            tooltip.add(Text.translatable("item.reforge_stone.reforge_stone_tooltip", Text.translatable(data.translate())));
            data.modifiers().forEach(x -> tooltip.add(x.getTooltip()));
        }
    }
}
