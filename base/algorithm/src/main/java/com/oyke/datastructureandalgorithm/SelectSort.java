package com.oyke.datastructureandalgorithm;

import java.util.Arrays;

/**
 * 选择排序
 */
public class SelectSort {
    public static void main(String[] args) {
        int[] array = new int[]{4, 5, 1, 6, 9, 2, 8, 3, 0, 7};
        System.out.println("排序前：" + Arrays.toString(array));
        selectSort(array);
        System.out.println("排序后：" + Arrays.toString(array));
    }

    public static void selectSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int min = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[min]) {
                    min = j;
                }
            }
            if (i != min) {
                int temp = array[i];
                array[i] = array[min];
                array[min] = temp;
            }
        }
    }
}
