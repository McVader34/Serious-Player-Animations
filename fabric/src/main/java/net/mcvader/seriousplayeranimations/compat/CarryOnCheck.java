package net.mcvader.seriousplayeranimations.compat;

import immersive_melodies.item.InstrumentItem;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.item.Item;
import tschipp.carryon.common.carry.CarryOnData;
import tschipp.carryon.common.carry.CarryOnDataManager;

public class CarryOnCheck {

    public static void check(AbstractClientPlayerEntity player) {
        CarryOnData carry = CarryOnDataManager.getCarryData(player);
        if (carry.isCarrying() && !player.isInSwimmingPose() && !player.isFallFlying()) {
            player.disableArms(true);
        }

    }




}
