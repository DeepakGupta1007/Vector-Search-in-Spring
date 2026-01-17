# Semantic Search with Spring AI and Qdrant

## Objective
This project implements a **semantic search engine** using Spring Boot and Qdrant. It allows users to store text data with generated vector embeddings and perform natural language searches to find relevant information.

## Key Features
-   **Local Embeddings**: Uses **Sentence Transformers** (ONNX) locally, eliminating the need for external API keys (e.g., OpenAI).
-   **Vector Database**: Integrates with **Qdrant** for efficient vector storage and similarity search.
-   **User Scoping**: Data and searches are scoped by `userId`.
-   **AOP Logging**: Request and response details are logged to both in-memory store and persisted files (`save.log`, `search.log`) using Spring AOP.

## Tech Stack
-   Java 17
-   Spring Boot 3.4.1
-   Spring AI 1.0.0-M6
-   Qdrant (Vector Store)

## how to Run

### 1. Prerequisites
-   **Docker**: Required to run Qdrant.
-   **Java 17+**: Required to run the application.

### 2. Start Qdrant
Run the following command to start a Qdrant instance in Docker:
```bash
docker run -p 6333:6333 -p 6334:6334 qdrant/qdrant
```

### 3. Run Application
Use the included Maven wrapper to start the application:
```bash
./mvnw clean spring-boot:run
```
*Note: The first run may take a few minutes to download the embedding model.*

## API Usage

### Save Data
Generates an embedding and saves the text.
```bash
curl -X POST http://localhost:8080/api/vector/save \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "user1",
    "text": "Spring AI makes semantic search implementation very easy and powerful."
  }'
```

### Search Data
Searches for similar texts.
```bash
curl -X POST http://localhost:8080/api/vector/search \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "user1",
    "query": "Is implementing semantic search difficult?"
  }'
```

### View Logs
View the request/response logs locally.
```bash
curl http://localhost:8080/api/logs
```
Logs are also persisted to `save.log` and `search.log` in the project root.
