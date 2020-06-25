// Grant Schorbach
// TCSS 342 - Data Structures

import java.io.*;
import java.text.DecimalFormat;

public class Main {

    public static void main(String[] args){
        System.out.println("Compression");

        String fileName = "WarAndPeace.txt";
        String codesFile = "codes.txt";
        String compressedFile = "compressed.txt";

        fileReader(fileName, codesFile, compressedFile);

        String fileName2 = "FiftyShadesofGrey.txt";
        String codesFile2 = "codes2.txt";
        String compressedFile2 = "compressed2.txt";

        fileReader(fileName2, codesFile2, compressedFile2);

//        testCodingTree();

    }

    private static void fileReader(String inputFile, String codeFile, String compressedFile){
        String content = readFile(inputFile);
        long startTime = System.currentTimeMillis();
        CodingTree TREE = new CodingTree(content);
//        CodingTree TREE = new CodingTree("hello");
        try{
            PrintStream CODEFILE = new PrintStream(codeFile);
            CODEFILE.print(TREE.CODES);
            CODEFILE.close();

            CODEFILE = new PrintStream(compressedFile);
            int saveLen = (TREE.BITS.length() + 7) >> 3;
            byte[] saveBit = new byte[saveLen];
            for(int i = 0; i < saveLen; ++i) {
                byte b = 0;
                for (int k = 0; k < 8; ++k) {
                    if (8 * i + k < TREE.BITS.length() && TREE.BITS.charAt(8 * i + k) == '1') {
                        b |= i << k;
                    }
                    saveBit[i] = b;
                }
            }
            CODEFILE.write(saveBit);
            CODEFILE.close();

            long endTime = System.currentTimeMillis();
            int originalSize = content.length();
            int compressedSize = saveLen;
            double ratio = 100.0 * compressedSize / originalSize;
            long time =  endTime - startTime;
            DecimalFormat format = new DecimalFormat("##.00");
            System.out.println("Original Size: " + originalSize + " bytes");
            System.out.println("Compressed Size: " + compressedSize + " bytes");
            System.out.println("Compression Ratio: " + format.format(ratio) + "%");
            System.out.println("Total Time: " + time + "ms");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private static String readFile(String input){
        try{
            FileReader file = new FileReader(input);
            BufferedReader buffer = new BufferedReader(file);
            StringBuilder builder = new StringBuilder();
            char[] hold = new char[4096];
            int read;
            while((read = buffer.read(hold)) > 0){
                builder.append(hold, 0, read);
            }
            return builder.toString();
        } catch (FileNotFoundException e){
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void testCodingTree(){
        System.out.println("Test Coding Tree");
        String testString = "This is my test String.";
        CodingTree tree = new CodingTree(testString);

        tree.display();

        HuffmanNode node1 = new HuffmanNode(1, 1);

        int[] sampleArray = tree.computerFrequency(testString);
        for(int i = 0; i < sampleArray.length; i++) {
            System.out.print(sampleArray[i] + ", ");
        }
    }

}
