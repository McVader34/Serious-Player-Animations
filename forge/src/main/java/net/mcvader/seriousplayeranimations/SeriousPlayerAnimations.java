package net.mcvader.seriousplayeranimations;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.mcvader.seriousplayeranimations.config.ClientConfig;
import net.mcvader.seriousplayeranimations.config.ConfigWrapper;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Mod(SeriousPlayerAnimations.MOD_ID)
public class SeriousPlayerAnimations  {
	public static final String MOD_ID = "seriousplayeranimations";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ClientConfig config;
	public static boolean PARAGLIDER_COMPAT;
	public static boolean CARRYON_COMPAT;
	public static boolean SUPPLEMENTARIES_COMPAT;
	public static boolean IMMERSIVE_MELODIES_COMPAT;
	public static boolean ULTRACRAFT_COMPAT;
	public static boolean SWORDBLOCKING_COMPAT;
	public static boolean OLDCOMBATMOD_COMPAT;
	public static boolean TRIGGERHAPPY_COMPAT;

	public SeriousPlayerAnimations() {
		LOGGER.info("Loading up some Serious Animations.");

		AutoConfig.register(ConfigWrapper.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
		config = AutoConfig.getConfigHolder(ConfigWrapper.class).getConfig().client;

		ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> {
			return new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> {
				return AutoConfig.getConfigScreen(ConfigWrapper.class, screen).get();
			});
		});

		PARAGLIDER_COMPAT = ModList.get().isLoaded("paraglider");
		CARRYON_COMPAT = ModList.get().isLoaded("carryon");
		SUPPLEMENTARIES_COMPAT = ModList.get().isLoaded("supplementaries");
		IMMERSIVE_MELODIES_COMPAT = ModList.get().isLoaded("immersive_melodies");
		ULTRACRAFT_COMPAT = ModList.get().isLoaded("ultracraft");
		SWORDBLOCKING_COMPAT = ModList.get().isLoaded("swordblocking");
		OLDCOMBATMOD_COMPAT = ModList.get().isLoaded("oldcombatmod");
		TRIGGERHAPPY_COMPAT = ModList.get().isLoaded("triggerhappy");



	}
}