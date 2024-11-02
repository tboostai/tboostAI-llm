package com.tboostai_llm.controller;

import com.tboostai_llm.entity.request.Message;
import com.tboostai_llm.entity.response.FormattedDescription;
import com.tboostai_llm.entity.response.SearchParamsResponse;
import com.tboostai_llm.service.OpenAIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class VehicleInfoController {

    private static final Logger logger = LoggerFactory.getLogger(VehicleInfoController.class);
    private final OpenAIService openAIService;

    public VehicleInfoController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/llm")
    public Mono<SearchParamsResponse> getLlmData(@RequestBody List<Message> messages) {
        try {
            return openAIService.getResponseFromLLM(messages);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/description")
    public Mono<FormattedDescription> getDescriptionData(@RequestBody Object description) {
        try {
            logger.info("Request Body is {}", description);
            return openAIService.beautifulDescriptions(description);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
