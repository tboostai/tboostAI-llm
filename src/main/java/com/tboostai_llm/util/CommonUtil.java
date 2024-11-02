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

import static com.tboostai_llm.common.GeneralConstants.*;

public class CommonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    public static Map<String, String> generateOpenAIRequestHeader(String openAIAPIKey) {
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put(CONTENT_TYPE, APPLICATION_JSON);
        requestHeaders.put(ACCEPT, APPLICATION_JSON);
        requestHeaders.put(AUTHORIZATION, openAIAPIKey);

        return requestHeaders;
    }

    public static Map<String, Object> buildOpenAIRequestBody(OpenAIRequest openAIRequest) {
        Map<String, Object> body = new HashMap<>();
        body.put(OPENAI_MODEL_KEY, OPENAI_MODEL_VALUE);

        List<Map<String, String>> messages = new ArrayList<>();

        for (Message message : openAIRequest.getMessages()) {
            Map<String, String> messageMap = new HashMap<>();
            messageMap.put(OPENAI_ROLE, message.getRole());
            messageMap.put(OPENAI_CONTENT, message.getContent());
            messages.add(messageMap);
        }

        body.put(OPENAI_MESSAGES, messages);

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
            String content = root.path(OPENAI_CHOICES).get(0).path(OPENAI_MESSAGE).path(OPENAI_CONTENT).asText();
            logger.info("Response content from OpenAI: {}", content);
            return objectMapper.readValue(content, clazz);
        } catch (JsonProcessingException e) {
            logger.error("Failed to parse json response", e);
        }
        return null;
    }

}
