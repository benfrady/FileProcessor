package com.youssef.filesprocessor.processors;

import org.apache.commons.io.FilenameUtils;

import com.youssef.filesprocessor.searchreplace.SearchReplaceObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public abstract class FileProcessor
{
    private File inputFile;
    private Path outputFile;
    private SearchReplaceObject searchReplaceObject;

    public abstract String getSupportedExtension();

    public abstract void processFile(final String inputfile) throws IOException;

    private void init(final File inputFile,
                      final Path outputFile,
                      final SearchReplaceObject searchReplaceObject)
    {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.searchReplaceObject = searchReplaceObject;
    }

    public Path getOutputFile()
    {
    	return outputFile;
    }

    public File getInputFile()
    {
    	return inputFile;
    }

    public boolean isSupportedFile()
    {
        return getSupportedExtension().equalsIgnoreCase(FilenameUtils.getExtension(inputFile.getName()));
    }

    public double getFileSizeMegaBytes()
    {
		return (double) inputFile.length() / (1024 * 1024);
	}

    public void doProcess(final File input,
                          final Path outputFilePath,
                          final SearchReplaceObject searchReplaceObject) throws IOException
    {
        init(input, outputFilePath, searchReplaceObject);
        if (isSupportedFile())
        {
            processFile(inputFile.getAbsolutePath());
        }
    }

    public String getSearchWord()
    {
        return searchReplaceObject.getSearchWord();
    }

    public String searchAndReplace(final String text)
    {
        return searchReplaceObject.searchAndReplace(text);
    }
}
