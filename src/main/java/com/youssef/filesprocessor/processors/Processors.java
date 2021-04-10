package com.youssef.filesprocessor.processors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youssef.filesprocessor.searchreplace.SearchReplaceObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Service
public final class Processors
{
    private List<FileProcessor> fileProcessors;

    @Autowired
    public Processors(final List<FileProcessor> processors)
    {
        this.fileProcessors = processors;
    }

    public void processFile(final File inputFile,
                            final Path outputFile,
                            final SearchReplaceObject searchReplaceObject)
    {
        fileProcessors.forEach(p ->
        {
            try
            {
				p.doProcess(inputFile, outputFile, searchReplaceObject);
			}
            catch (IOException e)
            {
            	throw new RuntimeException(e.getMessage());
			}
        });
    }
}
