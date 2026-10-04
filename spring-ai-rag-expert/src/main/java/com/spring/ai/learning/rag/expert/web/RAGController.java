package com.spring.ai.learning.rag.expert.web;

import com.spring.ai.learning.rag.expert.model.Answer;
import com.spring.ai.learning.rag.expert.model.Question;
import com.spring.ai.learning.rag.expert.service.OpenAIService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RAGController {

    private final OpenAIService openAIService;

    public RAGController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }


    @PostMapping("/ask")
    public Answer getAnswer(@RequestBody Question question){
        return openAIService.getAnswer(question);
    }
}
