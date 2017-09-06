package br.com.cpqd.auth;

import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Utils {
	public static Document convertStringToXML(String result) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			InputSource resultXML = new InputSource(new StringReader(result));			

			return dBuilder.parse(resultXML);

		} catch (ParserConfigurationException e) {
			Logger lgr = Logger.getLogger(Connect.class.getName());
			lgr.log(Level.SEVERE, e.getMessage(), e);
		} catch (SAXException e) {
			Logger lgr = Logger.getLogger(Connect.class.getName());
			lgr.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			Logger lgr = Logger.getLogger(Connect.class.getName());
			lgr.log(Level.SEVERE, e.getMessage(), e);
		}	
		
		return null;		
	}
}
