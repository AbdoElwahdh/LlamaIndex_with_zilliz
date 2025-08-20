package com.example.rag.controller;

import com.example.rag.service.ZillizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    
    @Autowired
    private ZillizService zillizService;
    
    @GetMapping("/test-connection")
    public String testConnection() {
        zillizService.initializeConnection();
        return zillizService.testConnection();
    }
    
    @GetMapping("/create-collection")
    public String createCollection() {
        zillizService.initializeConnection();
        boolean result = zillizService.createCollection("test_collection");
        return result ? "✅ Collection created!" : "❌ Failed to create collection";
    }
    
    @GetMapping("/read-file")
    public String readFile() {
        String content = zillizService.readFileFromUploads("file1.txt");
        if (content != null) {
            return "✅ File content (first 200 chars):\n" + 
                   content.substring(0, Math.min(200, content.length())) + "...";
        } else {
            return "❌ Failed to read file";
        }
    }
    
    @GetMapping("/health")
    public String health() {
        return "Application is running!";
    }
}