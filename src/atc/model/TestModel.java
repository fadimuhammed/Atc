package atc.model;

import atc.exceptions.DuplicateFlightIDException;
import atc.exceptions.HoldingPatternFullException;
import atc.exceptions.InvalidPriorityException;
import atc.util.FlightType;
import java.time.LocalDateTime;

public class TestModel {
    public static void main(String[] args) {
        System.out.println("=== Phase 1 Model Test ===\n");

        testAircraftCreation();
        testPriorityCalculation();
        testFuelUpdate();
        testExceptions();
        testEqualsHashCode();
        testToString();

        System.out.println("\n=== All Tests Passed ===");
    }

    private static void testAircraftCreation() {
        System.out.println("--- Test: Aircraft Creation ---");

        CommercialFlight c1 = new CommercialFlight("AI101", "BOM", "DEL",
                LocalDateTime.now().plusHours(2), 75, false);
        System.out.println("Commercial: " + c1);
        assert c1.getFlightId().equals("AI101");
        assert c1.getType() == FlightType.COMMERCIAL;
        assert c1.getFuelLevel() == 75;
        assert !c1.isEmergencyFlag();
        assert !c1.isLanded();

        CargoFlight cg1 = new CargoFlight("CG202", "MAA", "BLR",
                LocalDateTime.now().plusHours(1), 45, false);
        System.out.println("Cargo: " + cg1);
        assert cg1.getType() == FlightType.CARGO;

        EmergencyFlight e1 = new EmergencyFlight("EM303", "HYD", "DEL",
                LocalDateTime.now().plusMinutes(30), 20);
        System.out.println("Emergency: " + e1);
        assert e1.getType() == FlightType.EMERGENCY;
        assert e1.isEmergencyFlag();
        assert e1.getFuelLevel() == 20;

        System.out.println("Aircraft creation: OK\n");
    }

    private static void testPriorityCalculation() {
        System.out.println("--- Test: Priority Calculation ---");

        PriorityCalculator calc = new PriorityCalculator();

        CommercialFlight commercial = new CommercialFlight("AI101", "BOM", "DEL",
                LocalDateTime.now().plusHours(1), 30, false);
        int commercialScore = commercial.getPriorityScore(calc);
        System.out.println("Commercial (fuel=30%, no emergency): " + commercialScore);

        CargoFlight cargo = new CargoFlight("CG202", "MAA", "BLR",
                LocalDateTime.now().plusHours(1), 30, false);
        int cargoScore = cargo.getPriorityScore(calc);
        System.out.println("Cargo (fuel=30%, no emergency): " + cargoScore);

        EmergencyFlight emergency = new EmergencyFlight("EM303", "HYD", "DEL",
                LocalDateTime.now().plusMinutes(30), 10);
        int emergencyScore = emergency.getPriorityScore(calc);
        System.out.println("Emergency (fuel=10%): " + emergencyScore);

        assert emergencyScore > commercialScore : "Emergency should have highest priority";
        assert emergencyScore > cargoScore : "Emergency should have highest priority";
        assert cargoScore > commercialScore : "Cargo should prioritize fuel more than commercial";

        commercial.setEmergencyFlag(true);
        int commercialEmergencyScore = commercial.getPriorityScore(calc);
        System.out.println("Commercial with emergency flag: " + commercialEmergencyScore);
        assert commercialEmergencyScore > commercialScore : "Emergency flag should increase score";

        System.out.println("Priority calculation: OK\n");
    }

    private static void testFuelUpdate() {
        System.out.println("--- Test: Fuel Level Update ---");

        CommercialFlight flight = new CommercialFlight("AI101", "BOM", "DEL",
                LocalDateTime.now().plusHours(1), 80, false);
        PriorityCalculator calc = new PriorityCalculator();

        int initialScore = flight.getPriorityScore(calc);
        System.out.println("Initial score (fuel=80%): " + initialScore);

        flight.setFuelLevel(20);
        int lowFuelScore = flight.getPriorityScore(calc);
        System.out.println("After fuel=20%: " + lowFuelScore);
        assert lowFuelScore > initialScore : "Lower fuel should increase priority";

        try {
            flight.setFuelLevel(150);
            assert false : "Should throw IllegalArgumentException";
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid fuel level correctly rejected: " + ex.getMessage());
        }

        try {
            flight.setFuelLevel(-10);
            assert false : "Should throw IllegalArgumentException";
        } catch (IllegalArgumentException ex) {
            System.out.println("Negative fuel level correctly rejected: " + ex.getMessage());
        }

        System.out.println("Fuel update: OK\n");
    }

    private static void testExceptions() {
        System.out.println("--- Test: Custom Exceptions ---");

        try {
            throw new DuplicateFlightIDException("AI101");
        } catch (DuplicateFlightIDException ex) {
            System.out.println("DuplicateFlightIDException: " + ex.getMessage());
            assert ex.getFlightId().equals("AI101");
        }

        try {
            throw new HoldingPatternFullException(5);
        } catch (HoldingPatternFullException ex) {
            System.out.println("HoldingPatternFullException: " + ex.getMessage());
            assert ex.getCapacity() == 5;
        }

        try {
            throw new InvalidPriorityException("Priority score cannot be negative");
        } catch (InvalidPriorityException ex) {
            System.out.println("InvalidPriorityException: " + ex.getMessage());
        }

        System.out.println("Exceptions: OK\n");
    }

    private static void testEqualsHashCode() {
        System.out.println("--- Test: equals/hashCode ---");

        CommercialFlight f1 = new CommercialFlight("AI101", "BOM", "DEL",
                LocalDateTime.now().plusHours(1), 50, false);
        CommercialFlight f2 = new CommercialFlight("AI101", "DEL", "BOM",
                LocalDateTime.now().plusHours(2), 30, true);
        CommercialFlight f3 = new CommercialFlight("AI102", "BOM", "DEL",
                LocalDateTime.now().plusHours(1), 50, false);

        assert f1.equals(f2) : "Same flight ID should be equal";
        assert f1.hashCode() == f2.hashCode() : "Equal objects must have same hashCode";
        assert !f1.equals(f3) : "Different flight ID should not be equal";
        assert !f1.equals(null) : "Should not equal null";
        assert !f1.equals("AI101") : "Should not equal string";

        System.out.println("equals/hashCode: OK\n");
    }

    private static void testToString() {
        System.out.println("--- Test: toString ---");

        CommercialFlight flight = new CommercialFlight("AI101", "BOM", "DEL",
                LocalDateTime.of(2025, 1, 15, 14, 30), 65, false);
        String str = flight.toString();
        System.out.println("toString output: " + str);
        assert str.contains("AI101");
        assert str.contains("COMMERCIAL");
        assert str.contains("65%");
        assert str.contains("NO");

        System.out.println("toString: OK\n");
    }
}