package me.yyna.transformhandlers;

import com.google.gson.Gson;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.io.*;
public class SettingsScreen {
    public static Settings settings = new Settings();
    public static final String pathname = "config/transformHandlersSettings.json";

    public static Screen CreateSettingsScreen(){
        //load
        String stringLoad = null;
        try {
            File file = new File(pathname);
            if (file.exists()){
                BufferedReader br = new BufferedReader(new FileReader(pathname));
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
            settings = gson.fromJson(stringLoad, Settings.class);
        }

        ConfigBuilder builder = ConfigBuilder.create()
                .setTitle(Text.translatable("settings"))
                .setSavingRunnable(() -> {
                    Gson gson = new Gson();
                    String stringSave = gson.toJson(settings);

                    File file = new File(pathname);
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        FileWriter fileWriter = new FileWriter(pathname);
                        fileWriter.write(stringSave);
                        fileWriter.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory globals = builder.getOrCreateCategory(Text.translatable("thsettings.globals"));

        globals.addEntry(entryBuilder.startTextDescription(Text.translatable("thsettings.globals.alpha")).build());

        globals.addEntry(entryBuilder
                .startBooleanToggle(Text.translatable("thsettings.globals.enable"), settings.enable)
                .setSaveConsumer(n -> settings.enable = n)
                .build()
        );

        globals.addEntry(genArmLeft(entryBuilder).build());
        globals.addEntry(genArmRight(entryBuilder).build());
        globals.addEntry(genItemMain(entryBuilder).build());
        globals.addEntry(genItemOff(entryBuilder).build());

        ConfigCategory overrides = builder.getOrCreateCategory(Text.translatable("thsettings.overrides"));

        overrides.addEntry(entryBuilder.startTextDescription(Text.translatable("thsettings.overrides.comingsoon")).build());

        return builder.build();
    }

    private static SubCategoryBuilder genArmRight(ConfigEntryBuilder entryBuilder){
        SubCategoryBuilder a = entryBuilder.startSubCategory(Text.translatable("thsettings.globals.arm.right")).setExpanded(false);
        a.add(entryBuilder
                .startBooleanToggle(Text.translatable("thsettings.globals.arm.right.e"), settings.ArmRight.enable)
                .setSaveConsumer(n -> settings.ArmRight.enable = n)
                .build()
        );
        a.add(entryBuilder
                .startLongSlider(Text.translatable("thsettings.globals.arm.right.x"), settings.ArmRight.x, -30L, 30L)
                .setSaveConsumer(n -> settings.ArmRight.x = n)
                .build()
        );
        a.add(entryBuilder
                .startLongSlider(Text.translatable("thsettings.globals.arm.right.y"), settings.ArmRight.y, -30L, 30L)
                .setSaveConsumer(n -> settings.ArmRight.y = n)
                .build()
        );
        a.add(entryBuilder
                .startLongSlider(Text.translatable("thsettings.globals.arm.right.z"), settings.ArmRight.z, -30L, 30L)
                .setSaveConsumer(n -> settings.ArmRight.z = n)
                .build()
        );
        return a;
    }
    private static SubCategoryBuilder genArmLeft(ConfigEntryBuilder entryBuilder){
        SubCategoryBuilder a = entryBuilder.startSubCategory(Text.translatable("thsettings.globals.arm.left")).setExpanded(false);
        a.add(entryBuilder
                .startBooleanToggle(Text.translatable("thsettings.globals.arm.left.e"), settings.ArmLeft.enable)
                .setSaveConsumer(n -> settings.ArmLeft.enable = n)
                .build()
        );
        a.add(entryBuilder
                .startLongSlider(Text.translatable("thsettings.globals.arm.left.x"), settings.ArmLeft.x, -30L, 30L)
                .setSaveConsumer(n -> settings.ArmLeft.x = n)
                .build()
        );
        a.add(entryBuilder
                .startLongSlider(Text.translatable("thsettings.globals.arm.left.y"), settings.ArmLeft.y, -30L, 30L)
                .setSaveConsumer(n -> settings.ArmLeft.y = n)
                .build()
        );
        a.add(entryBuilder
                .startLongSlider(Text.translatable("thsettings.globals.arm.left.z"), settings.ArmLeft.z, -30L, 30L)
                .setSaveConsumer(n -> settings.ArmLeft.z = n)
                .build()
        );
        return a;
    }
    private static SubCategoryBuilder genItemMain(ConfigEntryBuilder entryBuilder){
        SubCategoryBuilder a = entryBuilder.startSubCategory(Text.translatable("thsettings.globals.item.main")).setExpanded(false);
        a.add(entryBuilder
                .startBooleanToggle(Text.translatable("thsettings.globals.item.main.e"), settings.ItemsMain.enable)
                .setSaveConsumer(n -> settings.ItemsMain.enable = n)
                .build()
        );
        a.add(entryBuilder
                .startLongSlider(Text.translatable("thsettings.globals.item.main.x"), settings.ItemsMain.x, -30L, 30L)
                .setSaveConsumer(n -> settings.ItemsMain.x = n)
                .build()
        );
        a.add(entryBuilder
                .startLongSlider(Text.translatable("thsettings.globals.item.main.y"), settings.ItemsMain.y, -30L, 30L)
                .setSaveConsumer(n -> settings.ItemsMain.y = n)
                .build()
        );
        a.add(entryBuilder
                .startLongSlider(Text.translatable("thsettings.globals.item.main.z"), settings.ItemsMain.z, -30L, 30L)
                .setSaveConsumer(n -> settings.ItemsMain.z = n)
                .build()
        );
        return a;
    }
    private static SubCategoryBuilder genItemOff(ConfigEntryBuilder entryBuilder){
        SubCategoryBuilder a = entryBuilder.startSubCategory(Text.translatable("thsettings.globals.item.off")).setExpanded(false);
        a.add(entryBuilder
                .startBooleanToggle(Text.translatable("thsettings.globals.item.off.e"), settings.ItemsOff.enable)
                .setSaveConsumer(n -> settings.ItemsOff.enable = n)
                .build()
        );
        a.add(entryBuilder
                .startLongSlider(Text.translatable("thsettings.globals.item.off.x"), settings.ItemsOff.x, -30L, 30L)
                .setSaveConsumer(n -> settings.ItemsOff.x = n)
                .build()
        );
        a.add(entryBuilder
                .startLongSlider(Text.translatable("thsettings.globals.item.off.y"), settings.ItemsOff.y, -30L, 30L)
                .setSaveConsumer(n -> settings.ItemsOff.y = n)
                .build()
        );
        a.add(entryBuilder
                .startLongSlider(Text.translatable("thsettings.globals.item.off.z"), settings.ItemsOff.z, -30L, 30L)
                .setSaveConsumer(n -> settings.ItemsOff.z = n)
                .build()
        );
        return a;
    }
}
