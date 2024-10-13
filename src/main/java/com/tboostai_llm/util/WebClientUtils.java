package com.tboostai_llm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Map;

@Component
public class WebClientUtils {

    private final WebClient.Builder webClientBuilder;
    private static final Logger logger = LoggerFactory.getLogger(WebClientUtils.class);

    public WebClientUtils(@Qualifier("createExternalWebClientBuilder") WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public <T> Mono<T> sendExternalPostRequest(String uri, String bodyValue, Map<String, String> headerToValue, Class<T> responseType, Integer maxAttempts, Integer durationInSecond) {

        WebClient.RequestBodySpec uriSpec = webClientBuilder.build().post()
                .uri(uri);

        // Add headers
        for (Map.Entry<String, String> headerEntry : headerToValue.entrySet()) {
            uriSpec.header(headerEntry.getKey(), headerEntry.getValue());
        }

        // Make the request
        Mono<T> monoResult = uriSpec.bodyValue(bodyValue)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    logger.error("4xx error occurred while requesting {}: {}", uri, clientResponse.statusCode());
                    return Mono.error(new RuntimeException("Client error occurred while requesting " + uri));
                })
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                    logger.error("5xx error occurred while requesting {}: {}", uri, clientResponse.statusCode());
                    return Mono.error(new RuntimeException("Server error occurred while requesting " + uri));
                })
                .bodyToMono(responseType);

        // Retry logic if provided
        if (maxAttempts != null && durationInSecond != null) {
            monoResult = monoResult.retryWhen(Retry.fixedDelay(maxAttempts, Duration.ofSeconds(durationInSecond))
                    .doBeforeRetry(retrySignal -> logger.info("Retrying due to: {}", retrySignal.failure().getMessage())));
        }

        // Error logging and fallback
        return monoResult
                .doOnError(e -> logger.error("Error occurred while calling {}: {}", uri, e.getMessage(), e))  // Ensure all exceptions are logged
                .onErrorResume(e -> {
                    logger.error("Handling error for {}: {}", uri, e.getMessage(), e);
                    return Mono.error(new RuntimeException("Failed to retrieve data from " + uri, e));
                });
    }
}