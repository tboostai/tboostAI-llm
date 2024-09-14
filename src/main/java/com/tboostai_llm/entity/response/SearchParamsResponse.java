package com.tboostai_llm.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchParamsResponse implements Serializable {
    private String make;
    private String model;
    private Integer minYear;
    private Integer maxYear;
    private String trim;
    private Integer mileage;
    private Double minPrice;
    private Double maxPrice;
    private String color;
    private String bodyType;
    private String engineType;
    private String transmission;
    private String drivetrain;
    private Double longitude;
    private Double latitude;
    private String condition;
    private Integer capacity;
    private List<String> features;
}