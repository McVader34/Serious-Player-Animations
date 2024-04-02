package net.mcvader.seriousplayeranimations.mixin;


import dev.kosmx.playerAnim.core.util.Vec3f;
import net.mcvader.seriousplayeranimations.torsoPosGetter;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CapeFeatureRenderer.class)
public class CapeRenderMixin {
    float x;
    float y;
    float z;
    float prevX;
    float prevY;
    float prevZ;
    Vec3f Pos;
    Vec3f Rot;

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/network/AbstractClientPlayerEntity;FFFFFF)V",
            at = @At(value = "INVOKE",target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V", shift = At.Shift.AFTER))
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci){


        Pos = ((torsoPosGetter) abstractClientPlayerEntity).getTorsoPos();
        //Rot = abstractClientPlayerEntity.getTorsoRotation();
        x = Pos.getX();
        y = Pos.getY();
        z = Pos.getZ();

        if(abstractClientPlayerEntity.isInSneakingPose()){
                y -= 4.1F;

        } /*else if (Rot.getX() > 0) {
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
        }*/

        if (Pos.getY() != null) {
            matrixStack.translate(x*0.0625, y*0.0625, z*0.0625);
            prevX = x;
            prevY = y;
            prevZ = z;
        } else {
            if (abstractClientPlayerEntity.isInSneakingPose()) {prevY = -4.1f;}
            matrixStack.translate(prevX*0.0625, prevY*0.0625, prevZ*0.0625);
        }
    }
}
