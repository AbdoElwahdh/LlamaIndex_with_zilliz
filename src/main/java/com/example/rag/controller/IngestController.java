package com.example.rag.controller;

import com.example.rag.dto.IngestRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Marks this class as a REST API controller.
@RequestMapping("/api/ingest") // Base path for all endpoints in this controller.
public class IngestController {

    // Handles HTTP POST requests to /api/ingest
    @PostMapping
    public ResponseEntity<String> ingest(@RequestBody IngestRequest request) {
        // @RequestBody: Maps the HTTP request body (JSON) to an IngestRequest object.
        // ResponseEntity.ok(): Returns an HTTP 200 OK status.
        return ResponseEntity.ok("تم استلام المحتوى: " + request.getContent());
    }
}