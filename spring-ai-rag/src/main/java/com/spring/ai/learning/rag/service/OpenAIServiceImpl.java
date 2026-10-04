package com.spring.ai.learning.rag.service;

import com.spring.ai.learning.rag.model.Answer;
import com.spring.ai.learning.rag.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OpenAIServiceImpl implements OpenAIService {

    final ChatModel chatModel;

    final SimpleVectorStore simpleVectorStore;

//    @Value("classpath:/templates/rag-prompt-template.st")
//    private Resource ragPromptTemplate;

    @Value("classpath:/templates/rag-prompt-template-meta.st")
    private Resource ragPromptTemplate;

    @Override
    public Answer getAnswer(Question question) {
        System.out.println("Question: "+question.question());
        List<Document> documents = simpleVectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(question.question())
                        .topK(4)
                        .build()
        );
        System.out.println("documents retrieved: ");
        documents.forEach(doc -> {
            System.out.println("================================");
            System.out.println(doc.getText());
        });
        //documents.forEach(System.out::println);
        System.out.println("Content List: ");
        List<String> contentList = documents.stream().map(Document::getFormattedContent).toList();
        contentList.forEach(System.out::println);

        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);
        Prompt prompt = promptTemplate.create(Map.of(
                "input", question.question(),
                "documents", String.join("\n", contentList)
        ));
        ChatResponse response = chatModel.call(prompt);
        System.out.println(response.getMetadata());
        return new Answer(response.getResult().getOutput().getText());
    }
}
