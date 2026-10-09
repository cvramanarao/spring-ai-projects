package com.spring.ai.learning.functions.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonClassDescription("Weather API Request")
public record WeatherRequest(@JsonProperty(required = true, value = "latitude")  String latitude,
                             @JsonProperty(required = true, value = "longitude")  String longitude) {
}
