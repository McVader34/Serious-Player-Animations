package net.mcvader.seriousplayeranimations;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.loader.api.FabricLoader;
import net.mcvader.seriousplayeranimations.compat.CarryOnCheck;
import net.mcvader.seriousplayeranimations.compat.ImmersiveMelodiesItemCheck;
import net.mcvader.seriousplayeranimations.compat.SupplementariesFluteCheck;
import net.mcvader.seriousplayeranimations.compat.UltracraftCheck;
import net.mcvader.seriousplayeranimations.config.ClientConfig;
import net.mcvader.seriousplayeranimations.config.ConfigWrapper;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class SeriousPlayerAnimations implements ModInitializer, ClientModInitializer {
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

	@Override
	public void onInitialize() {
		LOGGER.info("Loading up some Serious Animations.");

		AutoConfig.register(ConfigWrapper.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
		config = AutoConfig.getConfigHolder(ConfigWrapper.class).getConfig().client;

        PARAGLIDER_COMPAT = FabricLoader.getInstance().isModLoaded("paraglider");
        CARRYON_COMPAT = FabricLoader.getInstance().isModLoaded("carryon");
        SUPPLEMENTARIES_COMPAT = FabricLoader.getInstance().isModLoaded("supplementaries");
		IMMERSIVE_MELODIES_COMPAT = FabricLoader.getInstance().isModLoaded("immersive_melodies");
		ULTRACRAFT_COMPAT = FabricLoader.getInstance().isModLoaded("ultracraft");
		SWORDBLOCKING_COMPAT = FabricLoader.getInstance().isModLoaded("swordblocking");
		OLDCOMBATMOD_COMPAT = FabricLoader.getInstance().isModLoaded("oldcombatmod");
		TRIGGERHAPPY_COMPAT = FabricLoader.getInstance().isModLoaded("triggerhappy");

	}



	@Override
	public void onInitializeClient()  {

	}
}