 package com.emat.main;
 
 import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.emat.log.SystemLog;
import com.emat.pojo.SystemInfo;
import com.emat.util.CommonUtil;
import com.emat.util.XmlUtil;
import com.emat.xml.XmlEntity;
 
 public class ConfigFile
 {
   private String filePath = "";
   private static final Logger syslogger = Logger.getLogger(SystemLog.class);
   
   public void init()
   {
     this.filePath = (CommonUtil.RUN_DIR + CommonUtil.FILE_SEPARATOR + "conf" + CommonUtil.FILE_SEPARATOR + "config.properties");
     readConf(this.filePath);
     
     String  uploadFilepath = ((Map<String, String>)CommonUtil.confMap.get("FILEPATH")).get("upload.filepath");
     String  backupFilepath = ((Map<String, String>)CommonUtil.confMap.get("FILEPATH")).get("backup.filepath");
     if(StringUtils.isNotEmpty(uploadFilepath) && uploadFilepath.endsWith(CommonUtil.FILE_SEPARATOR))uploadFilepath = uploadFilepath.substring(0, uploadFilepath.length()-1);
     if(StringUtils.isNotEmpty(backupFilepath) && backupFilepath.endsWith(CommonUtil.FILE_SEPARATOR))backupFilepath = backupFilepath.substring(0, backupFilepath.length()-1);
     CommonUtil.UPLOAD_FILEPATH = setWithDefaultValue(uploadFilepath, "");
     CommonUtil.BACKUP_FILEPATH = setWithDefaultValue(backupFilepath, "");
   
     SystemInfo systemInfo = new SystemInfo();
     /*systemInfo.setDatabase(setWithDefaultValue((String)((Map<String, String>)CommonUtil.confMap.get("JDBC")).get("database.database"), ""));
     systemInfo.setDbUser(setWithDefaultValue((String)((Map<String, String>)CommonUtil.confMap.get("JDBC")).get("database.username"), ""));
     systemInfo.setDbUrl(setWithDefaultValue((String)((Map<String, String>)CommonUtil.confMap.get("JDBC")).get("database.url"), ""));
     systemInfo.setDbPass(setWithDefaultValue((String)((Map<String, String>)CommonUtil.confMap.get("JDBC")).get("database.password"), ""));
     systemInfo.setDriverClassName(setWithDefaultValue((String)((Map<String, String>)CommonUtil.confMap.get("JDBC")).get("database.driverclassname"), ""));
   
     systemInfo.setMaxPoolSize(NumberUtils.toInt(((Map<String, String>)CommonUtil.confMap.get("JDBC")).get("cpool.maxPoolSize"), 20));
     systemInfo.setMinPoolSize(NumberUtils.toInt(((Map<String, String>)CommonUtil.confMap.get("JDBC")).get("cpool.minPoolSize"), 5));
     systemInfo.setMaxStatements(NumberUtils.toInt(((Map<String, String>)CommonUtil.confMap.get("JDBC")).get("cpool.maxStatements"), 100));
     systemInfo.setMaxIdleTime(NumberUtils.toInt(((Map<String, String>)CommonUtil.confMap.get("JDBC")).get("cpool.maxIdleTime"), 1000));
     systemInfo.setCheckoutTimeout(NumberUtils.toInt(((Map<String, String>)CommonUtil.confMap.get("JDBC")).get("cpool.checkoutTimeout"), 30000));
  */
     CommonUtil.systemInfo = systemInfo;
    
   }
   
   private boolean existConfigFile(String filePath)
   {
     File file = new File(filePath);
     if (!file.exists()) {
       return false;
     }
     return file.canRead();
   }
 
   public static String setWithDefaultValue(String value, String defaultValue)
   {
     if (CommonUtil.isEmpty(value))
     {
       return defaultValue;
     }
 
     return value;
   }
 
   @SuppressWarnings("resource")
   public void readConf(String filePath)
   {
     if (existConfigFile(filePath))
     {
       Map<String, Map<String, String>> confMap = new HashMap<>();
       try
       {
         BufferedReader bufferedReader = new BufferedReader(
           new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
         String strLine = "";
         String section = "";
         HashMap<String, String> sectionMap = null;
         while ((strLine = bufferedReader.readLine()) != null) {
           strLine = strLine.trim();
 
           if ((strLine.startsWith("#")) || (strLine.startsWith("//")))
           {
             continue;
           }
           if ((strLine.startsWith("[")) && (strLine.endsWith("]")))
           {
             if ((sectionMap != null) && (!CommonUtil.isEmpty(section)))
             {
               confMap.put(section.toUpperCase(), sectionMap);
             }
             section = strLine.substring(1, strLine.length() - 1);
             sectionMap = new HashMap<>();
           }
           else
           {
             if (sectionMap == null)
               continue;
             String[] key_value = strLine.split("=");
             if (key_value.length == 2)
             {
            	 sectionMap.put(key_value[0].trim().toLowerCase(), key_value[1].trim());
             }
             else if (key_value.length > 2)
             {
                 sectionMap.put(key_value[0].trim().toLowerCase(), strLine.substring(strLine.indexOf("=") + 1).trim());
             } else if (key_value.length == 1){
                 sectionMap.put(key_value[0].trim().toLowerCase(), "");
             }
           }
         }
 
         confMap.put(section.toUpperCase(), sectionMap);
         CommonUtil.confMap = confMap;
       }
       catch (Exception localException)
       {
       }
     }else{
    	 throw new RuntimeException(filePath + "配置文件不存在！");
     }
   }
 
   public static void main(String[] args)
   {
     ConfigFile config = new ConfigFile();
     config.init();
 
     for (String section : CommonUtil.confMap.keySet())
     {
       System.out.println("section: " + section);
       Map<String, String> map = (Map<String, String>)CommonUtil.confMap.get(section);
       for (String properties : map.keySet())
       {
         System.out.println(properties + "=" + (String)map.get(properties));
       }
     }
   }
 }

