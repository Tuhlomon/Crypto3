package org.suai;

public class Main {

    public static void main(String[] args) {
//        RC4 encrypt = new RC4("Key one for first try!!!!34234".getBytes());
//        encrypt.fileEncrypt("C:\\Users\\Tuhlomon\\Desktop\\crypto\\crypto2_input.jpg", "C:\\Users\\Tuhlomon\\Desktop\\crypto\\crypto3_encrypted.txt");
//        System.out.println("File was encrypted!");
//        RC4 decrypt = new RC4("Key one for first try!!!!34234".getBytes());
//        decrypt.fileEncrypt("C:\\Users\\Tuhlomon\\Desktop\\crypto\\crypto3_encrypted.txt", "C:\\Users\\Tuhlomon\\Desktop\\crypto\\crypto3_decrypted.jpg");
//        System.out.println("File was decrypted!");
//        System.out.println("Zero-to-one ratio is " + Analyzer.calcZeroToOneRatio("C:\\Users\\Tuhlomon\\Desktop\\crypto\\crypto3_encrypted.txt"));
//        RC4 getStream = new RC4("Key one for first try!!!!34234".getBytes());
//        getStream.getFile();

        Analyzer analyzer = new Analyzer("C:\\Users\\Tuhlomon\\Desktop\\crypto\\crypto3_ist.txt");
        System.out.println("Frequency test was" + (analyzer.frequensyTest() ? " passed" : " not passed"));
        System.out.println("Sequence test was" + (analyzer.sequenceTest() ? " passed" : " not passed"));
        System.out.println("Series test was" + (analyzer.seriesTest() ? " passed" : " not passed"));
        System.out.println("Autocorrelation test was" + (analyzer.autocorrelationTest() ? " passed" : " not passed"));
        System.out.println("Universal test was" + (analyzer.universalTest() ? " passed" : " not passed"));
    }
}
