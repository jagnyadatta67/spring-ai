package com._minuteconcept.spring.ai.openai.config;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class PdfFileReader {
    private final VectorStore vectorStore;

    @Value("classpath:Hello.txt")
    private Resource pdfResource;

    public PdfFileReader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void init() {

        TextReader textReader=new TextReader(pdfResource);

        System.out.println("***********************");
        vectorStore.accept(textReader.get());

    }
}
