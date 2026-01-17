package com.example.semanticsearch.service;

import com.example.semanticsearch.dto.SaveRequest;
import com.example.semanticsearch.dto.SearchRequest;
import com.example.semanticsearch.dto.SearchResponseItem;
import com.example.semanticsearch.repository.SemanticSearchRepository;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SemanticSearchService {

    private final SemanticSearchRepository repository;

    public SemanticSearchService(SemanticSearchRepository repository) {
        this.repository = repository;
    }

    public void save(SaveRequest request) {
        repository.save(request.getUserId(), request.getText());
    }

    public List<SearchResponseItem> search(SearchRequest request) {
        List<Document> documents = repository.search(request.getUserId(), request.getQuery());
        return documents.stream()
                .map(doc -> {
                    // Try to retrieve score or distance from metadata
                    Object scoreObj = doc.getMetadata().getOrDefault("score", doc.getMetadata().get("distance"));
                    double score = 0.0;
                    if (scoreObj instanceof Number) {
                        score = ((Number) scoreObj).doubleValue();
                    }
                    return new SearchResponseItem(doc.getText(), score);
                })
                .collect(Collectors.toList());
    }
}
