package me.yyna.transformhandlers;

import com.google.gson.Gson;
import me.yyna.transformhandlers.settingClasses.SettingPosition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Settings {
    public static Settings Load(){
        String stringLoad = null;
        try {
            File file = new File(Global.settingsPathname);
            if (file.exists()){
                BufferedReader br = new BufferedReader(new FileReader(Global.settingsPathname));
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                stringLoad = sb.toString();
                br.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (stringLoad != null) {
            Entrypoint.LOGGER.info(stringLoad);
            Gson gson = new Gson();
            return gson.fromJson(stringLoad, Settings.class);
        }

        return new Settings();
    }
    public boolean enable = true;
    public SettingPosition ArmRight = new SettingPosition();
    public SettingPosition ArmLeft = new SettingPosition();
    public SettingPosition ItemsMain = new SettingPosition();
    public SettingPosition ItemsOff = new SettingPosition();
}
