package com.example.semanticsearch.controller;

import com.example.semanticsearch.logging.InMemoryLogStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final InMemoryLogStore logStore;

    public LogController(InMemoryLogStore logStore) {
        this.logStore = logStore;
    }

    @GetMapping
    public Map<String, List<String>> getLogs() {
        return logStore.getLogs();
    }
}
