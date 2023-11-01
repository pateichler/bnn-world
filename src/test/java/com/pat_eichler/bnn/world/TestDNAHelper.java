package com.pat_eichler.bnn.world;

import com.pat_eichler.bnn.brain.DNA;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

public class TestDNAHelper {
    @Test
    void testCrossDNA(){
//        Random r = new Random(){ @Override public float nextFloat() {return  1;} };
        Random r = new Random();
        byte[] expectedData = new byte[]{(byte) 0x0F, (byte) 0xF0, (byte) 0xA4};
        int[] segments = new int[]{0, 8, 16};
        DNA dna1 = new DNA(expectedData, segments);
        DNA dna2 = new DNA(new byte[]{(byte) 0xF0, (byte) 0xF0, (byte) 0x59}, segments);

        DNA newDNA = DNAHelper.crossDNA(dna1, dna2, 0, r, 0d, 0.5d);
        System.out.println(Arrays.toString(newDNA.data));
    }

    @Test
    void testCrossDNANone(){
        Random r = new Random(){ @Override public float nextFloat() {return  1;} };
        byte[] expectedData = new byte[]{(byte) 0x0F, (byte) 0xF0, (byte) 0xA4};
        DNA dna1 = new DNA(expectedData, new int[0]);
        DNA dna2 = new DNA(new byte[]{(byte) 0xF0, (byte) 0xF0, (byte) 0x59}, new int[0]);

        DNA newDNA = DNAHelper.crossDNANoSegments(dna1, dna2, 0, r, 0.5d, 0.5d);
        Assertions.assertArrayEquals(expectedData, newDNA.data);
    }

    @Test
    void testCrossDNAHalfCross(){
        Random r = new Random(){
            boolean f = false;
            @Override
            public float nextFloat() {
                f = !f;
                return f ? 0 : 1;
            }
        };
        DNA dna1 = new DNA(new byte[]{(byte) 0x00, (byte) 0x00}, new int[0]);
        DNA dna2 = new DNA(new byte[]{(byte) 0xFF, (byte) 0xFF}, new int[0]);

        DNA newDNA = DNAHelper.crossDNANoSegments(dna1, dna2, 0, r, 0.5d, 0.5d);
        byte[] expectedData = new byte[]{(byte) 170, (byte) 170};
        Assertions.assertArrayEquals(expectedData, newDNA.data);
    }

    @Test
    void testCrossDNAFullMutate(){
        Random r = new Random(){
            boolean f = false;
            @Override
            public float nextFloat() {
                f = !f;
                return f ? 1 : 0;
            }
        };
        DNA dna1 = new DNA(new byte[]{(byte) 0x00, (byte) 0x00}, new int[0]);
        DNA dna2 = new DNA(new byte[]{(byte) 0x00, (byte) 0x00}, new int[0]);

        DNA newDNA = DNAHelper.crossDNANoSegments(dna1, dna2, 0, r, 0.5d, 0.5d);
        byte[] expectedData = new byte[]{(byte) 0xFF, (byte) 0xFF};
        Assertions.assertArrayEquals(expectedData, newDNA.data);
    }

    @Test
    void testCalculateGenePoolVariationNone(){
        //TODO: Complete
        byte[][] poolData = new byte[][]{
                {0,1,1,0},
                {0,1,1,0},
                {0,1,1,0},
                {0,1,1,0}
        };
        DNA[] genePool = new DNA[poolData.length];
        for (int i = 0; i < genePool.length; i++)
            genePool[i] = new DNA(poolData[i], new int[0]);

        Assertions.assertEquals(0, DNAHelper.calculateGenePoolVariation(genePool));
    }

    @Test
    void testCalculateGenePoolVariationSimple(){
        //TODO: Complete
        byte[][] poolData = new byte[][]{
                {0b00000001},
                {0b00001001},
                {0b00100001},
                {0b00001001},
        };
        DNA[] genePool = new DNA[poolData.length];
        for (int i = 0; i < genePool.length; i++)
            genePool[i] = new DNA(poolData[i], new int[0]);

        //(0.25*0.25*3 + 0.75*0.75 + 0.5*0.5*2 + 0.5*0.5*2)/(8*4)
        Assertions.assertEquals((1 + 1 - 0.25*0.25*4)/8, DNAHelper.calculateGenePoolVariation(genePool));
    }
}
