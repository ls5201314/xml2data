package com.emat.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import com.emat.db.SpringJdbc;

public class DbUtil {

	public static void data2Db(File file){
		JdbcTemplate jdbcTemplate = SpringJdbc.getJdbcTemplate(CommonUtil.systemInfo);
		BufferedReader in = null;
		String line = "";
		String[] lineArry = null;
		StringBuffer value = new StringBuffer();
		StringBuffer lines = new StringBuffer();
	    LinkedHashMap<String, String> docMap = new LinkedHashMap<String, String>();
	    List<LinkedHashMap<String, String>> dataList = new ArrayList<LinkedHashMap<String,String>>();
		String key = "";
		StringBuffer insertValue = new StringBuffer();
		StringBuffer insertFiled = new StringBuffer();
		try {
			  in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf8"));
			  while ((line = in.readLine()) != null)
			  {
				 if(StringUtils.isNotBlank(line))line = line.trim();
				 if(line.startsWith("<")){
					 lines = new StringBuffer();
				 }
				 if(StringUtils.isNotBlank(line))lines.append(line);
				 
				 if(line.startsWith("<REC>")){
					 dataList.add(docMap);
					 docMap = new LinkedHashMap<String, String>();
		         }else if(line.startsWith("<")){
					 if(StringUtils.isNotBlank(lines.toString())){
						 lineArry = lines.toString().split(">=");
			        	   key = lineArry[0].replace("<", "");
			        	   if(lineArry.length > 1){
			        		   value = new StringBuffer(lines.substring(key.length()+3, lines.length()));
			        	   }else if(lineArry.length == 1){
			        		   value = new StringBuffer();
			        	   }
			        	   docMap.put(key, value.toString());
					 }
		          }
				 
			  }
			  
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try{
	           if(null != in)in.close();
			} catch (IOException e) {
	    	  e.printStackTrace();
	        }
	 }
		String dbName = CommonUtil.DBNAME;
		for(String filed : CommonUtil.fieldList){
			insertFiled.append("`").append(filed).append("`").append(",");
		}
		insertFiled.append("`DBNAME`,`ID`");
		for(LinkedHashMap<String, String> map : dataList){
			insertValue.append("(");
			for(String filed : CommonUtil.fieldList){
				insertValue.append(StringUtil.str2dbStr(map.get(CommonUtil.dbMap.get(filed)))).append(",");
			}
			insertValue.append("'").append(dbName).append("',").append("'").append(UUIDUtil.getUUID()).append("'),");
		}
		if(insertValue.toString().endsWith(","))insertValue = new StringBuffer(insertValue.substring(0, insertValue.length()-1));
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ").append(dbName).append("(").append(insertFiled).append(")").append(" values ").append(insertValue.toString());
		//System.out.println(sql);
		try {
			jdbcTemplate.execute(sql.toString());
			System.out.println("导入数据库成功！");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("导入数据库失败！");
		}
	
	}
}
	     
