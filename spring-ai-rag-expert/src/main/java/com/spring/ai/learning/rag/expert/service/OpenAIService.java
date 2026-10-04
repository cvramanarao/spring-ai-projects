package com.spring.ai.learning.rag.expert.service;

import com.spring.ai.learning.rag.expert.model.Answer;
import com.spring.ai.learning.rag.expert.model.Question;

public interface OpenAIService {

    Answer getAnswer(Question question);

}
