package com.tboostai_llm.entity.response;

import lombok.Data;

@Data
public class AIChatResp {
    private String content;
    private boolean userContentSufficient;
    private boolean systemAccurateEnough;
    private String systemAccurateRate;
    private SearchParamsResponse requestParams;
}
