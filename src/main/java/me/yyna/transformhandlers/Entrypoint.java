package me.yyna.transformhandlers;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Entrypoint implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("transformhandlers");

	@Override
	public void onInitialize() {
		SettingsScreen.settings = Settings.Load();
	}
}
