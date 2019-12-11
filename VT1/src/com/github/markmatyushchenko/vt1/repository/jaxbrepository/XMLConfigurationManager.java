package com.github.markmatyushchenko.vt1.repository.jaxbrepository;

import com.github.markmatyushchenko.vt1.repository.jaxbrepository.adapter.entity.XMLObjectsList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

public class XMLConfigurationManager {

	private XMLConfigurationManager() {
	}

	private static volatile XMLConfigurationManager INSTANCE = null;

	public static XMLConfigurationManager getInstance() {
		if (INSTANCE == null) {
			synchronized (XMLConfigurationManager.class) {
				INSTANCE = new XMLConfigurationManager();
			}
		}
		return INSTANCE;
	}

	public Marshaller getMarshaller() {
		try {
			JAXBContext context = JAXBContext.newInstance(XMLObjectsList.class);
			Marshaller marshaller = context.createMarshaller();
			configureMarshaller(marshaller);
			return marshaller;
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Marshaller getMarshaller(File validationFile) {
		try {
			Marshaller marshaller = getMarshaller();
			configureMarshaller(marshaller, validationFile);
			return marshaller;
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Unmarshaller getUnmarshaller() {
		try {
			JAXBContext context = JAXBContext.newInstance(XMLObjectsList.class);
			return context.createUnmarshaller();
		} catch (JAXBException e) {
			return null;
		}
	}

	public Unmarshaller getUnmarshaller(File validationFile) {
		Unmarshaller unmarshaller = getUnmarshaller();
		configureUnmarshaller(unmarshaller, validationFile);
		return unmarshaller;
	}

	private void configureMarshaller(Marshaller marshaller) throws PropertyException {
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	}

	private void configureMarshaller(Marshaller marshaller, File validationFile) throws PropertyException {
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setSchema(getSchema(validationFile));
	}

	public void configureUnmarshaller(Unmarshaller unmarshaller, File validationFile) {
		unmarshaller.setSchema(getSchema(validationFile));
	}

	public Schema getSchema(File validationFile) {
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema employeeSchema = null;
		try {
			employeeSchema = sf.newSchema(validationFile);
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return employeeSchema;
	}

}
