package atc.exceptions;

public class HoldingPatternFullException extends Exception {
    private final int capacity;

    public HoldingPatternFullException(int capacity) {
        super("Holding pattern is full. Capacity: " + capacity + " aircraft");
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}