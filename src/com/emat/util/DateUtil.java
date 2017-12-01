package com.emat.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 日期相关的操作
 */

public class DateUtil {
    
	public final static String yearPattern = "yyyy";
    public final static String monthPattern = "yyyy-MM";
    public final static String datePattern = "yyyy-MM-dd";
    public final static String timePattern = "yyyy-MM-dd HH:mm:ss";

    private DateUtil() {
    }

    /**
     * 将一个字符串转换成日期格式
     * 
     * @param date
     * @param pattern
     * @return
     */
    public static Date toDate(String date, String pattern) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        if (pattern == null) {
            pattern = datePattern;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date newDate = new Date();
        try {
            newDate = sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDate;
    }
    
    public static String toTime(String date, String pattern) {
        Date d = toDate(date, pattern);
        Long time = d.getTime() / 1000;
        return time.toString();
    }
    
    

    /**
     * 把日期转换成字符串型
     * 
     * @param date
     * @param pattern
     * @return
     */
    public static String toString(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        if (pattern == null) {
            pattern = datePattern;
        }
        String dateString = "";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            dateString = sdf.format(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dateString;
    }

    /**
     * 把时间转换成字符串型
     * 
     * @param time
     * @param pattern
     * @return
     */
    public static String toString(Long time, String pattern) {
        if (time > 0) {
            if (time.toString().length() == 10) {
                time = time * 1000;
            }
            Date date = new Date(time);
            String str = DateUtil.toString(date, pattern);
            return str;
        }
        return "";
    }

    /**
     * 获取前n天或者后n天的时间
     * @param day
     * @return
     */
    public static Date getBeforeOrAfterDay(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return new Date(calendar.getTimeInMillis());
    }
    
	/**
     *将字符串格式fromPattern的字符串转为日期，格式toPattern
	 *
	 * @param date 日期字符串
	 * @return 返回格式化的日期
	 * @throws ParseException 分析时意外地出现了错误异常
	 */	    
	public static String strToDateFormat(String date, String fromPattern, String toPattern){
	       SimpleDateFormat formatter = new SimpleDateFormat(fromPattern);
	       formatter.setLenient(false);
	       Date newDate = new Date();
	       try {
	    	   newDate = formatter.parse(date);
	       } catch (ParseException e) {
	    	   e.printStackTrace();
	       }
	       formatter = new SimpleDateFormat(toPattern);
	       return formatter.format(newDate);
	}
	
}
