package com.spring.ai.learning.springaiintro.services;

import com.spring.ai.learning.springaiintro.Answer;
import com.spring.ai.learning.springaiintro.model.GetCapitalRequest;
import com.spring.ai.learning.springaiintro.model.GetCapitalResponse;
import com.spring.ai.learning.springaiintro.model.GetCapitalWithInfoResponse;
import com.spring.ai.learning.springaiintro.model.Question;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Objects;


@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatModel chatModel;

    public OpenAIServiceImpl(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Value("classpath:/templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;

    @Value("classpath:/templates/get-capital-with-info.st")
    private Resource getCapitalPromptWithInfo;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String getAnswer(String question) {

        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt =  promptTemplate.create();
        ChatResponse response = chatModel.call(prompt);
        return response.getResult().getOutput().getText();
    }

    @Override
    public Answer getAnswer(Question question) {
        PromptTemplate promptTemplate = new PromptTemplate(question.question());
        Prompt prompt =  promptTemplate.create();
        ChatResponse response = chatModel.call(prompt);
        return new Answer(response.getResult().getOutput().getText());
    }

    @Override
    public GetCapitalResponse getCapital(GetCapitalRequest getCapitalRequest) {

        BeanOutputConverter<GetCapitalResponse> converter = new BeanOutputConverter<>(GetCapitalResponse.class);
        String format = converter.getFormat();
        System.out.println(format);

        //PromptTemplate promptTemplate = new PromptTemplate("What is the capital of "+getCapitalRequest.stateOrCountry()+"?");
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Prompt prompt =  promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry(),
                "format", format));
        ChatResponse response = chatModel.call(prompt);
        ChatResponseMetadata llmMetadata = response.getMetadata();
        System.out.println(llmMetadata);
        String llmResponse = response.getResult().getOutput().getText();
        System.out.println(llmResponse);
        return converter.convert(Objects.requireNonNull(llmResponse));
        //return new Answer(responseString);
    }

    @Override
    public GetCapitalWithInfoResponse getCapitalWithInfo(GetCapitalRequest getCapitalRequest) {
        BeanOutputConverter<GetCapitalWithInfoResponse> converter = new BeanOutputConverter<>(GetCapitalWithInfoResponse.class);
        String format = converter.getFormat();
        System.out.println(format);
        //PromptTemplate promptTemplate = new PromptTemplate(getCapitalPromptWithInfo);
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Prompt prompt =  promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry(),
                "format", format));
        ChatResponse response = chatModel.call(prompt);
        ChatResponseMetadata llmMetadata = response.getMetadata();
        System.out.println(llmMetadata);
        String llmResponse = response.getResult().getOutput().getText();
        System.out.println(llmResponse);
        return converter.convert(Objects.requireNonNull(llmResponse));
        //return new Answer(response.getResult().getOutput().getText());
    }
}
