// Grant Schorbach
// TCSS 342 - Data Structures

import java.util.*;

public class MyCodingTree {
    public static final int CAP = 32768;
    public MyHashTable<String, String> codes;
    public MyHashTable<String, Integer> frequencyMap;
    public final ArrayList<Entry> keyList = new ArrayList<Entry>();
    public byte[] bytes;
    public Entry root;
    String bits;

    public static final String nonSeparators = "0123456789" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz" +
            "'-";

    MyCodingTree(String fulltext) {
        codes = new MyHashTable<>(CAP);
        frequencyMap = new MyHashTable<>(CAP);

        StringBuilder temp = new StringBuilder();
        String word;
        String separator;

        for(int i = 0; i < fulltext.length(); i++){
            if(nonSeparators.contains(fulltext.substring(i, i+1))){
                temp.append(fulltext.substring(i, i+1));
            }
            else {
                if(temp.length() != 0){
                    word = temp.toString();
                    temp.delete(0, temp.length());
                    if(frequencyMap.containsKey(word)){
                        frequencyMap.put(word, frequencyMap.get(word) + 1);
                    }
                    else{
                        frequencyMap.put(word, 1);
                        keyList.add(new Entry(word, ""));
                    }
                }
                separator = fulltext.substring(i, i+1);
                if(frequencyMap.containsKey(separator)){
                    frequencyMap.put(separator, frequencyMap.get(separator) + 1);
                }
                else{
                    frequencyMap.put(separator, 1);
                    keyList.add(new Entry(separator, ""));
                }
            }
        }

        for (int i = 0; i<keyList.size()-1; i++) {
            keyList.get(i).setFreq(frequencyMap.get(keyList.get(i).getKey()));
        }
        formTree(keyList);
        formCodes(root, "");
        writeBits(fulltext);
    }

    private void formTree(ArrayList<Entry> list) {
        ArrayList<Entry> temp = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            temp.add(list.get(i));
        }
        while (temp.size() > 1) {
            Collections.sort(temp);
            Entry newTree = new Entry(null, null);
            newTree.setFreq(temp.get(0).getFreq() + temp.get(1).getFreq());
            newTree.left = temp.get(0);
            newTree.right = temp.get(1);
            temp.remove(0);
            temp.remove(0);
            temp.add(0, newTree);
        }
        root = temp.get(0);
    }

    private void formCodes(Entry troot, String tcode) {
        if (!troot.isLeaf()) {
            formCodes(troot.left, tcode+'0');
            formCodes(troot.right, tcode+'1');
        } else {
            codes.put(troot.getKey(), tcode);
        }
    }

    private void writeBits(String fulltext) {
        StringBuilder encoded = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        for(int i = 0; i < fulltext.length(); i++){
            if(nonSeparators.contains(fulltext.substring(i, i+1))){
                temp.append(fulltext.substring(i, i+1));
            }
            else {
                if(temp.length() != 0){
                    encoded.append(codes.get(temp.toString()));
                    temp.delete(0, temp.length());
                }
                encoded.append(codes.get(fulltext.substring(i, i+1)));
            }
        }
        bytes = new byte[encoded.length()/8];
        for (int i = 0; i < bytes.length;i++) {
            bytes[i] = (byte) Integer.parseUnsignedInt(encoded.substring(i*8, (i*8)+8), 2);
        }
    }
}