package atc.persistence;

import atc.model.*;
import atc.util.FlightType;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FilePersistence {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static void saveRegistry(FlightRegistry registry, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("{\n");
            writer.write("  \"flights\": [\n");
            Aircraft[] flights = registry.getAllFlights();
            for (int i = 0; i < flights.length; i++) {
                Aircraft a = flights[i];
                writer.write("    {\n");
                writer.write("      \"flightId\": \"" + escapeJson(a.getFlightId()) + "\",\n");
                writer.write("      \"type\": \"" + a.getType() + "\",\n");
                writer.write("      \"origin\": \"" + escapeJson(a.getOrigin()) + "\",\n");
                writer.write("      \"destination\": \"" + escapeJson(a.getDestination()) + "\",\n");
                writer.write("      \"eta\": \"" + (a.getEta() != null ? a.getEta().format(DATE_FORMATTER) : "") + "\",\n");
                writer.write("      \"fuelLevel\": " + a.getFuelLevel() + ",\n");
                writer.write("      \"emergencyFlag\": " + a.isEmergencyFlag() + ",\n");
                writer.write("      \"landed\": " + a.isLanded() + "\n");
                writer.write("    }");
                if (i < flights.length - 1) writer.write(",");
                writer.write("\n");
            }
            writer.write("  ]\n");
            writer.write("}\n");
        }
    }

    public static String loadRegistry(String filename) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    public static Aircraft[] parseFlights(String jsonContent) {
        if (jsonContent == null || jsonContent.trim().isEmpty()) {
            return new Aircraft[0];
        }
        return parseSimpleJson(jsonContent);
    }

    private static Aircraft[] parseSimpleJson(String json) {
        java.util.List<Aircraft> flights = new java.util.ArrayList<>();
        int flightsStart = json.indexOf("\"flights\": [");
        if (flightsStart == -1) return new Aircraft[0];

        int contentStart = json.indexOf("[", flightsStart) + 1;
        int contentEnd = json.indexOf("]", contentStart);

        if (contentEnd == -1) return new Aircraft[0];

        String flightsContent = json.substring(contentStart, contentEnd).trim();
        if (flightsContent.isEmpty()) return new Aircraft[0];

        String[] flightEntries = flightsContent.split("\\},\\s*\\{");
        for (String entry : flightEntries) {
            entry = entry.replace("{", "").replace("}", "");
            Aircraft aircraft = parseFlightEntry(entry);
            if (aircraft != null) {
                flights.add(aircraft);
            }
        }

        return flights.toArray(new Aircraft[0]);
    }

    private static Aircraft parseFlightEntry(String entry) {
        String flightId = extractValue(entry, "flightId");
        String type = extractValue(entry, "type");
        String origin = extractValue(entry, "origin");
        String destination = extractValue(entry, "destination");
        String etaStr = extractValue(entry, "eta");
        String fuelStr = extractValue(entry, "fuelLevel");
        String emergencyStr = extractValue(entry, "emergencyFlag");

        if (flightId == null || type == null) return null;

        LocalDateTime eta = null;
        if (etaStr != null && !etaStr.isEmpty()) {
            try {
                eta = LocalDateTime.parse(etaStr, DATE_FORMATTER);
            } catch (Exception e) {
            }
        }

        int fuelLevel = 50;
        if (fuelStr != null) {
            try {
                fuelLevel = Integer.parseInt(fuelStr);
            } catch (Exception e) {
            }
        }

        boolean emergency = emergencyStr != null && emergencyStr.equals("true");

        FlightType flightType = parseFlightType(type);

        switch (flightType) {
            case COMMERCIAL:
                return new CommercialFlight(flightId, origin, destination, eta, fuelLevel, emergency);
            case CARGO:
                return new CargoFlight(flightId, origin, destination, eta, fuelLevel, emergency);
            case EMERGENCY:
                return new EmergencyFlight(flightId, origin, destination, eta, fuelLevel);
            default:
                return null;
        }
    }

    private static String extractValue(String entry, String key) {
        String search = "\"" + key + "\":";
        int index = entry.indexOf(search);
        if (index == -1) return null;

        int valueStart = index + search.length();
        while (valueStart < entry.length() && Character.isWhitespace(entry.charAt(valueStart))) {
            valueStart++;
        }

        if (valueStart >= entry.length()) return null;

        char firstChar = entry.charAt(valueStart);
        if (firstChar == '"') {
            int start = valueStart + 1;
            int end = entry.indexOf("\"", start);
            if (end == -1) return null;
            return entry.substring(start, end);
        } else {
            int end = valueStart;
            while (end < entry.length() && entry.charAt(end) != ',' && entry.charAt(end) != '}') {
                end++;
            }
            return entry.substring(valueStart, end).trim();
        }
    }

    private static FlightType parseFlightType(String typeStr) {
        if (typeStr == null) return FlightType.COMMERCIAL;
        try {
            return FlightType.valueOf(typeStr.toUpperCase());
        } catch (Exception e) {
            return FlightType.COMMERCIAL;
        }
    }

    private static String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}