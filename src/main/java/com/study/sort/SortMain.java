package com.study.sort;

import java.util.Arrays;

public class SortMain {

    public static void main(String[] args) {
        int[] arr = new int[]{4, 2, 5, 8, 1, 9, 3};
//        bubbleSort(arr);
//        selectSort(arr);
//        System.out.println(Arrays.toString(mergeSort(arr)));
//        quickSort(arr);
        insertSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 插入排序
     * 最佳情况：T(n) = O(n2)   最差情况：T(n) = O(n2)   平均情况：T(n) = O(n2)　
     */
    private static void insertSort(int[] arr) {
        int length = arr.length;
        for (int i=1; i<length; i++) {
            int temp = arr[i];
            int j;
            for (j=i; j>0 && temp<arr[j-1]; j--) {
                arr[j] = arr[j-1];
            }
            arr[j] = temp;
        }
    }

    /**
     * 快排
     * 最佳情况：T(n) = O(nlogn)   最差情况：T(n) = O(n2)   平均情况：T(n) = O(nlogn)　
     */
    private static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length-1);
    }

    /**
     * 快排递归
     */
    private static void quickSort(int[] arr, int start, int end) {
        if (start < end) {
            int mid = getMid(arr, start, end);
            quickSort(arr, start, mid-1);
            quickSort(arr, mid + 1, end);
        }
    }

    /**
     * 快排获取中间位
     */
    private static int getMid(int[] arr, int start, int end) {
        int firstValue = arr[start];
        while (start < end) {
            while (start < end && arr[end] >= firstValue) {
                end --;
            }
            arr[start] = arr[end];
            while (start < end && arr[start] <= firstValue) {
                start ++;
            }
            arr[end] = arr[start];
        }
        arr[start] = firstValue;
        return start;
    }

    /**
     * 归并排序
     * 最佳情况：T(n) = O(nlogn)  最差情况：T(n) = O(nlogn)  平均情况：T(n) = O(nlogn)
     * 需要额外的空间
     */
    private static int[] mergeSort(int[] arr) {
        int length = arr.length;
        if (length < 2) {
            return arr;
        }
        int mid = length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, length);
        return merge(mergeSort(left), mergeSort(right));
    }

    private static int[] merge(int[] left, int[] right) {
        int allLength = left.length + right.length;
        int[] result = new int[allLength];
        for (int index=0,i=0,j=0; index < allLength; index++) {
            if (i >= left.length) {
                result[index] = right[j++];
            } else if (j >= right.length) {
                result[index] = left[i++];
            } else if (left[i] >= right[j]) {
                result[index] = right[j++];
            } else if (left[i] <= right[j]) {
                result[index] = left[i++];
            }
        }
        return result;
    }

    /**
     * 选择排序
     * 最佳情况：T(n) = O(n2)  最差情况：T(n) = O(n2)  平均情况：T(n) = O(n2)
     */
    private static void selectSort(int[] arr) {
        int length = arr.length;
        int temp;
        int min;
        for (int i=0; i<length; i++) {
            min = i;
            for (int j=i+1; j<length; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            if (min != i) {
                temp = arr[i];
                arr[i] = arr[min];
                arr[min] = temp;
            }
        }
    }

    /**
     * 冒泡排序
     * 最佳情况：T(n) = O(n2)   最差情况：T(n) = O(n2)   平均情况：T(n) = O(n2)
     */
    private static void bubbleSort(int[] arr) {
        int length = arr.length;
        int temp;
        for (int i=0; i<length; i++) {
            for (int j=0; j<length-i-1; j++) {
                if (arr[j] > arr[j+1]) {
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }
}
