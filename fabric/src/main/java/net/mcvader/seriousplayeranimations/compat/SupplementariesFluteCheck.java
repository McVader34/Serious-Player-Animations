package net.mcvader.seriousplayeranimations.compat;

import net.mehvahdjukaar.supplementaries.common.items.FluteItem;
import net.minecraft.item.Item;

public class SupplementariesFluteCheck {

    public static boolean check(Item item) {
        return item instanceof FluteItem;
    }

}
