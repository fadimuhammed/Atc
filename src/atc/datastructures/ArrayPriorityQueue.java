package atc.datastructures;

import atc.model.Aircraft;
import atc.model.PriorityCalculator;

public class ArrayPriorityQueue {
    private Aircraft[] heap;
    private int size;
    private final int capacity;
    private final PriorityCalculator calculator;

    public ArrayPriorityQueue(int capacity, PriorityCalculator calculator) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.heap = new Aircraft[capacity];
        this.size = 0;
        this.calculator = calculator != null ? calculator : new PriorityCalculator();
    }

    public void enqueue(Aircraft aircraft) {
        if (aircraft == null) {
            throw new IllegalArgumentException("Aircraft cannot be null");
        }
        if (isFull()) {
            throw new IllegalStateException("Priority queue is full");
        }
        heap[size] = aircraft;
        bubbleUp(size);
        size++;
    }

    public Aircraft dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority queue is empty");
        }
        Aircraft max = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        bubbleDown(0);
        return max;
    }

    public Aircraft peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority queue is empty");
        }
        return heap[0];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }

    public Aircraft[] toArray() {
        Aircraft[] result = new Aircraft[size];
        System.arraycopy(heap, 0, result, 0, size);
        return result;
    }

    public int getPriority(Aircraft aircraft) {
        return aircraft.getPriorityScore(calculator);
    }

    private void bubbleUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (getPriority(heap[index]) > getPriority(heap[parent])) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    private void bubbleDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int largest = index;

            if (left < size && getPriority(heap[left]) > getPriority(heap[largest])) {
                largest = left;
            }
            if (right < size && getPriority(heap[right]) > getPriority(heap[largest])) {
                largest = right;
            }
            if (largest != index) {
                swap(index, largest);
                index = largest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        Aircraft temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            heap[i] = null;
        }
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PriorityQueue[size=").append(size).append("] ");
        for (int i = 0; i < size; i++) {
            if (heap[i] != null) {
                sb.append(heap[i].getFlightId()).append("(").append(getPriority(heap[i])).append(") ");
            }
        }
        return sb.toString();
    }
}