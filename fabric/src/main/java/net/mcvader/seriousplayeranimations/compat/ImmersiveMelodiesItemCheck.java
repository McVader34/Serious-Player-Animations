package net.mcvader.seriousplayeranimations.compat;

import immersive_melodies.item.InstrumentItem;
import net.mehvahdjukaar.supplementaries.common.items.FluteItem;
import net.minecraft.item.Item;
import static net.mcvader.seriousplayeranimations.SeriousPlayerAnimations.LOGGER;

public class ImmersiveMelodiesItemCheck {

    public static boolean check(Item item) {
        return item instanceof InstrumentItem;

    }

}
