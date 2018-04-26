import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Sort {
	/**
	 * Helper method swap
	 */
	private static void swap(int[] A, int i, int j) {
		int temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	}

	/**
	 * Implementaion of bubble sort
	 */
	private static void bubbleSort(int[] A) {
		boolean swapped = true;
		for (int i = A.length - 1; i > 0 && swapped; i--) {
			swapped = false;
			for (int j = 0; j < i; j++) {
				if (A[j] > A[j + 1]) {
					swap(A, j, j + 1);
					swapped = true;
				}
			}
		}
	}

	/**
	 * Implementation of insertion sort
	 */
	private static void insertionSort(int[] A){
		for(int i = 1; i < A.length; i++) {
			int selected = A[i];
			int j = i - 1;
			for(; j >= 0 && selected < A[j]; j--) {
				A[j + 1] = A[j];
			}
			A[j + 1] = selected;
		}
	}

	/**
	 * Implementation of selectionSort
	 */
	private static void selectionSort(int[] A){
		int i, j;
		for (i = 0; i < A.length - 1; i++) {
			int min = i;
			for (j = i + 1; j < A.length; j++) {
				if (A[j] < A[min]){
					min = j;
				}
			}
			if (min != i){
				swap(A, min, i);
			}
		}
	}

	/**
	 * Implementation of merge sort
	 */

	// Divide and Conquer
	private static void mergeSort(int[] A, int low, int high){
		if(low < high) {
			int mid = (int) Math.floor((low + high)/2);
			mergeSort(A, low, mid);
			mergeSort(A, mid + 1, high);
			merge(A, low, mid, high);
		}
	}

	private static void merge(int[] A, int low, int mid, int high){
		int[] left = Arrays.copyOfRange(A, low, mid + 1);
		int[] right = Arrays.copyOfRange(A, mid + 1, high + 1);
		int l = 0;
		int r = 0;
		int i = low;
		for (; i <= high && !((l == left.length) || (r == right.length)); i++){
			if (left[l] < right[r]) {
				A[i] = left[l];
				l += 1; 
			} else {
				A[i] = right[r];
				r += 1;
			}
		}
		if (l == left.length) {
			while (r < right.length){
				A[i] = right[r];
				i += 1;
				r += 1;
			}
		} else {
			while (l < left.length){
				A[i] = left[l];
				i += 1;
				l += 1;
			}
		}

	}

	/**
	 * Implementation of quick sort
	 * in place unstable sort
	 */
	private static void quickSort(int[] A, int low, int high){
		if (low < high){
			int q = partition(A, low, high);
			quickSort(A, low, q - 1);
			quickSort(A, q + 1, high);
		}	
	}

	private static int partition(int[] A, int low, int high){ 
		// lets use randomized pivot
		int randIdx = ThreadLocalRandom.current().nextInt(low, high + 1); //inclusive
		// Debug
		//System.out.println("Random: " + randIdx);
		swap(A, randIdx, high); // place random selected pivot at the last		
		int pivot = A[high]; // always use the last element as pivot
		int storeIdx = low - 1; 		
		for (int j = low; j < high; j++) {
			//System.out.println("stored index: " + storeIdx);
			if (A[j] < pivot) {
				storeIdx += 1; // increase storeIdx
				swap(A, storeIdx, j);				
			}
		} 
		swap(A, storeIdx + 1, high); // elements from low to storeIdx are smaller than pivot, there for change element at storeIdx + 1 and pivot
		return storeIdx + 1; // the index of previous pivot
	}

	/**
	 * Implementaion of radix sort
	 */
	private static int numberOfDigitsIn(int n) {
		return (int) Math.log10((double) n) + 1;
	}

	private static int getDthDigit(int n, int d) {
		for (int i = 0; i < d; i++)
			n = n / 10;
		return n % 10;
	}

	@SuppressWarnings("unchecked")
	private static void radixSort(int[] A) {
		ArrayList<Integer>[] bucket = new ArrayList[10]; 
		for (int i = 0; i < 10; i++)
			bucket[i] = new ArrayList<Integer>(); 
		int maxDigits = -999; 
		for (int i = 0; i < A.length; i++) {
			if (numberOfDigitsIn(A[i]) > maxDigits) {
				maxDigits = numberOfDigitsIn(A[i]);
			}
		}
		// System.out.println("max num digits: " + maxDigits);
		for (int i = 0; i < maxDigits; i++) {
			for (int j = 0; j < A.length; j++) {
				int d = getDthDigit(A[j], i);
				bucket[d].add(A[j]);
			}
			// put back
			int index = 0;
			for (ArrayList<Integer> a : bucket) {
				for (Integer num : a) {
					A[index] = num;
					index++;
				}
				a.clear();
			}
		}
	}

	/**
	 * Implementaion of pigeonhole/bucket sort
	 */
	@SuppressWarnings("unchecked")
	private static void pigeonholeSort(int[] A) {
		int n = A.length; 
		int m = -999; 
		for (int i = 0; i < n; i++) {
			if (A[i] > m) {
				m = A[i];
			}
		}
		ArrayList<Integer>[] bucket = new ArrayList[m + 1];
		for (int i = 0; i <= m; i++)
			bucket[i] = new ArrayList<Integer>(); 
		// put integers into buckets
		for (int i : A) {
			bucket[i].add(i);
		} 
		// put back
		int index = 0;
		for (ArrayList<Integer> a : bucket) {
			for (Integer num : a) {
				A[index] = num;
				index++;
			}
		}
	}

	

	public static void main(String[] args) {
		String command = args[0]; // algorithm name
		int n = Integer.parseInt(args[1]); // number of numbers
		int m = Integer.parseInt(args[2]); // biggest number in data
		Random gen = new Random();
		int[] A = new int[n];
		for (int i = 0; i < n; i++)
			A[i] = gen.nextInt(m);
		if (args.length > 3) {
			for (int x : A)
				System.out.print(x + " ");
			System.out.println();
		}
		long startTimeMs = System.currentTimeMillis();
		if (command.equals("arrays"))
			Arrays.sort(A);
		else if (command.equals("bubble"))
			bubbleSort(A);
		else if (command.equals("radix"))
			radixSort(A);
		else if (command.equals("pigeon"))
			pigeonholeSort(A);
		else if (command.equals("jsort"))
			Arrays.sort(A);
		else if (command.equals("quick"))		
			quickSort(A, 0, A.length - 1);
		else if (command.equals("insertion"))		
			insertionSort(A);
		else if (command.equals("merge"))		
			mergeSort(A, 0, A.length - 1);
		else if (command.equals("selection"))		
			selectionSort(A);
		else {
			System.out.println("invalid command");
			return;
		}
		long elapsedTimeMs = System.currentTimeMillis() - startTimeMs;
		System.out.println("time (ms): " + elapsedTimeMs);
		if (args.length > 3) {
			for (int x : A)
				System.out.print(x + " ");
			System.out.println();
		}
	}
}
