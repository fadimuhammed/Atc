package atc.model;

import atc.util.FlightType;
import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Aircraft {
    private final String flightId;
    private final FlightType type;
    private String origin;
    private String destination;
    private LocalDateTime eta;
    private int fuelLevel;
    private boolean emergencyFlag;
    private final long arrivalOrder;
    private boolean landed;

    private static long arrivalCounter = 0;

    protected Aircraft(String flightId, FlightType type, String origin, String destination,
                       LocalDateTime eta, int fuelLevel, boolean emergencyFlag) {
        if (flightId == null || flightId.trim().isEmpty()) {
            throw new IllegalArgumentException("Flight ID cannot be null or empty");
        }
        if (fuelLevel < 0 || fuelLevel > 100) {
            throw new IllegalArgumentException("Fuel level must be between 0 and 100");
        }
        this.flightId = flightId.trim().toUpperCase();
        this.type = type;
        this.origin = origin != null ? origin.trim().toUpperCase() : "";
        this.destination = destination != null ? destination.trim().toUpperCase() : "";
        this.eta = eta;
        this.fuelLevel = fuelLevel;
        this.emergencyFlag = emergencyFlag;
        this.arrivalOrder = ++arrivalCounter;
        this.landed = false;
    }

    public String getFlightId() {
        return flightId;
    }

    public FlightType getType() {
        return type;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin != null ? origin.trim().toUpperCase() : "";
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination != null ? destination.trim().toUpperCase() : "";
    }

    public LocalDateTime getEta() {
        return eta;
    }

    public void setEta(LocalDateTime eta) {
        this.eta = eta;
    }

    public int getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(int fuelLevel) {
        if (fuelLevel < 0 || fuelLevel > 100) {
            throw new IllegalArgumentException("Fuel level must be between 0 and 100");
        }
        this.fuelLevel = fuelLevel;
    }

    public boolean isEmergencyFlag() {
        return emergencyFlag;
    }

    public void setEmergencyFlag(boolean emergencyFlag) {
        this.emergencyFlag = emergencyFlag;
    }

    public long getArrivalOrder() {
        return arrivalOrder;
    }

    public boolean isLanded() {
        return landed;
    }

    public void setLanded(boolean landed) {
        this.landed = landed;
    }

    public abstract int getPriorityScore(PriorityCalculator calculator);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aircraft aircraft = (Aircraft) o;
        return Objects.equals(flightId, aircraft.flightId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightId);
    }

    @Override
    public String toString() {
        return String.format("%s [%s] ETA:%s Fuel:%d%% Emergency:%s Landed:%s",
                flightId, type, eta, fuelLevel, emergencyFlag ? "YES" : "NO", landed ? "YES" : "NO");
    }
}