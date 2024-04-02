package net.mcvader.seriousplayeranimations.mixin.NEA.fullbody;


import dev.tr7zw.notenoughanimations.access.PlayerData;
import dev.tr7zw.notenoughanimations.animations.fullbody.LadderAnimation;
import dev.tr7zw.notenoughanimations.versionless.animations.BodyPart;
import net.mcvader.seriousplayeranimations.torsoPosGetter;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LadderAnimation.class)
public class NEALadderMixin {

    @Inject(method = "apply", at = @At("HEAD"))
    public void apply(AbstractClientPlayerEntity entity, PlayerData data, PlayerEntityModel<AbstractClientPlayerEntity> model, BodyPart part, float delta, float tickCounter, CallbackInfo ci){
        ((torsoPosGetter) entity).disableAnimationB(true);
    }
}
