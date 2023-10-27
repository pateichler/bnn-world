package com.pat_eichler.bnn.world;

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
    
    Genetics[] genePool = loadGenePool(worldID);
    
    World w = new World(worldID, s, genePool);
    w.run();
  }
  
//  public void visualizeBestBrain(int worldID) {
//    Settings s = getSettingsFromWorld(worldID);
//
//    Genetics g = loadBestBrain(worldID);
//
//    visualizeBrain(g);
//  }
  
//  void visualizeBrain(Genetics g) {
//    Brain b = new Brain(g);
//    b.printConnectionStrengths();
//    System.out.println("===============");
//    b.printConnectionTypes();
//
//    SimpleRunner sr = new SimpleRunner(b);
//    double fit = sr.call();
//    System.out.println("Trained with fitness: " + fit);
//
//    SimpleRunnerVisualizer v = new SimpleRunnerVisualizer(b);
//    System.out.println("com.pat_eichler.Brain in last state:");
//    v.br.visualize();
//
//    System.out.println("====================");
//    b.printConnectionStrengths();
//    System.out.println("===============");
//    b.printConnectionTypes();
//
////    b.clearTransmitters();
//    v.run();
//  }
  
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
      return new Settings(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
//    Reader reader = null;
//    try {
//      reader = Files.newBufferedReader(path);
//
//      Gson gson = new Gson();
//      return gson.fromJson(reader, Settings.class);
//    } catch (IOException e) { }
//    finally {
//        try {
//          if(reader != null)
//            reader.close();
//        } catch (IOException e) {
//          e.printStackTrace();
//        }
//    }
  }
  
  void copySettingsToWorld(int worldID) throws IOException {
    // TODO: Replace with saving config file by using settings object
    Files.copy(Paths.get("config.json"), Paths.get("experiments", String.valueOf(worldID), "config.json"));
  }
  
  Genetics[] loadGenePool(int worldID) {
    String path = Paths.get("experiments", String.valueOf(worldID), "genePool.ser").toString();
    ObjectInputStream oi = null;
    
    try {
      FileInputStream fi = new FileInputStream(new File(path));
      oi = new ObjectInputStream(fi);
      return (Genetics[]) oi.readObject();
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
  
//  static void printVariationFromWorld(int worldID) {
//    Runner r = new Runner();
//    Settings s = r.getSettingsFromWorld(worldID);
//    Genetics[] genePool = r.loadGenePool(5);
//    System.out.println("Strength std: " + World.calculateGenePoolVariation(genePool, true));
//    System.out.println("Type std: " + World.calculateGenePoolVariation(genePool, false));
//
//    Genetics[] genePoolRandom = new Genetics[genePool.length];
//    for(int i = 0; i < genePool.length; i++)
//      genePoolRandom[i] = new NNGenetics();
//
//    System.out.println("===============");
//    System.out.println("Strength random std: " + World.calculateGenePoolVariation(genePoolRandom, true));
//    System.out.println("Type random std: " + World.calculateGenePoolVariation(genePoolRandom, false));
//  }
  
//  static void testBrain() {
//    Brain b = new Brain(new NNGenetics());
//    System.out.println(b);
//
//    try {
//      Brain b2 = new Brain(new NNGenetics((NNGenetics)b.dna, (NNGenetics)b.dna));
//      System.out.println(b2);
//    } catch (Exception e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
//  }
  
//  static void testNeuralNet() {
//    int inputNodes = Settings.Instance.totalNTCount() * 2;
//
//    int[] strengthLayers = new int[Settings.Instance.STRENGTH_NET_IN_LAYERS.length + 2];
//    strengthLayers[0] = inputNodes;
//    strengthLayers[strengthLayers.length - 1] = 3;
//
//    NeuralNetwork strengthNet = new NeuralNetwork(strengthLayers);
//
//    System.out.println(strengthNet.predict(new double[] {0,0,0,0,0})-1);
//
//    try {
//      NeuralNetwork newNet = new NeuralNetwork(strengthNet, strengthNet, 0.5);
//      System.out.println(newNet.predict(new double[] {0,0,0,0,0})-1);
//    } catch (Exception e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
//  }

}
