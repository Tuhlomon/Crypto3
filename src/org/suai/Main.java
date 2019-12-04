package org.suai;

public class Main {

    public static void main(String[] args) {
        RC4 encrypt = new RC4("Key one for first try!".getBytes());
        encrypt.fileEncrypt("C:\\Users\\Tuhlomon\\Desktop\\crypto\\crypto2_input.jpg", "C:\\Users\\Tuhlomon\\Desktop\\crypto\\crypto3_output.txt");
        System.out.println("File was encrypted!");
        RC4 decrypt = new RC4("Key one for first try!".getBytes());
        decrypt.fileEncrypt("C:\\Users\\Tuhlomon\\Desktop\\crypto\\crypto3_output.txt", "C:\\Users\\Tuhlomon\\Desktop\\crypto\\crypto3_decrypted.jpg");
        System.out.println("File was encrypted!");
    }
}
