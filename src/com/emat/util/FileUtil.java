package com.emat.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.emat.log.SystemLog;

public class FileUtil {

	private static final Logger syslogger = Logger.getLogger(SystemLog.class);
	 
	public static void moveFileToDirectory(File file){
		
		String subDir = file.getParent().replace(CommonUtil.UPLOAD_FILEPATH, "");
	    String backupDir = CommonUtil.BACKUP_FILEPATH + subDir;
	    File backFileDir = new File(backupDir);
	    if(!backFileDir.exists()){
	    	if(!backFileDir.canWrite())
	    		backFileDir.setWritable(true);
	    	backFileDir.mkdirs();
	    }
	    
	    File backFile = new File(backupDir + CommonUtil.FILE_SEPARATOR + file.getName());

	    if (backFile.exists())
	    {
	      int i = 0;
	      String renameFile = backupDir + CommonUtil.FILE_SEPARATOR + file.getName() + "_" + i;
	      File newFile = new File(renameFile);
	      while (newFile.exists())
	      {
	        i++;
	        renameFile = backupDir + CommonUtil.FILE_SEPARATOR + file.getName() + "_" + i;
	        newFile = new File(renameFile);
	      }
	      backFile.renameTo(newFile);
	    }

	    try
	    {
	      FileUtils.moveFileToDirectory(file, backFileDir, true);
	      syslogger.info("move the file " + file.getAbsolutePath() + " is success");
	    } catch (IOException e) {
	      syslogger.info("move file error : " + e.getMessage());
	    }
	}
}
