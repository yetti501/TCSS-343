// Grant Schorbach
// TCSS 342- Data Structures

import java.util.Arrays;

public class MyPriorityQueue <TEXT extends Comparable<TEXT>> {

    private Object[] heap;
    private int maxSize;
    private int elementSize;

    public MyPriorityQueue(int maxSize){
        heap = new Object[maxSize];
        Arrays.fill(heap, null);
        this.maxSize = maxSize;
        this.elementSize = 0;
    }

    public boolean insert( TEXT element){
        if(this.elementSize == maxSize){
            return false;
        }

        heap[this.elementSize] = element;

        for(int i = this.elementSize; i > 0;){
            int parent = parent(i);
            TEXT cur = (TEXT) heap[i], pr = (TEXT) heap[parent];
            if(cur.compareTo(pr) < 0){
                exchange(i, parent);
                i = parent;
            } else {
                break;
            }
        }

        ++this.elementSize;
        return true;
    }

    public TEXT popSmallest(){
        if(this.elementSize == 0){
            return null;
        }
        TEXT pop = (TEXT)heap[0];
        heap[0] = heap[--this.elementSize];

        if(elementSize == 0){
            return pop;
        }

        for(int i = 0; rightChild(i) < this.elementSize;){
            int next;
            if(isSmaller(i, leftChild(i)) && isSmaller(i, rightChild(i))){
                break;
            }
            next = isSmaller(leftChild(i), rightChild(i)) ? leftChild(i) : rightChild(i);
            exchange(i, next);
            i = next;
        }

        if(elementSize % 2== 0 && isSmaller(elementSize - 1, parent(elementSize -1))){
            exchange(elementSize - 1, parent(elementSize - 1));
        }
        return pop;
    }

    private void exchange(int A, int B){
        Object temp = heap[A];
        heap[A] = heap[B];
        heap[B] = temp;
    }
    private boolean isSmaller(int aIndex, int bIndex){
        return ((TEXT)heap[aIndex]).compareTo((TEXT)heap[bIndex]) <= 0;
    }

    public int getSize(){
        return elementSize;
    }

    private static int parent(int index){
        return index == 0 ? index :  (index - 1) / 2;
    }

    private static int leftChild(int parent){
        return 2 * parent + 2;
    }

    private static int rightChild(int parent){
        return 2 * parent +2;
    }









}
