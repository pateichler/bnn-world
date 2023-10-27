package com.pat_eichler.bnn.world;

import com.pat_eichler.config.ConfigClass;

@ConfigClass
public class WorldSettings {
    public Integer THREAD_COUNT;

    public Integer POP_SIZE;
    public Integer NUM_GENS;
    public Integer TOURN_SIZE;

//    public double MUTATION_RATE;
//    public double GENE_COMB_PRECISION;
    public Double MAX_PARENT_RATIO;

    public String BRAIN_RUNNER;

//    public BrainRunner createBrainRunner(Brain b) {
//        switch(BRAIN_RUNNER) {
//            case "simple":
//                return new SimpleRunner(b);
//            case "memory":
//                return new MemoryRunner(b);
//            default:
//                return null;
//        }
//    }
}
