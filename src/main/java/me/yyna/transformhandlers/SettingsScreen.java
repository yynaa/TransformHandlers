package me.yyna.transformhandlers;

import com.google.gson.Gson;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.io.*;
import java.util.function.Consumer;

public class SettingsScreen {
    public static Settings settings = new Settings();

    public static Screen CreateSettingsScreen(){
        //load
        settings = Settings.Load();

        ConfigBuilder builder = ConfigBuilder.create()
                .setTitle(Text.translatable("thsettings"))
                .transparentBackground()
                .setSavingRunnable(() -> {
                    Gson gson = new Gson();
                    String stringSave = gson.toJson(settings);

                    File file = new File(Global.settingsPathname);
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        FileWriter fileWriter = new FileWriter(Global.settingsPathname);
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

        globals.addEntry(genGlobalTransform(entryBuilder, "thsettings.globals.arm.right",
                "thsettings.globals.arm.right.e", settings.ArmRight.enable, n -> settings.ArmRight.enable = n,
                "thsettings.globals.arm.right.x", settings.ArmRight.x, n -> settings.ArmRight.x = n,
                "thsettings.globals.arm.right.y", settings.ArmRight.y, n -> settings.ArmRight.y = n,
                "thsettings.globals.arm.right.z", settings.ArmRight.z, n -> settings.ArmRight.z = n
                ).build());
        globals.addEntry(genGlobalTransform(entryBuilder, "thsettings.globals.arm.left",
                "thsettings.globals.arm.left.e", settings.ArmLeft.enable, n -> settings.ArmLeft.enable = n,
                "thsettings.globals.arm.left.x", settings.ArmLeft.x, n -> settings.ArmLeft.x = n,
                "thsettings.globals.arm.left.y", settings.ArmLeft.y, n -> settings.ArmLeft.y = n,
                "thsettings.globals.arm.left.z", settings.ArmLeft.z, n -> settings.ArmLeft.z = n
        ).build());
        globals.addEntry(genGlobalTransform(entryBuilder, "thsettings.globals.item.main",
                "thsettings.globals.item.main.e", settings.ItemsMain.enable, n -> settings.ItemsMain.enable = n,
                "thsettings.globals.item.main.x", settings.ItemsMain.x, n -> settings.ItemsMain.x = n,
                "thsettings.globals.item.main.y", settings.ItemsMain.y, n -> settings.ItemsMain.y = n,
                "thsettings.globals.item.main.z", settings.ItemsMain.z, n -> settings.ItemsMain.z = n
        ).build());
        globals.addEntry(genGlobalTransform(entryBuilder, "thsettings.globals.item.off",
                "thsettings.globals.item.off.e", settings.ItemsOff.enable, n -> settings.ItemsOff.enable = n,
                "thsettings.globals.item.off.x", settings.ItemsOff.x, n -> settings.ItemsOff.x = n,
                "thsettings.globals.item.off.y", settings.ItemsOff.y, n -> settings.ItemsOff.y = n,
                "thsettings.globals.item.off.z", settings.ItemsOff.z, n -> settings.ItemsOff.z = n
        ).build());

        ConfigCategory specials = builder.getOrCreateCategory(Text.translatable("thsettings.specials"));

        specials.addEntry(entryBuilder.startTextDescription(Text.translatable("thsettings.specials.howto1")).build());
        specials.addEntry(entryBuilder.startTextDescription(Text.translatable("thsettings.specials.howto2")).build());

        specials.addEntry(genSpecialTransform(entryBuilder, "thsettings.specials.charged_crossbow",
                "thsettings.specials.charged_crossbow.a", settings.ChargedCrossbow.apply, n -> settings.ChargedCrossbow.apply = n,
                "thsettings.specials.charged_crossbow.e", settings.ChargedCrossbow.enable, n -> settings.ChargedCrossbow.enable = n,
                "thsettings.specials.charged_crossbow.x", settings.ChargedCrossbow.x, n -> settings.ChargedCrossbow.x = n,
                "thsettings.specials.charged_crossbow.y", settings.ChargedCrossbow.y, n -> settings.ChargedCrossbow.y = n,
                "thsettings.specials.charged_crossbow.z", settings.ChargedCrossbow.z, n -> settings.ChargedCrossbow.z = n
        ).build());

        specials.addEntry(genSpecialTransform(entryBuilder, "thsettings.specials.filled_map",
                "thsettings.specials.filled_map.a", settings.FilledMap.apply, n -> settings.FilledMap.apply = n,
                "thsettings.specials.filled_map.e", settings.FilledMap.enable, n -> settings.FilledMap.enable = n,
                "thsettings.specials.filled_map.x", settings.FilledMap.x, n -> settings.FilledMap.x = n,
                "thsettings.specials.filled_map.y", settings.FilledMap.y, n -> settings.FilledMap.y = n,
                "thsettings.specials.filled_map.z", settings.FilledMap.z, n -> settings.FilledMap.z = n
        ).build());

        ConfigCategory overrides = builder.getOrCreateCategory(Text.translatable("thsettings.overrides"));

        overrides.addEntry(entryBuilder.startTextDescription(Text.translatable("thsettings.overrides.comingsoon")).build());

        return builder.build();
    }

    private static SubCategoryBuilder genGlobalTransform(ConfigEntryBuilder entryBuilder,
                                                         String subCategoryTranslatable,
                                                         String translatableEnable,
                                                         boolean defaultEnable,
                                                         Consumer<Boolean> consumerEnable,
                                                         String translatableX,
                                                         long defaultX,
                                                         Consumer<Long> consumerX,
                                                         String translatableY,
                                                         long defaultY,
                                                         Consumer<Long> consumerY,
                                                         String translatableZ,
                                                         long defaultZ,
                                                         Consumer<Long> consumerZ){
        SubCategoryBuilder a = entryBuilder.startSubCategory(Text.translatable(subCategoryTranslatable)).setExpanded(false);
        a.add(entryBuilder
                .startBooleanToggle(Text.translatable(translatableEnable), defaultEnable)
                .setSaveConsumer(consumerEnable)
                .build()
        );
        a.add(entryBuilder
                .startLongSlider(Text.translatable(translatableX), defaultX, -30L, 30L)
                .setSaveConsumer(consumerX)
                .build()
        );
        a.add(entryBuilder
                .startLongSlider(Text.translatable(translatableY), defaultY, -30L, 30L)
                .setSaveConsumer(consumerY)
                .build()
        );
        a.add(entryBuilder
                .startLongSlider(Text.translatable(translatableZ), defaultZ, -30L, 30L)
                .setSaveConsumer(consumerZ)
                .build()
        );
        return a;
    }

    private static SubCategoryBuilder genSpecialTransform(ConfigEntryBuilder entryBuilder,
                                                          String subCategoryTranslatable,
                                                          String translatableAllow,
                                                          boolean defaultAllow,
                                                          Consumer<Boolean> consumerAllow,
                                                          String translatableEnable,
                                                          boolean defaultEnable,
                                                          Consumer<Boolean> consumerEnable,
                                                          String translatableX,
                                                          long defaultX,
                                                          Consumer<Long> consumerX,
                                                          String translatableY,
                                                          long defaultY,
                                                          Consumer<Long> consumerY,
                                                          String translatableZ,
                                                          long defaultZ,
                                                          Consumer<Long> consumerZ){
        SubCategoryBuilder a = entryBuilder.startSubCategory(Text.translatable(subCategoryTranslatable)).setExpanded(false);
        a.add(entryBuilder
                .startBooleanToggle(Text.translatable(translatableAllow), defaultAllow)
                .setSaveConsumer(consumerAllow)
                .build()
        );
        a.add(entryBuilder
                .startBooleanToggle(Text.translatable(translatableEnable), defaultEnable)
                .setSaveConsumer(consumerEnable)
                .build()
        );
        a.add(entryBuilder
                .startLongSlider(Text.translatable(translatableX), defaultX, -30L, 30L)
                .setSaveConsumer(consumerX)
                .build()
        );
        a.add(entryBuilder
                .startLongSlider(Text.translatable(translatableY), defaultY, -30L, 30L)
                .setSaveConsumer(consumerY)
                .build()
        );
        a.add(entryBuilder
                .startLongSlider(Text.translatable(translatableZ), defaultZ, -30L, 30L)
                .setSaveConsumer(consumerZ)
                .build()
        );
        return a;
    }

    //depreciated
    /*
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
     */
}
