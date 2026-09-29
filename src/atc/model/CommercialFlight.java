package atc.model;

import atc.util.FlightType;
import java.time.LocalDateTime;

public class CommercialFlight extends Aircraft {
    public CommercialFlight(String flightId, String origin, String destination,
                            LocalDateTime eta, int fuelLevel, boolean emergencyFlag) {
        super(flightId, FlightType.COMMERCIAL, origin, destination, eta, fuelLevel, emergencyFlag);
    }

    @Override
    public int getPriorityScore(PriorityCalculator calculator) {
        return calculator.calculateCommercial(this);
    }

    @Override
    public FlightType getType() {
        return FlightType.COMMERCIAL;
    }
}