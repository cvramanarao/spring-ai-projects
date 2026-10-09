package com.spring.ai.learning.functions.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GeographicalCoordinatesResponse(
        @JsonPropertyDescription("This is the latitude of the given location") String latitude,
        @JsonPropertyDescription("This is the longitude of the given location") String longitude) {
}
