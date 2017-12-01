package com.emat.xml;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.emat.util.StringUtil;

public class XmlEntity implements Serializable
{
  private static final long serialVersionUID = 1732973065146470296L;
  private String name = "";
  private String value = "";
  private String tagName = "";
  private final HashMap<String, String> _hashAttributes = new HashMap<>();
  private XmlEntity _child;
  private XmlEntity _sibling;
  private static final String TAB = "\t";
  private static final String NEW_LINE = "\n";
  private static final String OPEN_BRACE = "<";
  private static final String OPEN_BRACE_SLASH = "</";
  private static final String CLOSE_BRACE = ">";
  
  public XmlEntity() {}
  
  public XmlEntity(String sName)
  {
    if (StringUtil.strValid(sName)) {
      this.name = sName;
    } else {
      throw new IllegalArgumentException("Name must not be null");
    }
  }
  
  public XmlEntity(String sName, String sValue)
  {
    if (StringUtil.strValid(sName)) {
      this.name = sName;
    } else {
      throw new IllegalArgumentException("Name must not be null");
    }
    if (StringUtil.strValid(sValue)) {
      this.value = sValue;
    } else {
      throw new IllegalArgumentException("Value must not be null");
    }
  }
  
  public String getName()
  {
    return this.name;
  }
  
  protected void setName(String sName)
  {
    if (sName != null) {
      this.name = sName;
    }
  }
  
  public String getValue()
  {
    return this.value;
  }
  
  protected void setValue(String sValue)
  {
    if (sValue != null) {
      this.value = sValue;
    }
  }
  
  public String getTagName() {
	return tagName;
}

public void setTagName(String tagName) {
	this.tagName = tagName;
}

protected void addAttribute(String sAttribName, String sAttribValue)
  {
    this._hashAttributes.put(sAttribName, sAttribValue);
  }
  
  public Set<String> getAttributeNames()
  {
    return this._hashAttributes.keySet();
  }
  
  public String getAttribute(String sAttribName)
  {
    String sAttribValue = null;
    if (sAttribName != null) {
      sAttribValue = (String)this._hashAttributes.get(sAttribName);
    }
    if (sAttribValue == null) {
      sAttribValue = "";
    }
    return sAttribValue;
  }
  
  public XmlEntity child()
  {
    return this._child;
  }
  
  public XmlEntity next()
  {
    return this._sibling;
  }
  
  public XmlEntity next(String tagName)
  {
    return next(tagName, false);
  }
  
  public XmlEntity next(String tagName, boolean caseSensetive)
  {
    XmlEntity next = next();
    while ((next != null) && (!elementNameEquals(next.getName(), tagName, caseSensetive))) {
      next = next.next();
    }
    return next;
  }
  
  protected void setAsLastSibling(XmlEntity sibling)
  {
    XmlEntity lastSibling = this;
    while (lastSibling.next() != null) {
      lastSibling = lastSibling.next();
    }
    lastSibling.setSibling(sibling);
  }
  
  private void setSibling(XmlEntity sibling)
  {
    this._sibling = sibling;
  }
  
  protected void setAsLastChild(XmlEntity child)
  {
    XmlEntity lastChild = this;
    while (lastChild.next() != null) {
      lastChild = lastChild.next();
    }
    lastChild.setChild(child);
  }
  
  private void setChild(XmlEntity child)
  {
    this._child = child;
  }
  
  public XmlEntity findFirstOccurrence(String tagName)
  {
    return findFirstOccurrence(tagName, false);
  }
  
  public XmlEntity findFirstOccurrence(String tagName, boolean caseSensitive)
  {
    XmlEntity occurrence = null;
    if (tagName != null)
    {
      String tmpTagName = tagName.trim();
      
      XmlEntity acioNext = this;
      while ((acioNext != null) && (occurrence == null)) {
        if (elementNameEquals(acioNext.getName(), tmpTagName, caseSensitive))
        {
          occurrence = acioNext;
        }
        else
        {
          occurrence = acioNext.findFirstEnclosedOccurrence(tmpTagName, caseSensitive);
          
          acioNext = acioNext.next();
        }
      }
    }
    return occurrence;
  }
  
  public XmlEntity findFirstEnclosedOccurrence(String tagName)
  {
    return findFirstEnclosedOccurrence(tagName, false);
  }
  
  public XmlEntity findFirstEnclosedOccurrence(String tagName, boolean caseSensitive)
  {
    XmlEntity occurrence = null;
    XmlEntity child = child();
    if (child != null) {
      occurrence = child.findFirstOccurrence(tagName, caseSensitive);
    }
    return occurrence;
  }
  
  public boolean checkForSuccess()
  {
    return getTagValue("response", "").equalsIgnoreCase("SUCCESS");
  }
  
  public String getTagValue(String sTagName, String sDefaultValue)
  {
    String sTagValue = sDefaultValue;
    XmlEntity acirTag = findFirstOccurrence(sTagName);
    if (acirTag != null) {
      sTagValue = acirTag.getValue();
    }
    return sTagValue;
  }
  
  public String getTagValue(String sTagName)
  {
    return getTagValue(sTagName, null);
  }
  
  public int getTagValue(String sTagName, int nDefaultValue)
  {
    return StringUtil.atoi(getTagValue(sTagName), nDefaultValue);
  }
  
  public long getTagValue(String sTagName, long lDefaultValue)
  {
    return StringUtil.atol(getTagValue(sTagName), lDefaultValue);
  }
  
  public float getTagValue(String sTagName, float fDefaultValue)
  {
    return StringUtil.atof(getTagValue(sTagName), fDefaultValue);
  }
  
  public double getTagValue(String sTagName, double dDefaultValue)
  {
    return StringUtil.atod(getTagValue(sTagName), dDefaultValue);
  }
  
  public boolean getTagValue(String sTagName, boolean bDefaultValue)
  {
    return StringUtil.atob(getTagValue(sTagName), bDefaultValue);
  }
  
  public String toString()
  {
    return toString(0);
  }
  
  private String singleToString(int nIndent)
  {
    this._nIndent = nIndent;
    StringBuffer sbRet = new StringBuffer();
    
    printIndent(sbRet, TAB);
    sbRet.append(OPEN_BRACE);
    sbRet.append(getName());
    sbRet.append(printAttributesToXMLString());
    sbRet.append(CLOSE_BRACE);
    if (StringUtil.strValid(getValue())) {
      sbRet.append(StringUtil.XMLEscape(getValue()));
    }
    XmlEntity child = child();
    if (child != null)
    {
      sbRet.append(NEW_LINE);
      sbRet.append(child.toString(nIndent + 1));
      printIndent(sbRet, TAB);
    }
    sbRet.append(OPEN_BRACE_SLASH);
    sbRet.append(getName());
    sbRet.append(CLOSE_BRACE);
    sbRet.append(NEW_LINE);
    
    return sbRet.toString();
  }
  
  private String toString(int nIndent)
  {
    this._nIndent = nIndent;
    StringBuffer sbRet = new StringBuffer();
    
    sbRet.append(singleToString(nIndent));
    
    XmlEntity next = next();
    while (next != null)
    {
      sbRet.append(next.singleToString(nIndent));
      next = next.next();
    }
    return sbRet.toString();
  }
  
  protected int _nIndent = 0;
  
  protected void printIndent(StringBuffer sbuffer, String sIndentString)
  {
    for (int nIdx = 0; nIdx < this._nIndent; nIdx++) {
      sbuffer.append(sIndentString);
    }
  }
  
  private String printAttributesToXMLString()
  {
    StringBuffer sbAttribs = new StringBuffer();
    for (Iterator<String> itAttribNames = getAttributeNames().iterator(); itAttribNames.hasNext();)
    {
      String sAttribName = (String)itAttribNames.next();
      if (StringUtil.strValid(sAttribName))
      {
        sbAttribs.append(' ').append(sAttribName).append("=\"");
        sbAttribs.append(StringUtil.XMLEscape(getAttribute(sAttribName)));
        sbAttribs.append('"');
      }
    }
    return sbAttribs.toString();
  }
  
  private boolean elementNameEquals(String elementName1, String elementName2, boolean caseSensetive)
  {
    return caseSensetive ? elementName1.equals(elementName2) : elementName1.equalsIgnoreCase(elementName2);
  }
}
