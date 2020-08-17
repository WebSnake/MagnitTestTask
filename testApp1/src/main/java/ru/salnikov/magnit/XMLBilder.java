package ru.salnikov.magnit;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLBilder {
	
	public static void convent(String pathCurrentXmlFile, String pathNewXmlFile, String xslFile) {
		TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File(xslFile));
        Transformer transformer = null;
		try {
			transformer = factory.newTransformer(xslt);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
        Source xml = new StreamSource(new File(pathCurrentXmlFile));
        try {
        	transformer.transform(xml, new StreamResult(new File(pathNewXmlFile)));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	public static void bild(ArrayList<Integer> values, String filePath) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            
            // создаем пустой объект Document
            Document doc = builder.newDocument();

            Element entries = doc.createElementNS("", "entries");
            doc.appendChild(entries);
 
            Element entry = doc.createElement("entry");
            entries.appendChild(entry);
            

            for (int i = 0; i < values.size(); i++) {
            	Element field = doc.createElement("field");
                entry.appendChild(field);
                field.appendChild(doc.createTextNode("" + values.get(i)));

            }
 
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);

            StreamResult file = new StreamResult(new File(filePath));

            transformer.transform(source, file);           
 
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
