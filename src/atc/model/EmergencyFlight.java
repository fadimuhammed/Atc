package atc.model;

import atc.util.FlightType;
import java.time.LocalDateTime;

public class EmergencyFlight extends Aircraft {
    public EmergencyFlight(String flightId, String origin, String destination,
                           LocalDateTime eta, int fuelLevel) {
        super(flightId, FlightType.EMERGENCY, origin, destination, eta, fuelLevel, true);
    }

    @Override
    public int getPriorityScore(PriorityCalculator calculator) {
        return calculator.calculateEmergency(this);
    }

    @Override
    public FlightType getType() {
        return FlightType.EMERGENCY;
    }

    @Override
    public void setEmergencyFlag(boolean emergencyFlag) {
        // Emergency flights always have emergency flag set to true
    }
}