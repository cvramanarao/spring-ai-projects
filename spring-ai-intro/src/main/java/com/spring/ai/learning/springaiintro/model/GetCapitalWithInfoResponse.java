package com.spring.ai.learning.springaiintro.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record GetCapitalWithInfoResponse(@JsonPropertyDescription("This is the capital city name") String city,
                                         @JsonPropertyDescription("This is the population in the city") String population,
                                         @JsonPropertyDescription("This is the region name where city is located") String region,
                                         @JsonPropertyDescription("This is the primary language spoken") String language,
                                         @JsonPropertyDescription("This is the currency used") String currency) {
}
