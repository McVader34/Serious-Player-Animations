package net.mcvader.seriousplayeranimations.mixin;

import com.mojang.authlib.GameProfile;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import dev.kosmx.playerAnim.api.layered.modifier.SpeedModifier;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.core.util.Vec3f;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.mcvader.seriousplayeranimations.IExampleAnimatedPlayer;
import net.mcvader.seriousplayeranimations.torsoPosGetter;
import net.minecraft.block.*;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.entity.mob.ZombieHorseEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.SwordItem;
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

import static dev.kosmx.playerAnim.api.TransformType.POSITION;
import static dev.kosmx.playerAnim.api.TransformType.ROTATION;
import static dev.kosmx.playerAnim.core.util.Ease.LINEAR;
import static java.lang.Math.*;
import static net.minecraft.item.Items.*;
import static net.minecraft.util.Hand.MAIN_HAND;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class SeriousPlayerAnimationsMixin extends PlayerEntity implements IExampleAnimatedPlayer, torsoPosGetter {

    @Shadow
    public abstract boolean isCreative();


    @Shadow @Final public ClientWorld clientWorld;
    @Unique
    private final ModifierLayer<IAnimation> modAnimationContainer = new ModifierLayer<>();

    public SeriousPlayerAnimationsMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Unique
    public KeyframeAnimation sword_attack = null;
    @Unique
    public KeyframeAnimation sword_attack2 = null;
    @Unique
    public KeyframeAnimation waving = null;
    @Unique
    public KeyframeAnimation idle_standing = null;
    @Unique
    public KeyframeAnimation idle_creative_flying = null;
    @Unique
    public KeyframeAnimation idle_creative_flying_item = null;
    @Unique
    public KeyframeAnimation walking = null;
    @Unique
    public KeyframeAnimation walking_backwards = null;
    @Unique
    public KeyframeAnimation running = null;
    @Unique
    public KeyframeAnimation turn_left = null;
    @Unique
    public KeyframeAnimation turn_right = null;
    @Unique
    public KeyframeAnimation jump = null;
    @Unique
    public KeyframeAnimation jump_sneak = null;
    @Unique
    public KeyframeAnimation jump_sneak2 = null;
    @Unique
    public KeyframeAnimation jump2 = null;
    @Unique
    public KeyframeAnimation idle_sneak = null;
    @Unique
    public KeyframeAnimation walking_sneak = null;
    @Unique
    public KeyframeAnimation walking_sneak_backwards = null;
    @Unique
    public KeyframeAnimation sword_attack_sneak = null;
    @Unique
    public KeyframeAnimation sword_attack_sneak2 = null;
    @Unique
    public KeyframeAnimation falling = null;
    @Unique
    public KeyframeAnimation blank_loop = null;
    @Unique
    public KeyframeAnimation elytra = null;
    @Unique
    public KeyframeAnimation elytra_firework = null;
    @Unique
    public KeyframeAnimation eating_right = null;
    @Unique
    public KeyframeAnimation eating_left = null;
    @Unique
    public KeyframeAnimation eating_right_sneak = null;
    @Unique
    public KeyframeAnimation eating_left_sneak = null;
    @Unique
    public KeyframeAnimation walking_eating_right = null;
    @Unique
    public KeyframeAnimation walking_sneak_eating_right = null;
    @Unique
    public KeyframeAnimation walking_eating_left = null;
    @Unique
    public KeyframeAnimation walking_sneak_eating_left = null;
    @Unique
    public KeyframeAnimation idle_in_water = null;
    @Unique
    public KeyframeAnimation forward_in_water = null;
    @Unique
    public KeyframeAnimation backwards_in_water = null;
    @Unique
    public KeyframeAnimation up_in_water = null;
    @Unique
    public KeyframeAnimation swimming = null;
    @Unique
    public KeyframeAnimation crawling = null;
    @Unique
    public KeyframeAnimation idle_crawling = null;
    @Unique
    public KeyframeAnimation crawling_backwards = null;
    @Unique
    public KeyframeAnimation idle_climbing = null;
    @Unique
    public KeyframeAnimation idle_climbing_sneak = null;
    @Unique
    public KeyframeAnimation climbing = null;
    @Unique
    public KeyframeAnimation climbing_sneak = null;
    @Unique
    public KeyframeAnimation climbing_backwards = null;
    @Unique
    public KeyframeAnimation punching = null;
    @Unique
    public KeyframeAnimation punching2 = null;
    @Unique
    public KeyframeAnimation punching3 = null;
    @Unique
    public KeyframeAnimation pickaxe = null;
    @Unique
    public KeyframeAnimation pickaxe_sneak = null;
    @Unique
    public KeyframeAnimation minecart_idle = null;
    @Unique
    public KeyframeAnimation minecart_eating_right = null;
    @Unique
    public KeyframeAnimation minecart_eating_left = null;
    @Unique
    public KeyframeAnimation minecart_sword_attack = null;
    @Unique
    public KeyframeAnimation minecart_sword_attack2 = null;
    @Unique
    public KeyframeAnimation horse_idle = null;
    @Unique
    public KeyframeAnimation horse_running = null;
    @Unique
    public KeyframeAnimation horse_eating_right = null;
    @Unique
    public KeyframeAnimation horse_eating_left = null;
    @Unique
    public KeyframeAnimation horse_sword_attack = null;
    @Unique
    public KeyframeAnimation horse_sword_attack2 = null;
    @Unique
    public KeyframeAnimation boat1 = null;

    @Unique
    public Vec3f torsoPos = new Vec3f(0, 0, 0);

    @Override
    public Vec3f getTorsoPos() {
        return this.torsoPos;
    }




    @Unique
    public KeyframeAnimation currentAnimation = null;

    @Unique
    public KeyframeAnimation.AnimationBuilder builder = null;

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void init(ClientWorld world, GameProfile profile, CallbackInfo info) {



        PlayerAnimationAccess.getPlayerAnimLayer((AbstractClientPlayerEntity) (Object) this).addAnimLayer(2000, modAnimationContainer);
        sword_attack = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "sword_attack"));
        sword_attack2 = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "sword_attack2"));
        waving = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "waving"));
        idle_standing = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "idle_standing"));
        idle_creative_flying = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "idle_creative_flying"));
        idle_creative_flying_item = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "idle_creative_flying_item"));
        walking = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "walking"));
        walking_backwards = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "walking_backwards"));
        running = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "running"));
        turn_left = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "turn_left"));
        turn_right = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "turn_right"));
        jump = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "jump"));
        jump2 = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "jump2"));
        jump_sneak = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "jump_sneak"));
        jump_sneak2 = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "jump_sneak2"));
        idle_sneak = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "idle_sneak"));
        walking_sneak = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "walking_sneak"));
        walking_sneak_backwards = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "walking_sneak_backwards"));
        sword_attack_sneak = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "sword_attack_sneak"));
        sword_attack_sneak2 = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "sword_attack_sneak2"));
        falling = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "falling"));
        blank_loop = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "blank_loop"));
        elytra = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "elytra"));
        elytra_firework = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "elytra_firework"));
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
        punching = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "punching"));
        punching2 = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "punching2"));
        punching3 = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "punching3"));
        pickaxe = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "pickaxe"));
        pickaxe_sneak = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "pickaxe_sneak"));
        walking_eating_right = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "walking_eating_right"));
        walking_sneak_eating_right = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "walking_sneak_eating_right"));
        walking_eating_left = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "walking_eating_left"));
        walking_sneak_eating_left = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "walking_sneak_eating_left"));
        minecart_idle = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "minecart_idle"));
        minecart_eating_right = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "minecart_eating_right"));
        minecart_eating_left = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "minecart_eating_left"));
        minecart_sword_attack = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "minecart_sword_attack"));
        minecart_sword_attack2 = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "minecart_sword_attack2"));
        horse_idle = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "horse_idle"));
        horse_running = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "horse_running"));
        horse_eating_right = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "horse_eating_right"));
        horse_eating_left = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "horse_eating_left"));
        horse_sword_attack = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "horse_sword_attack"));
        horse_sword_attack2 = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "horse_sword_attack2"));
        boat1 = PlayerAnimationRegistry.getAnimation(new Identifier("seriousplayeranimations", "boat1"));


        currentAnimation = idle_standing;

    }


    @Override
    public ModifierLayer<IAnimation> seriousplayeranimations_getModAnimation() {
        return modAnimationContainer;
    }

    @Unique
    public int fadeTime = 0;
    @Unique
    public String currentAnimationId = "";
    @Unique
    public String prevAnimationId = "";
    @Unique
    public boolean modified = false;
    @Unique
    public float animationSpeed = 1;
    @Unique
    public float prevAnimationSpeed = 1;
    @Unique
    public float byaw = 0;
    @Unique
    public float hyaw = 0;
    @Unique
    public float vx = 0;
    @Unique
    public float vy = 0;
    @Unique
    public float vz = 0;
    @Unique
    public double moveSpeed = 0;
    @Unique
    public float turn = 0;
    @Unique
    public boolean prevOnGround = false;
    @Unique
    public int priority = 0;
    @Unique
    public int prevPriority = 0;
    @Unique
    public Vec3d lastPos = new Vec3d(0, 0, 0);
    @Unique
    public int swordSeq = 0;
    @Unique
    public int jumpSeq = 0;
    @Unique
    public int punchSeq = 0;
    @Unique
    public String modifyId = "";
    @Unique
    public String prevModifyId = "";
    @Unique
    public String blockStateString = "";
    @Unique
    public String boatString = "";



    @Unique
    public void disableRightArm() {
        builder = currentAnimation.mutableCopy();
        var rightArm = builder.getPart("rightArm");
        assert rightArm != null;
        rightArm.setEnabled(false);
        currentAnimation = builder.build();
    }

    @Unique
    public void disableLeftArm() {
        builder = currentAnimation.mutableCopy();
        var leftArm = builder.getPart("leftArm");
        assert leftArm != null;
        leftArm.setEnabled(false);
        currentAnimation = builder.build();
    }


    @Override
    public void tick() {
        super.tick();
        animate();
    }


    @Unique
    public void animate() {
        ModifierLayer<IAnimation> animationContainer = modAnimationContainer;


        Vec3d pos = getPos();
        vx = (float) (pos.x - lastPos.x);
        vy = (float) (pos.y - lastPos.y);
        vz = (float) (pos.z - lastPos.z);
        moveSpeed = sqrt(pow(vx, 2) + pow(vz, 2));
        turn = bodyYaw - prevBodyYaw;
        Vector3f movementVector = new Vector3f((float) (pos.x - lastPos.x), 0, (float) (pos.z - lastPos.z));
        Vector3f lookVector = new Vector3f((float) cos(toRadians(bodyYaw + 90)), 0, (float) sin(toRadians(bodyYaw + 90)));


        byaw = bodyYaw;
        hyaw = headYaw;


        boolean isMovingBackwards;
        isMovingBackwards = movementVector.length() > 0 && movementVector.dot(lookVector) < 0;


        if (isOnGround()) {
            //walking
            if (moveSpeed < 0.23 && moveSpeed > 0 && !isMovingBackwards && !isInSneakingPose()) {
                if (((9 / 0.22) * moveSpeed) > 1) {
                    animationSpeed = (float) ((9 / 0.22) * moveSpeed);
                } else {
                    animationSpeed = 2;
                }
                currentAnimation = walking;
                currentAnimationId = "walking";
                fadeTime = 7;
                priority = 0;

                //walking backwards
            } else if (isMovingBackwards && !isInSneakingPose()) {
                if (((4 / 0.22) * moveSpeed) > 1) {
                    animationSpeed = (float) ((4 / 0.22) * moveSpeed);
                } else {
                    animationSpeed = 2;
                }
                if (vy > 0) {
                    animationSpeed = 0.1F;
                }
                currentAnimation = walking_backwards;
                currentAnimationId = "walking_backwards";
                fadeTime = 7;
                priority = 0;

                //running
            } else if (moveSpeed > 0.23 && isSprinting() && !isMovingBackwards && !isInSneakingPose()) {
                if (((3.7 / 0.28) * moveSpeed) > 1) {
                    animationSpeed = (float) ((3.7 / 0.28) * moveSpeed);
                } else {
                    animationSpeed = 1;
                }
                currentAnimation = running;
                currentAnimationId = "running";
                fadeTime = 2;
                priority = 0;


                //standing & turning
            } else if (moveSpeed == 0 && !isInSneakingPose()) {
                currentAnimation = idle_standing;
                currentAnimationId = "idle_standing";
                if (prevAnimationId.equals("idle_sneak")
                        || prevAnimationId.equals("walking_sneak")
                        || prevAnimationId.equals("jump")
                        ) {

                    fadeTime = 1;
                } else {
                    fadeTime = 10;
                }
                animationSpeed = 1f;
                priority = 0;
                if (turn < 0) {
                    currentAnimation = turn_left;
                    currentAnimationId = "turn_left";
                    if ((abs((((float) 1 / 2) * turn)) > 5)) {
                        animationSpeed = 2f;
                    } else {
                        animationSpeed = abs((((float) 1 / 2) * turn));
                    }

                } else if (turn > 0) {
                    currentAnimation = turn_right;
                    currentAnimationId = "turn_right";
                    if ((abs((((float) 1 / 2) * turn)) > 5)) {
                        animationSpeed = 2f;
                    } else {
                        animationSpeed = abs((((float) 1 / 2) * turn));
                    }


                }
                //sneaking
            } else if (isInSneakingPose() && moveSpeed == 0 && !isMovingBackwards) {
                currentAnimation = idle_sneak;
                currentAnimationId = "idle_sneak";
                animationSpeed = 1;
                if (prevAnimationId.equals("walking_sneak") || prevAnimationId.equals("walking_sneak_backwards")) {
                    fadeTime = 10;
                } else {
                    fadeTime = 1;
                }
                priority = 0;
            } else if (isInSneakingPose() && moveSpeed > 0 && !isMovingBackwards) {
                currentAnimation = walking_sneak;
                currentAnimationId = "walking_sneak";
                animationSpeed = (float) ((2 / 0.06) * moveSpeed);
                if (animationSpeed < 1) {
                    animationSpeed = 1;
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
                animationSpeed = (float) ((2 / 0.06) * moveSpeed);
                if (animationSpeed < 1) {
                    animationSpeed = 1;
                }
                if (prevAnimationId.equals("idle_sneak")
                        || prevAnimationId.equals("walking_sneak")) {
                    fadeTime = 7;
                } else {
                    fadeTime = 1;
                }
                priority = 0;

            }
        }

        //jumping
        if (vy > 0 && !isOnGround()) {
            fadeTime = 7;
            animationSpeed = 1;
            priority = 0;
            if (jumpSeq % 2 == 0) {
                if (isInSneakingPose()){
                    currentAnimation = jump_sneak;
                    currentAnimationId = "jump_sneak";
                } else {
                    currentAnimation = jump;
                    currentAnimationId = "jump";
                }
            } else {
                if (isInSneakingPose()){
                    currentAnimation = jump_sneak2;
                    currentAnimationId = "jump_sneak";
                } else {
                    currentAnimation = jump2;
                    currentAnimationId = "jump";
                }
            }
        } else if (currentAnimationId.equals("jump") && isInSneakingPose()){
            if (jumpSeq % 2 == 0) {
                currentAnimation = jump_sneak;
                currentAnimationId = "jump_sneak";
            } else {
                currentAnimation = jump_sneak2;
                currentAnimationId = "jump_sneak";
            }
        }

        //climbing
        if ((clientWorld.getBlockState(getBlockPos()).getBlock() instanceof LadderBlock || clientWorld.getBlockState(getBlockPos()).getBlock() instanceof VineBlock) && !isOnGround() && !hasVehicle()) {

            animationSpeed = 3;
            fadeTime = 7;
            priority = 0;
            blockStateString = String.valueOf(clientWorld.getBlockState(getBlockPos()));
            if (blockStateString.contains("facing=north") || blockStateString.contains("south=true")){
                byaw = 0;

            } else if (blockStateString.contains("facing=south")  || blockStateString.contains("north=true")) {
                byaw =180;

            } else if (blockStateString.contains("facing=west") || blockStateString.contains("east=true")) {
                byaw = 270;

            } else if (blockStateString.contains("facing=east") || blockStateString.contains("west=true")) {
                byaw = 90;

            }
            hyaw = ((hyaw % 360) + 360) % 360;
            setBodyYaw(byaw);
            hyaw = hyaw - byaw;
            hyaw = ((hyaw % 360) + 360) % 360;

            if (hyaw > 90 && hyaw <= 180) {
                setHeadYaw(byaw+90);
            } else if (hyaw > 180 && hyaw < 270) {
                setHeadYaw(byaw+270);
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
                && !isOnGround() && !hasVehicle()) {
            animationSpeed = 3;
            fadeTime = 7;
            priority = 0;
            byaw = ((float) toDegrees(atan2((getBlockPos().getZ() + 0.5 - pos.z), (getBlockPos().getX()) + 0.5 - pos.x)) - 90);
            byaw = ((byaw % 360) + 360) % 360;
            hyaw = ((hyaw % 360) + 360) % 360;
            setBodyYaw(byaw);
            hyaw = hyaw - byaw;
            hyaw = ((hyaw % 360) + 360) % 360;

            if (hyaw > 90 && hyaw <= 180) {
                setHeadYaw(byaw+90);
            } else if (hyaw > 180 && hyaw < 270) {
                setHeadYaw(byaw+270);
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
        } else if (clientWorld.getBlockState(getBlockPos()).getBlock() instanceof PowderSnowBlock && !isOnGround()) {
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

        //crawling
        if (isCrawling()) {
            if (moveSpeed > 0 && !isMovingBackwards){
                currentAnimation = crawling;
                currentAnimationId = "crawling";
                animationSpeed = (float) ((2 / 0.06) * moveSpeed);
                if (animationSpeed < 1) {
                    animationSpeed = 1;
                }
            } else if (moveSpeed > 0 && isMovingBackwards){
                currentAnimation = crawling_backwards;
                currentAnimationId = "crawling_backwards";
                animationSpeed = (float) ((2 / 0.06) * moveSpeed);
                if (animationSpeed < 1) {
                    animationSpeed = 1;
                }
            } else {
                currentAnimation = idle_crawling;
                currentAnimationId = "idle_crawling";
                animationSpeed = 2;
            }

            fadeTime = 7;
            priority = 0;

        }

        //in water
        if ((isInsideWaterOrBubbleColumn() || isInLava()) && !isOnGround() && !isInSwimmingPose()){
            if (moveSpeed > 0 && !isMovingBackwards) {
                currentAnimation = forward_in_water;
                currentAnimationId = "forward_in_water";
                animationSpeed = 1;
                fadeTime = 7;
                priority = 0;
            } else if (moveSpeed > 0 && isMovingBackwards) {
                currentAnimation = backwards_in_water;
                currentAnimationId = "backwards_in_water";
                animationSpeed = 1;
                fadeTime = 7;
                priority = 0;
            } else if (vy > 0) {
                currentAnimation = up_in_water;
                currentAnimationId = "up_in_water";
                animationSpeed = 1;
                fadeTime = 7;
                priority = 0;
            } else {
                currentAnimation = idle_in_water;
                currentAnimationId = "idle_in_water";
                fadeTime = 5;
                animationSpeed = 1;
                priority = 0;
            }
        } else if (isInsideWaterOrBubbleColumn() && isInSwimmingPose()) {
            currentAnimation = swimming;
            currentAnimationId = "swimming";
            animationSpeed = 1;
            fadeTime = 7;
            priority = 0;
        }

        //riding
        if (hasVehicle()) {
            if(getVehicle() instanceof MinecartEntity) {
                currentAnimation = minecart_idle;
                currentAnimationId = "minecart_idle";
                animationSpeed = 1;
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
                } else {
                    currentAnimation = horse_idle;
                    currentAnimationId = "horse_idle";
                }
                if (isUsingItem()) {
                    currentAnimation = horse_idle;
                    currentAnimationId = "horse_idle";
                }

                animationSpeed = 1;
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
                animationSpeed = 1;
                fadeTime = 1;
                priority = 0;
            } else if (getVehicle() instanceof PigEntity
                    || getVehicle() instanceof StriderEntity
                    || getVehicle() instanceof CamelEntity) {
                currentAnimation = horse_idle;
                currentAnimationId = "horse_idle";
                animationSpeed = 1;
                fadeTime = 1;
                priority = 0;
            }
        }

        //falling
        if (vy < -0.5 && !hasVehicle()) {
            currentAnimation = falling;
            currentAnimationId = "falling";
            fadeTime = 7;
            animationSpeed = 1;
            priority = 0;
        }

        //elytra
        if (isFallFlying()) {
            currentAnimation = elytra;
            currentAnimationId = "elytra";
            animationSpeed = 1;
            priority = 0;
            fadeTime = 5;
        }



        //handswinging
        if (handSwinging) {
            disableRightArm();
            currentAnimationId = "handswinging" + currentAnimationId;
            modifyId = "handswinging";
            fadeTime = 0;
            priority = 0;
        } else {
            modifyId = "";
        }



        //sword attack
        if (getMainHandStack().getItem() instanceof SwordItem && handSwinging && !isUsingItem()) {
            fadeTime = 0;
            animationSpeed = 1.5f;
            priority = 1;
            currentAnimationId = "sword_attack";
            if (swordSeq % 2 == 0) {
                if (isInSneakingPose()) {
                    currentAnimation = sword_attack_sneak;
                } else if (getVehicle() instanceof HorseEntity
                        || getVehicle() instanceof SkeletonHorseEntity
                        || getVehicle() instanceof ZombieHorseEntity
                        || getVehicle() instanceof DonkeyEntity
                        || getVehicle() instanceof MuleEntity
                        || getVehicle() instanceof BoatEntity
                        || getVehicle() instanceof ChestBoatEntity
                        || getVehicle() instanceof PigEntity
                        || getVehicle() instanceof StriderEntity
                        || getVehicle() instanceof CamelEntity) {
                    currentAnimation = horse_sword_attack;
                } else if (getVehicle() instanceof MinecartEntity) {
                    currentAnimation = minecart_sword_attack;
                } else {
                    currentAnimation = sword_attack;
                }

            } else {
                if (isInSneakingPose()) {
                    currentAnimation = sword_attack_sneak2;
                } else if (getVehicle() instanceof HorseEntity
                        || getVehicle() instanceof SkeletonHorseEntity
                        || getVehicle() instanceof ZombieHorseEntity
                        || getVehicle() instanceof DonkeyEntity
                        || getVehicle() instanceof MuleEntity
                        || getVehicle() instanceof BoatEntity
                        || getVehicle() instanceof ChestBoatEntity
                        || getVehicle() instanceof PigEntity
                        || getVehicle() instanceof StriderEntity
                        || getVehicle() instanceof CamelEntity) {
                    currentAnimation = horse_sword_attack2;
                } else if (getVehicle() instanceof MinecartEntity) {
                    currentAnimation = minecart_sword_attack2;
                } else {
                    currentAnimation = sword_attack2;
                }
            }

        //pickaxe
        } else if (getMainHandStack().getItem() instanceof PickaxeItem && handSwinging && !isUsingItem()) {
            fadeTime = 1;
            animationSpeed = 2f;
            priority = 0;

            if (isInSneakingPose()) {
                currentAnimationId = "pickaxe_sneak";
                currentAnimation = pickaxe_sneak;
            } else {
                currentAnimationId = "pickaxe";
                currentAnimation = pickaxe;

            }
        }

        if (getMainHandStack().getItem().equals(CROSSBOW)) {
            if (Objects.requireNonNull(getMainHandStack().getNbt()).getBoolean("Charged")){
                disableRightArm();
                disableLeftArm();
                modifyId = "crossbow";
                if (!prevModifyId.equals("crossbow")) {
                    fadeTime = 1;
                }
                priority = 0;
            }
        } else if (getOffHandStack().getItem().equals(CROSSBOW)) {
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
                if (getActiveHand().equals(MAIN_HAND)) {
                    if (!isInSneakingPose()) {
                        if (currentAnimationId.equals("walking")) {
                            currentAnimation = walking_eating_right;
                            currentAnimationId = "walking_eating_right";
                            animationSpeed = 2;
                        } else {
                            currentAnimation = eating_right;
                            currentAnimationId = "eating_right";
                            animationSpeed = 1;
                        }

                        if (getVehicle() instanceof MinecartEntity) {
                            currentAnimation = minecart_eating_right;
                            currentAnimationId = "minecart_eating_right";
                            animationSpeed = 1;
                        } else if (getVehicle() instanceof HorseEntity
                                || getVehicle() instanceof SkeletonHorseEntity
                                || getVehicle() instanceof ZombieHorseEntity
                                || getVehicle() instanceof DonkeyEntity
                                || getVehicle() instanceof MuleEntity
                                || getVehicle() instanceof BoatEntity
                                || getVehicle() instanceof ChestBoatEntity
                                || getVehicle() instanceof PigEntity
                                || getVehicle() instanceof StriderEntity
                                || getVehicle() instanceof CamelEntity) {
                            currentAnimation = horse_eating_right;
                            currentAnimationId = "horse_eating_right";
                            animationSpeed = 1;
                        }

                    } else {
                        if (currentAnimationId.equals("walking_sneak")) {
                            currentAnimation = walking_sneak_eating_right;
                            currentAnimationId = "walking_sneak_eating_right";
                            animationSpeed = 1;
                        } else {
                            currentAnimation = eating_right_sneak;
                            currentAnimationId = "eating_right_sneak";
                            animationSpeed = 1;
                        }



                    }
                } else {
                    if (!isInSneakingPose()) {
                        if (currentAnimationId.equals("walking")) {
                            currentAnimation = walking_eating_left;
                            currentAnimationId = "walking_eating_left";
                            animationSpeed = 2;
                        } else {
                            currentAnimation = eating_left;
                            currentAnimationId = "eating_left";
                            animationSpeed = 1;
                        }

                        if (getVehicle() instanceof MinecartEntity) {
                            currentAnimation = minecart_eating_left;
                            currentAnimationId = "minecart_eating_left";
                            animationSpeed = 1;
                        } else if (getVehicle() instanceof HorseEntity
                                || getVehicle() instanceof SkeletonHorseEntity
                                || getVehicle() instanceof ZombieHorseEntity
                                || getVehicle() instanceof DonkeyEntity
                                || getVehicle() instanceof MuleEntity
                                || getVehicle() instanceof BoatEntity
                                || getVehicle() instanceof ChestBoatEntity
                                || getVehicle() instanceof PigEntity
                                || getVehicle() instanceof StriderEntity
                                || getVehicle() instanceof CamelEntity) {
                            currentAnimation = horse_eating_left;
                            currentAnimationId = "horse_eating_left";
                            animationSpeed = 1;
                        }

                    } else {
                        if (currentAnimationId.equals("walking_sneak")) {
                            currentAnimation = walking_sneak_eating_left;
                            currentAnimationId = "walking_sneak_eating_left";
                            animationSpeed = 1;
                        } else {
                            currentAnimation = eating_left_sneak;
                            currentAnimationId = "eating_left_sneak";
                            animationSpeed = 1;
                        }

                    }

                }
                priority = 0;
                fadeTime = 3;

            } else if (isUsingSpyglass()) {
                //spyglass
                if (getActiveHand().equals(MAIN_HAND)) {
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
            } else if (getActiveItem().getItem().equals(TRIDENT)) {
                //trident
                if (getActiveHand().equals(MAIN_HAND)) {
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
            } else if (getActiveItem().getItem().equals(BRUSH)) {
                //brush
                if (getActiveHand().equals(MAIN_HAND)) {
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
            } else if (getActiveItem().getItem().equals(GOAT_HORN)) {
                //goat horn
                if (getActiveHand().equals(MAIN_HAND)) {
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
            } else if (getActiveItem().getItem().equals(BOW)) {
                //bow
                disableLeftArm();
                disableRightArm();
                modifyId = "bow";
                if (!modifyId.equals(prevModifyId)) {
                    fadeTime = 1;
                }
                priority = 0;
            } else if (getActiveItem().getItem().equals(SHIELD)) {
                //shield
                if (getActiveHand().equals(MAIN_HAND)) {
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
            } else if (getActiveItem().getItem().equals(CROSSBOW)) {
                //crossbow
                disableLeftArm();
                disableRightArm();

                modifyId = "crossbow_charging";
                if (!modifyId.equals(prevModifyId)) {
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

        if (animationSpeed != prevAnimationSpeed) {
            animationContainer.removeModifier(0);
            animationContainer.addModifierBefore(new SpeedModifier(animationSpeed));
            prevAnimationSpeed = animationSpeed;
        }
        if ((!Objects.equals(currentAnimationId, prevAnimationId) && priority >= prevPriority) || !animationContainer.isActive() || !Objects.equals(modifyId, prevModifyId)) {
            if (modified) {
                animationContainer.removeModifier(0);
            }
            if (prevAnimationId.equals("sword_attack")) {
                fadeTime = 1;
            }

            animationContainer.addModifierBefore(new SpeedModifier(animationSpeed));
            animationContainer.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(fadeTime, LINEAR), new KeyframeAnimationPlayer(currentAnimation));

            prevAnimationId = currentAnimationId;
            prevModifyId = modifyId;
            prevPriority = priority;
            modified = true;
            if (currentAnimationId.equals("sword_attack")) {
                swordSeq++;
            }
            if (currentAnimationId.contains("jump")) {
                jumpSeq++;
            }
            if (currentAnimationId.contains("punching")) {
                punchSeq++;
            }
        }
        if (animationContainer.isActive()) {
            torsoPos = animationContainer.get3DTransform("torso", POSITION, 0, new Vec3f(0, 0, 0));
            //LOGGER.info("POSITION: "+String.valueOf(torsoPos));
            var torsoAng = animationContainer.get3DTransform("torso", ROTATION, 0, new Vec3f(0, 0, 0));
            //LOGGER.info("ROTATION: "+String.valueOf(torsoAng));
        } else {
            torsoPos = new Vec3f(0, 0, 0);
        }
        lastPos = new Vec3d(pos.x, pos.y, pos.z);
        prevOnGround = isOnGround();



    }

}