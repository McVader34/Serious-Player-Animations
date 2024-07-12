package net.mcvader.seriousplayeranimations.compat;

import com.tacz.guns.api.item.IGun;
import net.minecraft.item.Item;

public class TACCheck {
    public static boolean check(Item item) {
        return item instanceof IGun;
    }
}
