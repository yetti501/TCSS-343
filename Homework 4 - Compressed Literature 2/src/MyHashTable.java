// Grant Schorbach
// TCSS 342 -  Data Structures

//Based my code off of:
//https://algs4.cs.princeton.edu/34hash/LinearProbingHashST.java.html

import static java.util.Objects.hash;

public class MyHashTable<K, V> {
    K[] Keys;
    V[] Values;
    int Cap;
    int entries = 0;
    int maxProbe = 0;
    int[] probes = new int[1000];

    MyHashTable(int capacity) {
        Keys = (K[]) new Object[capacity];
        Values = (V[]) new Object[capacity];
        Cap = capacity;
        for (int i = 0; i < probes.length; i++) {
            probes[i] = 0;
        }

    }

    public void put(K searchKey, V newValue) {
        int i = 0;
        int probe = 0;
        for (i = hash(searchKey); Keys[i] != null; i = (i+1)%Cap) {
            if (Keys[i].equals(searchKey)) {
                Values[i] = newValue;
                return;
            }
            probe++;
        }
        Keys[i] = searchKey;
        Values[i] = newValue;
        if (probe > maxProbe) maxProbe = probe;
        probes[probe]++;
        entries++;
    }

    public V get(K searchKey) {
        int i = 0;
        if (searchKey == null) return null;
        for (i = hash(searchKey); Keys[i] != null; i = (i+1)%Cap) {
            if (Keys[i].equals(searchKey)) {
                return Values[i];
            }
        }
        return null;
    }

    public boolean containsKey(K searchKey) {
        return get(searchKey)!=null;
    }

    public void stats() {
        StringBuilder display = new StringBuilder("Hash Table Stats");
        display.append("\n================");
        display.append("\nNumber of Entries: " + entries);
        display.append("\nNumber of Buckets: " + Cap);
        display.append("\nHistogram of Probes: [");
        for (int i = 0; i < maxProbe +1; i++) {
            display.append(probes[i]+ ", ");
            if (i > 0 && i % 20 == 0) {
                display.append("\n");
            }
        }
        display.append("]\nFill Percentage: " + ((float) entries/Cap)*100+"%");

        display.append("\nMax Linear Probe: " + maxProbe);
        float average = 0;
        for (int i = 0; i <= maxProbe; i++) {
            average += probes[i]*i;
        }
        average = average/entries;
        display.append("\nAverage Linear Probe: " + average);
        System.out.println(display.toString());
    }

    public String toString() {
        StringBuilder display = new StringBuilder("String - Code");
        for (int i = 0; i < Keys.length; i++) {
            if (Keys[i] != null) {
                display.append("\n" + Keys[i] + " - " + Values[i]);
            }
        }
        return display.toString();
    }

    private int hash(K searchKey) {
        return (Math.abs(searchKey.hashCode()%Cap));
    }


}