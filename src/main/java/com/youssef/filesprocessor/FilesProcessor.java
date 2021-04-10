package com.youssef.filesprocessor;

import com.youssef.filesprocessor.config.ProcessorsConfig;
import com.youssef.filesprocessor.config.SearchReplaceEngineConfig;
import com.youssef.filesprocessor.processors.Processors;
import com.youssef.filesprocessor.searchreplace.SearchReplaceEngine;
import com.youssef.filesprocessor.searchreplace.SearchReplaceObject;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilesProcessor
{
    private static final int INPUT_FILE = 0;
    private static final int OUTPUT_FILE = 1;
    private static final int SEARCH_WORD = 2;
    private static final int REPLACE_WORD = 3;

    private Path outputFile;
    private File inputFile;
    private String searchWord;
    private String replaceword;

    public static void main(final String[] args)
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProcessorsConfig.class, SearchReplaceEngineConfig.class);

        final FilesProcessor filesProcessor = new FilesProcessor();
        try
        {
            filesProcessor.parseMainArguments(args);
            filesProcessor.processFile(context);

        }
        catch (Throwable e)
        {
        	throw new RuntimeException(e.getMessage());
        }
    }

    private void parseMainArguments(final String[] args) throws IOException
    {
        final String inputFileName = args[INPUT_FILE];
        final String outputFileName = args[OUTPUT_FILE];
        searchWord = args[SEARCH_WORD];
        replaceword = args[REPLACE_WORD];
        inputFile = Paths.get(inputFileName).toFile();
        outputFile = Paths.get(outputFileName);

        if (!hasContent(inputFileName) ||
        	!hasContent(outputFileName) ||
        	!hasContent(searchWord) ||
        	!hasContent(replaceword) ||
            !inputFile.exists() ||
            !inputFile.isFile())
        {
            throw new IllegalArgumentException("Invalid inputs!");
        }
    }

    private boolean hasContent(final String text)
    {
    	return text != null && !text.isEmpty();
    }

    public void processFile(final AnnotationConfigApplicationContext context)
    {
        final Processors processors = context.getBean(Processors.class);
        final SearchReplaceEngine searchReplaceEngine = context.getBean(SearchReplaceEngine.class);
        final SearchReplaceObject searchReplaceObject = new SearchReplaceObject(searchWord, replaceword, searchReplaceEngine);
        processors.processFile(inputFile, outputFile, searchReplaceObject);
    }
}
