package net.mcvader.seriousplayeranimations.mixin.NEA.hands;

import dev.tr7zw.notenoughanimations.access.PlayerData;
import dev.tr7zw.notenoughanimations.animations.hands.LookAtItemAnimation;
import dev.tr7zw.notenoughanimations.versionless.animations.BodyPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LookAtItemAnimation.class)
public class NEALookAtItemMixin {

    @Inject(method = "apply", at = @At("HEAD"))
    public void apply(AbstractClientPlayerEntity entity, PlayerData data, PlayerEntityModel<AbstractClientPlayerEntity> model, BodyPart part, float delta, float tickCounter, CallbackInfo ci){
        Arm arm = part == BodyPart.LEFT_ARM ? Arm.LEFT : Arm.RIGHT;
        if (arm.equals(Arm.LEFT)) {
            entity.disableLeftArmB(true);
        } else {
            entity.disableRightArmB(true);
        }



    }
}