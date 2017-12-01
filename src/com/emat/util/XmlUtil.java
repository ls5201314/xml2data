package com.emat.util;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.emat.xml.XmlEntity;
import com.emat.xml.XmlEntityHandler;

public class XmlUtil {

	public static XmlEntity parseResponseString(String xml) throws IOException, SAXException, ParserConfigurationException{
	    
	    return parseResponseInputSource(new InputSource(new StringReader(xml)));
	  }			  
				  
	public static XmlEntity parseResponseInputSource(InputSource inputSource) throws IOException, SAXException, ParserConfigurationException{
	      XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
	      
	      XmlEntityHandler handler = new XmlEntityHandler();
	      
	      reader.setContentHandler(handler);
	      reader.setErrorHandler(handler);
	      
	      reader.parse(inputSource);
	      
	      return handler.getXmlRoot();
	  
	  }		
}
