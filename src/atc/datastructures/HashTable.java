package atc.datastructures;

public class HashTable {
    private static class Node {
        String key;
        Object value;
        Node next;

        Node(String key, Object value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private Node[] buckets;
    private int size;
    private int capacity;
    private static final double LOAD_FACTOR = 0.75;

    public HashTable(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.buckets = new Node[capacity];
        this.size = 0;
    }

    public HashTable() {
        this(16);
    }

    private int hash(String key) {
        int hash = key.hashCode();
        hash = Math.abs(hash);
        return hash % capacity;
    }

    public void put(String key, Object value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key and value cannot be null");
        }
        if (needsResize()) {
            resize();
        }

        int index = hash(key);
        Node newNode = new Node(key, value);

        if (buckets[index] == null) {
            buckets[index] = newNode;
        } else {
            Node current = buckets[index];
            while (current != null) {
                if (current.key.equals(key)) {
                    current.value = value;
                    return;
                }
                if (current.next == null) break;
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public Object get(String key) {
        if (key == null) {
            return null;
        }
        int index = hash(key);
        Node current = buckets[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public Object remove(String key) {
        if (key == null) {
            return null;
        }
        int index = hash(key);
        Node current = buckets[index];
        Node previous = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (previous == null) {
                    buckets[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return current.value;
            }
            previous = current;
            current = current.next;
        }
        return null;
    }

    public boolean containsKey(String key) {
        return get(key) != null;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private boolean needsResize() {
        return (double) size / capacity >= LOAD_FACTOR;
    }

    private void resize() {
        int newCapacity = capacity * 2;
        Node[] oldBuckets = buckets;

        buckets = new Node[newCapacity];
        int oldCapacity = capacity;
        capacity = newCapacity;
        size = 0;

        for (int i = 0; i < oldCapacity; i++) {
            Node current = oldBuckets[i];
            while (current != null) {
                put(current.key, current.value);
                current = current.next;
            }
        }
    }

    public void clear() {
        buckets = new Node[capacity];
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HashTable[size=").append(size).append(", capacity=").append(capacity).append("]\n");
        for (int i = 0; i < capacity; i++) {
            if (buckets[i] != null) {
                sb.append("  [" + i + "]: ");
                Node current = buckets[i];
                while (current != null) {
                    sb.append(current.key).append("->").append(current.value);
                    if (current.next != null) sb.append(" -> ");
                    current = current.next;
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}