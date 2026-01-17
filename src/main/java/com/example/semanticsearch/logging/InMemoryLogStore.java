package com.example.semanticsearch.logging;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryLogStore {

    private final Map<String, List<String>> logs = new ConcurrentHashMap<>();

    public InMemoryLogStore() {
        logs.put("save", new ArrayList<>());
        logs.put("search", new ArrayList<>());
        // Initialize headers
        String header = String.format("%-20s | %-50s | %-50s | %-25s | %-25s | %-15s",
                "Method", "Input", "Output", "Incoming Time", "Outgoing Time", "Duration (ms)");
        logs.get("save").add(header);
        logs.get("search").add(header);
        logs.get("save").add("-".repeat(header.length()));
        logs.get("search").add("-".repeat(header.length()));
    }

    public synchronized void addLog(String type, String logEntry) {
        logs.computeIfAbsent(type, k -> new ArrayList<>()).add(logEntry);
        writeToFile(type, logEntry);
    }

    private void writeToFile(String type, String logEntry) {
        try (java.io.PrintWriter out = new java.io.PrintWriter(new java.io.BufferedWriter(new java.io.FileWriter(type + ".log", true)))) {
            // Write header if file is empty (rudimentary check, or rely on the constructor initialization which is in-memory only)
            // Ideally check file size or existence, but for simplicity just append.
            // Since we initialized headers in memory, we might want to write them to file too if it's new.
            // For now, just append the entry.
            out.println(logEntry);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, List<String>> getLogs() {
        return logs;
    }
}
