package net.mcvader.seriousplayeranimations.mixin;

import com.mojang.authlib.GameProfile;
import com.spiderfrog.oldcombatmod.client.OldCombatModClient;
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
import eu.midnightdust.swordblocking.SwordBlockingClient;
import net.elidhan.triggerhappy.TriggerHappy;
import net.elidhan.triggerhappy.TriggerHappyClient;
import net.mcvader.seriousplayeranimations.IExampleAnimatedPlayer;
import net.mcvader.seriousplayeranimations.compat.*;
import net.mcvader.seriousplayeranimations.config.ClientConfig;
import net.mcvader.seriousplayeranimations.torsoPosGetter;
import net.minecraft.block.*;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.BipedEntityModel;
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
import tschipp.carryon.common.carry.CarryOnData;
import tschipp.carryon.common.carry.CarryOnDataManager;

import java.util.Objects;
import java.util.Optional;

import static dev.kosmx.playerAnim.api.TransformType.POSITION;
import static dev.kosmx.playerAnim.api.TransformType.ROTATION;
import static dev.kosmx.playerAnim.core.util.Ease.INOUTSINE;
import static dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry.*;
import static java.lang.Math.*;
import static net.mcvader.seriousplayeranimations.SeriousPlayerAnimations.*;
import static net.minecraft.util.Hand.MAIN_HAND;
import static net.minecraft.util.Hand.OFF_HAND;


@Unique
@Mixin(AbstractClientPlayerEntity.class)

public abstract class SeriousPlayerAnimationsMixin extends PlayerEntity implements IExampleAnimatedPlayer, torsoPosGetter {


    @Shadow @Final public ClientWorld clientWorld;

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
    public KeyframeAnimation axe = null;
    public KeyframeAnimation axe_sneak = null;
    public KeyframeAnimation shovel = null;
    public KeyframeAnimation shovel_sneak = null;
    public KeyframeAnimation paraglider = null;
    public KeyframeAnimation generic_handswing = null;
    public KeyframeAnimation shield = null;
    public KeyframeAnimation shield_sneak = null;
    public KeyframeAnimation trident = null;

    public Vec3f torso2 = new Vec3f(0, 0, 0);
    public Vec3f torsoRotation2 = new Vec3f(0, 0, 0);
    public Vec3f torsoPos = new Vec3f(0, 0, 0);
    public Vec3f torsoRotation = new Vec3f(0, 0, 0);
    public Vec3f zero = new Vec3f(0, 0, 0);

    @Override
    public Vec3f getTorsoPos() { return this.torsoPos; }

    @Override
    public Vec3f getTorsoRotation() { return this.torsoRotation; }



    
    public KeyframeAnimation currentAnimation = null;
    public KeyframeAnimation currentOverlay = null;
    public KeyframeAnimation prevAnimation = null;
    public KeyframeAnimation.AnimationBuilder builder = null;





    
    public void reloadAnimationVariables() {
        sword_attack = getAnimation(new Identifier(MOD_ID, "sword_attack"));
        sword_attack2 = getAnimation(new Identifier(MOD_ID, "sword_attack2"));
        idle_standing = getAnimation(new Identifier(MOD_ID, "idle_standing"));
        idle_creative_flying = getAnimation(new Identifier(MOD_ID, "idle_creative_flying"));
        idle_creative_flying_item = getAnimation(new Identifier(MOD_ID, "idle_creative_flying_item"));
        walking = getAnimation(new Identifier(MOD_ID, "walking"));
        walking_backwards = getAnimation(new Identifier(MOD_ID, "walking_backwards"));
        running = getAnimation(new Identifier(MOD_ID, "running"));
        turn_left = getAnimation(new Identifier(MOD_ID, "turn_left"));
        turn_right = getAnimation(new Identifier(MOD_ID, "turn_right"));
        idle_sneak = getAnimation(new Identifier(MOD_ID, "idle_sneak"));
        walking_sneak = getAnimation(new Identifier(MOD_ID, "walking_sneak"));
        walking_sneak_backwards = getAnimation(new Identifier(MOD_ID, "walking_sneak_backwards"));
        sword_attack_sneak = getAnimation(new Identifier(MOD_ID, "sword_attack_sneak"));
        sword_attack_sneak2 = getAnimation(new Identifier(MOD_ID, "sword_attack_sneak2"));
        falling = getAnimation(new Identifier(MOD_ID, "falling"));
        blank_loop = getAnimation(new Identifier(MOD_ID, "blank_loop"));
        elytra = getAnimation(new Identifier(MOD_ID, "elytra"));
        eating_right = getAnimation(new Identifier(MOD_ID, "eating_right"));
        eating_left = getAnimation(new Identifier(MOD_ID, "eating_left"));
        eating_right_sneak = getAnimation(new Identifier(MOD_ID, "eating_right_sneak"));
        eating_left_sneak = getAnimation(new Identifier(MOD_ID, "eating_left_sneak"));
        idle_in_water = getAnimation(new Identifier(MOD_ID, "idle_in_water"));
        forward_in_water = getAnimation(new Identifier(MOD_ID, "forward_in_water"));
        backwards_in_water = getAnimation(new Identifier(MOD_ID, "backwards_in_water"));
        up_in_water = getAnimation(new Identifier(MOD_ID, "up_in_water"));
        swimming = getAnimation(new Identifier(MOD_ID, "swimming"));
        crawling = getAnimation(new Identifier(MOD_ID, "crawling"));
        idle_crawling = getAnimation(new Identifier(MOD_ID, "idle_crawling"));
        crawling_backwards = getAnimation(new Identifier(MOD_ID, "crawling_backwards"));
        climbing = getAnimation(new Identifier(MOD_ID, "climbing"));
        climbing_sneak = getAnimation(new Identifier(MOD_ID, "climbing_sneak"));
        idle_climbing = getAnimation(new Identifier(MOD_ID, "idle_climbing"));
        idle_climbing_sneak = getAnimation(new Identifier(MOD_ID, "idle_climbing_sneak"));
        climbing_backwards = getAnimation(new Identifier(MOD_ID, "climbing_backwards"));
        pickaxe = getAnimation(new Identifier(MOD_ID, "pickaxe"));
        pickaxe_sneak = getAnimation(new Identifier(MOD_ID, "pickaxe_sneak"));
        minecart_idle = getAnimation(new Identifier(MOD_ID, "minecart_idle"));
        minecart_pickaxe = getAnimation(new Identifier(MOD_ID, "minecart_pickaxe"));
        horse_idle = getAnimation(new Identifier(MOD_ID, "horse_idle"));
        horse_running = getAnimation(new Identifier(MOD_ID, "horse_running"));
        horse_pickaxe = getAnimation(new Identifier(MOD_ID, "horse_pickaxe"));
        boat1 = getAnimation(new Identifier(MOD_ID, "boat1"));
        eating = getAnimation(new Identifier(MOD_ID, "eating"));
        bow_idle = getAnimation(new Identifier(MOD_ID, "bow_idle"));
        bow_sneak = getAnimation(new Identifier(MOD_ID, "bow_sneak"));
        sleeping = getAnimation(new Identifier(MOD_ID, "sleeping"));
        axe = getAnimation(new Identifier(MOD_ID, "axe"));
        axe_sneak = getAnimation(new Identifier(MOD_ID, "axe_sneak"));
        shovel = getAnimation(new Identifier(MOD_ID, "shovel"));
        shovel_sneak = getAnimation(new Identifier(MOD_ID, "shovel_sneak"));
        paraglider = getAnimation(new Identifier(MOD_ID, "paraglider"));
        generic_handswing = getAnimation(new Identifier(MOD_ID, "generic_handswing"));
        shield = getAnimation(new Identifier(MOD_ID, "shield"));
        shield_sneak = getAnimation(new Identifier(MOD_ID, "shield_sneak"));
        trident = getAnimation(new Identifier(MOD_ID, "trident"));


    }


    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void init(ClientWorld world, GameProfile profile, CallbackInfo info) {


        PlayerAnimationAccess.getPlayerAnimLayer((AbstractClientPlayerEntity) (Object) this).addAnimLayer(1, modAnimationContainer);
        modAnimationContainer.addModifierLast(AnimationSpeedModifier);
        modAnimationContainer.addModifierLast(AnimationMirrorModifier);
        AnimationMirrorModifier.setEnabled(false);


        PlayerAnimationAccess.getPlayerAnimLayer((AbstractClientPlayerEntity) (Object) this).addAnimLayer(2, modAnimationContainer2);
        modAnimationContainer2.addModifierLast(RightBowModifier);
        RightBowModifier.enabled = false;
        modAnimationContainer2.addModifierLast(LeftBowModifier);
        LeftBowModifier.enabled = false;

        //modAnimationContainer2.addModifierLast(ShieldModifier);
        //ShieldModifier.enabled = false;

        modAnimationContainer2.addModifierLast(OverlaySpeedModifier);
        modAnimationContainer2.addModifierLast(OverlayMirrorModifier);
        OverlayMirrorModifier.setEnabled(false);

        if (PARAGLIDER_COMPAT) {
            modAnimationContainer2.addModifierLast(ParagliderModifier);
            ParagliderModifier.enabled = false;
        }



        reloadAnimationVariables();
        currentAnimation = idle_standing;
        currentOverlay = blank_loop;


    }


    @Override
    public ModifierLayer<IAnimation> seriousplayeranimations_getModAnimation() {
        return modAnimationContainer;
    }

    @Override
    public void disableArms(boolean b) {this.disableArms = b;}
    public boolean disableArms = false;

    @Override
    public void disableRightArmB(boolean b) {this.disableRightArmB = b;}
    public boolean disableRightArmB = false;

    @Override
    public void disableLeftArmB(boolean b) {this.disableLeftArmB = b;}
    public boolean disableLeftArmB = false;

    @Override
    public void disableMainArmB(boolean b) {this.disableMainArmB = b;}
    public boolean disableMainArmB = false;

    @Override
    public void disableOffArmB(boolean b) {this.disableOffArmB = b;}
    public boolean disableOffArmB = false;

    @Override
    public void disableAnimationB(boolean b) {this.disableAnimationB = b;}
    public boolean disableAnimationB = false;

    @Override
    public void disableOverlayB(boolean b) {this.disableOverlayB = b;}
    public boolean disableOverlayB = false;

    @Override
    public void armPosMain(BipedEntityModel.ArmPose pos) {this.armPosMain = pos;}
    public BipedEntityModel.ArmPose armPosMain = BipedEntityModel.ArmPose.EMPTY;

    @Override
    public void armPosOff(BipedEntityModel.ArmPose pos) {this.armPosOff = pos;}
    public BipedEntityModel.ArmPose armPosOff = BipedEntityModel.ArmPose.EMPTY;





    int fadeTime = 0;
    int overlayFadeTime = 0;
    float overlayAnimationSpeed = 1;
    int overlayPriority = 0;
    int prevOverlayPriority = 0;
    String currentAnimationId = "";
    String prevAnimationId = "";
    String currentOverlayId = "";
    String prevOverlayId = "";
    boolean modified = false;
    float animationSpeed = 1;
    float byaw = 0;
    float hyaw = 0;
    float vx = 0;
    float vy = 0;
    float vz = 0;
    double moveSpeed = 0;
    float turn = 0;
    boolean prevOnGround = false;
    int priority = 0;
    int prevPriority = 0;
    Vec3d lastPos = new Vec3d(0, 0, 0);
    boolean swordSeq = true;
    String modifyId = "";
    String prevModifyId = "";
    String blockStateString = "";
    String boatString = "";
    int flychecker = 0;
    double vyfly = 0;
    boolean isMovingBackwards = false;
    Item activeItem = null;
    boolean crouched = false;
    double bodyYawRadians = 0;
    int animationTick = 0;
    Hand rightHand = MAIN_HAND;
    Hand leftHand = OFF_HAND;
    Vec3d pos;
    float prevbyaw = 0;
    Item item;

    public void disableMainArm() {
        builder = currentAnimation.mutableCopy();
        var rightArm = builder.getPart("rightArm");
        assert rightArm != null;
        var leftArm = builder.getPart("leftArm");
        assert leftArm != null;

        if (rightHand == MAIN_HAND) {
            rightArm.pitch.setEnabled(false);
            rightArm.yaw.setEnabled(false);
            rightArm.roll.setEnabled(false);
            currentAnimation = builder.build();
        } else if (leftHand == MAIN_HAND) {
            leftArm.pitch.setEnabled(false);
            leftArm.yaw.setEnabled(false);
            leftArm.roll.setEnabled(false);
            currentAnimation = builder.build();
        }

    }

    public void disableOffArm() {
        builder = currentAnimation.mutableCopy();
        var rightArm = builder.getPart("rightArm");
        assert rightArm != null;
        var leftArm = builder.getPart("leftArm");
        assert leftArm != null;


        if (rightHand == OFF_HAND) {
            rightArm.pitch.setEnabled(false);
            rightArm.yaw.setEnabled(false);
            rightArm.roll.setEnabled(false);
            currentAnimation = builder.build();
        } else if (leftHand == OFF_HAND) {
            leftArm.pitch.setEnabled(false);
            leftArm.yaw.setEnabled(false);
            leftArm.roll.setEnabled(false);
            currentAnimation = builder.build();
        }

    }
    
    public void disableRightArm() {
        builder = currentAnimation.mutableCopy();
        var rightArm = builder.getPart("rightArm");
        assert rightArm != null;
        rightArm.pitch.setEnabled(false);
        rightArm.yaw.setEnabled(false);
        rightArm.roll.setEnabled(false);
        currentAnimation = builder.build();

    }

    
    public void disableLeftArm() {
        builder = currentAnimation.mutableCopy();
        var leftArm = builder.getPart("leftArm");
        assert leftArm != null;
        leftArm.pitch.setEnabled(false);
        leftArm.yaw.setEnabled(false);
        leftArm.roll.setEnabled(false);
        currentAnimation = builder.build();
    }



    public void disableBothArms() {
        builder = currentAnimation.mutableCopy();
        var rightArm = builder.getPart("rightArm");
        assert rightArm != null;
        var leftArm = builder.getPart("leftArm");
        assert leftArm != null;
        rightArm.pitch.setEnabled(false);
        rightArm.yaw.setEnabled(false);
        rightArm.roll.setEnabled(false);
        leftArm.pitch.setEnabled(false);
        leftArm.yaw.setEnabled(false);
        leftArm.roll.setEnabled(false);
        currentAnimation = builder.build();
    }

    public void disableRightArmOverlayPos() {
        builder = currentAnimation.mutableCopy();
        var rightArm = builder.getPart("rightArm");
        assert rightArm != null;
        rightArm.x.setEnabled(false);
        rightArm.y.setEnabled(false);
        rightArm.z.setEnabled(false);
        currentAnimation = builder.build();
    }

    public void disableLeftArmOverlayPos() {
        builder = currentAnimation.mutableCopy();
        var leftArm = builder.getPart("leftArm");
        assert leftArm != null;
        leftArm.x.setEnabled(false);
        leftArm.y.setEnabled(false);
        leftArm.z.setEnabled(false);
        currentAnimation = builder.build();
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
        if (getconfig.enabled) {

            overlayFadeTime = fade;
            overlayAnimationSpeed = speed * getconfig.speedMultiplier;
            overlayPriority = priority;
            OverlayMirrorModifier.setEnabled(rightHand != MAIN_HAND);

            if (crouched) {
                currentOverlay = overlay_sneak;
                currentOverlayId = id + "_sneak";
            } else {
                currentOverlay = overlay;
                currentOverlayId = id;
            }

        } else {
            genericHandswing();
        }

    }


    public void genericHandswing() {
        if (preferredHand.equals(MAIN_HAND)) {
            disableMainArm();
        } else if (preferredHand.equals(OFF_HAND)){
            disableOffArm();
        }
        currentAnimationId = "handswinging" + currentAnimationId;
        modifyId = "handswinging";
        fadeTime = 0;
        priority = 0;

    }


    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {
        animate();
    }

    /*AdjustmentModifier ShieldModifier = new AdjustmentModifier((partName) -> {
        float rotationX = 0;
        float rotationY = 0;
        float rotationZ = 0;
        float offsetX = 0;
        float offsetY = 0;
        float offsetZ = 0;
        var pitch = config.getTest().pitch;
        var yaw = config.getTest().yaw;
        var roll = config.getTest().roll;
        var x = config.getTest().x;
        var y = config.getTest().y;
        var z = config.getTest().z;
        if (partName.equals("rightItem")) {
            rotationX = pitch;
            rotationY = yaw;
            rotationZ = roll;
            offsetX = x;
            offsetY = y;
            offsetZ = z;
        } else {
            return Optional.empty();
        }

        return Optional.of(new AdjustmentModifier.PartModifier(
                new Vec3f(rotationX, rotationY, rotationZ),
                new Vec3f(offsetX, offsetY, offsetZ))
        );
    });*/


    AdjustmentModifier RightBowModifier = new AdjustmentModifier((partName) -> {
        float rotationX = 0;
        float rotationY = 0;
        float rotationZ = 0;
        float offsetX = 0;
        float offsetY = 0;
        float offsetZ = 0;
        var pitch = getPitch();
        pitch = (float) Math.toRadians(pitch);
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
        switch (partName) {
            case "leftArm" -> {
                rotationZ = pitch;
                offsetY = -pitch;
            }
            case "rightArm" -> {
                rotationZ = (float) (pitch * 0.25);
                offsetY = pitch;
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

    AdjustmentModifier ParagliderModifier = new AdjustmentModifier((partName) -> {
        float rotationX = 0;
        float rotationY = 0;
        float rotationZ = 0;
        float offsetX = 0;
        float offsetY = 0;
        float offsetZ = 0;

        switch (partName) {
            case "torso" -> {
                if(isMovingBackwards){
                    rotationX = (float) (-0.75f * moveSpeed);
                } else {
                    rotationX = (float) (0.75f * moveSpeed);

                }
            }
            case "rightLeg","leftLeg" -> {
                if(isMovingBackwards){
                    rotationX = (float) (-moveSpeed * 1.5);
                    offsetY = (float) (-moveSpeed * 3.5);
                    offsetZ = (float) (-moveSpeed * 7);
                } else {
                    rotationX = (float) (moveSpeed * 1.5);
                    offsetY = (float) (-moveSpeed * 3.5);
                    offsetZ = (float) (moveSpeed * 7);

                }
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
    
    SpeedModifier OverlaySpeedModifier = new SpeedModifier();
    SpeedModifier AnimationSpeedModifier = new SpeedModifier();

    MirrorModifier OverlayMirrorModifier = new MirrorModifier();
    MirrorModifier AnimationMirrorModifier = new MirrorModifier();

    ModifierLayer<IAnimation> animationContainer = modAnimationContainer;
    ModifierLayer<IAnimation> animationContainer2 = modAnimationContainer2;

    public void animate() {
        animationTick++;

        if (getMainArm() == Arm.LEFT) {
            rightHand = OFF_HAND;
            leftHand = MAIN_HAND;
        } else {
            rightHand = MAIN_HAND;
            leftHand = OFF_HAND;
        }


        AnimationSpeedModifier.speed = animationSpeed * config.animationSpeedMultiplier;
        OverlaySpeedModifier.speed = overlayAnimationSpeed * config.animationSpeedMultiplier;

        byaw = bodyYaw;
        hyaw = headYaw;

        pos = getPos();
        vx = (float) (pos.x - lastPos.x);
        vy = (float) (pos.y - lastPos.y);
        vz = (float) (pos.z - lastPos.z);
        moveSpeed = sqrt(vx*vx + vz*vz);
        turn = byaw - prevbyaw;
        bodyYawRadians = toRadians(bodyYaw + 90);
        Vector3f movementVector = new Vector3f(vx, 0, vz);
        Vector3f lookVector = new Vector3f((float) cos(bodyYawRadians), 0, (float) sin(bodyYawRadians));
        isMovingBackwards = movementVector.length() > 0 && movementVector.dot(lookVector) < 0;


        currentOverlay = blank_loop;
        currentOverlayId = "blank_loop";
        overlayFadeTime = 10;
        overlayAnimationSpeed = 1;
        overlayPriority = 0;

        crouched = isInSneakingPose();

        //walking
        if (moveSpeed < 0.23 && moveSpeed > 0 && !isMovingBackwards && !crouched) {
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
        } else if (isMovingBackwards && !crouched) {
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
        } else if (moveSpeed > 0.23 && isSprinting() && !isMovingBackwards && !crouched) {
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
        } else if (moveSpeed == 0 && !crouched) {

            if (turn != 0) {
                currentAnimation = (turn < 0) ? turn_left : turn_right;
                currentAnimationId = (turn < 0) ? "turn_left" : "turn_right";
                priority = 0;

                if (!config.getTurningStanding().enabled) {
                    disableAnimation();
                }
                if ((abs((((float) 1 / 2) * turn)) > 5)) {
                    animationSpeed = 2f * config.getTurningStanding().speedMultiplier;
                } else {
                    animationSpeed = abs((((float) 1 / 2) * turn) * config.getTurningStanding().speedMultiplier);
                }

            } else {
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
            }

            //sneaking
        } else if (crouched && moveSpeed == 0 && !isMovingBackwards) {
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
        } else if (crouched && moveSpeed > 0 && !isMovingBackwards) {
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

        } else if (crouched && moveSpeed > 0) {
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
            animationSpeed = currentAnimationId.equals("walking") ? 4 : 1;
            priority = 0;
        }

        //flying
        vyfly = Math.round(vy * 1000.0) / 1000.0;
        if ((vyfly == 0.0 || Math.abs(vyfly) == 0.375) && !isOnGround() && !isInsideWaterOrBubbleColumn()) {
            flychecker++;
        } else if (Math.abs(vyfly) > 0.375 || isOnGround()) {
            flychecker = 0;
        }

        if (flychecker > 10) {
            currentAnimation = idle_creative_flying;
            currentAnimationId = "idle_creative_flying";
            if (!config.getIdleCreativeFlying().enabled) {
                disableAnimation();
            }
            fadeTime = 5;
            animationSpeed = config.getIdleCreativeFlying().speedMultiplier;
            priority = 0;
        }


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
            animationSpeed = (currentAnimationId.equals("walking")) ? 4 : 1;
            priority = 0;
        }

        //climbing
        if (!isOnGround() && !hasVehicle()) {
            var block = clientWorld.getBlockState(getBlockPos()).getBlock();
            if ((block instanceof LadderBlock || block instanceof VineBlock)) {
                animationSpeed = 3 * config.getClimbing().speedMultiplier;
                fadeTime = 7;
                priority = 0;
                if (!(getActiveItem().getItem() instanceof BowItem)){
                    blockStateString = String.valueOf(clientWorld.getBlockState(getBlockPos()));
                    byaw = getBodyYaw();
                    hyaw = getHeadYaw();
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
                    if (crouched) {
                        currentAnimation = climbing_sneak;
                        currentAnimationId = "climbing_sneak";
                    } else {
                        currentAnimation = climbing;
                        currentAnimationId = "climbing";
                    }
                } else if (isClimbing() && vy < 0) {
                    currentAnimation = climbing_backwards;
                    currentAnimationId = "climbing_backwards";
                } else if (crouched) {
                    currentAnimation = idle_climbing_sneak;
                    currentAnimationId = "idle_climbing_sneak";
                } else {
                    currentAnimation = idle_climbing;
                    currentAnimationId = "idle_climbing";
                }
            } else if ((block instanceof TwistingVinesPlantBlock
                    || block instanceof WeepingVinesPlantBlock
                    || block instanceof TwistingVinesBlock
                    || block instanceof WeepingVinesBlock
                    || block instanceof ScaffoldingBlock)
                    ) {
                animationSpeed = 3 * config.getClimbing().speedMultiplier;
                fadeTime = 7;
                priority = 0;
                if (!(getActiveItem().getItem() instanceof BowItem)) {
                    byaw = ((float) toDegrees(atan2((getBlockPos().getZ() + 0.5 - pos.z), (getBlockPos().getX()) + 0.5 - pos.x)) - 90);
                    hyaw = headYaw;
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

                if (isClimbing()) {
                    if (vy > 0) {
                        currentAnimation = crouched ? climbing_sneak : climbing;
                        currentAnimationId = crouched ? "climbing_sneak" : "climbing";
                    } else if (vy < 0) {
                        currentAnimation = climbing_backwards;
                        currentAnimationId = "climbing_backwards";
                    } else {
                        currentAnimation = crouched ? idle_climbing_sneak : idle_climbing;
                        currentAnimationId = crouched ? "idle_climbing_sneak" : "idle_climbing";
                    }
                } else {
                    currentAnimation = crouched ? idle_climbing_sneak : idle_climbing;
                    currentAnimationId = crouched ? "idle_climbing_sneak" : "idle_climbing";
                }

            } else if (block instanceof PowderSnowBlock) {
                fadeTime = 7;
                priority = 0;

                if ((String.valueOf(getArmorItems())).contains("leather_boots")) {
                    if (vy > 0) {
                        if (crouched) {
                            currentAnimation = climbing_sneak;
                            currentAnimationId = "climbing_sneak";
                        } else {
                            currentAnimation = climbing;
                            currentAnimationId = "climbing";
                        }
                    } else if (vy < 0) {
                        currentAnimation = climbing_backwards;
                        currentAnimationId = "climbing_backwards";
                    } else if (crouched) {
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
            } else if (moveSpeed > 0){
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
            } else if (moveSpeed > 0) {
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
            var vehicle = getVehicle();
            if(vehicle instanceof MinecartEntity) {
                currentAnimation = minecart_idle;
                currentAnimationId = "minecart_idle";
                if (!config.getMinecartIdle().enabled) {
                    disableAnimation();
                }
                animationSpeed = 1 * config.getMinecartIdle().speedMultiplier;
                fadeTime = 2;
                priority = 0;
            } else if (vehicle instanceof HorseEntity
                    || vehicle instanceof SkeletonHorseEntity
                    || vehicle instanceof ZombieHorseEntity
                    || vehicle instanceof DonkeyEntity
                    || vehicle instanceof MuleEntity) {
                if (moveSpeed > 0 && !isMovingBackwards) {
                    currentAnimation = horse_running;
                    currentAnimationId = "horse_running";
                    if (!config.getHorseRunning().enabled) {
                        disableAnimation();
                    }
                } else {
                    fadeTime = 5;
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
            } else if (vehicle instanceof BoatEntity || vehicle instanceof ChestBoatEntity) {
                boatString = String.valueOf(getVehicle());
                currentAnimation = boat1;
                currentAnimationId = "boat1";
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
        if(handSwinging) {
            //sword attack

            if ((getMainHandStack().getItem() instanceof SwordItem || getMainHandStack().getItem() instanceof TridentItem)&& !isUsingItem() && preferredHand.equals(MAIN_HAND)) {
                overlayAnimationSpeed = 1.4f * config.getSwordAttack().speedMultiplier;

                overlayFadeTime = 0;
                overlayPriority = 1;

                OverlayMirrorModifier.setEnabled(rightHand != MAIN_HAND);

                if (swordSeq) {

                    if (crouched) {
                        currentOverlay = sword_attack_sneak;
                        currentOverlayId = "sword_attack_sneak";
                    } else {
                        currentOverlay = sword_attack;
                        currentOverlayId = "sword_attack";
                    }


                } else {
                    if (crouched) {
                        currentOverlay = sword_attack_sneak2;
                        currentOverlayId = "sword_attack_sneak";
                    } else {
                        currentOverlay = sword_attack2;
                        currentOverlayId = "sword_attack";
                    }

                }

                if (!config.getSwordAttack().enabled) {
                    disableOverlay();
                    genericHandswing();
                }


                //pickaxe
            } else if (getMainHandStack().getItem() instanceof PickaxeItem && preferredHand.equals(MAIN_HAND)) {
                loopedToolAnimation(pickaxe, pickaxe_sneak, "pickaxe", config.getPickaxe(), 1, 2, 0);
                //axe
            } else if (getMainHandStack().getItem() instanceof AxeItem && preferredHand.equals(MAIN_HAND)) {
                loopedToolAnimation(axe, axe_sneak, "axe", config.getAxe(), 1, 1.5f, 0);
                //shovel
            } else if (getMainHandStack().getItem() instanceof ShovelItem && preferredHand.equals(MAIN_HAND)) {
                loopedToolAnimation(shovel, shovel_sneak, "shovel", config.getShovel(), 1, 1.5f, 0);

            } else {
                genericHandswing();
            }
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


        if (isUsingItem()) {
            activeItem = getActiveItem().getItem();
            if (activeItem.isFood()) {
                //eating


                if (config.getEating().enabled) {
                    overlayFadeTime = 9;
                    overlayAnimationSpeed = 1 * config.getEating().speedMultiplier;
                    overlayPriority = 0;



                    if (getActiveHand().equals(rightHand)) {
                        currentOverlay = eating_right;
                        currentOverlayId = "eating_right";

                        disableRightArmOverlayPos();
                        OverlayMirrorModifier.setEnabled(false);
                    } else if (getActiveHand().equals(leftHand)) {
                        currentOverlay = eating_right;
                        currentOverlayId = "eating_left";

                        disableLeftArmOverlayPos();
                        OverlayMirrorModifier.setEnabled(true);
                    }






                }

            } else if (isUsingSpyglass()) {
                //spyglass
                if (getActiveHand().equals(MAIN_HAND)) {
                    disableMainArmB = true;
                    //modifyId = "spy_right";
                } else {
                    disableOffArmB = true;
                    //modifyId = "spy_left";
                }
                fadeTime = 1;
                priority = 0;
            } else if (activeItem instanceof TridentItem) {
                //trident
                if (config.getTrident().enabled){
                    overlayFadeTime = 5;
                    overlayAnimationSpeed = config.getTrident().speedMultiplier;
                    overlayPriority = 0;

                    if (getActiveHand().equals(rightHand)) {
                        if (crouched){
                            disableRightArmOverlayPos();
                            disableLeftArmOverlayPos();
                        } else {
                            currentOverlay = trident;
                            currentOverlayId = "right_trident";
                        }
                        setBodyYaw(hyaw+55);
                        OverlayMirrorModifier.setEnabled(false);
                    } else if (getActiveHand().equals(leftHand)) {
                        if (crouched){
                            disableRightArmOverlayPos();
                            disableLeftArmOverlayPos();
                        } else {
                            currentOverlay = trident;
                            currentOverlayId = "left_trident";
                        }
                        setBodyYaw(hyaw-55);
                        OverlayMirrorModifier.setEnabled(true);
                    }

                } else {
                    if (getActiveHand().equals(MAIN_HAND)) {
                        disableMainArmB = true;
                        //modifyId = "trident_right";
                    } else {
                        disableOffArmB = true;
                        //modifyId = "trident_left";
                    }
                    priority = 0;
                    fadeTime = 1;
                }


            } else if (activeItem instanceof BrushItem) {
                //brush
                if (getActiveHand().equals(MAIN_HAND)) {
                    disableMainArmB = true;
                    //modifyId = "brush_right";
                } else {
                    disableOffArmB = true;
                    //modifyId = "brush_left";
                }


                priority = 0;
            } else if (activeItem instanceof GoatHornItem) {
                //goat horn
                if (getActiveHand().equals(MAIN_HAND)) {
                    disableMainArmB = true;
                    //modifyId = "horn_right";
                } else {
                    disableOffArmB = true;
                    //modifyId = "horn_left";
                }
                fadeTime = 1;
                priority = 0;
            } else if (activeItem instanceof BowItem) {
                //bow
                if (hasVehicle() || isCrawling() || !config.getBow().enabled) {
                    disableRightArm();
                    disableLeftArm();
                    modifyId = "bow_idle";
                    fadeTime = 1;


                } else {
                    overlayFadeTime = 6;
                    overlayAnimationSpeed = 1 * config.getBow().speedMultiplier;
                    overlayPriority = 0;


                    if (getActiveHand().equals(rightHand)) {
                        if (crouched){
                            currentOverlay = bow_sneak;
                            currentOverlayId = "right_bow_sneak";
                            overlayFadeTime = 1;
                        } else {
                            currentOverlay = bow_idle;
                            currentOverlayId = "right_bow_idle";
                        }
                        disableRightArmOverlayPos();

                        RightBowModifier.enabled = true;
                        setBodyYaw(hyaw-90);
                        OverlayMirrorModifier.setEnabled(false);
                    } else if (getActiveHand().equals(leftHand)) {
                        if (crouched){
                            currentOverlay = bow_sneak;
                            currentOverlayId = "left_bow_sneak";
                            overlayFadeTime = 1;
                        } else {
                            currentOverlay = bow_idle;
                            currentOverlayId = "left_bow_idle";
                        }
                        disableLeftArmOverlayPos();
                        LeftBowModifier.enabled = true;
                        setBodyYaw(hyaw+90);
                        OverlayMirrorModifier.setEnabled(true);
                    }
                }


            } else if (activeItem instanceof ShieldItem) {
                //shield
                if (config.getShield().enabled) {
                    overlayFadeTime = 5;
                    overlayAnimationSpeed = config.getShield().speedMultiplier;
                    overlayPriority = 0;

                    //ShieldModifier.enabled = true;


                    if (getActiveHand().equals(rightHand)) {
                        if (crouched){
                            currentOverlay = shield_sneak;
                            currentOverlayId = "right_shield_sneak";
                            overlayFadeTime = 1;
                        } else {
                            currentOverlay = shield;
                            currentOverlayId = "right_shield";
                        }

                        OverlayMirrorModifier.setEnabled(false);
                    } else if (getActiveHand().equals(leftHand)) {
                        if (crouched){
                            currentOverlay = shield_sneak;
                            currentOverlayId = "left_shield_sneak";
                            overlayFadeTime = 1;
                        } else {
                            currentOverlay = shield;
                            currentOverlayId = "left_shield";
                        }


                        OverlayMirrorModifier.setEnabled(true);
                    }
                } else {
                    if (getActiveHand().equals(MAIN_HAND)) {
                        disableMainArmB = true;
                        //modifyId = "shield_right";
                    } else {
                        disableOffArmB = true;
                        //modifyId = "shield_left";
                    }
                    priority = 0;
                    fadeTime = 1;
                }


            } else if (activeItem instanceof CrossbowItem) {
                //crossbow
                disableArms = true;


                //modifyId = "crossbow_charging";
                //priority = 0;
            } else if (SUPPLEMENTARIES_COMPAT) {
                //flute
                if (SupplementariesFluteCheck.check(activeItem)){
                    disableArms = true;
                    //modifyId = "flute";
                    //priority = 0;
                    //fadeTime = 1;
                }
            } else {

                if (getActiveHand().equals(MAIN_HAND)) {
                    disableMainArmB = true;
                    //modifyId = "general_right";
                } else {
                    disableOffArmB = true;
                    //modifyId = "general_left";
                }

                priority = 0;
            }
        } else {
            modifyId = "";
        }

        if (!getMainHandStack().isEmpty()) {
            item = getMainHandStack().getItem();

            if (PARAGLIDER_COMPAT) {
                if (getMainHandStack().hasNbt()) {
                    if (Objects.requireNonNull(getMainHandStack().getNbt()).contains("Paragliding")) {
                        if (Objects.requireNonNull(getMainHandStack().getNbt()).getBoolean("Paragliding")) {
                            currentOverlay = paraglider;
                            currentOverlayId = "paraglider";
                            ParagliderModifier.enabled = true;
                            fadeTime = 5;
                            animationSpeed = 1;
                            priority = 0;
                            disableAnimation();
                            disableArms = true;


                        }
                    }

                }
            }

            if (SWORDBLOCKING_COMPAT) {
                if (SwordBlockingCheck.check(this)) {
                    disableOverlay();
                    disableMainArmB = true;
                    //modifyId = "sword_block";
                    //if (!Objects.equals(prevModifyId, modifyId)) {
                    //    fadeTime = 1;
                    //}
                }
            }

            if (OLDCOMBATMOD_COMPAT) {
                if (OldCombatModCheck.check(this)) {
                    disableOverlay();
                    disableMainArmB = true;
                    //modifyId = "sword_block";
                    //if (!Objects.equals(prevModifyId, modifyId)) {
                    //    fadeTime = 1;
                    //}
                }
            }


            if (IMMERSIVE_MELODIES_COMPAT) {
                if (ImmersiveMelodiesItemCheck.check(item)) {
                    disableArms = true;
                }
            }

            if(ULTRACRAFT_COMPAT) {
                if (UltracraftCheck.isGenericGun(item)) {
                    disableMainArmB = true;
                    //modifyId = "generic_gun";
                    //if (!Objects.equals(prevModifyId, modifyId)) {
                    //    fadeTime = 1;
                    //}
                } else if (UltracraftCheck.isNailgun(item)) {
                    disableArms = true;
                }
            }

            if (armPosMain.equals(BipedEntityModel.ArmPose.CROSSBOW_HOLD) || armPosMain.equals(BipedEntityModel.ArmPose.CROSSBOW_CHARGE)) {
                disableArms = true;
            } else if (armPosMain.equals(BipedEntityModel.ArmPose.BOW_AND_ARROW) && !(item instanceof BowItem)) {
                disableArms = true;
            }

            if (TRIGGERHAPPY_COMPAT) {
                if (TriggerHappyCheck.checkOneHanded(armPosMain)) {
                    disableMainArmB = true;
                } else if (TriggerHappyCheck.checkTwoHanded(armPosMain)) {
                    disableArms = true;
                }
            }
        }



        if (!getOffHandStack().isEmpty()) {
            item = getOffHandStack().getItem();

            if (IMMERSIVE_MELODIES_COMPAT) {
                if (ImmersiveMelodiesItemCheck.check(item)) {
                    disableArms = true;
                }
            }



            if (armPosOff.equals(BipedEntityModel.ArmPose.CROSSBOW_HOLD) || armPosOff.equals(BipedEntityModel.ArmPose.CROSSBOW_CHARGE)) {
                disableArms = true;
            } else if (armPosOff.equals(BipedEntityModel.ArmPose.BOW_AND_ARROW) && !(item instanceof BowItem)) {
                disableArms = true;
            }

            if (TRIGGERHAPPY_COMPAT) {
                if (TriggerHappyCheck.checkOneHanded(armPosOff)) {
                    disableOffArmB = true;
                } else if (TriggerHappyCheck.checkTwoHanded(armPosOff)) {
                    disableArms = true;
                }
            }






        }



        if (CARRYON_COMPAT) {
            CarryOnCheck.check((AbstractClientPlayerEntity) (Object) this);
        }




        if (disableRightArmB) {
            disableRightArm();
            disableRightArmB = false;
            modifyId = "disable_right";
            if (!Objects.equals(prevModifyId, modifyId)) {
                fadeTime = 1;
            }
        }
        if (disableLeftArmB) {
            disableLeftArm();
            disableLeftArmB = false;
            modifyId = "disable_left";
            if (!Objects.equals(prevModifyId, modifyId)) {
                fadeTime = 1;
            }
        }
        if (disableMainArmB) {
            disableMainArm();
            disableMainArmB = false;
            modifyId = "disable_main";
            if (!Objects.equals(prevModifyId, modifyId)) {
                fadeTime = 1;
            }
        }
        if (disableOffArmB) {
            disableOffArm();
            disableOffArmB = false;
            modifyId = "disable_off";
            if (!Objects.equals(prevModifyId, modifyId)) {
                fadeTime = 1;
            }
        }
        if (disableArms) {
            disableBothArms();
            disableArms = false;
            modifyId = "disable_both";
            if (!Objects.equals(prevModifyId, modifyId)) {
                fadeTime = 1;
            }

        }
        if (disableAnimationB) {
            disableAnimation();
            disableAnimationB = false;
        }
        if (disableOverlayB) {
            disableOverlay();
            disableAnimationB = false;
        }




        if ((!Objects.equals(currentAnimationId, prevAnimationId) && priority >= prevPriority) || !animationContainer.isActive() || !Objects.equals(modifyId, prevModifyId)) {

            animationContainer.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(fadeTime, INOUTSINE), new KeyframeAnimationPlayer(currentAnimation));
            animationTick = 0;

            prevAnimationId = currentAnimationId;
            prevAnimation = currentAnimation;
            prevModifyId = modifyId;
            prevPriority = priority;
            modified = true;
        }

        lastPos = new Vec3d(pos.x, pos.y, pos.z);
        prevOnGround = isOnGround();
        prevbyaw = byaw;

        if ((!Objects.equals(currentOverlayId, prevOverlayId) && overlayPriority >= prevOverlayPriority) || !animationContainer2.isActive()){
            RightBowModifier.enabled = false;
            LeftBowModifier.enabled = false;
            //ShieldModifier.enabled = false;

            if(prevOverlayId.contains("trident") && !currentOverlayId.contains("trident")) {
                overlayFadeTime = 3;
            }

            if(PARAGLIDER_COMPAT) {
                if (!currentOverlayId.equals("paraglider")) {
                    ParagliderModifier.enabled = false;
                }
            }


            if (currentOverlayId.equals("sword_attack") || currentOverlayId.equals("sword_attack_sneak")) {
                swordSeq = !swordSeq;
                animationContainer2.setAnimation(null);
                animationContainer2.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(overlayFadeTime, INOUTSINE), new KeyframeAnimationPlayer(currentOverlay));

            } else {
                animationContainer2.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(overlayFadeTime, INOUTSINE), new KeyframeAnimationPlayer(currentOverlay), true);
            }




            prevOverlayId = currentOverlayId;
            prevOverlayPriority = overlayPriority;
        }

        if (animationContainer.isActive()){
            torso2 = animationContainer2.get3DTransform("torso", POSITION, 0, zero);
            if (torso2.getZ() == 0 && torso2.getY() == 0) {
                torsoPos = animationContainer.get3DTransform("torso", POSITION, 0, zero);
            } else {
                torsoPos = torso2;
            }

            torsoRotation2 = animationContainer2.get3DTransform("torso", ROTATION, 0,zero);
            if (torsoRotation2.getX() == 0) {
                torsoRotation = animationContainer.get3DTransform("torso", ROTATION, 0, zero);
            } else {
                torsoRotation = torsoRotation2;
            }

        }

        //LOGGER.info("ROTATION: " + animationContainer2.get3DTransform("rightItem", ROTATION, 0, zero));

        //LOGGER.info("POSITION: " + animationContainer2.get3DTransform("rightItem", POSITION, 0, zero));
        //LOGGER.info(String.valueOf(armPosMain));
        //LOGGER.info(String.valueOf(armPosOff));


    }


}