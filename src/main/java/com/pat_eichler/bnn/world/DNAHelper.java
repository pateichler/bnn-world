package com.pat_eichler.bnn.world;

import com.pat_eichler.bnn.brain.DNA;

import java.util.Random;

public class DNAHelper {
    public static DNA crossDNA(DNA dna1, DNA dna2, double fitRatio, Random rand, WorldSettings settings){
        //TODO: Test method
        if(dna1.data.length != dna2.data.length)
            throw new RuntimeException("DNA not same length! Can't cross DNA");

        byte[] data = new byte[dna1.data.length];
        boolean use1 = true;

        for(int i = 0; i < dna1.data.length; i++){
            int b1 = dna1.data[i], b2 = dna2.data[i], bNew = 0;
            for (int b = 0; b < 8; b++){
                if(rand.nextFloat() < settings.MUTATION_RATE && rand.nextFloat() > 0.5f)
                    bNew += 1;
                else{
                    if(rand.nextFloat() < settings.CROSSOVER_RATE)
                        use1 = !use1;
                    bNew += (use1 ? b1 : b2) & 1;
                }

                b1 = b1<<1;
                b2 = b2<<1;
                bNew = bNew<<1;
            }
        }

        return new DNA(data);
    }

    public static double calculateGenePoolVariation(DNA[] genePool){
        //TODO: Test method
        float[] averageBits = new float[genePool[0].data.length * 8];
        for(DNA dna : genePool){
            for(int i = 0; i < dna.data.length; i++){
                for(int b = dna.data[i]; b > 0; b=b<<1){
                    averageBits[i * 8 + b] += b & 1;
                }
            }
        }

        double total = 0d;
        for (float averageBit : averageBits) {
            float diff = 0.5f - averageBit / genePool.length;
            total += diff * diff;
        }
        return total;
    }
}
