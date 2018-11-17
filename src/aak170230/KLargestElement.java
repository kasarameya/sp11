package aak170230;

import java.util.Iterator;
import java.util.Random;

/**
 * Implementation of Data Structure and Algorithms
 * Created by aak170230 Ameya Kasar on 11/15/2018.
 **/
public class KLargestElement<T extends Comparable<? super T>> implements Iterable<T> {
    int THRESHOLD;
    public static Random random;
    public static int trials;

    public KLargestElement(){
        this.THRESHOLD = 13;
        random = new Random();
        trials = 25;
    }


    public static void main(String[] args) {
        int numOfElements = 100;

        int choice = 0;
        if(args.length > 0){
            numOfElements = Integer.parseInt(args[0]);
        }
        if(args.length > 1){
            choice = Integer.parseInt(args[1]);
        }

        if(args.length > 2){
            trials = Integer.parseInt(args[2]);
        }
        int k = numOfElements/2;

        Integer[] integers = new Integer[numOfElements];
        for (int i = 0; i < integers.length ; i++) {
            integers[i] = i;
        }
        List<Integer> list = Arrays.asList(integers);
        KLargestElement<Integer> kLargestElement = new KLargestElement<>();
        Timer timer = new Timer();
        switch (choice){
            case 0:
                for(int t=0;t<trials;t++){
                    Shuffle.shuffle(integers);
//                    System.out.println(kLargestElement.select(integers,k));
                    kLargestElement.select(integers,k);
                }
                break;
            case 1:
                for(int t=0;t<trials;t++){
                    Shuffle.shuffle(integers);
//                    System.out.println(kLargestElement.pQueue(list.iterator(),k));
                    kLargestElement.pQueue(list.iterator(),k);
                }
                break;
        }
        timer.end();
        timer.scale(trials);

        System.out.println("Choice: " + choice + "\n" + timer);
    }

    public T pQueue(Iterator<T> stream,int k){
        BinaryHeap bh = new BinaryHeap();
        return (T) bh.kthLargest(stream,k);

    }

    public T select(T[]arr,int k){
        return arr[select(arr,0,arr.length,k)];
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

    public class BinaryHeap<T extends Comparable<? super T>> {
        T[] pq;
        Comparator<T> comp;
        int size;
        // Constructor for building an empty priority queue using natural ordering of T
        public BinaryHeap(T[] q) {

            // Use a lambda expression to create comparator from compareTo
            this(q, (T a, T b) -> a.compareTo(b));
        }

        public BinaryHeap(){}

        // Constructor for building an empty priority queue with custom comparator
        public BinaryHeap(T[] q, Comparator<T> c) {
            pq = q;
            comp = c;
            this.size = q.length;
        }

        /** Build a priority queue with a given array q, using q[0..n-1].
         *  It is not necessary that n == q.length.
         *  Extra space available can be used to add new elements.
         *  Implement this if solving optional problem.  To be called from heap sort.
         */
        private BinaryHeap(T[] q, Comparator<T> c, int n) {
            pq = q;
            comp = c;
            this.size = pq.length;
            this.buildHeap();
        }

        public void add(T x) { /* throw exception if pq is full */
            if(this.size == pq.length){
                throw new IndexOutOfBoundsException();
            }
            pq[this.size] = x;
            percolateUp(this.size);
            this.size++;
        }

        public boolean offer(T x) { /* return false if pq is full */
            if(this.size == pq.length)
                return false;
            this.add(x);
            return true;
        }

        public T remove() { /* throw exception if pq is empty */
            T min;
            if (this.size == 0){
                throw new NoSuchElementException();
            }else if(this.size == 1){
                this.size--;
                return pq[0];
            }else{
                min =  pq[0];
                pq[0] = pq[this.size-1];
                this.size--;
                this.percolateDown(0);
                return min;
            }
        }

        public T poll() { /* return null if pq is empty */
            if(this.size == 0)
                return null;
            // remove only if there is an element in the heap
            return this.remove();
        }

        public T peek() { /* return null if pq is empty */
            if(this.size == 0)
                return null;
            return pq[0];
        }

        /** pq[i] may violate heap order with parent */
        void percolateUp(int i) { /* to be implemented */
            T temp = pq[i];
            int parent = parent(i);
            while(i > 0 && this.comp.compare(pq[parent],temp)>0){
                pq[i] = pq[parent];
                i = parent;
                parent = parent(i);
            }
            pq[i] = temp;
        }

        /** pq[i] may violate heap order with children */
        void percolateDown(int i) { /* to be implemented */
            T temp = pq[i];
            int child = leftChild(i);
            while(child < this.size){
                //check which child is smaller of the 2 children and select it
                if(child < this.size-1 && this.comp.compare(pq[child],pq[child+1]) > 0){
                    child++;
                }
                if(this.comp.compare(temp,pq[child])<0){
                    break;
                }
                pq[i] = pq[child];
                i = child;
                child = leftChild(i);
            }
            pq[i] = temp;
        }

        // Assign x to pq[i].  Indexed heap will override this method
        void move(int i, T x) {
            pq[i] = x;
        }

        public void printQueue()
        {
            System.out.println("Queue:");
            for(int x=0;x<this.size;x++)
            {
                System.out.println(pq[x]);
            }
            System.out.println("Priority queue size is now "+this.size);
        }

        int parent(int i) {
            return (i-1)/2;
        }

        int leftChild(int i) {
            return 2*i + 1;
        }

        public int size(){
            return this.size;
        }

// end of functions for team project




// start of optional problem: heap sort (Q2)

        /** Create a heap.  Precondition: none.
         *  Implement this ifsolving optional problem
         */
        void buildHeap() {
//        int i= this.parent(this.size -1);
//        while (i>=0){
//            this.percolateDown(i);
//            i--;
//        }

            for(int i=this.size-1;i>=0;i--){
                this.percolateDown(i);
            }
        }

        /* sort array arr[].
           Sorted order depends on comparator used to buid heap.
           min heap ==> descending order
           max heap ==> ascending order
           Implement this for optional problem
         */
        public <T extends Comparable<? super T>> void heapSort(T[] arr, Comparator<T> c) { /* to be implemented */
        }

        // Sort array using natural ordering
        public <T extends Comparable<? super T>> void heapSort(T[] arr) {
            heapSort(arr, (T a, T b) -> a.compareTo(b));
        }
// end of optional problem: heap sort (Q2)



// start of optional problem: kth largest element (Q3)

        public void replace(T x) {
	/* TO DO.  Replaces root of binary heap by x if x has higher priority
	     (smaller) than root, and restore heap order.  Otherwise do nothing.
	   This operation is used in finding largest k elements in a stream.
	   Implement this if solving optional problem.
	 */
        }

        /** Return the kth largest element of stream using custom comparator.
         *  Assume that k is small enough to fit in memory, but the stream is arbitrarily large.
         *  If stream has less than k elements return null.
         */
        public <T extends Comparable<? super T>> T kthLargest(Iterator<T> stream, int k, Comparator<T> c) {
            Integer[] arr = new Integer[k];
            int i =0;
            if(stream.hasNext()){
                while(i<k)
                    arr[i++] = (Integer) stream.next();
                BinaryHeap bnhp = new BinaryHeap((T[])arr, c,k);
                bnhp.buildHeap();
                while(stream.hasNext())
                {
                    T x = stream.next();
                    if(bnhp.pq[0].compareTo(x) <0){
                        bnhp.pq[0] = x;
                        bnhp.percolateDown(0);
                    }
                }
                T kth = (T) bnhp.peek();
                return kth;
            }
            return null;
        }

        /** Return the kth largest element of stream using natural ordering.
         *  Assume that k is small enough to fit in memory, but the stream is arbitrarily large.
         *  If stream has less than k elements return null.
         */
        public <T extends Comparable<? super T>> T kthLargest(Iterator<T> stream, int k) {
            return kthLargest(stream, k, (T a, T b) -> a.compareTo(b));
        }
// end of optional problem: kth largest element (Q3)

    }

    public static class Shuffle {

        public static void shuffle(int[] arr) {
            shuffle(arr, 0, arr.length-1);
        }

        public static<T> void shuffle(T[] arr) {
            shuffle(arr, 0, arr.length-1);
        }

        public static void shuffle(int[] arr, int from, int to) {
            int n = to - from  + 1;
            for(int i=1; i<n; i++) {
                int j = random.nextInt(i);
                swap(arr, i+from, j+from);
            }
        }

        public static<T> void shuffle(T[] arr, int from, int to) {
            int n = to - from  + 1;
            Random random = new Random();
            for(int i=1; i<n; i++) {
                int j = random.nextInt(i);
                swap(arr, i+from, j+from);
            }
        }

        static void swap(int[] arr, int x, int y) {
            int tmp = arr[x];
            arr[x] = arr[y];
            arr[y] = tmp;
        }

        static<T> void swap(T[] arr, int x, int y) {
            T tmp = arr[x];
            arr[x] = arr[y];
            arr[y] = tmp;
        }

        public static<T> void printArray(T[] arr, String message) {
            printArray(arr, 0, arr.length-1, message);
        }

        public static<T> void printArray(T[] arr, int from, int to, String message) {
            System.out.print(message);
            for(int i=from; i<=to; i++) {
                System.out.print(" " + arr[i]);
            }
            System.out.println();
        }
    }

    public static class Timer {
        long startTime, endTime, elapsedTime, memAvailable, memUsed;
        boolean ready;

        public Timer() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public void start() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public Timer end() {
            endTime = System.currentTimeMillis();
            elapsedTime = endTime-startTime;
            memAvailable = Runtime.getRuntime().totalMemory();
            memUsed = memAvailable - Runtime.getRuntime().freeMemory();
            ready = true;
            return this;
        }

        public long duration() { if(!ready) { end(); }  return elapsedTime; }

        public long memory()   { if(!ready) { end(); }  return memUsed; }

        public void scale(int num) { elapsedTime /= num; }

        public String toString() {
            if(!ready) { end(); }
            return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed/1048576) + " MB / " + (memAvailable/1048576) + " MB.";
        }
    }
}
