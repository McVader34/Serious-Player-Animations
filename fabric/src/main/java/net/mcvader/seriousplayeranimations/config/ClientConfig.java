package net.mcvader.seriousplayeranimations.config;


import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;


@Config(name = "client")
public class ClientConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public float animationSpeedMultiplier = 1;

    @ConfigEntry.Category(value = "Animations")

    /*@ConfigEntry.Gui.CollapsibleObject
    public TestConfig test = new TestConfig();
    public TestConfig getTest() {
        return test;
    }*/


    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig idleStanding = new AnimationConfig();
    public AnimationConfig getIdleStanding() {
        return idleStanding;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig walking = new AnimationConfig();
    public AnimationConfig getWalking() {
        return walking;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig walkingBackwards = new AnimationConfig();
    public AnimationConfig getWalkingBackwards() {
        return walkingBackwards;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig running = new AnimationConfig();
    public AnimationConfig getRunning() {
        return running;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig turningStanding = new AnimationConfig();
    public AnimationConfig getTurningStanding() {
        return turningStanding;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig idleSneaking = new AnimationConfig();
    public AnimationConfig getIdleSneaking() {
        return idleSneaking;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig walkingSneak = new AnimationConfig();
    public AnimationConfig getWalkingSneak() {
        return walkingSneak;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig walkingSneakBackwards = new AnimationConfig();
    public AnimationConfig getWalkingSneakBackwards() {
        return walkingSneakBackwards;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig idleCreativeFlying = new AnimationConfig();
    public AnimationConfig getIdleCreativeFlying() {
        return idleCreativeFlying;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig falling = new AnimationConfig();
    public AnimationConfig getFalling() {
        return falling;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig idleCrawling = new AnimationConfig();
    public AnimationConfig getIdleCrawling() {
        return idleCrawling;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig crawling = new AnimationConfig();
    public AnimationConfig getCrawling() {
        return crawling;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig crawlingBackwards = new AnimationConfig();
    public AnimationConfig getCrawlingBackwards() {
        return crawlingBackwards;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig climbing = new AnimationConfig();
    public AnimationConfig getClimbing() {
        return climbing;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig forwardInWater = new AnimationConfig();
    public AnimationConfig getForwardInWater() {
        return forwardInWater;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig backwardsInWater = new AnimationConfig();
    public AnimationConfig getBackwardsInWater() {
        return backwardsInWater;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig upInWater = new AnimationConfig();
    public AnimationConfig getUpInWater() {
        return upInWater;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig idleInWater = new AnimationConfig();
    public AnimationConfig getIdleInWater() {
        return idleInWater;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig swimming = new AnimationConfig();
    public AnimationConfig getSwimming() {
        return swimming;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig minecartIdle = new AnimationConfig();
    public AnimationConfig getMinecartIdle() {
        return minecartIdle;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig horseIdle = new AnimationConfig();
    public AnimationConfig getHorseIdle() {
        return horseIdle;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig horseRunning = new AnimationConfig();
    public AnimationConfig getHorseRunning() {
        return horseRunning;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig boat = new AnimationConfig();
    public AnimationConfig getBoat() {
        return boat;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig elytra = new AnimationConfig();
    public AnimationConfig getElytra() {
        return elytra;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig swordAttack = new AnimationConfig();
    public AnimationConfig getSwordAttack() {
        return swordAttack;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig bow = new AnimationConfig();
    public AnimationConfig getBow() {
        return bow;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig pickaxe = new AnimationConfig();
    public AnimationConfig getPickaxe() {return pickaxe;}

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig axe = new AnimationConfig();
    public AnimationConfig getAxe() {
        return axe;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig shovel = new AnimationConfig();
    public AnimationConfig getShovel() {
        return shovel;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig sleeping = new AnimationConfig();
    public AnimationConfig getSleeping() {
        return sleeping;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig eating = new AnimationConfig();
    public AnimationConfig getEating() {
        return eating;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig shield = new AnimationConfig();
    public AnimationConfig getShield() {
        return shield;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public AnimationConfig trident = new AnimationConfig();
    public AnimationConfig getTrident() {
        return trident;
    }



    public static class AnimationConfig {
        public boolean enabled = true;
        public float speedMultiplier = 1;
    }

    public static class TestConfig {
        public float x = 0;
        public float y = 0;
        public float z = 0;
        public float pitch = 0;
        public float yaw = 0;
        public float roll = 0;
    }

}