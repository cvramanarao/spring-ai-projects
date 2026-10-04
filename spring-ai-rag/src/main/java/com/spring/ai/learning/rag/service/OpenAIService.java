package com.spring.ai.learning.rag.service;

import com.spring.ai.learning.rag.model.Answer;
import com.spring.ai.learning.rag.model.Question;

public interface OpenAIService {

    Answer getAnswer(Question question);

}
