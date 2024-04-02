package net.mcvader.seriousplayeranimations.compat;

import net.mcvader.seriousplayeranimations.torsoPosGetter;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import tschipp.carryon.common.carry.CarryOnData;
import tschipp.carryon.common.carry.CarryOnDataManager;

public class CarryOnCheck {

    public static void check(AbstractClientPlayerEntity player) {
        CarryOnData carry = CarryOnDataManager.getCarryData(player);
        if (carry.isCarrying() && !player.isInSwimmingPose() && !player.isFallFlying()) {
            ((torsoPosGetter) player).disableArms(true);
        }

    }




}
