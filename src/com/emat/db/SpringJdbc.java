 package com.emat.db;
 
 import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.emat.pojo.SystemInfo;
import com.mchange.v2.c3p0.ComboPooledDataSource;
 
 public class SpringJdbc
 {
   private static Logger logger = Logger.getLogger(SpringJdbc.class);
 
   public static JdbcTemplate jdbcTemplate = null;
 
   public static JdbcTemplate getJdbcTemplate(SystemInfo si)
   {
     if (jdbcTemplate != null) {
       return jdbcTemplate;
     }
 
     try
     {
       ComboPooledDataSource dataSource = new ComboPooledDataSource();
 
       dataSource.setDriverClass(si.getDriverClassName());
       dataSource.setJdbcUrl(si.getDbUrl());
       dataSource.setUser(si.getDbUser());
       dataSource.setPassword(si.getDbPass());
 
       dataSource.setMaxPoolSize(si.getMaxPoolSize());
 
       dataSource.setMinPoolSize(si.getMinPoolSize());
 
       dataSource.setMaxStatements(si.getMaxStatements());
       dataSource.setMaxIdleTime(si.getMaxIdleTime());
       dataSource.setCheckoutTimeout(si.getCheckoutTimeout());
 
       dataSource.setAutomaticTestTable("Test");
 
       dataSource.setTestConnectionOnCheckin(true);
 
       dataSource.setIdleConnectionTestPeriod(1800);
 
       JdbcTemplate jt = new JdbcTemplate();
       jt.setDataSource(dataSource);
 
       jdbcTemplate = jt;
 
       
       return jdbcTemplate;
     }
     catch (Exception e) {
       e.printStackTrace();
 
       logger.error("Err:DBCONN ERROR" + e.toString());
     }
     return null;
   }
 
 }

