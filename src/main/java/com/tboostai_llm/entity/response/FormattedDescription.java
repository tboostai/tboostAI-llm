package com.tboostai_llm.entity.response;

import lombok.Data;

import java.util.List;

@Data
public class FormattedDescription {
    private String originalDescription;
    private List<String> summarized;
    private List<String> extractedFeatures;
}
