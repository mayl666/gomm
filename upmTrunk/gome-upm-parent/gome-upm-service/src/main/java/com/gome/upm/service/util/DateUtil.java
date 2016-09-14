package com.gome.upm.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;

import org.springframework.util.StringUtils;


/**
 * 时间处理类
 * @author zhangzhixiang-ds
 *
 */
public class DateUtil {

	/**
	 * 
	 * @param time
	 * @param pattern   "yyyy-MM-dd-HH-mm-ss"
	 * @return
	 */
	public static String formatTime2Str(TemporalAccessor time, String pattern){
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
		return dateTimeFormatter.format(time);
	}
	
	/**
	 * string转date
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static Date Str2Date(String str, String pattern){
		if(StringUtils.isEmpty(pattern)){
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
	    SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
	    Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return date;
	}
	
	/**
	 * string转long
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static long getLongTimeFromStr(String str, String pattern){
		Date date = DateUtil.Str2Date(str, pattern);
		return date.getTime();
	}

	/**
	 * 获取当天开始时间
	 * @return
	 */
	public static long getLongTimeDayStart(){
		Calendar calendar = Calendar.getInstance();
	     calendar.set(Calendar.HOUR_OF_DAY,0);
	     calendar.set(Calendar.MINUTE,0);
	     calendar.set(Calendar.SECOND,0);
	     calendar.set(Calendar.MILLISECOND,0);
		return calendar.getTimeInMillis();
	}
	
	/**
	 * 获取当天开始时间戳
	 * @return
	 */
	public static long getLongTimeDayStart2(){
		LocalDateTime localDateTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
	    long time = localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
	    return time;
	}
	
	/**
	 * 获取一月之前开始时间戳
	 * @return
	 */
	public static long getLongTimeMonthBeforeStart2(){
		LocalDateTime localDateTime = LocalDateTime.now().minusDays(30).withHour(0).withMinute(0).withSecond(0).withNano(0);
	    long time = localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
	    return time;
	}
	
	/**
	 * 获取当天日期
	 * @return
	 */
	public static int getToDay(){
		LocalDate ld = LocalDate.now();
		return Integer.parseInt(ld.toString().replaceAll("-", ""));
	}
	
	/**
	 * long转string
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static String LongToStr(Long time, String pattern){
		Instant ins = Instant.ofEpochMilli(time);
		LocalDateTime ldt = LocalDateTime.ofInstant(ins, ZoneOffset.ofHours(8));
		return DateUtil.formatTime2Str(ldt, pattern);
	}
	
	/**
	 * 获取一月之前开始时间string
	 * @return
	 */
	public static String getMonthBefore(){
		LocalDate ld = LocalDate.now().minusDays(30);
		return ld.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+" 00:00:00";
	}
	
	/**
	 * 获取当天开始string
	 * @return
	 */
	public static String getDayBefore(){
		LocalDate ld = LocalDate.now();
		return ld.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+" 00:00:00";
	}
  
	
	public static void main(String[] args) {
		DateUtil.getLongTimeFromStr("2016-07-23 00:00:00", "yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.now();
		System.out.println(localDateTime.plusHours(-2).getHour());
		System.out.println(DateUtil.formatTime2Str(localDateTime.plusMinutes(-30), "yyyy-MM-dd-HH-mm-ss"));
		System.out.println(DateUtil.formatTime2Str(localDateTime.plusDays(-365), "yyyy-MM-dd-HH-mm-ss"));
		System.out.println(DateUtil.getLongTimeDayStart2()+"|"+DateUtil.getLongTimeDayStart());
		System.out.println(DateUtil.LongToStr(1473350940968l, "yyyy-MM-dd HH:mm:ss"));
		System.out.println(getLongTimeDayStart2());
		System.out.println(DateUtil.getLongTimeMonthBeforeStart2());
	}

}
