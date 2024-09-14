package com.tboostai_llm.controller;

import com.tboostai_llm.entity.request.OpenAIRequest;
import com.tboostai_llm.entity.response.SearchParamsResponse;
import com.tboostai_llm.service.OpenAIService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class VehicleRecommendationController {

    private final OpenAIService openAIService;

    public VehicleRecommendationController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/llm")
    public Mono<SearchParamsResponse> getLlmData(@RequestBody OpenAIRequest request) {
        try {
            return openAIService.getResponseFromLLM(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/llm-test")
    public String getLlmDataTest(@RequestBody String content) {

        return "Response from tboostAI-llm, and request body content is : " + content;
    }
}
