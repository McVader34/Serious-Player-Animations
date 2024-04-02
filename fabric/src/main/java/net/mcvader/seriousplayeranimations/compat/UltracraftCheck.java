package net.mcvader.seriousplayeranimations.compat;

import absolutelyaya.ultracraft.item.AbstractNailgunItem;
import absolutelyaya.ultracraft.item.AbstractWeaponItem;
import ewewukek.musketmod.GunItem;
import ewewukek.musketmod.MusketItem;
import ewewukek.musketmod.PistolItem;
import immersive_melodies.item.InstrumentItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class UltracraftCheck {

    public static boolean isGenericGun(Item item) {
        if (item instanceof AbstractWeaponItem w) {
            if (w.shouldAim()) {
                return true;
            }
        }


        return false;

    }

    public static boolean isNailgun(Item item) {

        if (item instanceof AbstractNailgunItem) {
            return true;
        }
        return false;


    }

}
