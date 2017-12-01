 package com.emat.util;
 
 import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.emat.pojo.SolrInfo;
import com.emat.pojo.SystemInfo;
import com.emat.pojo.TimeInfo;
 
 public class CommonUtil
 {
   public static String FILE_SEPARATOR = System.getProperty("file.separator");
 
   public static String RUN_DIR = System.getProperty("user.dir").substring(0, System.getProperty("user.dir").lastIndexOf(FILE_SEPARATOR));
 
   public static String UPLOAD_FILEPATH = "";
 
   public static String BACKUP_FILEPATH = "";
   
   public static String IMAGE_SAVEPATH = "";
 
   public static TimeInfo TIME_INFO = null;
 
   public static SolrInfo SOLR_INFO = null;
   
   public static SystemInfo  systemInfo = null;
 
   public static boolean ISIMPORTING = false;
 
   public static int IMPORT_TOTAL_NUM = 0;
 
   public static int DELETE_TOTAL_NUM = 0;
 
   public static int IMPORT_EACHDB_NUM = 0;
 
   public static int DELETE_EACHDB_NUM = 0;
 
   public static int IMPORT_NUM = 0;
 
   public static int DELETE_NUM = 0;
 
   public static long CURRENT_TIME = System.currentTimeMillis();
 
   public static String OS_NAME = "";
   
   public static String DBNAME = "";
   
   public static Map<String, Map<String, String>> confMap = null;
   
   public static Map<String, String> dbMap;
   
   public static List<String> fieldList;
   
   public static boolean isEmpty(String str)
   {
     if (str == null) {
       return true;
     }
 
     return str.trim().equals("");
   }
 
   @SuppressWarnings("null")
   public static boolean isByRegex(String content, String regex)
   {
     boolean flag = false;
     if ((content != null) || (content.length() != 0))
     {
       Pattern pattern = Pattern.compile(regex, 34);
 
       Matcher matcher = pattern.matcher(content);
 
       if (matcher.find()) {
         flag = true;
       }
     }
 
     return flag;
   }
 
   public static boolean isIP(String ip)
   {
     boolean flag = false;
     String[] ips = ip.split("\\.");
     if (ips.length != 4)
     {
       return flag;
     }
     flag = isByRegex(ip, "^[1-2]?[0-9]?[0-9]\\.([1-2]?[0-9]?[0-9])|(0)\\.([1-2]?[0-9]?[0-9])|(0)\\.[1-2]?[0-9]?[0-9]$");
     return flag;
   }
 
   public static String getOSName()
   {
     Properties prop = System.getProperties();
     String os = prop.getProperty("os.name");
 
     return os.toLowerCase();
   }
   
 }

