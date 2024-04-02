package net.mcvader.seriousplayeranimations.compat;

import net.elidhan.triggerhappy.TriggerHappyClient;
import net.minecraft.client.render.entity.model.BipedEntityModel;

public class TriggerHappyCheck {
    public static boolean checkOneHanded(BipedEntityModel.ArmPose armPose) {
        return  armPose.equals(TriggerHappyClient.ONE_HANDED_GUN);
    }

    public static boolean checkTwoHanded(BipedEntityModel.ArmPose armPose) {
        return  armPose.equals(TriggerHappyClient.TWO_HANDED_GUN);
    }
}
