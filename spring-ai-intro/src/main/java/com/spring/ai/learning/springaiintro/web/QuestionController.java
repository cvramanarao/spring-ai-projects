package com.spring.ai.learning.springaiintro.web;

import com.spring.ai.learning.springaiintro.Answer;
import com.spring.ai.learning.springaiintro.model.GetCapitalRequest;
import com.spring.ai.learning.springaiintro.model.GetCapitalResponse;
import com.spring.ai.learning.springaiintro.model.GetCapitalWithInfoResponse;
import com.spring.ai.learning.springaiintro.model.Question;
import com.spring.ai.learning.springaiintro.services.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
class QuestionController {

    private final OpenAIService openAIService;

    QuestionController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/capitalWithInfo")
    public GetCapitalWithInfoResponse getCapitalWithInfo(@RequestBody GetCapitalRequest getCapitalRequest){
        return openAIService.getCapitalWithInfo(getCapitalRequest);
    }

    @PostMapping("/capital")
    public GetCapitalResponse getCapital(@RequestBody GetCapitalRequest getCapitalRequest){
        return openAIService.getCapital(getCapitalRequest);
    }

    @PostMapping("/ask")
    public Answer askQuestion(@RequestBody Question question) {
        return openAIService.getAnswer(question);
    }
}
