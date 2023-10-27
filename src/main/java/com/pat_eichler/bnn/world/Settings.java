package com.pat_eichler.bnn.world;

import com.pat_eichler.bnn.brain.BrainSettings;
import com.pat_eichler.config.ConfigClass;
import com.pat_eichler.config.ConfigManager;
import com.pat_eichler.config.processor.ProcessConfig;

import java.io.File;
import java.io.FileNotFoundException;

@ConfigClass @ProcessConfig(defaultsFileName = "defaultSettings.json", infoFileName = "webConfigSettings.json")
public class Settings {
    public final WorldSettings worldSettings;
    public final BrainSettings brainSettings;

    public Settings(File globalSettingsFile) throws FileNotFoundException {
        this(globalSettingsFile, globalSettingsFile);
    }

    public Settings(File worldSettingsFile, File brainSettingsFile) throws FileNotFoundException {
        worldSettings = ConfigManager.loadConfig(worldSettingsFile, WorldSettings.class);
        brainSettings = ConfigManager.loadConfig(brainSettingsFile, BrainSettings.class);
    }
}
