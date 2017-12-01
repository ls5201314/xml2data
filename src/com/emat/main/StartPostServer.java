 package com.emat.main;
 
 import java.io.File;

import org.apache.log4j.PropertyConfigurator;

import com.emat.util.CommonUtil;
 
 public class StartPostServer
 {
   public static void main(String[] args)
   {
     ConfigFile config = new ConfigFile();
     config.init();
 
     String uploadPath = CommonUtil.UPLOAD_FILEPATH;
     File file = new File(uploadPath);
     String log4j_path = CommonUtil.RUN_DIR + CommonUtil.FILE_SEPARATOR + "conf" + CommonUtil.FILE_SEPARATOR + "log4j.properties";
     PropertyConfigurator.configure(log4j_path);
 
     if (!file.exists())
     {
    	 if(!file.canWrite())
    		 file.setWritable(true);
       file.mkdirs();
     }
     File backupFile = new File(CommonUtil.BACKUP_FILEPATH);
     if (!backupFile.exists())
     {
    	 if(!backupFile.canWrite())
    		 backupFile.setWritable(true);
       backupFile.mkdir();
     }
     
     File[] docList = file.listFiles();
     for (File doc : docList)
     {
       if (!doc.getName().toLowerCase().endsWith(".xml"))continue;
       System.out.println("开始转换文件：" + doc.getName());
       File desFile = new File(CommonUtil.BACKUP_FILEPATH + File.separator + doc.getName().replace(".xml", ".data"));
       xml2data.parseXml2Data(doc, desFile);
     }
     System.out.println("转换完成。。。");     
 
   }
   
 }

