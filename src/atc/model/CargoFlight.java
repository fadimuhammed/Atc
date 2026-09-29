package atc.model;

import atc.util.FlightType;
import java.time.LocalDateTime;

public class CargoFlight extends Aircraft {
    public CargoFlight(String flightId, String origin, String destination,
                       LocalDateTime eta, int fuelLevel, boolean emergencyFlag) {
        super(flightId, FlightType.CARGO, origin, destination, eta, fuelLevel, emergencyFlag);
    }

    @Override
    public int getPriorityScore(PriorityCalculator calculator) {
        return calculator.calculateCargo(this);
    }

    @Override
    public FlightType getType() {
        return FlightType.CARGO;
    }
}