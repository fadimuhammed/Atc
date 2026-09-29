package atc.model;

import atc.datastructures.ArrayPriorityQueue;
import atc.datastructures.CircularQueue;
import atc.datastructures.HashTable;
import atc.datastructures.SortedArrayRegistry;
import atc.datastructures.Stack;
import atc.exceptions.DuplicateFlightIDException;
import atc.exceptions.HoldingPatternFullException;
import atc.exceptions.InvalidPriorityException;
import atc.util.FlightType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightRegistry {
    private final HashTable flightTable;
    private final ArrayPriorityQueue priorityQueue;
    private final CircularQueue holdingPattern;
    private final SortedArrayRegistry sortedRegistry;
    private final Stack undoStack;
    private final PriorityCalculator calculator;
    private final int holdingPatternCapacity;

    public FlightRegistry(int holdingPatternCapacity, int priorityQueueCapacity, int registryCapacity) {
        this.holdingPatternCapacity = holdingPatternCapacity;
        this.calculator = new PriorityCalculator();
        this.flightTable = new HashTable(32);
        this.priorityQueue = new ArrayPriorityQueue(priorityQueueCapacity, calculator);
        this.holdingPattern = new CircularQueue(holdingPatternCapacity);
        this.sortedRegistry = new SortedArrayRegistry(registryCapacity);
        this.undoStack = new Stack();
    }

    public FlightRegistry() {
        this(10, 100, 100);
    }

    public void registerFlight(Aircraft aircraft) throws DuplicateFlightIDException {
        String flightId = aircraft.getFlightId();
        if (flightTable.containsKey(flightId)) {
            throw new DuplicateFlightIDException(flightId);
        }

        flightTable.put(flightId, aircraft);
        sortedRegistry.insert(aircraft);

        if (aircraft.getPriorityScore(calculator) > 0) {
            priorityQueue.enqueue(aircraft);
        }

        undoStack.push(new Action(ActionType.REGISTER, aircraft));
    }

    public Aircraft processLanding() {
        if (priorityQueue.isEmpty()) {
            if (!holdingPattern.isEmpty()) {
                Aircraft aircraft = (Aircraft) holdingPattern.dequeue();
                try {
                    priorityQueue.enqueue(aircraft);
                } catch (Exception e) {
                    return null;
                }
            } else {
                return null;
            }
        }

        Aircraft aircraft = priorityQueue.dequeue();
        if (aircraft != null) {
            aircraft.setLanded(true);
            undoStack.push(new Action(ActionType.LAND, aircraft));
        }
        return aircraft;
    }

    public void addToHoldingPattern(Aircraft aircraft) throws HoldingPatternFullException {
        if (holdingPattern.isFull()) {
            throw new HoldingPatternFullException(holdingPatternCapacity);
        }
        holdingPattern.enqueue(aircraft);
        undoStack.push(new Action(ActionType.HOLD, aircraft));
    }

    public Aircraft getFlight(String flightId) {
        return (Aircraft) flightTable.get(flightId);
    }

    public boolean containsFlight(String flightId) {
        return flightTable.containsKey(flightId);
    }

    public Aircraft[] getAllFlights() {
        return sortedRegistry.toArray();
    }

    public Aircraft[] rangeQuery(String lowId, String highId) {
        return sortedRegistry.rangeQuery(lowId, highId);
    }

    public Aircraft dequeueFromHoldingPattern() {
        if (holdingPattern.isEmpty()) {
            return null;
        }
        return (Aircraft) holdingPattern.dequeue();
    }

    public Aircraft peekNextLanding() {
        return priorityQueue.peek();
    }

    public void updateFlightPriority(String flightId) throws InvalidPriorityException {
        Aircraft aircraft = (Aircraft) flightTable.get(flightId);
        if (aircraft == null) {
            throw new InvalidPriorityException("Flight not found: " + flightId);
        }

        int newScore = aircraft.getPriorityScore(calculator);
        if (newScore < 0) {
            throw new InvalidPriorityException("Invalid priority score: " + newScore);
        }

        undoStack.push(new Action(ActionType.UPDATE, aircraft));
    }

    public Aircraft undoLastAction() {
        if (undoStack.isEmpty()) {
            return null;
        }

        Action action = (Action) undoStack.pop();
        Aircraft aircraft = action.getAircraft();
        return aircraft;
    }

    public int getRegisteredCount() {
        return flightTable.size();
    }

    public int getQueueCount() {
        return priorityQueue.size();
    }

    public int getHoldingPatternCount() {
        return holdingPattern.size();
    }

    public boolean isHoldingPatternFull() {
        return holdingPattern.isFull();
    }

    private static class Action {
        private final ActionType type;
        private final Aircraft aircraft;

        public Action(ActionType type, Aircraft aircraft) {
            this.type = type;
            this.aircraft = aircraft;
        }

        public ActionType getType() {
            return type;
        }

        public Aircraft getAircraft() {
            return aircraft;
        }
    }

    private enum ActionType {
        REGISTER, LAND, HOLD, UPDATE
    }
}