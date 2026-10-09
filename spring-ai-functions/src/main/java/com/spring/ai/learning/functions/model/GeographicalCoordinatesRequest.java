package com.spring.ai.learning.functions.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonClassDescription("Get Geographical Coordinates latitude and longitude based on the location in city[,state, country] format and optionally state and country")
public record GeographicalCoordinatesRequest(
        @JsonProperty(required = true, value = "name") @JsonPropertyDescription("name of the city optionally comma separated and followed by state and/or country in this format city[, state, country]") String location,
        @JsonProperty(required = false, value = "state") @JsonPropertyDescription("Optional for state") String state,
        @JsonProperty(required = false, value = "country") @JsonPropertyDescription("Optional for country full country name or two letter ISO Country Code") String country
) {
}
