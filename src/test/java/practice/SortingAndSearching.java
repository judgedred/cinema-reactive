package practice;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class SortingAndSearching {

//    10.1
    @Test
    public void sortedMerge() {
        int[] arr1 = {1, 3, 5, 7, 9, 0, 0, 0, 0, 0, 0, 0};
        int[] arr2 = {2, 4, 6, 10, 11, 12};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 0};
        assertArrayEquals(expected, sortMerge(arr1, arr2, 5));
        int[] arr3 = {1, 3, 5, 7, 9, 0, 0, 0, 0, 0, 0, 0};
        assertArrayEquals(expected, sortMerge2(arr3, arr2, 5));
    }

    private int[] sortMerge(int[] arr1, int[] arr2, int buffer) {
        int bufferIndex = buffer;
        for (int i = 0, j = 0; i < arr1.length && j < arr2.length; i++) {
            if (arr2[j] < arr1[i] || i == bufferIndex) {
                if (i == bufferIndex) {
                    arr1[i] = arr2[j];
                    bufferIndex++;
                    j++;
                } else {
                    for (int k = bufferIndex - 1; k >= i; k--) {
                        arr1[k + 1] = arr1[k];
                    }
                    arr1[i] = arr2[j];
                    j++;
                    bufferIndex++;
                }
            }
        }
        return arr1;
    }

    private int[] sortMerge2(int[] arr1, int[] arr2, int buffer) {
        int end = buffer + arr2.length - 1;
        for (int i = end, j = arr2.length - 1, k = buffer - 1; i >= 0 && j >= 0; i--) {
            if (arr1[k] > arr2[j]) {
                arr1[i] = arr1[k];
                k--;
            } else {
                arr1[i] = arr2[j];
                j--;
            }
        }
        return arr1;
    }
}
