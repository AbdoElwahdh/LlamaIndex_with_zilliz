package com.example.rag.controller;

import com.example.rag.dto.QueryRequest;
import com.example.rag.dto.QueryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/query") // All endpoints here start with /api/query
public class QueryController {

    // POST http://localhost:8080/api/query
    @PostMapping
    public ResponseEntity<QueryResponse> query(@RequestBody QueryRequest request) {
        // For now, we just return the same question as the answer (mock logic)

        // Create a fake response
        QueryResponse response = new QueryResponse(
                "You asked: " + request.getQuestion(), // Answer (mocked)
                0.99f // Fake score
        );

        // Return the response as JSON with 200 OK
        return ResponseEntity.ok(response);
    }
}
