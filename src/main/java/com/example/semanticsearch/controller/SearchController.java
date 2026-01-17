package com.example.semanticsearch.controller;

import com.example.semanticsearch.dto.SaveRequest;
import com.example.semanticsearch.dto.SearchRequest;
import com.example.semanticsearch.dto.SearchResponseItem;
import com.example.semanticsearch.service.SemanticSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vector")
public class SearchController {

    private final SemanticSearchService service;

    public SearchController(SemanticSearchService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody SaveRequest request) {
        service.save(request);
        return ResponseEntity.ok("Saved successfully");
    }

    @PostMapping("/search")
    public ResponseEntity<List<SearchResponseItem>> search(@RequestBody SearchRequest request) {
        return ResponseEntity.ok(service.search(request));
    }
}
