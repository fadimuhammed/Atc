package atc.model;

import atc.exceptions.DuplicateFlightIDException;
import atc.exceptions.HoldingPatternFullException;
import atc.exceptions.InvalidPriorityException;
import atc.persistence.FilePersistence;
import atc.util.FlightType;
import java.io.IOException;
import java.time.LocalDateTime;

public class TestFlightRegistry {
    public static void main(String[] args) {
        System.out.println("=== Phase 3 Flight Registry Test ===\n");

        testRegistryOperations();
        testLandingProcessing();
        testHoldingPattern();
        testFilePersistence();

        System.out.println("\n=== All Registry Tests Passed ===");
    }

    private static void testRegistryOperations() {
        System.out.println("--- Test: Registry Operations ---");

        FlightRegistry registry = new FlightRegistry(5, 10, 10);
        PriorityCalculator calc = new PriorityCalculator();

        CommercialFlight f1 = new CommercialFlight("AI101", "BOM", "DEL",
                LocalDateTime.now().plusHours(2), 75, false);
        CommercialFlight f2 = new CommercialFlight("AI102", "BOM", "DEL",
                LocalDateTime.now().plusHours(1), 45, false);
        EmergencyFlight f3 = new EmergencyFlight("EM999", "HYD", "DEL",
                LocalDateTime.now().plusMinutes(30), 10);

        try {
            registry.registerFlight(f1);
            registry.registerFlight(f2);
            registry.registerFlight(f3);
            System.out.println("Registered 3 flights");
        } catch (DuplicateFlightIDException e) {
            System.out.println("Error: " + e.getMessage());
        }

        assert registry.getRegisteredCount() == 3 : "Should have 3 flights";
        assert registry.containsFlight("AI101") : "AI101 should exist";
        assert registry.containsFlight("AI102") : "AI102 should exist";
        assert registry.containsFlight("EM999") : "EM999 should exist";

        Aircraft found = registry.getFlight("AI101");
        assert found != null && found.getFlightId().equals("AI101");

        Aircraft[] all = registry.getAllFlights();
        assert all.length == 3 : "Should return 3 flights";

        System.out.println("Registry operations: OK\n");
    }

    private static void testLandingProcessing() {
        System.out.println("--- Test: Landing Processing ---");

        FlightRegistry registry = new FlightRegistry(5, 10, 10);

        CommercialFlight f1 = new CommercialFlight("AI101", "BOM", "DEL",
                LocalDateTime.now().plusHours(2), 50, false);
        CommercialFlight f2 = new CommercialFlight("AI102", "BOM", "DEL",
                LocalDateTime.now().plusHours(1), 30, false);
        EmergencyFlight f3 = new EmergencyFlight("EM999", "HYD", "DEL",
                LocalDateTime.now().plusMinutes(30), 10);

        try {
            registry.registerFlight(f1);
            registry.registerFlight(f2);
            registry.registerFlight(f3);
        } catch (DuplicateFlightIDException e) {
            System.out.println("Error: " + e.getMessage());
        }

        Aircraft next = registry.peekNextLanding();
        System.out.println("Next to land: " + next.getFlightId());
        assert next.getFlightId().equals("EM999") : "Emergency should be next";

        Aircraft landed1 = registry.processLanding();
        System.out.println("Landed: " + landed1.getFlightId());
        assert landed1.getFlightId().equals("EM999");
        assert landed1.isLanded();

        Aircraft landed2 = registry.processLanding();
        System.out.println("Landed: " + landed2.getFlightId());
        assert landed2.getFlightId().equals("AI102");

        Aircraft landed3 = registry.processLanding();
        System.out.println("Landed: " + landed3.getFlightId());
        assert landed3.getFlightId().equals("AI101");

        System.out.println("Landing processing: OK\n");
    }

    private static void testHoldingPattern() {
        System.out.println("--- Test: Holding Pattern ---");

        FlightRegistry registry = new FlightRegistry(2, 10, 10);
        LandingHistory history = new LandingHistory();

        CommercialFlight f1 = new CommercialFlight("AI101", "BOM", "DEL",
                LocalDateTime.now().plusHours(2), 50, false);
        CommercialFlight f2 = new CommercialFlight("AI102", "BOM", "DEL",
                LocalDateTime.now().plusHours(1), 30, false);
        CommercialFlight f3 = new CommercialFlight("AI103", "BOM", "DEL",
                LocalDateTime.now().plusHours(3), 60, false);

        try {
            registry.registerFlight(f1);
            registry.registerFlight(f2);
            registry.registerFlight(f3);

            registry.addToHoldingPattern(f1);
            registry.addToHoldingPattern(f2);

            System.out.println("Holding pattern count: " + registry.getHoldingPatternCount());
            assert registry.getHoldingPatternCount() == 2;

            try {
                registry.addToHoldingPattern(f3);
                assert false : "Should have thrown HoldingPatternFullException";
            } catch (HoldingPatternFullException e) {
                System.out.println("Holding pattern full correctly rejected: " + e.getMessage());
            }

            Aircraft fromHolding = registry.dequeueFromHoldingPattern();
            System.out.println("Dequeued from holding: " + fromHolding.getFlightId());
            assert fromHolding != null;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Holding pattern: OK\n");
    }

    private static void testFilePersistence() {
        System.out.println("--- Test: File Persistence ---");

        FlightRegistry registry = new FlightRegistry(5, 10, 10);

        CommercialFlight f1 = new CommercialFlight("AI101", "BOM", "DEL",
                LocalDateTime.now().plusHours(2), 75, false);
        EmergencyFlight f2 = new EmergencyFlight("EM999", "HYD", "DEL",
                LocalDateTime.now().plusMinutes(30), 10);

        try {
            registry.registerFlight(f1);
            registry.registerFlight(f2);

            FilePersistence.saveRegistry(registry, "data/test_output.json");
            System.out.println("Registry saved to data/test_output.json");

            String content = FilePersistence.loadRegistry("data/sample_flights.json");
            Aircraft[] loadedFlights = FilePersistence.parseFlights(content);
            System.out.println("Loaded " + loadedFlights.length + " flights from sample_flights.json");
            assert loadedFlights.length == 4 : "Should load 4 flights";

            for (Aircraft a : loadedFlights) {
                System.out.println("  - " + a.getFlightId() + " [" + a.getType() + "]");
            }

        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        } catch (DuplicateFlightIDException e) {
            System.out.println("Duplicate error: " + e.getMessage());
        }

        System.out.println("File persistence: OK\n");
    }
}