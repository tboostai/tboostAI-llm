package com.tboostai_llm.entity.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Message implements Serializable {
    private String role;
    private String content;
}
