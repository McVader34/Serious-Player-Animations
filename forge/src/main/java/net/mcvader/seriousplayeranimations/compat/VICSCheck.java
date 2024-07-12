package net.mcvader.seriousplayeranimations.compat;

import com.vicmatskiv.pointblank.item.GunItem;
import net.minecraft.item.Item;

public class VICSCheck {
    public static boolean check(Item item) {
        return item instanceof GunItem;
    }
}
