package com.spring.ai.learning.functions.web;


import com.spring.ai.learning.functions.model.Answer;
import com.spring.ai.learning.functions.model.GeographicalCoordinatesRequest;
import com.spring.ai.learning.functions.model.GeographicalCoordinatesResponse;
import com.spring.ai.learning.functions.model.WeatherResponse;
import com.spring.ai.learning.functions.services.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jt, Spring Framework Guru.
 */
@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final OpenAIService openAIService;

    @PostMapping("/geographical_coordinates")
    public GeographicalCoordinatesResponse askQuestion(@RequestBody GeographicalCoordinatesRequest geographicalCoordinatesRequest) {
        return openAIService.getGeographicalCoordinates(geographicalCoordinatesRequest);
    }


    @PostMapping("/weather")
    public WeatherResponse askWeather(@RequestBody GeographicalCoordinatesRequest geographicalCoordinatesRequest) {
        return openAIService.getWeather(geographicalCoordinatesRequest);
    }

}
