package com.oyke.datastructureandalgorithm;

import java.util.Arrays;

/**
 * 快速排序
 */
public class QuickSort {

    public static void main(String[] args) {

        int[] array = new int[]{4, 5, 1, 6, 9, 2, 8, 3, 0, 7};

        quickSort(array, 0, array.length - 1);

        System.out.println(Arrays.toString(array));
    }

    public static void quickSort(int[] array, int start, int end) {

        int left = start;//左指针等于数组第一个位置
        int right = end;//右指针等于数组最后一个位置
        int center = array[(left + right) / 2];//数组的中间元素为基准值
        int temp;

        while (left < right) {//左右指针向中间遍历

            while (array[left] < center) {//左边数据小于center直接下一个
                left++;
            }

            while (array[right] > center) {//右边大于center直接下一个
                right--;
            }

            if (left >= right) {//左边等于右边说明遍历完成，直接退出
                break;
            }

            //交换左边大于center的值与右边小于center的值
            temp = array[left];
            array[left] = array[right];
            array[right] = temp;


            if (array[left] == center) right--;//防止有相等，进入死循环，
            if (array[right] == center) left++;//若交换后的右边的值 与 中轴值的相等，左边一定是比pivot小的值
        }

        if (left == right) {
            left++;
            right--;
        }

        if (start < right) {
            quickSort(array, left, right);
        }

        if (end > left) {
            quickSort(array, left, right);
        }

    }
}
