package atc.datastructures;

import atc.model.Aircraft;

public class SortedArrayRegistry {
    private Aircraft[] array;
    private int size;
    private final int capacity;
    private Comparator comparator;

    public interface Comparator {
        int compare(Aircraft a1, Aircraft a2);
    }

    public SortedArrayRegistry(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.array = new Aircraft[capacity];
        this.size = 0;
        this.comparator = (a1, a2) -> a1.getFlightId().compareTo(a2.getFlightId());
    }

    public SortedArrayRegistry(int capacity, Comparator comparator) {
        this(capacity);
        if (comparator != null) {
            this.comparator = comparator;
        }
    }

    public void insert(Aircraft aircraft) {
        if (aircraft == null) {
            throw new IllegalArgumentException("Aircraft cannot be null");
        }
        if (isFull()) {
            throw new IllegalStateException("Registry is full");
        }
        if (contains(aircraft.getFlightId())) {
            throw new IllegalArgumentException("Aircraft with flight ID " + aircraft.getFlightId() + " already exists");
        }

        int index = findInsertionIndex(aircraft);
        shiftRight(index);
        array[index] = aircraft;
        size++;
    }

    public Aircraft remove(String flightId) {
        int index = binarySearch(flightId);
        if (index == -1) {
            return null;
        }
        Aircraft removed = array[index];
        shiftLeft(index);
        size--;
        return removed;
    }

    public Aircraft find(String flightId) {
        int index = binarySearch(flightId);
        return index == -1 ? null : array[index];
    }

    public boolean contains(String flightId) {
        return binarySearch(flightId) != -1;
    }

    public int binarySearch(String flightId) {
        int left = 0;
        int right = size - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            String midId = array[mid].getFlightId();

            if (midId.equals(flightId)) {
                return mid;
            } else if (midId.compareTo(flightId) < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    public int binarySearchRange(String lowId, String highId) {
        int left = 0;
        int right = size - 1;
        int startIndex = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            String midId = array[mid].getFlightId();

            if (midId.compareTo(lowId) >= 0) {
                startIndex = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return startIndex;
    }

    public Aircraft[] rangeQuery(String lowId, String highId) {
        int startIndex = binarySearchRange(lowId, highId);
        if (startIndex == -1) return new Aircraft[0];

        int endIndex = -1;
        for (int i = startIndex; i < size; i++) {
            String currentId = array[i].getFlightId();
            if (currentId.compareTo(highId) <= 0) {
                endIndex = i;
            } else {
                break;
            }
        }

        if (endIndex == -1) return new Aircraft[0];

        Aircraft[] result = new Aircraft[endIndex - startIndex + 1];
        System.arraycopy(array, startIndex, result, 0, result.length);
        return result;
    }

    private int findInsertionIndex(Aircraft aircraft) {
        String flightId = aircraft.getFlightId();
        int left = 0;
        int right = size - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            String midId = array[mid].getFlightId();

            if (midId.compareTo(flightId) < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    private void shiftRight(int index) {
        for (int i = size; i > index; i--) {
            array[i] = array[i - 1];
        }
    }

    private void shiftLeft(int index) {
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[size - 1] = null;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Aircraft[] toArray() {
        Aircraft[] result = new Aircraft[size];
        System.arraycopy(array, 0, result, 0, size);
        return result;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    public void setComparator(Comparator comparator) {
        if (comparator != null) {
            this.comparator = comparator;
            // For simplicity, rebuild registry with new comparator
            // In production, would re-sort existing elements
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SortedArrayRegistry[size=").append(size).append("]\n");
        for (int i = 0; i < size; i++) {
            if (array[i] != null) {
                sb.append("  ").append(array[i].getFlightId()).append("\n");
            }
        }
        return sb.toString();
    }
}