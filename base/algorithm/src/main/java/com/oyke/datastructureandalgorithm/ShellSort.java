package com.oyke.datastructureandalgorithm;

import java.util.Arrays;

/**
 * 希尔排序
 */
public class ShellSort {
    public static void main(String[] args) {

        int[] array = new int[]{4, 5, 1, 6, 9, 2, 8, 3, 0, 7};

        shellSort(array);

        System.out.println(Arrays.toString(array));
    }

    public static void shellSort(int[] array) {
        int gap = 1;
        while (gap < array.length) {
            gap = gap * 3 + 1;
        }
        while (gap > 0) {
            for (int i = gap; i < array.length; i++) {
                int temp = array[i];
                int j = i - gap;
                while (j >= 0 && array[j] > temp) {
                    array[j + gap] = array[j];
                    j -= gap;
                }
                array[j + gap] = temp;
            }
            gap = (int) Math.floor(gap / 3);
        }
    }
}
