package com.oyke.datastructureandalgorithm;

import java.util.Arrays;

/**
 * 插入排序
 */
public class InsertSort {

    public static void main(String[] args) {
        int[] array = new int[]{4, 5, 1, 6, 9, 2, 8, 3, 0, 7};
        System.out.println("排序前：" + Arrays.toString(array));
        insertSort(array);
        System.out.println("排序后：" + Arrays.toString(array));
    }

    public static void insertSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int temp = array[i];
            int j = i;
            while (j > 0 && temp < array[j - 1]) {
                array[j] = array[j - 1];
                j--;
            }
            if (j != i) {
                array[j] = temp;
            }
        }
    }


}
