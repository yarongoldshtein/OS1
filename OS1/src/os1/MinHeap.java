/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os1;

/**
 *
 * @author yaron
 */
public class MinHeap {

    /**
     *
     * @author Yaron
     *
     */
    private catchNode acn[];
    private int size;

    /**
     * constructor
     *
     * @param arr - array of number that build the MinHeap
     */
    public MinHeap(catchNode[] arr) {
        this.size = arr.length;
        this.acn = new catchNode[size];
        for (int i = 0; i < size; i++) {
            acn[i] = new catchNode(arr[i]);
        }
    }

    /**
     * find the number "parent's"
     *
     * @param i - the "child" number
     * @return the number "parent's"
     */
    private int parent(int i) {
        return (i - 1) / 2;
    }

    /**
     * find the number left "child"
     *
     * @param p - the "perent" number
     * @return - the number left "child"
     */
    private int left(int p) {
        return 2 * p + 1;
    }

    /**
     * find the number right "child"
     *
     * @param p - the "perent" number
     * @return - the number right "child"
     */
    private int right(int p) {
        return 2 * p + 2;
    }

    /**
     * keep the MinHeap health
     *
     * @param v - the number that need fixing if needed
     * @param heapSize - the array size
     */
    private void minHeapify(int v, int heapSize) {
        int small;
        int left = left(v);
        int right = right(v);

        if (left < heapSize && ((acn[left].comparePerZ(acn[v])) == -1)) {
            small = left;
        } else {
            small = v;
        }
        if (right < heapSize && (acn[right].comparePerZ(acn[small]) == -1)) {
            small = right;
        }
        if (small != v) {
            exchange(v, small);
            minHeapify(small, heapSize);
        }
    }

    /**
     * exchange between two numbers
     *
     * @param i - index of one number
     * @param j - index of the other number
     */
    private void exchange(int i, int j) {
        catchNode temp =new catchNode(acn[i]);
        acn[i] =new catchNode(acn[j]);
        acn[j] =new catchNode(temp);
    }

    /**
     * build MinHeap from given array
     */
    public void buildMinHeap() {
        for (int i = size / 2; i >= 0; i--) {
            minHeapify(i, size);
        }
    }

    /**
     * insert new catchNode to the minHeap
     *
     * @param val - new number
     */
    public void minHeapInsert(catchNode val) {
        resize(1);
        acn[size - 1] = new catchNode(0,0,999999);
        heapDecreaseKey(size - 1, val);
    }

    /**
     * increases the array of the minHeap
     *
     * @param increment - the difference between the old array and the new array
     */
    private void resize(int increment) {
        catchNode temp[] = new catchNode[size + increment];
        for (int i = 0; i < size; i++) {
            temp[i] =new catchNode(acn[i]);
        }
        acn = temp;
        size = size + increment;
    }

    /**
     * Decrease a key in the minHeap
     *
     * @param i - the index
     * @param val - the value
     */
    private void heapDecreaseKey(int i, catchNode val) {
        if (val.comparePerZ(acn[i])<=0) {
            acn[i] =new catchNode(val);
            while (i > 0 && acn[parent(i)].comparePerZ(acn[i])==1) {
                exchange(i, parent(i));
                i = parent(i);
            }
        }
    }

    /**
     * sort the array of the minHeap
     */
    public void heapSort() {
        int heapSize = size;
        for (int i = heapSize - 1; i >= 1; i--) {
            exchange(0, i);
            heapSize--;
            minHeapify(0, heapSize);
        }
        for (int i = 0; i < size / 2; i++) {
            exchange(0 + i, size - 1 - i);
        }
    }

    /**
     * removes and returns the smallest number of the minHeap
     *
     * @return - the smallest number of the minHeap
     */
    public catchNode heapExtractMin() {
        catchNode min = new catchNode(0,0,9999999);
        if (!isEmpty()) {
            min = acn[0];
            acn[0] = acn[size - 1];
            size = size - 1;
            minHeapify(0, size);
        }
        return min;
    }

    /**
     * check if the MinHeap is empty
     *
     * @return - true if the MinHeap is empty, false if not
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * print the MinHeap
     */
    public void print() {
        for (int i = 0; i < size; i++) {
            System.out.print(acn[i] + ", ");
        }
        System.out.println();
    }

    /**
     * check if the array create a minHeap
     *
     * @param arr - the given array
     * @return - true if the array create a minHeap, false if not
     */
    public static boolean isMinHeap(int[] arr) {
        boolean ans = true;
        for (int i = 0; ans && i < arr.length / 2; i++) {
            int left = i * 2 + 1;
            int right = i * 2 + 2;
            if (left < arr.length && arr[i] > arr[left]) {
                ans = false;
            }
            if (ans && right < arr.length && arr[i] > arr[right]) {
                ans = false;
            }
        }
        return ans;
    }
}
