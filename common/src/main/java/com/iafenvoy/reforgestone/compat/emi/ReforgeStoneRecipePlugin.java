package com.iafenvoy.reforgestone.compat.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;

@EmiEntrypoint
public class ReforgeStoneRecipePlugin implements EmiPlugin {
    @Override
    public void register(EmiRegistry registry) {
        ReforgeStoneRecipeHelper.register(registry);
    }
}
