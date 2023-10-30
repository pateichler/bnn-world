package com.pat_eichler.bnn.world;

import com.pat_eichler.bnn.brain.DNA;
import com.pat_eichler.bnn.brain.Genetics;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.google.gson.Gson;

public class Runner {

  public static void main(String[] args) {
//    System.out.println(Runtime.getRuntime().availableProcessors());

//    com.pat_eichler.Runner r = new com.pat_eichler.Runner();\
//    r.visualizeBestBrain(48);
    
    Runner r = new Runner();
    r.runNew();
  }

  public void runNew() {
    runNew(getDefaultSettings());
  }

  public void runNew(Settings s) {
    int worldID = getLastWorldID() + 1;
    
    World w = new World(worldID, s);

    try {
      copySettingsToWorld(worldID);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    w.run();
  }
  
  public void runPrevious(int worldID) {
    Settings s = getSettingsFromWorld(worldID);
    runPrevious(worldID, s);
  }
  
  public void runPrevious(int worldID, Settings s) {
    if(s == null) {
      System.out.println("Error: no settings given for world ... exiting");
      return;
    }
    
    DNA[] genePool = loadGenePool(worldID);
    
    World w = new World(worldID, s, genePool);
    w.run();
  }
  
  Settings getDefaultSettings() {
    // TODO: Don't use paths
    File f = new File(Paths.get("config.json").toString());
    return getSettingsFromFile(f);
  }
  
  Settings getSettingsFromWorld(int worldID) {
    // TODO: Don't use paths
    File f = new File(Paths.get("experiments", String.valueOf(worldID), "config.json").toString());

    return getSettingsFromFile(f);
  }
  
  Settings getSettingsFromFile(File file) {
    try {
      return Settings.getSettings(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
  
  void copySettingsToWorld(int worldID) throws IOException {
    // TODO: Replace with saving config file by using settings object
    Files.copy(Paths.get("config.json"), Paths.get("experiments", String.valueOf(worldID), "config.json"));
  }
  
  DNA[] loadGenePool(int worldID) {
    String path = Paths.get("experiments", String.valueOf(worldID), "genePool.ser").toString();
    ObjectInputStream oi = null;
    
    try {
      FileInputStream fi = new FileInputStream(new File(path));
      oi = new ObjectInputStream(fi);
      return (DNA[]) oi.readObject();
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }finally {
      if(oi != null) {
        try {
          oi.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }
    
    return null;
  }
  
  Genetics loadBestBrain(int worldID) {
    String path = Paths.get("experiments", String.valueOf(worldID), "bestGenes.ser").toString();
    ObjectInputStream oi = null;
    
    try {
      FileInputStream fi = new FileInputStream(new File(path));
      oi = new ObjectInputStream(fi);
      return (Genetics) oi.readObject();
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }finally {
      if(oi != null) {
        try {
          oi.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }

    return null;
  }

  int getLastWorldID() {
    File file = new File("experiments");
    String[] directories = file.list(new FilenameFilter() {
      @Override
      public boolean accept(File current, String name) {
        return new File(current, name).isDirectory();
      }
    });
    
    int maxID = 0;
    for(String d : directories) {
      try {
        int w = Integer.parseInt(d);
        if(w > maxID)
          maxID = w;
        
      }catch(NumberFormatException e) { }
    }
    
    return maxID;
  }
}
