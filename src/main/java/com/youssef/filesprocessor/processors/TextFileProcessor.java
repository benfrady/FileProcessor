package com.youssef.filesprocessor.processors;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


@Service
public final class TextFileProcessor extends FileProcessor
{
    private static final String EXTENTION = "txt";
    private int bufferSize;
    private String buffer = "";
    private BufferedWriter bufferWriter;

    @Override
    public String getSupportedExtension()
    {
        return EXTENTION;
    }

    @Override
    public void processFile(final String inputfile) throws IOException
    {
		bufferSize = getFileSizeMegaBytes() >= 1 ? 1000 : 1;

		final BufferedWriter bufferWriter = Files.newBufferedWriter(getOutputFile());
		this.bufferWriter = bufferWriter;

		final Stream<String> lines = Files.lines(Paths.get(inputfile));
		lines.forEachOrdered(line ->
		{
			try 
			{
				processLine(line);
			}
			catch (final IOException e)
			{
				throw new RuntimeException(e.getMessage());
			}
		});

		bufferWriter.write(buffer);
		bufferWriter.flush();
	}

    private void processLine(final String line) throws IOException
    {
        buffer += line + System.lineSeparator();

        if (buffer.length() >= bufferSize)
        {
            if (searchAndReplace(buffer).length() >= 0)
            {
            	bufferWriter.append(searchAndReplace(buffer));
            }

            buffer = "";
        }
    }
}
