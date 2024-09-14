package com.tboostai_llm.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class OpenAIRequest implements Serializable {

    private String model;

    @JsonProperty("messages")
    private List<Message> messages;

}
