package org.suai;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class RC4 {
    private final byte[] S = new byte[256];
    private final byte[] T = new byte[256];
    private final int keylen;

    public RC4(final byte[] key) {
        if (key.length < 1 || key.length > 256) {
            throw new IllegalArgumentException("key must be between 1 and 256 bytes");
        } else {
            keylen = key.length;
            for (int i = 0; i < 256; i++) {
                S[i] = (byte) i;
                T[i] = key[i % keylen];
            }
            int j = 0;
            for (int i = 0; i < 256; i++) {
                j = (j + S[i] + T[i]) & 0xFF;
                S[i] ^= S[j];
                S[j] ^= S[i];
                S[i] ^= S[j];
            }
        }
    }

    public byte[] encrypt(final byte[] plaintext) {
        final byte[] ciphertext = new byte[plaintext.length];
        int i = 0, j = 0, k, t;
        for (int counter = 0; counter < plaintext.length; counter++) {
            i = (i + 1) & 0xFF;
            j = (j + S[i]) & 0xFF;
            S[i] ^= S[j];
            S[j] ^= S[i];
            S[i] ^= S[j];
            t = (S[i] + S[j]) & 0xFF;
            k = S[t];
            ciphertext[counter] = (byte) (plaintext[counter] ^ k);
        }
        return ciphertext;
    }

    public void fileEncrypt(String inputFile, String outputFile) {
        byte[] pt = new byte[8];
        byte[] ct;
        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            FileInputStream fis = new FileInputStream(inputFile);
            int i;
            while (fis.read(pt, 0, 8) != -1) {
                ct = encrypt(pt);
                fos.write(ct);
                i = 0;
                while (i < 8) pt[i++] = ' ';
            }
            fos.flush();
        } catch (Exception e) {
            System.out.println("Error!\n" + e.getMessage());
        }
    }

    public void getFile(){
        byte[] pt = new byte[16];
        for (int i = 0; i < 16; i++){
            pt[i] = 0;
        }
        pt = encrypt(pt);
        try {
            FileOutputStream fos = new FileOutputStream("C:\\Users\\Tuhlomon\\Desktop\\crypto\\crypto3_ist.txt");
            fos.write(pt);
        }
        catch (Exception e){
            System.out.println("FILE OUTPUT STREAM ERROR!");
        }
    }
}