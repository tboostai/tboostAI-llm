package com.tboostai_llm.entity.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class Message implements Serializable {
    private String role;
    private String content;
}
