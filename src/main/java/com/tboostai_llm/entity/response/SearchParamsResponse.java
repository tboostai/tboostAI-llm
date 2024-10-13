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
    private List<String> make;
    private List<String> model;
    private Integer minYear;
    private Integer maxYear;
    private List<String> trim;
    private Integer mileage;
    private Double minPrice;
    private Double maxPrice;
    private List<String> color;
    private List<String> bodyType;
    private List<String> engineType;
    private List<String> transmission;
    private List<String> drivetrain;
    private Double longitude;
    private Double latitude;
    private List<String> condition;
    private Integer capacity;
    private List<String> features;
}