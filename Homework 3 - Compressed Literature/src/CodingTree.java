// Grant Schorbach
// TCSS 342 - Data Structures

import java.util.*;

public class CodingTree implements Comparable<CodingTree>{

    private HuffmanNode NODE;
    private CodingTree LEFT;
    private CodingTree RIGHT;
    public Map<Character, String> CODES;
    public String BITS = "";

    public CodingTree(String message) {
        int[] freq = computerFrequency(message);
        buildTree(freq);
        CODES = new HashMap<>();
        buildCodeMap("", CODES);
        StringBuilder sb = new StringBuilder();
        char[] chars = message.toCharArray();

        for(char c:chars){
            sb.append(CODES.get(c));
        }
        BITS = sb.toString();
    }

    private CodingTree(HuffmanNode NODE){
        this.NODE = NODE;
        LEFT = null;
        RIGHT = null;
    }

    private CodingTree(CodingTree left, CodingTree right){
        this.NODE = new HuffmanNode(left.NODE, right.NODE);
        LEFT = left;
        RIGHT = right;
    }

// This is the the myPriorityQueue method.

//    private void buildTree(int[] freq){
//        MyPriorityQueue<CodingTree> heap = new MyPriorityQueue<CodingTree>(freq.length);
//        for(int k = 0; k < freq.length; ++k){
//            if(freq[k] == 0){
//                continue;
//            }
//            System.out.printf("char = %c, freq = %d", k, freq[k]);
//            HuffmanNode node = new HuffmanNode(k, freq[k]);
//            heap.insert(new CodingTree(node));
//        }
//
//        while(heap.getSize() > 1){
//            CodingTree min0 = heap.popSmallest();
//            CodingTree min1 = heap.popSmallest();
//            heap.insert(new CodingTree(min0, min1));
//        }
//
//        CodingTree tree = heap.popSmallest();
//        this.NODE = tree.NODE;
//        this.LEFT = tree.LEFT;
//        this.RIGHT = tree.RIGHT;
//    }

    private void buildTree(int[] freq){
        PriorityQueue<CodingTree> heap = new PriorityQueue<>(freq.length);
        for(int i = 0; i < freq.length; i++){
            if(freq[i] == 0){
                continue;
            }
            System.out.println("Char: " + Character.toString((char) i) + " Freq = " + freq[i]);
            HuffmanNode node = new HuffmanNode(i, freq[i]);
            heap.add(new CodingTree(node));
        }

        while(heap.size() > 1){
            CodingTree min0 = heap.poll();
            CodingTree min1 = heap.poll();
            heap.add(new CodingTree(min0, min1));
        }

        CodingTree tree = heap.poll();
        this.NODE = tree.NODE;
        this.LEFT = tree.LEFT;
        this.RIGHT = tree.RIGHT;
    }

    private void buildCodeMap(String prefix, Map<Character, String> CODES){
        if(NODE.isValid()){
            CODES.put((char)NODE.getKey(), prefix);
            System.out.println("Char: " + (char)NODE.getKey() + " Codes: " + prefix);
        } else {
            LEFT.buildCodeMap(prefix + "0", CODES);
            RIGHT.buildCodeMap(prefix + "1", CODES);
        }
    }

    public int[] computerFrequency(String incoming){
        int[] frequency = new int[256];
        Arrays.fill(frequency, 0);
        for(int i = 0; i < incoming.length(); ++i){
            char c = incoming.charAt(i);
            ++frequency[c&0xff];
        }
        return frequency;
    }

    public void display(){
        display(0);
        System.out.println();
    }

    private void display(int depth){
        char[] space = new char[12 * (depth + 1)];
        Arrays.fill(space, ' ');
        System.out.print(" " + NODE);
        if(LEFT != null){
            LEFT.display(depth +1);
        }
        if(RIGHT != null){
            System.out.println(new String(space));
            RIGHT.display(depth + 1);
        }
    }

    public int compareTo(CodingTree o){
        return NODE.getFrequency() - o.NODE.getFrequency();
    }
}
