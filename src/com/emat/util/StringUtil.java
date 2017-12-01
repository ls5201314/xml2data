package com.emat.util;

import org.apache.commons.lang.StringUtils;

public class StringUtil {

	 public static boolean strValid(String sTest, int nMaxLength)
	  {
	    boolean boolRet = false;
	    if (sTest != null) {
	      if (nMaxLength > 0)
	      {
	        if ((sTest.length() > 0) && (sTest.length() <= nMaxLength)) {
	          boolRet = true;
	        }
	      }
	      else if (sTest.length() > 0) {
	        boolRet = true;
	      }
	    }
	    return boolRet;
	  }
	  
	  public static boolean strValid(String sTest)
	  {
	    return strValid(sTest, -1);
	  }
	  
	  public static String XMLEscape(String sToEscape)
	  {
	    if (sToEscape == null) {
	      return null;
	    }
	    StringBuffer sbRet = new StringBuffer(sToEscape.length());
	    for (int nLoop = 0; nLoop < sToEscape.length(); nLoop++)
	    {
	      char entity = sToEscape.charAt(nLoop);
	      if (entity == '&') {
	        sbRet.append("&amp;");
	      } else if (entity == '<') {
	        sbRet.append("&lt;");
	      } else if (entity == '>') {
	        sbRet.append("&gt;");
	      } else if (entity == '"') {
	        sbRet.append("&#034;");
	      } else if (entity == '\'') {
	        sbRet.append("&#039;");
	      } else {
	        sbRet.append(entity);
	      }
	    }
	    return sbRet.toString();
	  }
	  
	  public static int atoi(String number, int defaultValue)
	  {
	    int returnValue = defaultValue;
	    if (number != null) {
	      try
	      {
	        returnValue = Integer.parseInt(number);
	      }
	      catch (NumberFormatException nfe) {}
	    }
	    return returnValue;
	  }
	  
	  public static long atol(String number, long defaultValue)
	  {
	    long returnValue = defaultValue;
	    if (number != null) {
	      try
	      {
	        returnValue = Long.parseLong(number);
	      }
	      catch (NumberFormatException nfe) {}
	    }
	    return returnValue;
	  }
	  
	  public static float atof(String number, float defaultValue)
	  {
	    float returnValue = defaultValue;
	    if (number != null) {
	      try
	      {
	        returnValue = Float.parseFloat(number);
	      }
	      catch (NumberFormatException nfe) {}
	    }
	    return returnValue;
	  }
	  
	  public static double atod(String number, double defaultValue)
	  {
	    double returnValue = defaultValue;
	    if (number != null) {
	      try
	      {
	        returnValue = Double.parseDouble(number);
	      }
	      catch (NumberFormatException nfe) {}
	    }
	    return returnValue;
	  }
	  
	  public static boolean atob(String key, boolean defaultValue)
	  {
	    boolean returnValue = defaultValue;
	    if (key != null)
	    {
	      String sKey = key.trim().toLowerCase();
	      if (("yes".equals(sKey)) || ("on".equals(sKey)) || ("true".equals(sKey)) || ("1".equals(sKey))) {
	        returnValue = true;
	      }
	      if (("no".equals(sKey)) || ("off".equals(sKey)) || ("false".equals(sKey)) || ("0".equals(sKey))) {
	        returnValue = false;
	      }
	    }
	    return returnValue;
	  }
	  
	  public static String str2dbStr(String str){
		  StringBuffer sb = new StringBuffer();
		  if(StringUtils.isNotBlank(str)){
			  sb.append(str.replace("'", "''").replace("\"", "").replace("\\", "\\\\"));
		  }else{
			  sb.append(" ");
		  }
		  return sb.toString();
	  }
}
