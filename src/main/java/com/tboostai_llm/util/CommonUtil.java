package com.tboostai_llm.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tboostai_llm.entity.request.Message;
import com.tboostai_llm.entity.request.OpenAIRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    public static Map<String, String> generateOpenAIRequestHeader(String openAIAPIKey) {
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Content-Type", "application/json");
        requestHeaders.put("Accept", "application/json");
        requestHeaders.put("Authorization", openAIAPIKey);

        return requestHeaders;
    }

    public static Map<String, Object> buildOpenAIRequestBody(OpenAIRequest openAIRequest) {
        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4o");

        List<Map<String, String>> messages = new ArrayList<>();

        for (Message message : openAIRequest.getMessages()) {
            Map<String, String> messageMap = new HashMap<>();
            messageMap.put("role", message.getRole());
            messageMap.put("content", message.getContent());
            messages.add(messageMap);
        }

        body.put("messages", messages);

        return body;
    }

    public static String parseObjToString(Object obj) {

        String jsonBody = "";
        try {
            jsonBody = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("Failed to build request body JSON", e);
        }

        logger.info("RequestBody is {}", jsonBody);

        return jsonBody;
    }

    public static <T> T parseJsonToObject(String jsonBody, Class<T> clazz) {
        logger.info("jsonBody is {}", jsonBody);
        JsonNode root;
        try {
            root = objectMapper.readTree(jsonBody);
            String content = root.path("choices").get(0).path("message").path("content").asText();
            logger.info("Response content from OpenAI: {}", content);
            return objectMapper.readValue(content, clazz);
        } catch (JsonProcessingException e) {
            logger.error("Failed to parse json response", e);
        }
        return null;
    }

}
