package atc.datastructures;

import atc.model.Aircraft;
import atc.model.PriorityCalculator;

public class SortAlgorithms {
    private SortAlgorithms() {
    }

    public static void mergeSort(Aircraft[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        mergeSort(array, 0, array.length - 1);
    }

    private static void mergeSort(Aircraft[] array, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            merge(array, left, mid, right);
        }
    }

    private static void merge(Aircraft[] array, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Aircraft[] leftArray = new Aircraft[n1];
        Aircraft[] rightArray = new Aircraft[n2];

        for (int i = 0; i < n1; i++) {
            leftArray[i] = array[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = array[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (leftArray[i].getFlightId().compareTo(rightArray[j].getFlightId()) <= 0) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

    public static void mergeSortByPriority(Aircraft[] array, PriorityCalculator calculator) {
        if (array == null || array.length <= 1) {
            return;
        }
        mergeSortByPriority(array, 0, array.length - 1, calculator);
    }

    private static void mergeSortByPriority(Aircraft[] array, int left, int right, PriorityCalculator calculator) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortByPriority(array, left, mid, calculator);
            mergeSortByPriority(array, mid + 1, right, calculator);
            mergeByPriority(array, left, mid, right, calculator);
        }
    }

    private static void mergeByPriority(Aircraft[] array, int left, int mid, int right, PriorityCalculator calculator) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Aircraft[] leftArray = new Aircraft[n1];
        Aircraft[] rightArray = new Aircraft[n2];

        for (int i = 0; i < n1; i++) {
            leftArray[i] = array[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = array[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            int leftPriority = leftArray[i].getPriorityScore(calculator);
            int rightPriority = rightArray[j].getPriorityScore(calculator);

            if (leftPriority >= rightPriority) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

    public static void mergeSortByEta(Aircraft[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        mergeSortByEta(array, 0, array.length - 1);
    }

    private static void mergeSortByEta(Aircraft[] array, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortByEta(array, left, mid);
            mergeSortByEta(array, mid + 1, right);
            mergeByEta(array, left, mid, right);
        }
    }

    private static void mergeByEta(Aircraft[] array, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Aircraft[] leftArray = new Aircraft[n1];
        Aircraft[] rightArray = new Aircraft[n2];

        for (int i = 0; i < n1; i++) {
            leftArray[i] = array[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = array[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (leftArray[i].getEta() == null || rightArray[j].getEta() == null) {
                array[k] = leftArray[i];
                i++;
            } else if (leftArray[i].getEta().isBefore(rightArray[j].getEta()) ||
                    leftArray[i].getEta().isEqual(rightArray[j].getEta())) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }
}