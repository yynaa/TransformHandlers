package me.yyna.transformhandlers;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import static me.yyna.transformhandlers.SettingsScreen.CreateSettingsScreen;

public class ModMenuInteg implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> CreateSettingsScreen();
    }
}
