package com.gome.monitoringplatform;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gome.framework.util.JsonUtil;

public class TestMain {
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	static SimpleDateFormat formatter11 = new SimpleDateFormat("HH:mm:ss");
	public static void main(String[] args) throws ParseException {
////		Date startTime=new Date();
////		System.out.println(formatter.format(startTime));
////		//昨天
////		Date startYesterdayTime=new Date(startTime.getTime()-1000*60*60*24);
////		System.out.println(formatter.format(startYesterdayTime));
////		//一周前
////		Date startWeekTime=new Date(startTime.getTime()-1000*60*60*24*7);
////		System.out.println(formatter.format(startWeekTime));
//		Date startTime=formatter.parse("2016-06-29 00:00:00");
//		//结束时间
//		Date endTime=formatter.parse("2016-07-09 23:55:00");
//		Integer ttt=  (int) ((endTime.getTime()-startTime.getTime())/1000/60/60/24);
//		for(int i=0;i<=ttt;i++){
//			//当前时间
//			Date startTimeNow=new Date(startTime.getTime()+i*1000*60*60*24);
////			System.out.println("开始时间："+formatter.format(startTimeNow));
//			Date endTimeNow=formatter.parse(formatter.format(startTimeNow).split(" ")[0]+" 23:55:00");
////			System.out.println("结束时间："+formatter.format(endTimeNow));
//			System.out.println("----------------------------------");
//		}
////		System.out.println(ttt);
//		Date ddd=new Date((long)1466488064000.00);
//		System.out.println(formatter.format(ddd));
//		Object[] dddd=new Object[]{34,3465,3461,23,4324,2345,2345,23462,3};
//		System.out.println(JsonUtil.toJson(dddd));
//		List<Object[]> list=new ArrayList<Object[]>();
//		list.add(new Object[]{12312,23});
//		list.add(new Object[]{634,23});
//		String ttt=JsonUtil.toJson(list);
//		System.out.println(ttt);
//		sdfsd();
		
		String ww="2016-07-05 14:43:13";
		String[]  ll=ww.split(" ");
		String[]  ll1=ll[1].split(":");
		String ddd=ll[0]+" "+ll1[0]+":"+Integer.parseInt(ll1[1])/5*5+":00";
		Date endTime=formatter.parse(ddd);
		Date startTime=new Date(endTime.getTime()-1000*60*5);
		System.out.println("开始时间----"+formatter.format(startTime));
		System.out.println("结束时间----"+formatter.format(endTime));
		
		
	}
	public static void sdfsd() throws ParseException{
		Date startTime=formatter.parse("2016-07-01 00:00:00");
		System.out.println(startTime.getTime());
		//结束时间
		Date endTime=formatter.parse("2016-07-02 23:55:00");
		System.out.println(endTime.getTime());
		while(startTime.getTime()<endTime.getTime()){
			
		}
		
	}
	
}
