package net.mcvader.seriousplayeranimations.mixin;


import net.mcvader.seriousplayeranimations.torsoPosGetter;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {


    @Shadow
    private static BipedEntityModel.ArmPose getArmPose(AbstractClientPlayerEntity player, Hand hand) {
        return null;
    }

    @Inject(method = {"setModelPose"}, at = {@At(value = "INVOKE",target = "Lnet/minecraft/client/render/entity/PlayerEntityRenderer;getArmPose(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/client/render/entity/model/BipedEntityModel$ArmPose;", shift = At.Shift.BY, by = 2)})
    private void setModelPose(AbstractClientPlayerEntity player, CallbackInfo ci){
        ((torsoPosGetter) player).armPosMain(getArmPose(player, Hand.MAIN_HAND));
        ((torsoPosGetter) player).armPosOff(getArmPose(player, Hand.OFF_HAND));

    }
}
