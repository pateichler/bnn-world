package com.pat_eichler.bnn.world;

import com.pat_eichler.bnn.brain.DNA;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.Arrays;
import java.util.Random;

public class DNAHelper {

    public static DNA crossDNA(DNA dna1, DNA dna2, double fitRatio, Random rand, WorldSettings settings){
        return crossDNA(dna1, dna2, fitRatio, rand, settings.MUTATION_RATE, settings.CROSSOVER_RATE);
    }

    public static DNA crossDNA(DNA dna1, DNA dna2, double fitRatio, Random rand, double mutationRate, double crossoverRate){
        //Method could probably be optimized
        if(dna1.data.length != dna2.data.length)
            throw new RuntimeException("DNA not same length! Can't cross DNA");

        byte[] data = new byte[dna1.data.length];
        boolean use1 = true;
        int curSegment = 0;

        for(int i = 0; i < dna1.data.length; i++){
            int b1 = dna1.data[i], b2 = dna2.data[i], bNew = 0;
            for (int b = 0; b < 8; b++){
                //, b1>>=1, b2>>=1
                bNew = bNew<<1;
                if(curSegment < dna1.segments.length && dna1.segments[curSegment] == i*8+b) {
                    curSegment++;
                    if(rand.nextFloat() < crossoverRate)
                        use1 = !use1;
                }

                int bit = ((use1 ? b1 : b2) >> (7-b)) & 1;
                bNew += rand.nextFloat() < mutationRate ? bit ^ 1 : bit;
            }
            data[i] = (byte) (bNew);
        }

        return new DNA(data, dna1.segments);
    }

    public static DNA crossDNANoSegments(DNA dna1, DNA dna2, double fitRatio, Random rand, double mutationRate, double crossoverRate){
        //Method could probably be optimized
        if(dna1.data.length != dna2.data.length)
            throw new RuntimeException("DNA not same length! Can't cross DNA");

        byte[] data = new byte[dna1.data.length];
        boolean use1 = true;

        for(int i = 0; i < dna1.data.length; i++){
            int b1 = dna1.data[i], b2 = dna2.data[i], bNew = 0;
            for (int b = 0; b < 8; b++){
                //, b1>>=1, b2>>=1
                bNew = bNew<<1;
                if(rand.nextFloat() < crossoverRate)
                    use1 = !use1;

                int bit = ((use1 ? b1 : b2) >> (7-b)) & 1;
                bNew += rand.nextFloat() < mutationRate ? bit ^ 1 : bit;
            }
            data[i] = (byte) (bNew);
        }

        return new DNA(data, dna1.segments);
    }

    public static double calculateGenePoolVariation(DNA[] genePool){
        int[] averageBits = new int[genePool[0].data.length * 8];
        for(DNA dna : genePool){
            for(int i = 0; i < dna.data.length; i++){
                for(int b = dna.data[i], bVal = 0; b > 0; b=b>>1, bVal++){
                    averageBits[i * 8 + bVal] += b & 1;
                }
            }
        }

        double total = 0d;
        for (int a : averageBits) {
            double diff = (0.5d - (double) a/genePool.length);
            total += 1d - diff * diff * 4;
        }

        return  total / averageBits.length;

        //TODO: Remove error since unnecessary
//        float[] error = new float[genePool[0].data.length * 8];
//        for(DNA dna : genePool){
//            for(int i = 0; i < dna.data.length; i++){
//                for(int b = dna.data[i], bVal = 0; bVal < 8; b=b>>1, bVal++){
//                    float diff = (float) averageBits[i * 8 + bVal] / genePool.length - (b & 1);
//                    error[i * 8 + bVal] += diff * diff;
//                }
//            }
//        }

//        double total = 0d;
//        for (float e : error)
//            total += e;
//
//        return total/(error.length * genePool.length);
    }
}
