package com.example.semanticsearch.repository;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class SemanticSearchRepository {

    private final VectorStore vectorStore;

    public SemanticSearchRepository(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void save(String userId, String text) {
        Document document = new Document(text, Map.of("userId", userId));
        vectorStore.add(List.of(document));
    }

    public List<Document> search(String userId, String query) {
        FilterExpressionBuilder b = new FilterExpressionBuilder();
        return vectorStore.similaritySearch(
                org.springframework.ai.vectorstore.SearchRequest.builder()
                        .query(query)
                        .topK(5)
                        .filterExpression(b.eq("userId", userId).build())
                        .build());
    }
}
