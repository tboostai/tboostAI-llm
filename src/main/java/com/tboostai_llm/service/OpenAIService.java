package com.tboostai_llm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tboostai_llm.entity.request.Message;
import com.tboostai_llm.entity.request.OpenAIRequest;
import com.tboostai_llm.entity.response.SearchParamsResponse;
import com.tboostai_llm.util.WebClientUtils;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAIService {

    @Value("${openai.project.key}")
    private String openAIAPIKey;

    @Value("${openai.project.chat.url}")
    private String openAIAPIChatUrl;

    @Resource
    private WebClientUtils webClientUtils;

    private static final Logger logger = LoggerFactory.getLogger(OpenAIService.class);

    public Mono<SearchParamsResponse> getResponseFromLLM(OpenAIRequest openAIRequest) throws Exception {
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Content-Type", "application/json");
        requestHeaders.put("Accept", "application/json");
        requestHeaders.put("Authorization", openAIAPIKey);
        Mono<String> responseResStr = webClientUtils.sendExternalPostRequest(openAIAPIChatUrl, buildRequestBody(openAIRequest), requestHeaders, String.class, null, null);

        return responseResStr.map(this::parseResponse);
    }

    private Map<String, Object> buildRequestBody(OpenAIRequest openAIRequest) {
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

    public SearchParamsResponse parseResponse(String jsonResponse) {
        ObjectMapper objectMapper = new ObjectMapper();


        JsonNode root;
        try {
            root = objectMapper.readTree(jsonResponse);
            String content = root.path("choices").get(0).path("message").path("content").asText();

            return objectMapper.readValue(content, SearchParamsResponse.class);
        } catch (JsonProcessingException e) {
            logger.error("Failed to parse json response", e);
        }
        return null;
    }
}
