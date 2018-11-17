package aak170230;

import java.util.Iterator;
import java.util.Random;

/**
 * Implementation of Data Structure and Algorithms
 * Created by aak170230 Ameya Kasar on 11/15/2018.
 **/
public class KLargestElement<T extends Comparable<? super T>> implements Iterable<T> {
    int THRESHOLD = 13;


    public static void main(String[] args) {
        KLargestElement<Integer> kLargestElement = new KLargestElement<>();
        Integer[] integers = new Integer[16000000];
        for (int i = 0; i < integers.length ; i++) {
            integers[i] = i;

        }
        kLargestElement.select(integers, integers.length / 2);

    }

    public void select(T[]arr,int k){
        System.out.println(arr[select(arr,0,arr.length,k)]);
    }

    private int select(T[] arr, int p, int r, int k) {
        int q = randomizedPartition(arr,p,p+r-1);
        int left = q - p;
        int right = r - left - 1;
        if(right >= k){
            return select(arr, q + 1, right, k);
        }else if(right+1 == k){
            return q;
        }else{
            return select(arr, p, left, k - right - 1);
        }
    }

    private int randomizedPartition(T[] arr, int p, int r) {
        Random random = new Random();
        int i = p + random.nextInt(r - p + 1);
        swap(arr, i, r);
        i = p - 1;
        T x = arr[r];
        for (int j = p; j <r ; j++) {
            if(arr[j].compareTo(x) <= 0){
                i++;
                swap(arr,i,j);
            }
        }
        swap(arr,i+1,r);
        return i+1;
    }

    private void swap(T[] arr, int i, int r) {
        T temp = arr[i];
        arr[i] = arr[r];
        arr[r] = temp;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }
}
