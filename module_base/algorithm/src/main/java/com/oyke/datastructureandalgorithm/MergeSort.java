package com.oyke.datastructureandalgorithm;

import java.util.Arrays;

/**
 * 归并排序
 */
public class MergeSort {
    public static void main(String[] args) {

        int[] array = new int[]{4, 5, 1, 6, 9, 2, 8, 3, 0, 7};

        mergeSort(array);

        System.out.println(Arrays.toString(array));
    }

    public static void mergeSort(int[] array) {
        if (array.length < 2) return;
        int mid = (int) Math.floor(array.length/2);
        int[] leftArray = Arrays.copyOfRange(array,0,mid);
        int[] rightArray = Arrays.copyOfRange(array,mid,array.length);
        mergeTow(leftArray,rightArray);
    }
    public static void mergeTow(int[]array1,int[]array2){

    }
}
