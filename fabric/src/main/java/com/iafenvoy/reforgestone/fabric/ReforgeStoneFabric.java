package com.iafenvoy.reforgestone.fabric;

import com.iafenvoy.reforgestone.data.stone.StoneTypeData;
import com.iafenvoy.reforgestone.data.stone.StoneTypeRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;

public final class ReforgeStoneFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        DynamicRegistries.registerSynced(StoneTypeRegistry.REGISTRY_KEY, StoneTypeData.CODEC, StoneTypeData.CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
    }
}
