// Grant Schorbach
// TCSS 342 - Data Structures

public class Entry implements Comparable<Entry>{
    private String key;
    private String code;
    private int frequency;
    public Entry left, right;

    Entry(String key, String code) {
        this.key = key;
        this.code = code;
        frequency = 1;
    }

    public String getKey() {
        return key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String newValue) {
        code = newValue;
    }

    public int getFreq() {
        return frequency;
    }

    public void setFreq(int newFrequency) {
        frequency = newFrequency;
    }

    public boolean isLeaf() {
        return (left == null) && (right == null);
    }

    @Override
    public int compareTo(Entry that) {
        return this.frequency - that.frequency;
    }

    public boolean equalsEnt(String other) {
        return this.key.equals(other);
    }
}
