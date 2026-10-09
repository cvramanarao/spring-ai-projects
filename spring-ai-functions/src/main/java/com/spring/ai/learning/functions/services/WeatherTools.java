package com.spring.ai.learning.functions.services;

import com.spring.ai.learning.functions.config.SpringAIFunctionsConfigProperties;
import com.spring.ai.learning.functions.model.GeographicalCoordinatesRequest;
import com.spring.ai.learning.functions.model.WeatherRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@Component
public class WeatherTools {


    private final SpringAIFunctionsConfigProperties springAIFunctionsConfigProperties;


    @Tool(description = "Find the latitude and longitude for a given location; City, state or Country.")
    public String getGeoCoordinates(GeographicalCoordinatesRequest geographicalCoordinatesRequest) {
        RestClient restClient = RestClient.builder()
                .baseUrl(springAIFunctionsConfigProperties.getGeoCoordinatesApiUrl())
                .build();

        String response = restClient.get().uri(uriBuilder -> {
            uriBuilder.path("/v1/search");
            uriBuilder.queryParam("name", geographicalCoordinatesRequest.location());
            URI uri = uriBuilder.build();
            log.info("Geo Coordinates api: {}", uri);
            return uri;
        }).retrieve().body(String.class);
        log.info("response: {}", response);
        return response;
    }


    @Tool(description = "Find the weather for a given location given the geographical coordinates latitude and longitude.")
    public String getWeather(WeatherRequest weatherRequest){
        RestClient restClient = RestClient.builder()
                .baseUrl(springAIFunctionsConfigProperties.getWeatherApiUrl())
                .build();

        String response = restClient.get().uri(uriBuilder -> {
            uriBuilder.path("/v1/forecast");
            uriBuilder.queryParam("latitude", weatherRequest.latitude());
            uriBuilder.queryParam("longitude", weatherRequest.longitude());
            uriBuilder.queryParam("current", "temperature_2m,relative_humidity_2m,wind_speed_10m");
            URI uri = uriBuilder.build();
            log.info("weather api: {}", uri);
            return uri;
        }).retrieve().body(String.class);
        log.info("response: {}", response);
        return response;
    }
}
