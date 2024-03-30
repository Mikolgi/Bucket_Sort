package bucketsort;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.*;
import java.util.Scanner;

public class BucketSort {
    private static int count;

    public static void main(String[] args) {
        try {
            File file = new File("data.txt");

            Scanner in = new Scanner(file);
            ArrayList<int[]> arrays = new ArrayList<>();

            while (in.hasNextLine()) {
                String size = in.nextLine();
                String line = in.nextLine();
                String[] elements = line.split(" ");
                int[] array = new int[elements.length];

                for (int i = 0; i < elements.length; i++) {
                    array[i] = Integer.parseInt(elements[i]);
                }

                arrays.add(array);
            }

            in.close();

            for (int[] array : arrays) {
                long startTime = System.nanoTime();
                count = 0;
                bucketSort(array);
                long endTime = System.nanoTime();
                int numbers = array.length;
                System.out.println();
                System.out.println("Количество итераций  " + count);
                System.out.println("Время в наносекундах  " +((endTime - startTime)));
                System.out.println("Количество элементов  " + numbers);
                System.out.println();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void insertionSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            count++;
            int pasteElement = array[i];
            int j;
            for (j = i; j > 0; j--) {
                if (array[j - 1] <= pasteElement) {
                    break;
                }
                array[j] = array[j - 1];
            }
            array[j] = pasteElement;
        }
    }

    public static int[] findMinMax(int[] array) {
        if (array.length == 0) {
            return new int[] { 0, 0 };
        }
        int min = array[0];
        int max = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
            if (array[i] > max) {
                max = array[i];
            }
        }
        return new int[] { min, max };
    }

    public static int[] calculateBlockSize(int[] array, int n) {
        int[] blockSize = new int[n];
        int[] minMax = findMinMax(array);
        for (int i = 0; i < array.length; i++) {
            int blockNumber = (int) (1L * n * (array[i] - minMax[0]) / (minMax[1] - minMax[0] + 1));
            blockSize[blockNumber] += 1;
        }
        return blockSize;
    }

    public static void bucketSort(int[] array) {
        int n = (int) Math.sqrt(array.length);
        int[] minMax = findMinMax(array);
        if (minMax[0] == minMax[1]) {
            return;
        }
        int[][] buckets = new int[n][];
        int[] addIndex = new int[n];
        int[] blockSize = calculateBlockSize(array, n);
        for (int i = 0; i < buckets.length; i++) {
            count++;
            buckets[i] = new int[blockSize[i]];
        }
        for (int i = 0; i < array.length; i++) {
            count++;
            int blockNumber = (int) (1L * n * (array[i] - minMax[0]) / (minMax[1] - minMax[0] + 1));
            buckets[blockNumber][addIndex[blockNumber]++] = array[i];
        }
        for (int[] bucket : buckets) {
            count++;
            if (bucket.length <= 32) {
                insertionSort(bucket);
            } else {
                bucketSort(bucket);
            }
        }
        int index = 0;
        for (int[] bucket : buckets) {
            count++;
            for (int element : bucket) {
                array[index++] = element;
            }
        }
    }
}
