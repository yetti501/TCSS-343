// Grant Schorbach
// TCSS 342 - Data Structures

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        StringBuilder message = new StringBuilder();

        BufferedReader br = new BufferedReader(new FileReader("WarAndPeace.txt"));
        File original = new File("WarAndPeace.txt");
        long STARTTIME = System.nanoTime();
        int r;
        while((r = br.read()) != -1){
            message.append((char) r);
        }
        MyCodingTree ct = new MyCodingTree(message.toString());
        ct.codes.stats();
        File codes = new File("codes.txt");
        if (!codes.exists()) {
            codes.createNewFile();
        }
        FileWriter fw = new FileWriter(codes.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        for (int i = 0; i < ct.codes.entries; i++) {
            bw.write(ct.keyList.get(i).getKey());
            bw.write("=" + ct.codes.get(ct.keyList.get(i).getKey()) + ", ");

        }

        File file = new File("encoded.txt");
        if(!file.exists()){
            file.createNewFile();
        }

        FileOutputStream fos = new FileOutputStream(file.getAbsoluteFile());
        fos.write(ct.bytes);
        fos.close();
        br.close();
        bw.close();

        long ENDTIME = System.nanoTime();
        double TOTALTIME = (double)(ENDTIME - STARTTIME)/1000000; // in milliseconds
        System.out.println("Runtime: " + TOTALTIME + " ms");
        System.out.println("File Compression: "+
                ((float) file.length()/(float)original.length())*100+"%");

    }
}