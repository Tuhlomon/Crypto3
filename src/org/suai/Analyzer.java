package org.suai;

import java.io.FileInputStream;

public class Analyzer {
    public static float calcZeroToOneRatio(String cipherFile) { //частотный тест(?)
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


}
