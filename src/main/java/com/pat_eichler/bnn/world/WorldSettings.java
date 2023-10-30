package com.pat_eichler.bnn.world;

import com.pat_eichler.config.ConfigClass;
import com.pat_eichler.config.processor.ConfigProperty;

@ConfigClass
public class WorldSettings {
    //TODO: Remove field and put it in a app properties file
    @ConfigProperty(defualtValue = "2")
    public Integer THREAD_COUNT;

    @ConfigProperty(defualtValue = "20", comment = "Population size of brains.")
    public Integer POP_SIZE;
    @ConfigProperty(defualtValue = "500", comment = "Number of generations to run.")
    public Integer NUM_GENS;
    @ConfigProperty(defualtValue = "8", comment = "Tournament size of selecting mates.")
    public Integer TOURN_SIZE;
    @ConfigProperty(defualtValue = "0.0025", comment = "Probability of a single bit mutating.")
    public Double MUTATION_RATE;
    @ConfigProperty(defualtValue = "0.005", comment = "Probability of a single bit crossing over to other parent.")
    public Double CROSSOVER_RATE;

    public Double MAX_PARENT_RATIO;
    @ConfigProperty(defualtValue = "SimpleRunner", comment = "Brain runner to use in world.")
    public String BRAIN_RUNNER;
}
