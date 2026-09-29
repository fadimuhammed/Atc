package atc.model;

import atc.util.FlightType;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LandingHistory {
    private static class LandingRecord {
        String flightId;
        FlightType type;
        LocalDateTime eta;
        LocalDateTime actualLandingTime;
        int fuelAtLanding;
        long waitTimeMinutes;

        LandingRecord(Aircraft aircraft, LocalDateTime actualTime) {
            this.flightId = aircraft.getFlightId();
            this.type = aircraft.getType();
            this.eta = aircraft.getEta();
            this.actualLandingTime = actualTime;
            this.fuelAtLanding = aircraft.getFuelLevel();
            if (eta != null) {
                this.waitTimeMinutes = Duration.between(eta, actualTime).toMinutes();
            } else {
                this.waitTimeMinutes = 0;
            }
        }
    }

    private final List<LandingRecord> records;
    private LocalDateTime lastLandingTime;

    public LandingHistory() {
        this.records = new ArrayList<>();
        this.lastLandingTime = LocalDateTime.now();
    }

    public void recordLanding(Aircraft aircraft) {
        LocalDateTime now = LocalDateTime.now();
        LandingRecord record = new LandingRecord(aircraft, now);
        records.add(record);
        lastLandingTime = now;
    }

    public int getTotalLandings() {
        return records.size();
    }

    public int getEmergencyLandings() {
        int count = 0;
        for (LandingRecord r : records) {
            if (r.type == FlightType.EMERGENCY) {
                count++;
            }
        }
        return count;
    }

    public int getCommercialLandings() {
        int count = 0;
        for (LandingRecord r : records) {
            if (r.type == FlightType.COMMERCIAL) {
                count++;
            }
        }
        return count;
    }

    public int getCargoLandings() {
        int count = 0;
        for (LandingRecord r : records) {
            if (r.type == FlightType.CARGO) {
                count++;
            }
        }
        return count;
    }

    public double getAverageWaitTimeMinutes() {
        if (records.isEmpty()) return 0.0;
        long total = 0;
        for (LandingRecord r : records) {
            total += r.waitTimeMinutes;
        }
        return (double) total / records.size();
    }

    public double getAverageWaitTimeByType(FlightType type) {
        long total = 0;
        int count = 0;
        for (LandingRecord r : records) {
            if (r.type == type) {
                total += r.waitTimeMinutes;
                count++;
            }
        }
        return count == 0 ? 0.0 : (double) total / count;
    }

    public int getAverageFuelAtLanding() {
        if (records.isEmpty()) return 0;
        int total = 0;
        for (LandingRecord r : records) {
            total += r.fuelAtLanding;
        }
        return total / records.size();
    }

    public void printReport() {
        System.out.println("=== LANDING HISTORY REPORT ===");
        System.out.println("Total Landings: " + getTotalLandings());
        System.out.println("Emergency Landings: " + getEmergencyLandings());
        System.out.println("Commercial Landings: " + getCommercialLandings());
        System.out.println("Cargo Landings: " + getCargoLandings());
        System.out.println("Average Wait Time: " + String.format("%.2f", getAverageWaitTimeMinutes()) + " minutes");
        System.out.println("Average Wait Time (Emergency): " + String.format("%.2f", getAverageWaitTimeByType(FlightType.EMERGENCY)) + " minutes");
        System.out.println("Average Wait Time (Commercial): " + String.format("%.2f", getAverageWaitTimeByType(FlightType.COMMERCIAL)) + " minutes");
        System.out.println("Average Wait Time (Cargo): " + String.format("%.2f", getAverageWaitTimeByType(FlightType.CARGO)) + " minutes");
        System.out.println("Average Fuel at Landing: " + getAverageFuelAtLanding() + "%");
    }

    public List<LandingRecord> getRecords() {
        return new ArrayList<>(records);
    }
}