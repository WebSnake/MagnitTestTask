package ru.salnikov.magnit;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Sum {
	
	public static Long sumFields(String filePath) {
			Long sum = 0L;
            // Создается строитель документа
            DocumentBuilder documentBuilder = null;
			try {
				documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
            // Создается дерево DOM документа из файла
            Document document = null;
			try {
				document = documentBuilder.parse(filePath);
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
 
            // Получаем корневой элемент
            Node entries = document.getDocumentElement();
            NodeList entry = entries.getChildNodes();
            
            Integer field = 0;
            for (int i = 0; i < entry.getLength(); i++) {
            	field = Integer.parseInt(entries.getChildNodes().item(i).getAttributes().item(0).getTextContent());
            	sum += field;
            }

            return sum;    
    }
}
