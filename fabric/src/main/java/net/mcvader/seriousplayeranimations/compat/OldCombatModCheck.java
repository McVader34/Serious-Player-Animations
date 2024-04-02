package net.mcvader.seriousplayeranimations.compat;

import com.spiderfrog.oldcombatmod.client.OldCombatModClient;
import net.minecraft.entity.LivingEntity;

public class OldCombatModCheck {

    public static boolean check(LivingEntity player) {
        return OldCombatModClient.isPlayerSwordblocking(player);
    }


}
