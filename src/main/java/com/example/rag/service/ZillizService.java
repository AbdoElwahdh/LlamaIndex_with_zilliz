package com.example.rag.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.milvus.v2.client.MilvusClientV2;
import io.milvus.v2.client.ConnectConfig;
import io.milvus.v2.service.collection.request.CreateCollectionReq;
import io.milvus.v2.service.collection.request.HasCollectionReq;
import io.milvus.common.clientenum.ConsistencyLevelEnum; // هذا الاستيراد غير مستخدم في الكود الحالي، لكن لا يسبب مشكلة.

import java.util.Scanner;

/**
 * Zilliz Service
 */
@Service
public class ZillizService {

    private static final Logger logger = LoggerFactory.getLogger(ZillizService.class);

    @Value("${zilliz.endpoint:https://default-endpoint}")
    private String zillizEndpoint;

    @Value("${zilliz.token:default-token}")
    private String zillizToken;

    private MilvusClientV2 client;

    /**
     * Initialize real connection to Zilliz
     */
    public void initializeConnection() {
        logger.info("Connecting to Zilliz...");

        try {
            ConnectConfig config = ConnectConfig.builder()
                    .uri(zillizEndpoint)
                    .token(zillizToken)
                    .build();

            client = new MilvusClientV2(config);
            logger.info("✅ Successfully connected to Zilliz!");

        } catch (Exception e) { // هنا تم وضع catch الخاص بـ initializeConnection
            logger.error("❌ Failed to connect: {}", e.getMessage());
            throw new RuntimeException("Connection failed", e);
        }
    }

    /**
     * Test method
     */
    public String testConnection() {
        if (client == null) {
            initializeConnection();
        }
        return "✅ Connected to Zilliz: " + zillizEndpoint;
    }

    // هنا الكود الباقي كان سليم تقريباً
    // ...

    /**
     * Create simple collection
     */
    public boolean createCollection(String collectionName) {
        logger.info("Creating collection: {}", collectionName);

        try {
            // Check if exists first
            HasCollectionReq hasReq = HasCollectionReq.builder()
                    .collectionName(collectionName)
                    .build();

            Boolean exists = client.hasCollection(hasReq);
            if (exists) {
                logger.info("Collection {} already exists", collectionName);
                return true;
            }

            // Create very simple collection
            CreateCollectionReq createReq = CreateCollectionReq.builder()
                    .collectionName(collectionName)
                    .dimension(768) // Simple fixed dimension
                    .build();

            client.createCollection(createReq);
            logger.info("✅ Collection {} created successfully!", collectionName);
            return true;

        } catch (Exception e) {
            logger.error("❌ Failed to create collection: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Read file from resources/uploads folder
     */
    public String readFileFromUploads(String filename) {
        logger.info("Reading file: {}", filename);

        try {
            // القراءة من resources/uploads
            ClassPathResource resource = new ClassPathResource("uploads/" + filename);

            if (!resource.exists()) {
                logger.error("File not found: {}", filename);
                return null;
            }

            // اقرأ كل محتوى الملف
            StringBuilder content = new StringBuilder();
            try (Scanner scanner = new Scanner(resource.getInputStream())) {
                while (scanner.hasNextLine()) {
                    content.append(scanner.nextLine()).append("\n");
                }
            }

            logger.info("✅ File read successfully. Length: {} characters", content.length());
            return content.toString();

        } catch (Exception e) {
            logger.error("❌ Error reading file: {}", e.getMessage());
            return null;
        }
    }
}