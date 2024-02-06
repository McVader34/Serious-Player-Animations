package net.mcvader.seriousplayeranimations;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.mcvader.seriousplayeranimations.config.ClientConfig;
import net.mcvader.seriousplayeranimations.config.ConfigWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeriousPlayerAnimations implements ModInitializer, ClientModInitializer {
	public static String MOD_ID = "seriousplayeranimations";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ClientConfig config;


	@Override
	public void onInitialize() {
		LOGGER.info("Loading up some Serious Animations.");

		AutoConfig.register(ConfigWrapper.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
		config = AutoConfig.getConfigHolder(ConfigWrapper.class).getConfig().client;


	}

	@Override
	public void onInitializeClient()  {

	}
}