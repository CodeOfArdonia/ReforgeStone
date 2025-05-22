package com.iafenvoy.reforgestone.forge;

import com.iafenvoy.reforgestone.ReforgeStone;
import com.iafenvoy.reforgestone.data.stone.StoneTypeData;
import com.iafenvoy.reforgestone.data.stone.StoneTypeRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DataPackRegistryEvent;

@Mod(ReforgeStone.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ReforgeStoneForge {
    public ReforgeStoneForge() {
        ReforgeStone.init();
    }

    @SubscribeEvent
    public static void newDynamicRegistry(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(StoneTypeRegistry.REGISTRY_KEY, StoneTypeData.CODEC, StoneTypeData.CODEC);
    }
}
