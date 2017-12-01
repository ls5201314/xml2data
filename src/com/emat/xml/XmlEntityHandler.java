package com.emat.xml;

import java.util.EmptyStackException;
import java.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlEntityHandler extends DefaultHandler
{
  private static final String ROOT_NAME = "aciroot";
  private boolean lastTagWasClose;
  private boolean lastTagWasOpen;
  private StringBuffer currentCharData;
  private final XmlEntity topNode;
  private final Stack<XmlEntity> activeNodes;
  
  public XmlEntityHandler()
  {
    this.lastTagWasClose = true;
    this.lastTagWasOpen = false;
    
    this.topNode = new XmlEntity();
    this.topNode.setName(ROOT_NAME);
    
    this.activeNodes = new Stack<XmlEntity>();
    this.activeNodes.push(this.topNode);
  }
  
  public XmlEntity getXmlRoot()
  {
    return this.topNode.next();
  }
  
  private XmlEntity retrieveCurrentlyActiveNode()
  {
    XmlEntity acirCurrentlyActiveNode = null;
    if (!this.activeNodes.empty()) {
      try
      {
        acirCurrentlyActiveNode = (XmlEntity)this.activeNodes.pop();
      }
      catch (EmptyStackException ese) {}
    }
    return acirCurrentlyActiveNode;
  }
  
  public void startElement(String uri, String localName, String qName, Attributes attributes)
    throws SAXException
  {
    XmlEntity newNode = new XmlEntity();
    newNode.setTagName(qName);
    for (int i = 0; i < attributes.getLength(); i++) {
      newNode.addAttribute(attributes.getQName(i), attributes.getValue(i));
      if(attributes.getQName(i).equals("name"))newNode.setName(attributes.getValue(i));
    }
    if(newNode.getName().isEmpty())newNode.setName(qName);
    XmlEntity activeNode = retrieveCurrentlyActiveNode();
    if (this.lastTagWasOpen)
    {
      activeNode.setAsLastChild(newNode);
      
      this.activeNodes.push(activeNode);
    }
    else if (this.lastTagWasClose)
    {
      activeNode.setAsLastSibling(newNode);
    }
    else
    {
      throw new SAXException("Inconsistant status flag, last tag wasn't either open or close.");
    }
    this.activeNodes.push(newNode);
    
    this.lastTagWasClose = false;
    this.lastTagWasOpen = true;
    
    this.currentCharData = new StringBuffer("");
  }
  
  public void characters(char[] chr, int start, int length)
    throws SAXException
  {
    this.currentCharData.append(chr, start, length);
  }
  
  public void endElement(String uri, String localName, String qName)
    throws SAXException
  {
    if (this.lastTagWasOpen)
    {
      XmlEntity activeNode = retrieveCurrentlyActiveNode();
      
      activeNode.setValue(this.currentCharData.toString());
      
      this.activeNodes.push(activeNode);
    }
    else if (this.lastTagWasClose)
    {
      String nodeName = " ";
      XmlEntity node = retrieveCurrentlyActiveNode();
      while ((node != null) && (!nodeName.equals(qName)))
      {
        node = retrieveCurrentlyActiveNode();
        nodeName = node.getTagName();
      }
      this.activeNodes.push(node);
    }
    this.lastTagWasClose = true;
    this.lastTagWasOpen = false;
  }
}
