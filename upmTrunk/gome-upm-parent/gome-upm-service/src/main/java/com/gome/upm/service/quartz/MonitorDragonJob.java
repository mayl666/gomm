package com.gome.upm.service.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.gome.upm.common.util.AppConfigUtil;
import com.gome.upm.common.web.httpClient.HttpClientUtils;
import com.gome.upm.dao.AlarmRangeMapper;
import com.gome.upm.dao.MoMonitorMapper;
import com.gome.upm.domain.AlarmRange;
import com.gome.upm.service.util.DBContextHolder;

public class MonitorDragonJob {
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private AlarmRangeMapper alarmRangeMapper;
	private static String business ="business";
	
	private static  List<AlarmRange> rangeList;
	@Resource
	private MoMonitorMapper moMonitorMapper;
	public void work() {
		DBContextHolder.setDataSource("dataSourceOne");
		AlarmRange a = new AlarmRange();
		a.setBusinessType(business);
		rangeList =alarmRangeMapper.selectByBusinessType(a);
		DBContextHolder.setDataSource("dataSourceThree");
		try{
			int g3pp_realy_na = moMonitorMapper.g3pp_realy_na_count();
			
			sendMessage("G3PP真预留状态大量停在NA","dregon",g3pp_realy_na);
	
			int g3pp_realy_dh = moMonitorMapper.g3pp_realy_dh_count();
			
			sendMessage("G3PP真预留状态大量停在DH","dregon",g3pp_realy_dh);
	
			int g3pp_order_pr = moMonitorMapper.g3pp_order_pr_count();
			
			sendMessage("G3PP订单状态大量停在PR","dregon",g3pp_order_pr);
	
			int g3pp_order_dh = moMonitorMapper.g3pp_order_dh_count();
			
			sendMessage("G3PP订单状态大量停在DH","dregon",g3pp_order_dh);
	
			int g3pp_order_cco = moMonitorMapper.g3pp_order_cco_count();
			
			sendMessage("G3PP订单状态大量停在CCO","dregon",g3pp_order_cco);
	
			int smi_order_pr = moMonitorMapper.smi_order_pr_count();
			
			sendMessage("SMI状态大量停在PR","dregon",smi_order_pr);
	
			int order_od = moMonitorMapper.order_od_count();
			
			sendMessage("订单状态大量停留在OD","dragon",order_od);
		}catch(Exception e){
			
		}
	}
	void sendMessage(String subject, String type, Integer count) {
//		String url = "http://10.58.62.204/alarmplatform/alarm";
		Map<String,Integer> map = new HashMap<String,Integer>();
		int setCount = 0;
		for(AlarmRange alarmRange:rangeList){
			if(alarmRange.getType().equals(subject)){
				map.put(String.valueOf(alarmRange.getLevel()), Integer.valueOf(alarmRange.getValue()));
				setCount++;
			}
			//只有三级
			if(setCount>2){
				break;
			}
		}
		int count1=0;
		int count2=0;
		int count3=0;
		String jb="一级";
		int alarmLevel = 3;
		if(map.get("1")!=null){
			count1=map.get("1");
		}
		if(map.get("2")!=null){
			count2=map.get("2");
		}
		if(map.get("3")!=null){
			count3=map.get("3");
		}
		if(count1!=0&& count>count1){
			jb="一级";
			alarmLevel=1;
		}else if(count2!=0&& count>count2){
			jb="二级";
			alarmLevel=2;
		}else if(count3!=0&& count>count3){
			jb="三级";
			alarmLevel=3;
		}else{
			return;
		}
		String url = AppConfigUtil.getStringValue("prtg.alarm.url");
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("type", type);
		paramMap.put("mail", "liuyuqiang@yolo24.com");
		paramMap.put("subject", "业务报警");
		StringBuffer sb = new StringBuffer();
		sb.append("监控组，您好！");
		sb.append("</br></br>订单  \"<font color='#FF0000'>" + subject + "</font>\"  出现异常,请及时处理,谢谢.</br></br>");
		sb.append("报警时间  : " + formatter.format(new Date()) + "</br></br>");
		sb.append("订单类型  : <font color='#FF0000'>" + subject + "</font></br></br>");
		sb.append("描述  : <font color='#FF0000'>" + subject + "量为" + count + "单</font></br></br>");
		sb.append("报警级别  : <font color='#FF0000'>" + jb + "</font>");
		paramMap.put("content", sb.toString());
		paramMap.put("id", "0");
		paramMap.put("level", alarmLevel+"");
		try {
			String result = HttpClientUtils.post(url, paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
