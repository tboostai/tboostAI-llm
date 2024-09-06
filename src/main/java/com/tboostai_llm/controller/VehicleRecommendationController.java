package com.tboostai_llm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VehicleRecommendationController {
    @GetMapping("/llm-endpoint")
    public String getLlmData() {
        return "Data from tboostAI_llm service";
    }
}
