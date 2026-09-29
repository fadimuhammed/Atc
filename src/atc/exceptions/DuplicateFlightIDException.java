package atc.exceptions;

public class DuplicateFlightIDException extends Exception {
    private final String flightId;

    public DuplicateFlightIDException(String flightId) {
        super("Flight with ID '" + flightId + "' already exists in the registry");
        this.flightId = flightId != null ? flightId.trim().toUpperCase() : "";
    }

    public String getFlightId() {
        return flightId;
    }
}