package com.study.sort;

public class BinarySearch {

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4};
        System.out.println(binarySearch(arr, 5));
        System.out.println(binarySearchRecursion(arr, 2, 0, arr.length - 1));
    }

    private static int binarySearchRecursion(int[] arr, int target, int start, int end) {
        if (start <= end) {
            int mid = (start + end) / 2;
            if (arr[mid] < target) {
                return binarySearchRecursion(arr, target, mid + 1, end);
            } else if (arr[mid] > target) {
                return binarySearchRecursion(arr, target, start, mid - 1);
            } else {
                return mid;
            }
        }
        return -1;
    }

    private static int binarySearch(int[] arr, int target) {
        int start = 0;
        int end = arr.length - 1;
        int mid;
        while (start <= end) {
            mid = (start + end) / 2;
            if (target > arr[mid]) {
                start = mid + 1;
            } else if (target < arr[mid]) {
                end = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
}
