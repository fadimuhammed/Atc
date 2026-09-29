package atc.model;

import atc.util.FlightType;
import java.time.Duration;
import java.time.LocalDateTime;

public class PriorityCalculator {
    public static class Weights {
        public final int emergencyWeight;
        public final int fuelWeight;
        public final int waitWeight;

        public Weights(int emergencyWeight, int fuelWeight, int waitWeight) {
            if (emergencyWeight < 0 || fuelWeight < 0 || waitWeight < 0) {
                throw new IllegalArgumentException("Weights must be non-negative");
            }
            this.emergencyWeight = emergencyWeight;
            this.fuelWeight = fuelWeight;
            this.waitWeight = waitWeight;
        }
    }

    public static final Weights DEFAULT_COMMERCIAL = new Weights(1000, 10, 1);
    public static final Weights DEFAULT_CARGO = new Weights(800, 15, 2);
    public static final Weights DEFAULT_EMERGENCY = new Weights(10000, 0, 0);

    private Weights commercialWeights;
    private Weights cargoWeights;
    private Weights emergencyWeights;

    public PriorityCalculator() {
        this.commercialWeights = DEFAULT_COMMERCIAL;
        this.cargoWeights = DEFAULT_CARGO;
        this.emergencyWeights = DEFAULT_EMERGENCY;
    }

    public PriorityCalculator(Weights commercialWeights, Weights cargoWeights, Weights emergencyWeights) {
        this.commercialWeights = commercialWeights != null ? commercialWeights : DEFAULT_COMMERCIAL;
        this.cargoWeights = cargoWeights != null ? cargoWeights : DEFAULT_CARGO;
        this.emergencyWeights = emergencyWeights != null ? emergencyWeights : DEFAULT_EMERGENCY;
    }

    public static Weights getDefaultWeights(FlightType type) {
        switch (type) {
            case COMMERCIAL:
                return DEFAULT_COMMERCIAL;
            case CARGO:
                return DEFAULT_CARGO;
            case EMERGENCY:
                return DEFAULT_EMERGENCY;
            default:
                return DEFAULT_COMMERCIAL;
        }
    }

    public int calculateCommercial(Aircraft aircraft) {
        Weights w = commercialWeights;
        int emergencyScore = aircraft.isEmergencyFlag() ? w.emergencyWeight : 0;
        int fuelScore = w.fuelWeight * (100 - aircraft.getFuelLevel());
        int waitScore = w.waitWeight * getWaitMinutes(aircraft);
        return emergencyScore + fuelScore + waitScore;
    }

    public int calculateCargo(Aircraft aircraft) {
        Weights w = cargoWeights;
        int emergencyScore = aircraft.isEmergencyFlag() ? w.emergencyWeight : 0;
        int fuelScore = w.fuelWeight * (100 - aircraft.getFuelLevel());
        int waitScore = w.waitWeight * getWaitMinutes(aircraft);
        return emergencyScore + fuelScore + waitScore;
    }

    public int calculateEmergency(Aircraft aircraft) {
        return emergencyWeights.emergencyWeight;
    }

    private int getWaitMinutes(Aircraft aircraft) {
        if (aircraft.getEta() == null) {
            return 0;
        }
        long minutes = Duration.between(aircraft.getEta(), LocalDateTime.now()).toMinutes();
        return minutes > 0 ? (int) minutes : 0;
    }

    public Weights getCommercialWeights() {
        return commercialWeights;
    }

    public void setCommercialWeights(Weights commercialWeights) {
        this.commercialWeights = commercialWeights != null ? commercialWeights : DEFAULT_COMMERCIAL;
    }

    public Weights getCargoWeights() {
        return cargoWeights;
    }

    public void setCargoWeights(Weights cargoWeights) {
        this.cargoWeights = cargoWeights != null ? cargoWeights : DEFAULT_CARGO;
    }

    public Weights getEmergencyWeights() {
        return emergencyWeights;
    }

    public void setEmergencyWeights(Weights emergencyWeights) {
        this.emergencyWeights = emergencyWeights != null ? emergencyWeights : DEFAULT_EMERGENCY;
    }
}