package com.pat_eichler.bnn.world;

import com.pat_eichler.config.ConfigManager;

public class ExampleWorld {
    public static void main(String[] args){
        Settings s = ConfigManager.loadConfigFromResources("config.json", Settings.class);
        World w = new World(0, s);
        w.run();
    }
}
