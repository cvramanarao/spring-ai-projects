package com.spring.ai.learning.rag.expert.service;

import com.spring.ai.learning.rag.expert.model.Answer;
import com.spring.ai.learning.rag.expert.model.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class OpenAIServiceImpl implements OpenAIService {

    final ChatModel chatModel;

    final VectorStore vectorStore;

    @Value("classpath:/templates/rag-prompt-template.st")
    private Resource ragPromptTemplate;

    @Value("classpath:/templates/system-message.st")
    private Resource systemMessageTemplate;


    @Override
    public Answer getAnswer(Question question) {
        log.info("Question: {}",question.question());
        PromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemMessageTemplate);
        Message systemMessage = systemPromptTemplate.createMessage();
        
        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(question.question())
                        .topK(4)
                        .build()
        );
        log.info("documents retrieved: ");
        documents.forEach(doc -> {
            log.info("================================");
            log.info(doc.getText());
        });
        //documents.forEach(System.out::println);
        log.info("Content List: ");
        List<String> contentList = documents.stream().map(Document::getFormattedContent).toList();
        contentList.forEach(System.out::println);

        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);
        Message userMessage = promptTemplate.createMessage(Map.of(
                "input", question.question(),
                "documents", String.join("\n", contentList)
        ));
        ChatResponse response = chatModel.call(new Prompt(List.of(systemMessage, userMessage)));
        log.info("{}", response.getMetadata());
        return new Answer(response.getResult().getOutput().getText());
    }
}
