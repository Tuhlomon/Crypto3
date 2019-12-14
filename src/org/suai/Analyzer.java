package org.suai;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class Analyzer {
    private int[] bits;
    private int N;
    private boolean seriesTest = false;
    private boolean sequenseTestWasRunning = false;

    public static float calcZeroToOneRatio(String cipherFile) {
        byte[] ct = new byte[16];
        long zeros = 0;
        long ones = 0;
        int tmp;
        try {
            FileInputStream fis2 = new FileInputStream(cipherFile);
            while (fis2.read(ct, 0, 16) != -1) {
                for (int i = 0; i < 16; i++){
                    tmp = ct[i];
                    for (int j = 0; j < 8; j++){
                        if ((tmp & 1) == 1) ones++;
                        else zeros++;
                        tmp = tmp >> 1;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Calculation zero-to-one ratio error!\n" + e.getMessage());
        }
        return (float)zeros/ones;
    }

    public Analyzer(String input){
        try {
            FileInputStream fis = new FileInputStream(input);
            File f = new File(input);
            int n = (int)f.length();
            N = n*8;
            byte[] bytes = new byte[n];
            bits = new int[N];
            fis.read(bytes);
            int tmp;
            for (int i = 0; i < n; i++) {
                tmp = bytes[i];
                for (int j = 0; j < 8; j++) {
                    bits[i * 8 + j] = (tmp & 0x80) >> 7;
                    tmp = tmp << 1;
                }
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public boolean frequensyTest(){
        double tmp = (2/Math.sqrt(N)) * (Arrays.stream(bits).sum() - (float)N/2 );
        if (tmp >= -3 && tmp <= 3)
            return true;
        else
            return false;
    }

    public boolean sequenceTest(){
        double[] counter1 = new double[2];
        double[] counter2 = new double[4];
        double[] counter4 = new double[16];
        double[] counter8 = new double[256];
        for (int i = 0; i < N; i++){
            counter1[bits[i]]++;
        }
        double p1 = (double)N/2;
        for (int i = 0; i < N; i += 2){
            counter2[2*bits[i+1] + bits[i]]++;
        }
        double p2 = (double)N/4;
        for (int i = 0; i < N; i += 4){
            counter4[8*bits[i+3] + 4*bits[i+2] + 2*bits[i+1] + bits[i]]++;
        }
        double p4 = (double)N/16;
        for (int i = 0; i < N; i += 8){
            counter8[128*bits[i+7] + 64*bits[i+6] + 32*bits[i+5] + 16*bits[i+4] + 8*bits[i+3] + 4*bits[i+2] + 2*bits[i+1] + bits[i]]++;
        }
        double p8 = (double)N/256;
        for (int i = 0; i < 2; i++)
            counter1[i] = Math.pow(((counter1[i] - p1) / p1), 2);
        for (int i = 0; i < 4; i++)
            counter2[i] = Math.pow(((counter2[i] - p2) / p2), 2);
        for (int i = 0; i < 16; i++)
            counter4[i] = Math.pow(((counter4[i] - p4) / p4), 2);
        for (int i = 0; i < 256; i++)
            counter8[i] = Math.pow(((counter8[i] - p8) / p8), 2);
        double t1 = Arrays.stream(counter1).sum();
        double t2 = Arrays.stream(counter2).sum();
        double t4 = Arrays.stream(counter4).sum();
        double t8 = Arrays.stream(counter8).sum();
        double hi1 = 2.71;
        double hi2 = 6.25;
        double hi4 = 22.3;
        double hi8 = 226.52;

        // for next test
        double tmp = 0;
        tmp += Math.pow(((counter1[0] - p1) / p1), 2) + Math.pow(((counter1[1] - p1) / p1), 2);
        tmp += Math.pow(((counter2[0] - p2) / p2), 2) + Math.pow(((counter2[3] - p2) / p2), 2);
        tmp += Math.pow(((counter4[0] - p4) / p4), 2) + Math.pow(((counter4[1] - p4) / p4), 2);
        tmp += Math.pow(((counter8[0] - p8) / p8), 2) + Math.pow(((counter8[1] - p8) / p8), 2);
        if (tmp < hi8)
            seriesTest = true;
        sequenseTestWasRunning = true;
        //

        if (t1 < hi1 && t2 < hi2 && t4 < hi4 && t8 < hi8)
            return true;
        else
            return false;
    }

    public boolean seriesTest(){
        if (sequenseTestWasRunning)
            return seriesTest;
        else
            sequenceTest();
        return  seriesTest;
    }

    public boolean autocorrelationTest(){
        boolean pass = true;
        int[] tau = new int[N];
        double hits = 0, r, d;
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++)
                tau[j] = bits[(i+j+1)%N];
            for (int j = 0; j < N; j++)
                if (bits[j] == tau[j])
                    hits++;
            d = N - 4;
            r = (hits - d)/N;
            if (r < -3 && r > 3){
                pass = false;
                break;
            }
        }
        return pass;
    }

    public boolean universalTest(){
        int[] parts = new int[N];
        int L = 8;
        int Q = 2000;
        int K = N/8-Q;
        double[] table = new double[256];
        int tmp = 0;
        int counter = 0;
        int pindex = 0;
        double sum = 0;
        for (int i = 0; i < N; i++){
            tmp = tmp + bits[i] * (int)Math.pow(2, counter++);
            if (counter == L){
                parts[pindex++] = tmp;
                counter = 0;
                tmp = 0;
            }
        }
        int i = 0;
        while (i < Q && i < N){
            table[parts[i]] = i;
            i++;
        }
        for (int k = i; k < Q+K; k++){
            sum += (Math.log10(k-table[parts[k]])) / (Math.log10(2));
            table[parts[k]] = k;
        }
        sum /= K;
        double e = 7.1836656;
        double d = 3.238;
        double C = 0.7 - 0.8 / L + ((4 + (double)32 / L) * Math.pow(2, (double)(-3 / L))) / 15;
        double result = (sum - e)/(C * Math.sqrt(d));
        if (result >= -1.2816 && result <= 1.2816)
            return true;
        else
            return false;
    }
}
