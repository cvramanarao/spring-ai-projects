package com.spring.ai.learning.functions.services;



import com.spring.ai.learning.functions.config.SpringAIFunctionsConfigProperties;
import com.spring.ai.learning.functions.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.Map;

/**
 * Created by jt, Spring Framework Guru.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class OpenAIServiceImpl implements OpenAIService {


    final ChatClient.Builder chatClientBuilder;
    final WeatherTools weatherTools;
    final SpringAIFunctionsConfigProperties springAIFunctionsConfigProperties;

    @Value("classpath:/templates/get-geographical-coordinates-for-location.st")
    private Resource getGeoGraphicalCoordinatesPrompt;

    @Value("classpath:/templates/get-weather-for-location-prompt.st")
    private Resource getWeatherPrompt;

    @Value("classpath:/templates/system-prompt.st")
    private Resource systemPrompt;

    @Override
    public Answer getAnswer(Question question) {
        return null;
    }

    @Override
    public GeographicalCoordinatesResponse getGeographicalCoordinates(GeographicalCoordinatesRequest geographicalCoordinatesRequest) {
        log.info("OpenAiServiceImpl || getAnswer() -->");
        BeanOutputConverter<GeographicalCoordinatesResponse> converter = new BeanOutputConverter<>(GeographicalCoordinatesResponse.class);
        String format = converter.getFormat();
        ChatClient chatClient = chatClientBuilder.build();
        PromptTemplate promptTemplate = new PromptTemplate(getGeoGraphicalCoordinatesPrompt);
        Prompt prompt =  promptTemplate.create(Map.of("location", geographicalCoordinatesRequest.location(),
                "format", format));
        ChatClientResponse chatClientResponse = chatClient.prompt(prompt).tools(weatherTools).call().chatClientResponse();
        String llmResponse = chatClientResponse.chatResponse().getResult().getOutput().getText();

        log.info("llmResponse: {}", llmResponse);

        return converter.convert(llmResponse);
    }

    @Override
    public WeatherResponse getWeather(GeographicalCoordinatesRequest geographicalCoordinatesRequest) {
        BeanOutputConverter<WeatherResponse> converter = new BeanOutputConverter<>(WeatherResponse.class);
        String format = converter.getFormat();
        log.info("OpenAiServiceImpl || getWeather() -->");
        ChatClient chatClient = chatClientBuilder.build();
        //PromptTemplate promptTemplate = new PromptTemplate("what is the weather  in the {location} in the city[,state, country] format and optionally state and country?");
        PromptTemplate promptTemplate = new PromptTemplate(getWeatherPrompt);
        Prompt prompt = promptTemplate.create(
                Map.of("location", geographicalCoordinatesRequest.location(),
                        "format", converter.getFormat()
                )
        );
        ChatClientResponse chatClientResponse = chatClient.prompt( prompt)
                .system(systemPrompt)
                .tools(weatherTools)
                .call()
                .chatClientResponse();
        String llmResponse = chatClientResponse.chatResponse().getResult().getOutput().getText();

        log.info("llmResponse: {}", llmResponse);
        log.info("<-- OpenAiServiceImpl || getWeather()");

        return converter.convert(llmResponse);
    }


}
