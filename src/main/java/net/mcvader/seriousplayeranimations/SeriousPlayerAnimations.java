package net.mcvader.seriousplayeranimations;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeriousPlayerAnimations implements ModInitializer, ClientModInitializer {
	public static String MOD_ID = "seriousplayeranimations";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
	LOGGER.info("Loading up some Serious Animations.");

	}

	@Override
	public void onInitializeClient() {

	}
}