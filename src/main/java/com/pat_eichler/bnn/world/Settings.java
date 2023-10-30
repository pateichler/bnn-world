package com.pat_eichler.bnn.world;

import com.pat_eichler.bnn.brain.BrainSettings;
import com.pat_eichler.bnn.brain.Connection;
import com.pat_eichler.config.ConfigClass;
import com.pat_eichler.config.ConfigManager;
import com.pat_eichler.config.processor.ProcessConfig;

import java.io.File;
import java.io.FileNotFoundException;

@ConfigClass @ProcessConfig(defaultsFileName = "defaultSettings.json", infoFileName = "webConfigSettings.json")
public class Settings {
    public BrainSettings brainSettings;
    public WorldSettings worldSettings;

    public Settings(){}

    public Settings(BrainSettings brainSettings, WorldSettings worldSettings){
        this.brainSettings = brainSettings;
        this.worldSettings = worldSettings;
    }

    public static Settings getSettings(File globalSettingsFile) throws FileNotFoundException {
        return ConfigManager.loadConfig(globalSettingsFile, Settings.class);
    }

    public static Settings getSettings(File worldSettingsFile, File brainSettingsFile) throws FileNotFoundException {
        BrainSettings brainSettings = ConfigManager.loadConfig(brainSettingsFile, BrainSettings.class);
        WorldSettings worldSettings = ConfigManager.loadConfig(worldSettingsFile, WorldSettings.class);
        return new Settings(brainSettings, worldSettings);
    }
}
