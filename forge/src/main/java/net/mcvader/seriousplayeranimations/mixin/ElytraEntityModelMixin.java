package net.mcvader.seriousplayeranimations.mixin;

import dev.kosmx.playerAnim.core.util.Vec3f;
import net.mcvader.seriousplayeranimations.torsoPosGetter;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;


@Mixin(ElytraEntityModel.class)
public abstract class ElytraEntityModelMixin <T extends LivingEntity>  extends AnimalModel<T> implements torsoPosGetter {
    float prevY = 0;
    float prevZ = 0;

    @Shadow @Final private ModelPart rightWing;

    @Shadow @Final private ModelPart leftWing;





    @Shadow protected abstract Iterable<ModelPart> getBodyParts();

    @Shadow protected abstract Iterable<ModelPart> getHeadParts();





    /**
     * @author McVader
     * @reason Because it wasn't working
     */
    @Overwrite
    public void setAngles(T livingEntity, float f, float g, float h, float i, float j) {
        float k = 0.2617994F;
        float l = -0.2617994F;
        float m = 0.0F;
        float n = 0.0F;
        float pivZ = 0.0F;
        if (livingEntity.isFallFlying()) {
            float o = 1.0F;
            Vec3d vec3d = livingEntity.getVelocity();
            if (vec3d.y < 0.0) {
                Vec3d vec3d2 = vec3d.normalize();
                o = 1.0F - (float)Math.pow(-vec3d2.y, 1.5);
            }

            k = o * 0.34906584F + (1.0F - o) * k;
            l = o * -1.5707964F + (1.0F - o) * l;
            pivZ = 0.0F;
        } else if (livingEntity.isInSneakingPose()) {
            k = 0.6981317F;
            l = -0.7853982F;
            m = 3.0F;
            n = 0.08726646F;
            pivZ = 0.0F;
        } else if (livingEntity.isSprinting() && !livingEntity.isInSwimmingPose()) {
            k = 0.9F;
            l = -0.2617994F;
            m = 0.0F;
            n = 0.1F;
            pivZ = -2.5F;
        }

        if (livingEntity instanceof AbstractClientPlayerEntity abstractClientPlayerEntity) {
            abstractClientPlayerEntity.elytraPitch += (k - abstractClientPlayerEntity.elytraPitch) * 0.1F;
            abstractClientPlayerEntity.elytraYaw += (n - abstractClientPlayerEntity.elytraYaw) * 0.1F;
            abstractClientPlayerEntity.elytraRoll += (l - abstractClientPlayerEntity.elytraRoll) * 0.1F;



            Vec3f Pos = ((torsoPosGetter) abstractClientPlayerEntity).getTorsoPos();



            if (!Pos.getY().isNaN()) {
                m = Pos.getY();
                pivZ = Pos.getZ();

                prevY = Pos.getY();
                prevZ = Pos.getZ();
            } else {
                m = prevY;
                pivZ = prevZ;
            }




            this.leftWing.pitch = abstractClientPlayerEntity.elytraPitch;
            this.leftWing.yaw = abstractClientPlayerEntity.elytraYaw;
            this.leftWing.roll = abstractClientPlayerEntity.elytraRoll;
        } else {
            this.leftWing.pitch = k;
            this.leftWing.roll = l;
            this.leftWing.yaw = n;
        }
        this.leftWing.pivotY = m;
        this.leftWing.pivotZ = pivZ;

        this.rightWing.yaw = -this.leftWing.yaw;
        this.rightWing.pivotY = this.leftWing.pivotY;
        this.rightWing.pivotZ = this.leftWing.pivotZ;
        this.rightWing.pitch = this.leftWing.pitch;
        this.rightWing.roll = -this.leftWing.roll;
    }


}
