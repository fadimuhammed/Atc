package atc.datastructures;

public class CircularQueue {
    private Object[] array;
    private int front;
    private int rear;
    private int size;
    private final int capacity;

    public CircularQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.array = new Object[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    public void enqueue(Object item) {
        if (isFull()) {
            throw new IllegalStateException("Queue is full");
        }
        rear = (rear + 1) % capacity;
        array[rear] = item;
        size++;
    }

    public Object dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        Object item = array[front];
        array[front] = null;
        front = (front + 1) % capacity;
        size--;
        return item;
    }

    public Object peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return array[front];
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

    public Object[] toArray() {
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            result[i] = array[index];
        }
        return result;
    }

    public void clear() {
        for (int i = 0; i < capacity; i++) {
            array[i] = null;
        }
        front = 0;
        rear = -1;
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CircularQueue[front->");
        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            sb.append(array[index]);
            if (i < size - 1) sb.append(" -> ");
        }
        sb.append("] capacity=").append(capacity);
        return sb.toString();
    }
}