/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.service.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

/** OVERVIEW: This class represents the XML validating process. 
 *  It does the required validations on the given XML file based on the XSD file. 
 *  @since 0.1
 *  @version 0.1
 *  @author JusticeLeague
 */
public class XMLValidator {

	private static XMLValidator instance;
	private static final String XSD_PATH = "cezmi.xsd";
	
	/**
	 *  Empty constructor for the XMLValidator, nothing to be done
	 */
	private XMLValidator() {
	}

	/**
	 * Invokes the private constructor of the XMLValidator class, by the rules
	 * of the singleton pattern. Only one XMLValidator object can exist and it
	 * is immutable.
	 * 
	 * @effects an instance of a XMLValidator object.
	 */
	public static synchronized XMLValidator getInstance() {
		if (instance == null) {
			instance = new XMLValidator();
		}

		return instance;
	}

/**
	 *  Validates the design of the given XML File based on the default XSD file.
	 *  @requires An XML file has to be given.
	 *  @effects the boolean value that represents the validation success based on the XSD file.
	 *  @modifies _xmlFile
	 */
	public boolean validateXML(File _xmlFile){

		try {
			File xsdFile = new File(XSD_PATH);
			File xmlFile = _xmlFile;

			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(xsdFile);
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(xmlFile));
		} catch (SAXException | IOException e) {
			System.err.println(e);
			return false;
		}

		System.out.println("XML file is valid based on the XSD file.");
		return true;
	}

}
