package net.mcvader.seriousplayeranimations.mixin;

import com.mojang.authlib.GameProfile;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import dev.kosmx.playerAnim.api.layered.modifier.AdjustmentModifier;
import dev.kosmx.playerAnim.api.layered.modifier.MirrorModifier;
import dev.kosmx.playerAnim.api.layered.modifier.SpeedModifier;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.core.util.Vec3f;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.mcvader.seriousplayeranimations.IExampleAnimatedPlayer;
import net.mcvader.seriousplayeranimations.config.ClientConfig;
import net.mcvader.seriousplayeranimations.torsoPosGetter;
import net.minecraft.block.*;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.entity.mob.ZombieHorseEntity;
import net.minecraft.entity.passive.DonkeyEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.MuleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.item.*;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.Optional;

import static dev.kosmx.playerAnim.api.TransformType.POSITION;
import static dev.kosmx.playerAnim.api.TransformType.ROTATION;
import static dev.kosmx.playerAnim.core.util.Ease.INOUTSINE;
import static java.lang.Math.*;
import static net.mcvader.seriousplayeranimations.SeriousPlayerAnimations.config;
import static net.minecraft.util.Hand.MAIN_HAND;
import static net.minecraft.util.Hand.OFF_HAND;


@Unique
@Mixin(AbstractClientPlayerEntity.class)

public abstract class SeriousPlayerAnimationsMixin extends PlayerEntity implements IExampleAnimatedPlayer, torsoPosGetter {


    @Shadow @Final public ClientWorld clientWorld;


    @Shadow public float elytraRoll;
    @Shadow public float elytraPitch;
    @Shadow public float elytraYaw;

    private final ModifierLayer<IAnimation> modAnimationContainerSuper = new ModifierLayer<>();

    private final ModifierLayer<IAnimation> modAnimationContainer = new ModifierLayer<>();
    
    private final ModifierLayer<IAnimation> modAnimationContainer2 = new ModifierLayer<>();

    public SeriousPlayerAnimationsMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }


    public KeyframeAnimation sword_attack = null;
    
    public KeyframeAnimation sword_attack2 = null;

    public KeyframeAnimation idle_standing = null;
    
    public KeyframeAnimation idle_creative_flying = null;
    
    public KeyframeAnimation idle_creative_flying_item = null;
    
    public KeyframeAnimation walking = null;
    
    public KeyframeAnimation walking_backwards = null;
    
    public KeyframeAnimation running = null;
    
    public KeyframeAnimation turn_left = null;
    
    public KeyframeAnimation turn_right = null;
    
    public KeyframeAnimation idle_sneak = null;
    
    public KeyframeAnimation walking_sneak = null;
    
    public KeyframeAnimation walking_sneak_backwards = null;
    
    public KeyframeAnimation sword_attack_sneak = null;
    
    public KeyframeAnimation sword_attack_sneak2 = null;
    
    public KeyframeAnimation falling = null;
    
    public KeyframeAnimation blank_loop = null;
    
    public KeyframeAnimation elytra = null;
    
    public KeyframeAnimation eating_right = null;
    
    public KeyframeAnimation eating_left = null;
    
    public KeyframeAnimation eating_right_sneak = null;
    
    public KeyframeAnimation eating_left_sneak = null;
    
    public KeyframeAnimation idle_in_water = null;
    
    public KeyframeAnimation forward_in_water = null;
    
    public KeyframeAnimation backwards_in_water = null;
    
    public KeyframeAnimation up_in_water = null;
    
    public KeyframeAnimation swimming = null;
    
    public KeyframeAnimation crawling = null;
    
    public KeyframeAnimation idle_crawling = null;
    
    public KeyframeAnimation crawling_backwards = null;
    
    public KeyframeAnimation idle_climbing = null;
    
    public KeyframeAnimation idle_climbing_sneak = null;
    
    public KeyframeAnimation climbing = null;
    
    public KeyframeAnimation climbing_sneak = null;
    
    public KeyframeAnimation climbing_backwards = null;
    
    public KeyframeAnimation pickaxe = null;
    
    public KeyframeAnimation pickaxe_sneak = null;
    
    public KeyframeAnimation minecart_idle = null;
    
    public KeyframeAnimation minecart_pickaxe = null;
    
    public KeyframeAnimation horse_idle = null;
    
    public KeyframeAnimation horse_running = null;
    
    public KeyframeAnimation horse_pickaxe = null;

    public KeyframeAnimation boat1 = null;
    
    public KeyframeAnimation eating = null;
    
    public KeyframeAnimation bow_idle = null;

    public KeyframeAnimation bow_sneak = null;

    public KeyframeAnimation sleeping = null;




    
    public Vec3f torsoPos = new Vec3f(0, 0, 0);
    public Vec3f torsoRotation = new Vec3f(0, 0, 0);

    @Override
    public Vec3f getTorsoPos() { return this.torsoPos; }

    @Override
    public Vec3f getTorsoRotation() { return this.torsoRotation; }



    
    public KeyframeAnimation currentAnimation = null;
    
    public KeyframeAnimation currentOverlay = null;
    
    public KeyframeAnimation prevAnimation = null;
    
    public KeyframeAnimation prevJumpingAnimation = null;

    
    public KeyframeAnimation.AnimationBuilder builder = null;

    
    public void reloadAnimationVariables() {
        sword_attack = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "sword_attack"));
        sword_attack2 = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "sword_attack2"));
        idle_standing = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "idle_standing"));
        idle_creative_flying = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "idle_creative_flying"));
        idle_creative_flying_item = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "idle_creative_flying_item"));
        walking = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "walking"));
        walking_backwards = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "walking_backwards"));
        running = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "running"));
        turn_left = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "turn_left"));
        turn_right = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "turn_right"));
        idle_sneak = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "idle_sneak"));
        walking_sneak = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "walking_sneak"));
        walking_sneak_backwards = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "walking_sneak_backwards"));
        sword_attack_sneak = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "sword_attack_sneak"));
        sword_attack_sneak2 = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "sword_attack_sneak2"));
        falling = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "falling"));
        blank_loop = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "blank_loop"));
        elytra = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "elytra"));
        eating_right = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "eating_right"));
        eating_left = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "eating_left"));
        eating_right_sneak = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "eating_right_sneak"));
        eating_left_sneak = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "eating_left_sneak"));
        idle_in_water = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "idle_in_water"));
        forward_in_water = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "forward_in_water"));
        backwards_in_water = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "backwards_in_water"));
        up_in_water = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "up_in_water"));
        swimming = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "swimming"));
        crawling = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "crawling"));
        idle_crawling = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "idle_crawling"));
        crawling_backwards = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "crawling_backwards"));
        climbing = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "climbing"));
        climbing_sneak = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "climbing_sneak"));
        idle_climbing = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "idle_climbing"));
        idle_climbing_sneak = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "idle_climbing_sneak"));
        climbing_backwards = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "climbing_backwards"));
        pickaxe = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "pickaxe"));
        pickaxe_sneak = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "pickaxe_sneak"));
         minecart_idle = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "minecart_idle"));
        minecart_pickaxe = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "minecart_pickaxe"));
        horse_idle = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "horse_idle"));
        horse_running = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "horse_running"));
        horse_pickaxe = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "horse_pickaxe"));
        boat1 = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "boat1"));
        eating = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "eating"));
        bow_idle = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "bow_idle"));
        bow_sneak = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "bow_sneak"));
        sleeping = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "sleeping"));


    }

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void init(ClientWorld world, GameProfile profile, CallbackInfo info) {

        PlayerAnimationAccess.getPlayerAnimLayer((AbstractClientPlayerEntity) (Object) this).addAnimLayer(1, modAnimationContainer);
        modAnimationContainer.addModifierLast(AnimationSpeedModifier);
        modAnimationContainer.addModifierLast(DisabledLeftArmTorsoModifier);
        modAnimationContainer.addModifierLast(DisabledRightArmTorsoModifier);
        DisabledLeftArmTorsoModifier.enabled = false;
        DisabledRightArmTorsoModifier.enabled = false;
        modAnimationContainer.addModifierLast(AnimationMirrorModifier);
        AnimationMirrorModifier.setEnabled(false);


        PlayerAnimationAccess.getPlayerAnimLayer((AbstractClientPlayerEntity) (Object) this).addAnimLayer(2, modAnimationContainer2);
        modAnimationContainer2.addModifierLast(RightBowModifier);
        RightBowModifier.enabled = false;
        modAnimationContainer2.addModifierLast(LeftBowModifier);
        LeftBowModifier.enabled = false;

        modAnimationContainer2.addModifierLast(OverlayRightArmTorsoModifier);
        OverlayRightArmTorsoModifier.enabled = false;
        modAnimationContainer2.addModifierLast(OverlayRightArmTorsoModifier);
        OverlayLeftArmTorsoModifier.enabled = false;

        modAnimationContainer2.addModifierLast(OverlaySpeedModifier);
        modAnimationContainer2.addModifierLast(OverlayMirrorModifier);
        OverlayMirrorModifier.setEnabled(false);

        PlayerAnimationAccess.getPlayerAnimLayer((AbstractClientPlayerEntity) (Object) this).addAnimLayer(5000, modAnimationContainerSuper);
        modAnimationContainerSuper.addModifierLast(SuperRightArmTorsoModifier);
        modAnimationContainerSuper.addModifierLast(SuperLeftArmTorsoModifier);
        SuperLeftArmTorsoModifier.enabled = false;
        SuperRightArmTorsoModifier.enabled = false;

        reloadAnimationVariables();
        currentAnimation = idle_standing;
        currentOverlay = blank_loop;


    }


    @Override
    public ModifierLayer<IAnimation> seriousplayeranimations_getModAnimation() {
        return modAnimationContainer;
    }


    
    public int fadeTime = 0;
    
    public int overlayFadeTime = 0;
    
    public float overlayAnimationSpeed = 1;
    
    public int overlayPriority = 0;
    
    public int prevOverlayPriority = 0;


    
    public String currentAnimationId = "";
    
    public String prevAnimationId = "";
    
    public String currentOverlayId = "";
    
    public String prevOverlayId = "";
    
    public boolean modified = false;
    
    public float animationSpeed = 1;

    public float byaw = 0;

    public float prevbyaw = 0;
    
    public float hyaw = 0;
    
    public float vx = 0;
    
    public float vy = 0;
    
    public float vz = 0;
    
    public double moveSpeed = 0;
    
    public float turn = 0;
    
    public boolean prevOnGround = false;
    
    public int priority = 0;
    
    public int prevPriority = 0;
    
    public Vec3d lastPos = new Vec3d(0, 0, 0);
    
    public int swordSeq = 0;
    
    public String modifyId = "";
    
    public String prevModifyId = "";
    
    public String blockStateString = "";
    
    public String boatString = "";
    
    public int flychecker = 0;
    
    public double vyfly = 0;
    
    public String prevJumpingAnimationId = "";
    
    float prevJumpingAnimationSpeed = 0;

    Hand rightHand = MAIN_HAND;
    Hand leftHand = OFF_HAND;





    
    public void disableRightArm() {
        builder = currentAnimation.mutableCopy();
        var rightArm = builder.getPart("rightArm");
        assert rightArm != null;
        rightArm.setEnabled(false);
        currentAnimation = builder.build();
        DisabledRightArmTorsoModifier.enabled = true;
    }

    
    public void disableLeftArm() {
        builder = currentAnimation.mutableCopy();
        var leftArm = builder.getPart("leftArm");
        assert leftArm != null;
        leftArm.setEnabled(false);
        currentAnimation = builder.build();
        DisabledLeftArmTorsoModifier.enabled = true;
    }

    public void disableAnimation() {
        currentAnimation = blank_loop;
        currentAnimationId = "blank_loop";

    }

    public void disableOverlay() {
        currentOverlay = blank_loop;
        currentOverlayId = "blank_loop";

    }

    public void loopedToolAnimation(KeyframeAnimation overlay, KeyframeAnimation overlay_sneak, String id, ClientConfig.AnimationConfig getconfig, int fade, float speed, int priority) {
        overlayFadeTime = fade;
        overlayAnimationSpeed = speed * getconfig.speedMultiplier;
        overlayPriority = priority;

        OverlayMirrorModifier.setEnabled(rightHand != MAIN_HAND);

        if (isInSneakingPose()) {
            currentOverlay = overlay_sneak;
            currentOverlayId = id + "_sneak";
        } else {
            currentOverlay = overlay;
            currentOverlayId = id;
        }

        if (!getconfig.enabled){
            disableOverlay();
        }
    }


    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {
        animate();
    }



    
    AdjustmentModifier RightBowModifier = new AdjustmentModifier((partName) -> {
        float rotationX = 0;
        float rotationY = 0;
        float rotationZ = 0;
        float offsetX = 0;
        float offsetY = 0;
        float offsetZ = 0;
        var pitch = getPitch();
        pitch = (float) Math.toRadians(pitch);
        var yaw = (float) Math.toRadians(hyaw-90);
        switch (partName) {
            case "rightArm" -> {
                rotationZ = -pitch;
                offsetY = pitch;
            }
            case "leftArm" -> {
                rotationZ = (float) (-pitch * 0.25);
                offsetY = -pitch;
            }


            default -> {
                return Optional.empty();
            }
        }

        return Optional.of(new AdjustmentModifier.PartModifier(
                new Vec3f(rotationX, rotationY, rotationZ),
                new Vec3f(offsetX, offsetY, offsetZ))
        );
    });
    AdjustmentModifier LeftBowModifier = new AdjustmentModifier((partName) -> {
        float rotationX = 0;
        float rotationY = 0;
        float rotationZ = 0;
        float offsetX = 0;
        float offsetY = 0;
        float offsetZ = 0;
        var pitch = getPitch();
        pitch = (float) Math.toRadians(pitch);
        var yaw = (float) Math.toRadians(hyaw-90);
        switch (partName) {
            case "leftArm" -> {
                rotationZ = pitch;
                offsetY = pitch;
            }
            case "rightArm" -> {
                rotationZ = (float) (pitch * 0.25);
                offsetY = -pitch;
            }


            default -> {
                return Optional.empty();
            }
        }

        return Optional.of(new AdjustmentModifier.PartModifier(
                new Vec3f(rotationX, rotationY, rotationZ),
                new Vec3f(offsetX, offsetY, offsetZ))
        );
    });

    AdjustmentModifier DisabledRightArmTorsoModifier = new AdjustmentModifier((partName) -> {
        float rotationX = 0;
        float rotationY = 0;
        float rotationZ = 0;
        float offsetX = 0;
        float offsetY = 0;
        float offsetZ = 0;

        if (partName.equals("rightArm")) {
            offsetX = torsoPos.getX();
            offsetY = torsoPos.getY();
            offsetZ = torsoPos.getZ();
            if (isInSneakingPose()){
                offsetY = torsoPos.getY() - 3;
            }
        } else {
            return Optional.empty();
        }
        return Optional.of(new AdjustmentModifier.PartModifier(
                new Vec3f(rotationX, rotationY, rotationZ),
                new Vec3f(offsetX, offsetY, offsetZ))
        );
    });

    AdjustmentModifier DisabledLeftArmTorsoModifier = new AdjustmentModifier((partName) -> {
        float rotationX = 0;
        float rotationY = 0;
        float rotationZ = 0;
        float offsetX = 0;
        float offsetY = 0;
        float offsetZ = 0;

        if (partName.equals("leftArm")) {
            offsetX = torsoPos.getX();
            offsetY = torsoPos.getY();
            offsetZ = torsoPos.getZ();
            if (isInSneakingPose()){
                offsetY = torsoPos.getY() - 3;
            }
        } else {
            return Optional.empty();
        }
        return Optional.of(new AdjustmentModifier.PartModifier(
                new Vec3f(rotationX, rotationY, rotationZ),
                new Vec3f(offsetX, offsetY, offsetZ))
        );
    });

    AdjustmentModifier OverlayRightArmTorsoModifier = new AdjustmentModifier((partName) -> {
        float rotationX = 0;
        float rotationY = 0;
        float rotationZ = 0;
        float offsetX = 0;
        float offsetY = 0;
        float offsetZ = 0;

        if (partName.equals("rightArm")) {
            offsetX = torsoPos.getX();
            offsetY = torsoPos.getY();
            offsetZ = torsoPos.getZ();
            if (isInSneakingPose()){
                offsetY = torsoPos.getY() - 3;
            }
        } else {
            return Optional.empty();
        }
        return Optional.of(new AdjustmentModifier.PartModifier(
                new Vec3f(rotationX, rotationY, rotationZ),
                new Vec3f(offsetX, offsetY, offsetZ))
        );
    });

    AdjustmentModifier OverlayLeftArmTorsoModifier = new AdjustmentModifier((partName) -> {
        float rotationX = 0;
        float rotationY = 0;
        float rotationZ = 0;
        float offsetX = 0;
        float offsetY = 0;
        float offsetZ = 0;

        if (partName.equals("leftArm")) {
            offsetX = torsoPos.getX();
            offsetY = torsoPos.getY();
            offsetZ = torsoPos.getZ();
            if (isInSneakingPose()){
                offsetY = torsoPos.getY() - 3;
            }
        } else {
            return Optional.empty();
        }
        return Optional.of(new AdjustmentModifier.PartModifier(
                new Vec3f(rotationX, rotationY, rotationZ),
                new Vec3f(offsetX, offsetY, offsetZ))
        );
    });

    AdjustmentModifier SuperRightArmTorsoModifier = new AdjustmentModifier((partName) -> {
        float rotationX = 0;
        float rotationY = 0;
        float rotationZ = 0;
        float offsetX = 0;
        float offsetY = 0;
        float offsetZ = 0;

        if (partName.equals("rightArm")) {
            offsetX = torsoPos.getX();
            offsetY = torsoPos.getY();
            offsetZ = torsoPos.getZ();
            if (isInSneakingPose()){
                offsetY = torsoPos.getY() - 3;
            }
        } else {
            return Optional.empty();
        }
        return Optional.of(new AdjustmentModifier.PartModifier(
                new Vec3f(rotationX, rotationY, rotationZ),
                new Vec3f(offsetX, offsetY, offsetZ))
        );
    });

    AdjustmentModifier SuperLeftArmTorsoModifier = new AdjustmentModifier((partName) -> {
        float rotationX = 0;
        float rotationY = 0;
        float rotationZ = 0;
        float offsetX = 0;
        float offsetY = 0;
        float offsetZ = 0;

        if (partName.equals("leftArm")) {
            offsetX = torsoPos.getX();
            offsetY = torsoPos.getY();
            offsetZ = torsoPos.getZ();
            if (isInSneakingPose()){
                offsetY = torsoPos.getY() - 3;
            }
        } else {
            return Optional.empty();
        }
        return Optional.of(new AdjustmentModifier.PartModifier(
                new Vec3f(rotationX, rotationY, rotationZ),
                new Vec3f(offsetX, offsetY, offsetZ))
        );
    });
    
    SpeedModifier OverlaySpeedModifier = new SpeedModifier();
    SpeedModifier AnimationSpeedModifier = new SpeedModifier();

    MirrorModifier OverlayMirrorModifier = new MirrorModifier();
    MirrorModifier AnimationMirrorModifier = new MirrorModifier();


    
    public void animate() {

        if (getMainArm() == Arm.LEFT) {
            rightHand = OFF_HAND;
            leftHand = MAIN_HAND;
        } else {
            rightHand = MAIN_HAND;
            leftHand = OFF_HAND;
        }

        ModifierLayer<IAnimation> animationContainer = modAnimationContainer;
        ModifierLayer<IAnimation> animationContainer2 = modAnimationContainer2;
        ModifierLayer<IAnimation> animationContainerSuper = modAnimationContainer2;

        AnimationSpeedModifier.speed = animationSpeed * config.animationSpeedMultiplier;
        OverlaySpeedModifier.speed = overlayAnimationSpeed * config.animationSpeedMultiplier;


        byaw = bodyYaw;
        hyaw = headYaw;


        Vec3d pos = getPos();
        vx = (float) (pos.x - lastPos.x);
        vy = (float) (pos.y - lastPos.y);
        vz = (float) (pos.z - lastPos.z);
        moveSpeed = sqrt(pow(vx, 2) + pow(vz, 2));
        turn = byaw - prevbyaw;
        Vector3f movementVector = new Vector3f((float) (pos.x - lastPos.x), 0, (float) (pos.z - lastPos.z));
        Vector3f lookVector = new Vector3f((float) cos(toRadians(bodyYaw + 90)), 0, (float) sin(toRadians(bodyYaw + 90)));




        boolean isMovingBackwards;
        isMovingBackwards = movementVector.length() > 0 && movementVector.dot(lookVector) < 0;


        currentOverlay = blank_loop;
        currentOverlayId = "blank_loop";
        overlayFadeTime = 10;
        overlayAnimationSpeed = 1;
        overlayPriority = 0;



        //walking
        if (moveSpeed < 0.23 && moveSpeed > 0 && !isMovingBackwards && !isInSneakingPose()) {
            if (((9 / 0.22) * moveSpeed) > 1) {
                animationSpeed = (float) ((9 / 0.22) * moveSpeed * config.getWalking().speedMultiplier);
            } else {
                animationSpeed = 2 * config.getWalking().speedMultiplier;
            }
            currentAnimation = walking;
            currentAnimationId = "walking";
            if (!config.getWalking().enabled) {
                disableAnimation();
            }
            fadeTime = 7;
            priority = 0;

            //walking backwards
        } else if (isMovingBackwards && !isInSneakingPose()) {
            if (((4 / 0.22) * moveSpeed) > 1) {
                animationSpeed = (float) ((4 / 0.22) * moveSpeed * config.getWalkingBackwards().speedMultiplier);
            } else {
                animationSpeed = 2 * config.getWalkingBackwards().speedMultiplier;
            }
            if (vy > 0) {
                animationSpeed = 0.1F;
            }
            currentAnimation = walking_backwards;
            currentAnimationId = "walking_backwards";
            if (!config.getWalkingBackwards().enabled) {
                disableAnimation();
            }
            fadeTime = 7;
            priority = 0;

            //running
        } else if (moveSpeed > 0.23 && isSprinting() && !isMovingBackwards && !isInSneakingPose()) {
            if (((3 / 0.28) * moveSpeed) > 1) {
                animationSpeed = (float) ((3 / 0.28) * moveSpeed * config.getRunning().speedMultiplier);
            } else {
                animationSpeed = 1 * config.getRunning().speedMultiplier;
            }
            currentAnimation = running;
            currentAnimationId = "running";
            if (!config.getRunning().enabled) {
                disableAnimation();
            }
            fadeTime = 2;
            priority = 0;


            //standing & turning
        } else if (moveSpeed == 0 && !isInSneakingPose()) {
            currentAnimation = idle_standing;
            currentAnimationId = "idle_standing";
            if (!config.getIdleStanding().enabled) {
                disableAnimation();
            }
            if (prevAnimationId.equals("idle_sneak")
                    || prevAnimationId.equals("walking_sneak")
                    || prevAnimationId.equals("jump")
                    ) {

                fadeTime = 1;
            } else {
                fadeTime = 10;
            }
            animationSpeed = config.getIdleStanding().speedMultiplier;

            priority = 0;

            if (turn < 0) {
                currentAnimation = turn_left;
                currentAnimationId = "turn_left";
                if (!config.getTurningStanding().enabled) {
                    disableAnimation();
                }
                if ((abs((((float) 1 / 2) * turn)) > 5)) {
                    animationSpeed = 2f * config.getTurningStanding().speedMultiplier;
                } else {
                    animationSpeed = abs((((float) 1 / 2) * turn) * config.getTurningStanding().speedMultiplier);
                }

            } else if (turn > 0) {
                currentAnimation = turn_right;
                currentAnimationId = "turn_right";
                if (!config.getTurningStanding().enabled) {
                    disableAnimation();
                }
                if ((abs((((float) 1 / 2) * turn)) > 5)) {
                    animationSpeed = 2f * config.getTurningStanding().speedMultiplier;
                } else {
                    animationSpeed = abs((((float) 1 / 2) * turn) * config.getTurningStanding().speedMultiplier);
                }


            }
            //sneaking
        } else if (isInSneakingPose() && moveSpeed == 0 && !isMovingBackwards) {
            currentAnimation = idle_sneak;
            currentAnimationId = "idle_sneak";
            if (!config.getIdleSneaking().enabled) {
                disableAnimation();
            }
            animationSpeed = 1 * config.getIdleSneaking().speedMultiplier;

            if (prevAnimationId.equals("walking_sneak") || prevAnimationId.equals("walking_sneak_backwards")) {
                fadeTime = 10;
            } else {
                fadeTime = 1;
            }
            priority = 0;
        } else if (isInSneakingPose() && moveSpeed > 0 && !isMovingBackwards) {
            currentAnimation = walking_sneak;
            currentAnimationId = "walking_sneak";
            if (!config.getIdleSneaking().enabled) {
                disableAnimation();
            }
            animationSpeed = (float) ((2 / 0.06) * moveSpeed * config.getWalkingSneak().speedMultiplier);
            if (animationSpeed < 1) {
                animationSpeed = 1 * config.getWalkingSneak().speedMultiplier;
            }
            if (prevAnimationId.equals("idle_sneak") || prevAnimationId.equals("walking_sneak_backwards")) {
                fadeTime = 7;
            } else {
                fadeTime = 1;
            }
            priority = 0;

        } else if (isInSneakingPose() && moveSpeed > 0 && isMovingBackwards) {
            currentAnimation = walking_sneak_backwards;
            currentAnimationId = "walking_sneak_backwards";
            if (!config.getWalkingSneakBackwards().enabled) {
                disableAnimation();
            }
            animationSpeed = (float) ((2 / 0.06) * moveSpeed * config.getWalkingSneakBackwards().speedMultiplier);
            if (animationSpeed < 1) {
                animationSpeed = 1 * config.getWalkingSneakBackwards().speedMultiplier;
            }
            if (prevAnimationId.equals("idle_sneak")
                    || prevAnimationId.equals("walking_sneak")) {
                fadeTime = 7;
            } else {
                fadeTime = 1;
            }
            priority = 0;

        }


        //jumping
        if (vy > 0 && !isOnGround()) {
            //fadeTime = 5;
            if (currentAnimationId.equals("walking")) {
                animationSpeed = 4;
            } else {
                animationSpeed = 1;
            }
            prevJumpingAnimationId = currentAnimationId;
            prevJumpingAnimation = currentAnimation;
            prevJumpingAnimationSpeed = animationSpeed;

            priority = 0;

        }

        //flying
        vyfly = (double)Math.round(vy * 1000d) / 1000d;
        if ((vyfly == 0.0 || vyfly == 0.375 || vyfly == -0.375) && !isOnGround() && !isInsideWaterOrBubbleColumn()) {
            flychecker++;


        } else if (vyfly < -0.375 || vyfly > 0.375 || isOnGround()) {
            flychecker = 0;
        }
        if (flychecker > 10) {
            currentAnimation = idle_creative_flying;
            currentAnimationId = "idle_creative_flying";
            if (!config.getIdleCreativeFlying().enabled) {
                disableAnimation();
            }
            fadeTime = 5;
            animationSpeed = 1 * config.getIdleCreativeFlying().speedMultiplier;
            priority = 0;

        }

//        LOGGER.info(currentAnimationId);
//        LOGGER.info(String.valueOf(vyfly));


        //falling
        if (vy < -0.6 && !hasVehicle() && !isOnGround()) {
            currentAnimation = falling;
            currentAnimationId = "falling";
            if (!config.getFalling().enabled) {
                disableAnimation();
            }
            fadeTime = 8;
            animationSpeed = 1 * config.getFalling().speedMultiplier;
            priority = 0;
        } else if (!isOnGround()) {
            if (currentAnimationId.equals("walking")) {
                animationSpeed = 4;
            } else {
                animationSpeed = 1;
            }
            priority = 0;
        }

        //climbing
        if (!isOnGround() && !hasVehicle()) {
            if ((clientWorld.getBlockState(getBlockPos()).getBlock() instanceof LadderBlock || clientWorld.getBlockState(getBlockPos()).getBlock() instanceof VineBlock)) {

                animationSpeed = 3 * config.getClimbing().speedMultiplier;
                fadeTime = 7;
                priority = 0;
                if (!(getActiveItem().getItem() instanceof BowItem)){
                    blockStateString = String.valueOf(clientWorld.getBlockState(getBlockPos()));
                    if (blockStateString.contains("facing=north") || blockStateString.contains("south=true")) {
                        byaw = 0;

                    } else if (blockStateString.contains("facing=south") || blockStateString.contains("north=true")) {
                        byaw = 180;

                    } else if (blockStateString.contains("facing=west") || blockStateString.contains("east=true")) {
                        byaw = 270;

                    } else if (blockStateString.contains("facing=east") || blockStateString.contains("west=true")) {
                        byaw = 90;

                    }

                    byaw = ((byaw % 360) + 360) % 360;
                    hyaw = ((hyaw % 360) + 360) % 360;
                    setBodyYaw(byaw);
                    hyaw = hyaw - byaw;
                    hyaw = ((hyaw % 360) + 360) % 360;

                    if (hyaw > 90 && hyaw <= 180) {
                        setHeadYaw(byaw + 90);
                    } else if (hyaw > 180 && hyaw < 270) {
                        setHeadYaw(byaw + 270);
                    }
                }

                if (isClimbing() && vy > 0) {
                    if (isInSneakingPose()) {
                        currentAnimation = climbing_sneak;
                        currentAnimationId = "climbing_sneak";
                    } else {
                        currentAnimation = climbing;
                        currentAnimationId = "climbing";
                    }
                } else if (isClimbing() && vy < 0) {
                    currentAnimation = climbing_backwards;
                    currentAnimationId = "climbing_backwards";
                } else if (isInSneakingPose()) {
                    currentAnimation = idle_climbing_sneak;
                    currentAnimationId = "idle_climbing_sneak";
                } else {
                    currentAnimation = idle_climbing;
                    currentAnimationId = "idle_climbing";
                }
            } else if ((clientWorld.getBlockState(getBlockPos()).getBlock() instanceof TwistingVinesPlantBlock
                    || clientWorld.getBlockState(getBlockPos()).getBlock() instanceof WeepingVinesPlantBlock
                    || clientWorld.getBlockState(getBlockPos()).getBlock() instanceof TwistingVinesBlock
                    || clientWorld.getBlockState(getBlockPos()).getBlock() instanceof WeepingVinesBlock
                    || clientWorld.getBlockState(getBlockPos()).getBlock() instanceof ScaffoldingBlock)
                    ) {
                animationSpeed = 3 * config.getClimbing().speedMultiplier;
                fadeTime = 7;
                priority = 0;
                if (!(getActiveItem().getItem() instanceof BowItem)) {
                    byaw = ((float) toDegrees(atan2((getBlockPos().getZ() + 0.5 - pos.z), (getBlockPos().getX()) + 0.5 - pos.x)) - 90);
                    byaw = ((byaw % 360) + 360) % 360;
                    hyaw = ((hyaw % 360) + 360) % 360;
                    setBodyYaw(byaw);
                    hyaw = hyaw - byaw;
                    hyaw = ((hyaw % 360) + 360) % 360;

                    if (hyaw > 90 && hyaw <= 180) {
                        setHeadYaw(byaw + 90);
                    } else if (hyaw > 180 && hyaw < 270) {
                        setHeadYaw(byaw + 270);
                    }
                }

                if (isClimbing() && vy > 0) {
                    if (isInSneakingPose()) {
                        currentAnimation = climbing_sneak;
                        currentAnimationId = "climbing_sneak";
                    } else {
                        currentAnimation = climbing;
                        currentAnimationId = "climbing";
                    }
                } else if (isClimbing() && vy < 0) {
                    currentAnimation = climbing_backwards;
                    currentAnimationId = "climbing_backwards";
                } else if (isInSneakingPose()) {
                    currentAnimation = idle_climbing_sneak;
                    currentAnimationId = "idle_climbing_sneak";
                } else {
                    currentAnimation = idle_climbing;
                    currentAnimationId = "idle_climbing";
                }
            } else if (clientWorld.getBlockState(getBlockPos()).getBlock() instanceof PowderSnowBlock) {
                fadeTime = 7;
                priority = 0;

                if ((String.valueOf(getArmorItems())).contains("leather_boots")) {
                    if (vy > 0) {
                        if (isInSneakingPose()) {
                            currentAnimation = climbing_sneak;
                            currentAnimationId = "climbing_sneak";
                        } else {
                            currentAnimation = climbing;
                            currentAnimationId = "climbing";
                        }
                    } else if (vy < 0) {
                        currentAnimation = climbing_backwards;
                        currentAnimationId = "climbing_backwards";
                    } else if (isInSneakingPose()) {
                        currentAnimation = idle_climbing_sneak;
                        currentAnimationId = "idle_climbing_sneak";
                    } else {
                        currentAnimation = idle_climbing;
                        currentAnimationId = "idle_climbing";
                    }
                }
            }
            if (!config.getClimbing().enabled) {
                disableAnimation();
            }
        }

        //crawling
        if (isCrawling()) {
            if (moveSpeed > 0 && !isMovingBackwards){
                currentAnimation = crawling;
                currentAnimationId = "crawling";
                if (!config.getCrawling().enabled) {
                    disableAnimation();
                }
                animationSpeed = (float) ((2 / 0.06) * moveSpeed * config.getCrawling().speedMultiplier);
                if (animationSpeed < 1) {
                    animationSpeed = 1 * config.getCrawling().speedMultiplier;
                }
            } else if (moveSpeed > 0 && isMovingBackwards){
                currentAnimation = crawling_backwards;
                currentAnimationId = "crawling_backwards";
                if (!config.getCrawlingBackwards().enabled) {
                    disableAnimation();
                }
                animationSpeed = (float) ((2 / 0.06) * moveSpeed * config.getCrawlingBackwards().speedMultiplier);
                if (animationSpeed < 1) {
                    animationSpeed = 1 * config.getCrawlingBackwards().speedMultiplier;
                }
            } else {
                currentAnimation = idle_crawling;
                currentAnimationId = "idle_crawling";
                if (!config.getIdleCrawling().enabled) {
                    disableAnimation();
                }
                animationSpeed = 2 * config.getIdleCrawling().speedMultiplier;
            }

            fadeTime = 7;
            priority = 0;

        }

        //in water
        if ((isInsideWaterOrBubbleColumn() || isInLava()) && !isOnGround() && !isInSwimmingPose()){
            if (moveSpeed > 0 && !isMovingBackwards) {
                currentAnimation = forward_in_water;
                currentAnimationId = "forward_in_water";
                if (!config.getForwardInWater().enabled) {
                    disableAnimation();
                }
                animationSpeed = 1 * config.getForwardInWater().speedMultiplier;
                fadeTime = 7;
                priority = 0;
            } else if (moveSpeed > 0 && isMovingBackwards) {
                currentAnimation = backwards_in_water;
                currentAnimationId = "backwards_in_water";
                if (!config.getBackwardsInWater().enabled) {
                    disableAnimation();
                }
                animationSpeed = 1 * config.getBackwardsInWater().speedMultiplier;
                fadeTime = 7;
                priority = 0;
            } else if (vy > 0) {
                currentAnimation = up_in_water;
                currentAnimationId = "up_in_water";
                if (!config.getUpInWater().enabled) {
                    disableAnimation();
                }
                animationSpeed = 1 * config.getUpInWater().speedMultiplier;
                fadeTime = 7;
                priority = 0;
            } else {
                currentAnimation = idle_in_water;
                currentAnimationId = "idle_in_water";
                if (!config.getIdleInWater().enabled) {
                    disableAnimation();
                }
                fadeTime = 5;
                animationSpeed = 1 * config.getIdleInWater().speedMultiplier;
                priority = 0;
            }
        } else if (isInsideWaterOrBubbleColumn() && isInSwimmingPose()) {
            currentAnimation = swimming;
            currentAnimationId = "swimming";
            if (!config.getSwimming().enabled) {
                disableAnimation();
            }
            animationSpeed = 1 * config.getSwimming().speedMultiplier;
            fadeTime = 7;
            priority = 0;
        }


        //riding
        if (hasVehicle()) {
            if(getVehicle() instanceof MinecartEntity) {
                currentAnimation = minecart_idle;
                currentAnimationId = "minecart_idle";
                if (!config.getMinecartIdle().enabled) {
                    disableAnimation();
                }
                animationSpeed = 1 * config.getMinecartIdle().speedMultiplier;
                fadeTime = 2;
                priority = 0;
            } else if (getVehicle() instanceof HorseEntity
                    || getVehicle() instanceof SkeletonHorseEntity
                    || getVehicle() instanceof ZombieHorseEntity
                    || getVehicle() instanceof DonkeyEntity
                    || getVehicle() instanceof MuleEntity) {
                if (moveSpeed > 0 && !isMovingBackwards) {
                    currentAnimation = horse_running;
                    currentAnimationId = "horse_running";
                    if (!config.getHorseRunning().enabled) {
                        disableAnimation();
                    }
                } else {
                    currentAnimation = horse_idle;
                    currentAnimationId = "horse_idle";
                    if (!config.getHorseIdle().enabled) {
                        disableAnimation();
                    }
                }
                if (isUsingItem()) {
                    currentAnimation = horse_idle;
                    currentAnimationId = "horse_idle";
                    if (!config.getHorseIdle().enabled) {
                        disableAnimation();
                    }
                }

                animationSpeed = 1 * config.getHorseRunning().speedMultiplier;
                fadeTime = 1;
                priority = 0;
            } else if (getVehicle() instanceof BoatEntity || getVehicle() instanceof ChestBoatEntity) {
                boatString = String.valueOf(getVehicle());
                if (boatString.contains("Raft")) {
                    currentAnimation = boat1;
                    currentAnimationId = "boat1";
                } else {
                    currentAnimation = boat1;
                    currentAnimationId = "boat1";
                }
                if (!config.getBoat().enabled) {
                    disableAnimation();
                }
                animationSpeed = 1 * config.getBoat().speedMultiplier;
                fadeTime = 1;
                priority = 0;
            } else {
                currentAnimation = horse_idle;
                currentAnimationId = "horse_idle";
                if (!config.getHorseIdle().enabled) {
                    disableAnimation();
                }
                animationSpeed = 1 * config.getHorseIdle().speedMultiplier;
                fadeTime = 1;
                priority = 0;
            }
        }




        //elytra
        if (isFallFlying()) {
            currentAnimation = elytra;
            currentAnimationId = "elytra";
            if (!config.getElytra().enabled) {
                disableAnimation();
            }
            animationSpeed = 1 * config.getElytra().speedMultiplier;
            priority = 0;
            fadeTime = 5;
        }



        //handswinging
        if (handSwinging
                && (!(getMainHandStack().getItem() instanceof PickaxeItem) || !config.getPickaxe().enabled)
                && (!(getMainHandStack().getItem() instanceof SwordItem) || !config.getSwordAttack().enabled)) {
            if (rightHand == MAIN_HAND) {
                disableRightArm();
            } else {
                disableLeftArm();
            }

            currentAnimationId = "handswinging" + currentAnimationId;
            modifyId = "handswinging";
            fadeTime = 0;
            priority = 0;
        } else {
            modifyId = "";
        }



        //sword attack
        if (getMainHandStack().getItem() instanceof SwordItem && handSwinging && !isUsingItem()) {
            overlayAnimationSpeed = 1.4f * config.getSwordAttack().speedMultiplier;

            if (prevOverlayId.contains("sword_attack")) {
                overlayFadeTime = 0;
            } else {
                overlayFadeTime = 1;
            }
            overlayPriority = 1;

            OverlayMirrorModifier.setEnabled(rightHand != MAIN_HAND);

            if (swordSeq % 2 == 0) {

                if (isInSneakingPose()) {
                    currentOverlay = sword_attack_sneak;
                    currentOverlayId = "sword_attack_sneak";
                } else {
                    currentOverlay = sword_attack;
                    currentOverlayId = "sword_attack";
                }


            } else {
                if (isInSneakingPose()) {
                    currentOverlay = sword_attack_sneak2;
                    currentOverlayId = "sword_attack_sneak";
                } else {
                    currentOverlay = sword_attack2;
                    currentOverlayId = "sword_attack";
                }

            }

            if (!config.getSwordAttack().enabled) {
                disableOverlay();
            }


        //pickaxe
        } else if (getMainHandStack().getItem() instanceof PickaxeItem && handSwinging) {
            loopedToolAnimation(pickaxe, pickaxe_sneak, "pickaxe", config.getPickaxe(), 1, 2, 0);

            /*overlayFadeTime = 1;
            overlayAnimationSpeed = 2 * config.getPickaxe().speedMultiplier;
            overlayPriority = 0;

            OverlayMirrorModifier.setEnabled(rightHand != MAIN_HAND);

            if (isInSneakingPose()) {
                currentOverlay = pickaxe_sneak;
                currentOverlayId = "pickaxe_sneak";
            } else {
                currentOverlay = pickaxe;
                currentOverlayId = "pickaxe";
            }

            if (!config.getPickaxe().enabled){
                disableOverlay();
            }*/

        }

        //sleeping
        if (isSleeping()) {
            fadeTime = 2;
            animationSpeed = 2;
            priority = 0;
            disableAnimation();

            currentOverlay = sleeping;
            currentOverlayId = "sleeping";

            if (!config.getSleeping().enabled) {
                disableOverlay();
            }
        }

        if (getMainHandStack().getItem() instanceof CrossbowItem) {
            if (Objects.requireNonNull(getMainHandStack().getNbt()).getBoolean("Charged")){
                disableRightArm();
                disableLeftArm();
                modifyId = "crossbow";
                if (!prevModifyId.equals("crossbow")) {
                    fadeTime = 1;
                }
                priority = 0;
            }
        } else if (getOffHandStack().getItem() instanceof CrossbowItem) {
            if (Objects.requireNonNull(getOffHandStack().getNbt()).getBoolean("Charged")){
                disableRightArm();
                disableLeftArm();
                modifyId = "crossbow";
                if (!prevModifyId.equals("crossbow")) {
                    fadeTime = 1;
                }
                priority = 0;
            }

        }

        if (isUsingItem()) {
            if (getActiveItem().getItem().isFood()) {
                //eating
                overlayFadeTime = 9;
                overlayAnimationSpeed = 1f;

                overlayPriority = 0;


                if (getActiveHand().equals(rightHand)) {
                    currentOverlayId = "eating_right";
                    currentOverlay = eating_right;
                    OverlayRightArmTorsoModifier.enabled = true;



                } else if (getActiveHand().equals(leftHand)) {
                    currentOverlayId = "eating_left";
                    currentOverlay = eating_left;
                    OverlayLeftArmTorsoModifier.enabled = true;


                }


            } else if (isUsingSpyglass()) {
                //spyglass
                if (getActiveHand().equals(rightHand)) {
                    disableRightArm();
                    modifyId = "spy_right";
                } else {
                    disableLeftArm();
                    modifyId = "spy_left";
                }


                if (!modifyId.equals(prevModifyId)) {
                    fadeTime = 1;
                }
                priority = 0;
            } else if (getActiveItem().getItem() instanceof TridentItem) {
                //trident
                if (getActiveHand().equals(rightHand)) {
                    disableRightArm();
                    modifyId = "trident_right";
                } else {
                    disableLeftArm();
                    modifyId = "trident_left";
                }

                if (!modifyId.equals(prevModifyId)) {
                    fadeTime = 1;
                }
                priority = 0;
            } else if (getActiveItem().getItem() instanceof BrushItem) {
                //brush
                if (getActiveHand().equals(rightHand)) {
                    disableRightArm();
                    modifyId = "brush_right";
                } else {
                    disableLeftArm();
                    modifyId = "brush_left";
                }

                if (!modifyId.equals(prevModifyId)) {
                    fadeTime = 1;
                }
                priority = 0;
            } else if (getActiveItem().getItem() instanceof GoatHornItem) {
                //goat horn
                if (getActiveHand().equals(rightHand)) {
                    disableRightArm();
                    modifyId = "horn_right";
                } else {
                    disableLeftArm();
                    modifyId = "horn_left";
                }

                if (!modifyId.equals(prevModifyId)) {
                    fadeTime = 1;
                }

                priority = 0;
            } else if (getActiveItem().getItem() instanceof BowItem) {
                //bow
                if (hasVehicle() || isCrawling() || !config.getBow().enabled) {
                    disableRightArm();
                    disableLeftArm();
                    modifyId = "bow_idle";

                    if (!modifyId.equals(prevModifyId)) {
                        fadeTime = 1;
                    }
                } else {
                    overlayFadeTime = 6;
                    overlayAnimationSpeed = 1 * config.getBow().speedMultiplier;
                    overlayPriority = 1;


                    if (getActiveHand().equals(rightHand)) {
                        if (isInSneakingPose()){
                            currentOverlay = bow_sneak;
                            currentOverlayId = "right_bow_sneak";
                            overlayFadeTime = 1;
                        } else {
                            currentOverlay = bow_idle;
                            currentOverlayId = "right_bow_idle";
                        }
                        OverlayRightArmTorsoModifier.enabled = true;

                        RightBowModifier.enabled = true;
                        setBodyYaw(hyaw-90);
                        OverlayMirrorModifier.setEnabled(false);
                    } else if (getActiveHand().equals(leftHand)) {
                        if (isInSneakingPose()){
                            currentOverlay = bow_sneak;
                            currentOverlayId = "left_bow_sneak";
                            overlayFadeTime = 1;
                        } else {
                            currentOverlay = bow_idle;
                            currentOverlayId = "left_bow_idle";
                        }
                        OverlayLeftArmTorsoModifier.enabled = true;
                        LeftBowModifier.enabled = true;
                        setBodyYaw(hyaw+90);
                        OverlayMirrorModifier.setEnabled(true);
                    }
                }


            } else if (getActiveItem().getItem() instanceof Item) {
                //shield
                if (getActiveHand().equals(rightHand)) {
                    disableRightArm();
                    modifyId = "shield_right";
                } else {
                    disableLeftArm();
                    modifyId = "shield_left";
                }

                if (!modifyId.equals(prevModifyId)) {
                    fadeTime = 1;
                }
                priority = 0;
            } else if (getActiveItem().getItem() instanceof CrossbowItem) {
                //crossbow
                disableRightArm();
                disableLeftArm();


                modifyId = "crossbow_charging";
                if (!prevModifyId.equals("crossbow_charging")) {
                    fadeTime = 1;
                }
                priority = 0;
            }
        } else {
            modifyId = "";
        }



        if (!prevModifyId.isEmpty()) {
            fadeTime = 0;
        }



        if ((!Objects.equals(currentAnimationId, prevAnimationId) && priority >= prevPriority) || !animationContainer.isActive() || !Objects.equals(modifyId, prevModifyId)) {



            animationContainer.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(fadeTime, INOUTSINE), new KeyframeAnimationPlayer(currentAnimation));
            DisabledRightArmTorsoModifier.enabled = false;
            DisabledLeftArmTorsoModifier.enabled = false;

            SuperLeftArmTorsoModifier.enabled = false;
            SuperRightArmTorsoModifier.enabled = false;


            prevAnimationId = currentAnimationId;
            prevAnimation = currentAnimation;
            prevModifyId = modifyId;
            prevPriority = priority;
            modified = true;


        }



        lastPos = new Vec3d(pos.x, pos.y, pos.z);
        prevOnGround = isOnGround();


        if ((!Objects.equals(currentOverlayId, prevOverlayId) && overlayPriority >= prevOverlayPriority) || !animationContainer2.isActive()){


            RightBowModifier.enabled = false;
            LeftBowModifier.enabled = false;



            animationContainer2.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(overlayFadeTime, INOUTSINE), new KeyframeAnimationPlayer(currentOverlay));
            prevOverlayId = currentOverlayId;

            OverlayRightArmTorsoModifier.enabled = false;
            OverlayLeftArmTorsoModifier.enabled = false;

            SuperLeftArmTorsoModifier.enabled = false;
            SuperRightArmTorsoModifier.enabled = false;

            if (currentOverlayId.equals("sword_attack") || currentOverlayId.equals("sword_attack_sneak")) {
                swordSeq++;
            }
        }

        prevbyaw = byaw;

        if (animationContainer.isActive()) {

            var torso2 = animationContainer2.get3DTransform("torso", POSITION, 0, new Vec3f(0,0,0));
            if (Objects.equals(currentOverlayId, "blank_loop") || torso2.getY() == null) {
                torsoPos = animationContainer.get3DTransform("torso", POSITION, 0, new Vec3f(0, 0, 0));
            } else {
                torsoPos = torso2;
            }
            //LOGGER.info("POSITION: "+String.valueOf(torsoPos));


            var torsoRotation2 = animationContainer2.get3DTransform("torso", ROTATION, 0, new Vec3f(0, 0, 0));
            if (Objects.equals(currentOverlayId, "blank_loop") || torso2.getY() == null) {
                torsoRotation = animationContainer.get3DTransform("torso", ROTATION, 0, new Vec3f(0, 0, 0));
            } else {
                torsoRotation = torsoRotation2;
            }


            //LOGGER.info("ROTATION: "+String.valueOf(torsoAng));

        } else {
            torsoPos = new Vec3f(0, 0, 0);
        }



        /*try {
            if (!((Objects.requireNonNull(getMainHandStack().getNbt()).get("weapon_attributes")) == null)) {

                animationContainerSuper.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(overlayFadeTime, INOUTSINE),new KeyframeAnimationPlayer(blank_loop));
                SuperLeftArmTorsoModifier.enabled = true;
                SuperRightArmTorsoModifier.enabled = true;

            }
        } catch (Exception ignored) {}*/

    }

}