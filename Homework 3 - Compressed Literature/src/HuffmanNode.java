// Grant Schorbach
// TCSS 342 - Data Structures

import java.text.DecimalFormat;

public class HuffmanNode {
    public static final int LEAF = -1;
    private int KEY;
    private int FREQUENCY;

    public HuffmanNode(int key, int frequency){
        this.KEY = key;
        this.FREQUENCY = frequency;
    }

    public HuffmanNode(HuffmanNode a, HuffmanNode b){
        this.KEY = LEAF;
        this.FREQUENCY = a.FREQUENCY + b.FREQUENCY;
    }

    public boolean isValid(){
        return KEY != LEAF;
    }

    public int getKey(){
        return KEY;
    }

    public int getFrequency(){
        return FREQUENCY;
    }

    public String toString(){
        String output;
        DecimalFormat format0 = new DecimalFormat("#");
        DecimalFormat format1 =  new DecimalFormat("##.00");
        DecimalFormat format2 = new DecimalFormat("##.0000");
        if(isValid()){
            String temp = ("[" + format1.format(KEY) + ", " + format2.format(FREQUENCY) + "]");
            output = temp;
        } else {
            String temp = ("[" + format0.format(KEY) + ", " + format2.format(FREQUENCY) + "]");
            output = temp;
        }
        return output;
    }

}
