package com.youssef.filesprocessor.processors;

import org.springframework.stereotype.Service;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public final class XmlFileProcessor extends FileProcessor
{
    private static final String EXTENTION = "xml";
    private final XMLEventFactory xmlEventFactory = XMLEventFactory.newFactory();

    @Override
    public String getSupportedExtension()
    {
        return EXTENTION;
    }

    @Override
    public void processFile(final String inputfile) throws IOException
    {
    	final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
    	final XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();

		try
		{
			final XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(inputfile));
			final XMLEventWriter writer = xmlOutputFactory.createXMLEventWriter(Files.newBufferedWriter(getOutputFile()));
			while (reader.hasNext())
            {
                final XMLEvent event = (XMLEvent) reader.next();
		        processWriter(event, writer);
            }

			writer.flush();
            writer.close();
		}
		catch (final FileNotFoundException | XMLStreamException e1)
		{
			throw new RuntimeException(e1.getMessage()); 
		}
    }

    public void processWriter(final XMLEvent event,
                              final XMLEventWriter writer) throws XMLStreamException
    {
        if (event.isStartElement())
        {
            writer.add(processStartElement(event.asStartElement()));
        }
        else
        {
            writer.add(event);
        }
    }

    public StartElement processStartElement(final StartElement event)
    {
        final QName qName = event.getName();
        final String text = qName.getLocalPart();

        if (!event.getAttributes().hasNext())
        {
            return event;
        }

        return xmlEventFactory.createStartElement(qName.getPrefix(),
        		qName.getNamespaceURI(),
                text,
                processAttributes(event.getAttributes()),
                event.getNamespaces(),
                event.getNamespaceContext());

    }

    public Iterator<Attribute> processAttributes(final Iterator<Attribute> attributes)
    {
        final List<Attribute> newAttributes = new ArrayList<>();
        attributes.forEachRemaining(attribute -> newAttributes.add(processAttribute(attribute)));
        return newAttributes.iterator();
    }

    public Attribute processAttribute(final Attribute attribute)
    {
        final String text = attribute.getValue();
        final String newText = searchAndReplace(text);
        return xmlEventFactory.createAttribute(attribute.getName(), newText);
    }
}
