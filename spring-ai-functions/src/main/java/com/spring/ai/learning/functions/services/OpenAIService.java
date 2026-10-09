package com.spring.ai.learning.functions.services;


import com.spring.ai.learning.functions.model.*;

/**
 * Created by jt, Spring Framework Guru.
 */
public interface OpenAIService {

    Answer getAnswer(Question question);

    GeographicalCoordinatesResponse getGeographicalCoordinates(GeographicalCoordinatesRequest geographicalCoordinatesRequest);

    WeatherResponse getWeather(GeographicalCoordinatesRequest geographicalCoordinatesRequest);
}
