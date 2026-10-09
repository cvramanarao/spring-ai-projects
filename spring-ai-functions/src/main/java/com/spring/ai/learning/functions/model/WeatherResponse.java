package com.spring.ai.learning.functions.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.math.BigDecimal;

public record WeatherResponse(@JsonPropertyDescription("Windspeed in KMH") BigDecimal windSpeed,
                              @JsonPropertyDescription("Direction of wind") Integer windDegrees,
                              @JsonPropertyDescription("Current temperature in Celsius") BigDecimal temperature,
                              @JsonPropertyDescription("Current Humidity") Integer humidity,
                              @JsonPropertyDescription("Epoch time of sunset GMT ") Integer sunset,
                              @JsonPropertyDescription("Epoch time of sunrise GMT ") Integer sunrise,
                              @JsonPropertyDescription("Min temperature in Celsius") BigDecimal minTemperature,
                              @JsonPropertyDescription("Max temperature in Celsius") BigDecimal maxTemperature) {
}
