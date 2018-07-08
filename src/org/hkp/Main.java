package org.hkp;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    private static int size = 1000000;

    public static void main(String[] args) {
        // two necessary arguments: expectedSize and fpp
        // must specify T
        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size);
        // BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size, 0.05);
        for (int i = 0; i < size; i++) {
            bloomFilter.put(i);
        }
        for(int i = 0; i < 100; i++) {
            boolean exist = bloomFilter.mightContain(i);
            if (!exist) {
                System.out.println("miss:" +  i);
            }
        }
        // validate false positive
        // default fpp is 3%
        for (int j = size + 1; j < size + 10000; j++) {
            boolean flag = bloomFilter.mightContain(j);
            if (flag) {
                System.out.println("false positive contains:" + j);
            }
        }

        // bloom filter supports serialization and deserialization
        final String fileName = "bloom_filter_data";
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            bloomFilter.writeTo(fileOutputStream);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            BloomFilter<Integer> newBloomFilter = BloomFilter.readFrom(fileInputStream, Funnels.integerFunnel());

            for(int i = 0; i < 100; i++) {
                boolean exist = newBloomFilter.mightContain(i);
                if (!exist) {
                    System.out.println("miss in new:" +  i);
                }
            }
            // validate false positive
            // default fpp is 3%
            for (int j = size + 1; j < size + 10000; j++) {
                boolean flag = newBloomFilter.mightContain(j);
                if (flag) {
                    System.out.println("false positive contains in new bloom filter:" + j);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}