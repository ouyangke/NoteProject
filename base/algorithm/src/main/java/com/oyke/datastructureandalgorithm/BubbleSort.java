package com.oyke.datastructureandalgorithm;

import java.util.Arrays;

/**
 * 冒泡排序
 */
public class BubbleSort {


    public static void main(String[] args) {

        int[] array = new int[]{4, 5, 1, 6, 9, 2, 8, 3, 0, 7};

        bubbleSort(array);

        System.out.println(Arrays.toString(array));
    }

    public static void bubbleSort(int[] array) {

        for (int i = 1; i < array.length; i++) {
            boolean flag = true;

            for (int j = 0; j < array.length - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    flag = false;
                }
            }
            if (flag) {
                break;
            }
        }
    }
}
