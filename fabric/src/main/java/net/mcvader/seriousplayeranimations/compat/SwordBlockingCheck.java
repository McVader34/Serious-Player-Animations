package net.mcvader.seriousplayeranimations.compat;

import com.spiderfrog.oldcombatmod.client.OldCombatModClient;
import eu.midnightdust.swordblocking.SwordBlockingClient;
import net.minecraft.entity.LivingEntity;

public class SwordBlockingCheck {

    public static boolean check(LivingEntity player) {
        return SwordBlockingClient.isWeaponBlocking(player);
    }


}
