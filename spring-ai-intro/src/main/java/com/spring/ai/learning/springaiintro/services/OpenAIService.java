package com.spring.ai.learning.springaiintro.services;

import com.spring.ai.learning.springaiintro.Answer;
import com.spring.ai.learning.springaiintro.model.GetCapitalRequest;
import com.spring.ai.learning.springaiintro.model.GetCapitalResponse;
import com.spring.ai.learning.springaiintro.model.GetCapitalWithInfoResponse;
import com.spring.ai.learning.springaiintro.model.Question;

public interface OpenAIService {

    String getAnswer(String question);
    Answer getAnswer(Question question);

    GetCapitalResponse getCapital(GetCapitalRequest getCapitalRequest);

    GetCapitalWithInfoResponse getCapitalWithInfo(GetCapitalRequest getCapitalRequest);
}
