package net.mcvader.seriousplayeranimations.mixin.NEA.fullbody;


import dev.tr7zw.notenoughanimations.access.PlayerData;
import dev.tr7zw.notenoughanimations.animations.fullbody.CrawlingAnimation;
import dev.tr7zw.notenoughanimations.versionless.NEABaseMod;
import dev.tr7zw.notenoughanimations.versionless.animations.BodyPart;
import dev.tr7zw.notenoughanimations.versionless.config.Config;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.mcvader.seriousplayeranimations.SeriousPlayerAnimations.LOGGER;

@Mixin(CrawlingAnimation.class)
public class NEACrawlingMixin {


    @Inject(method = "apply", at = @At("HEAD"))
    public void apply(AbstractClientPlayerEntity entity, PlayerData data, PlayerEntityModel<AbstractClientPlayerEntity> model, BodyPart part, float delta, float tickCounter, CallbackInfo ci){
            entity.disableAnimationB(true);

    }
}
