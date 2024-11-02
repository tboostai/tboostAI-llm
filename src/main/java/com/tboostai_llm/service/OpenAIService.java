package com.tboostai_llm.service;

import com.tboostai_llm.entity.request.Message;
import com.tboostai_llm.entity.request.OpenAIRequest;
import com.tboostai_llm.entity.response.FormattedDescription;
import com.tboostai_llm.entity.response.SearchParamsResponse;
import com.tboostai_llm.util.CommonUtil;
import com.tboostai_llm.util.WebClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.tboostai_llm.common.GeneralConstants.*;

@Service
public class OpenAIService {

    @Value("${openai.project.key}")
    private String openAIAPIKey;

    @Value("${openai.project.chat.url}")
    private String openAIAPIChatUrl;

    private final WebClientUtils webClientUtils;

    private static final Logger logger = LoggerFactory.getLogger(OpenAIService.class);

    public OpenAIService(WebClientUtils webClientUtils) {
        this.webClientUtils = webClientUtils;
    }

    public Mono<SearchParamsResponse> getResponseFromLLM(List<Message> messages) {
        OpenAIRequest openAIRequest = new OpenAIRequest();
        openAIRequest.setMessages(messages);
        Map<String, String> requestHeaders = CommonUtil.generateOpenAIRequestHeader(openAIAPIKey);
        String requestBody = CommonUtil.parseObjToString(CommonUtil.buildOpenAIRequestBody(openAIRequest));
        Mono<String> responseResStr = webClientUtils.sendExternalPostRequest(openAIAPIChatUrl, requestBody, requestHeaders, String.class, 3, 5);
        logger.info("OpenAI Response is {}", responseResStr);
        return responseResStr.mapNotNull(response -> CommonUtil.parseJsonToObject(response, SearchParamsResponse.class));
    }

    public Mono<FormattedDescription> beautifulDescriptions(Object description) {
        Map<String, String> requestHeaders = CommonUtil.generateOpenAIRequestHeader(openAIAPIKey);
        logger.info("Request header is {}", requestHeaders);
        Message systemMsg = new Message();
        systemMsg.setRole(OPENAI_SYSTEM);
        systemMsg.setContent(OPENAI_SYSTEM_DEFAULT_MSG_FOR_BEAUTIFUL_DESC);

        Message descMsg = new Message();
        descMsg.setRole(OPENAI_USER);
        descMsg.setContent(description.toString());

        OpenAIRequest openAIRequest = new OpenAIRequest();
        List<Message> messages = new ArrayList<>();
        messages.add(systemMsg);
        messages.add(descMsg);
        openAIRequest.setMessages(messages);

        logger.info("OpenAI Request in class {} is {}", this.getClass().getName(), openAIRequest);

        String requestBody = CommonUtil.parseObjToString(CommonUtil.buildOpenAIRequestBody(openAIRequest));

        Mono<String> responseResStr = webClientUtils.sendExternalPostRequest(openAIAPIChatUrl, requestBody, requestHeaders, String.class, 3, 5);
        logger.info("OpenAI response in class {} is {}", this.getClass().getName(), responseResStr);

        return responseResStr.mapNotNull(response -> CommonUtil.parseJsonToObject(response, FormattedDescription.class));
    }
}
