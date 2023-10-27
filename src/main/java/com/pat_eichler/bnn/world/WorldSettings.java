package com.pat_eichler.bnn.world;

import com.pat_eichler.config.ConfigClass;

@ConfigClass
public class WorldSettings {
    public Integer THREAD_COUNT;

    public Integer POP_SIZE;
    public Integer NUM_GENS;
    public Integer TOURN_SIZE;
    public Double MUTATION_RATE;
    public Double CROSSOVER_RATE;
    public Double MAX_PARENT_RATIO;

    public String BRAIN_RUNNER;
}
