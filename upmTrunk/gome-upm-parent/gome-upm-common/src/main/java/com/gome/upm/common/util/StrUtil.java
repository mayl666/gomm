package com.gome.upm.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StrUtil {

	
	/**
	 * 根据输入的字符串和指定的字符串截取内容，并获取指定的部分
	 * @param target
	 * @param code
	 * @param index
	 * @return
	 */
	public static String splitStr(String target,String code,int index){
		String split[] = target.split(code);
		String string = "";
		try {
			string = split[index];
		} catch (Exception e) {
			string = split[0];
		}
		
		return string;
	}
	
	/**
	 * 根据传入的时间和格式进行格式化
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDateToTime(Date date,String pattern){
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String format = sdf.format(date);
		return format;
	} 
	
	
	/**
	 * 根据传入的时间字符串和格式进行解析
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static Date parseTimeToDate(String time,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			date = new Date();
		}
		return date;
	}  
	
	/**
	 * 获取指定日期那天的开始时间   第一种方法
	 * @param date
	 * @return
	 */
	public static Date getOneDayStart(Date date){
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    return calendar.getTime();
	}
	
	/**
	 * 获取指定日期那天的结束时间    第一种方法
	 * @param date
	 * @return
	 */
	public static Date getOneDayEnd(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.SECOND, -1);
		return calendar.getTime();
	}
	
	/**
	 * 获取指定日期那天的开始时间   另一种方法
	 * @param date
	 * @return
	 */
	public static Date getOneDaySartAother(Date date){
		SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
	    SimpleDateFormat formater2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date start = null;
		try {
			start = formater2.parse(formater.format(new Date())+ " 00:00:00");
		} catch (ParseException e) {
		}
		
	    return start;
	}
	
	/**
	 * 获取指定日期那天的结束时间   另一种方法
	 * @param date
	 * @return
	 */
	public static Date getOneDayEndAnother(Date date){
		SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
	    SimpleDateFormat formater2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date end = null;
		try {
			end = formater2.parse(formater.format(new Date())+ " 23:59:59");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return end;
	}
	
}
