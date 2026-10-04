package com.spring.ai.learning.rag.expert.bootstrap;

import com.spring.ai.learning.rag.expert.config.VectorStoreProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class LoadVectorStore implements CommandLineRunner {

    @Autowired
    private VectorStore vectorStore;

    @Autowired
    private VectorStoreProperties vectorStoreProperties;

    @Override
    public void run(String... args) throws Exception {
        log.info("Loading Vector Store...");
        if (vectorStore.similaritySearch("Sportsman").isEmpty()) {
            vectorStoreProperties.getDocumentsToLoad().forEach(document -> {
                log.info("Loading document: {}", document.getFilename());
                TikaDocumentReader documentReader = new TikaDocumentReader(document);
                List<Document> documents = documentReader.get();
                TextSplitter textSplitter = TokenTextSplitter.builder().build();
                List<Document> splitDocuments = textSplitter.split(documents);
                vectorStore.add(splitDocuments);
            });
        }
        log.info("Vector Store loaded...");
    }
}
