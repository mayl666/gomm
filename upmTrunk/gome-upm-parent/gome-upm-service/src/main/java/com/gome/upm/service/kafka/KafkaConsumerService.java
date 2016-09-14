package com.gome.upm.service.kafka;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gome.upm.common.util.StrUtil;
import com.gome.upm.domain.AlarmRecord;
import com.gome.upm.service.AlarmRecordService;

@Service
public class KafkaConsumerService {

	private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerService.class); 
	
	@Resource
    private AlarmRecordService recordService;
	
	public void consumeMessage(String message){
		
		if(message.length() > 0){
    		//报警时间：20160904233323，1分钟内支付成功数：4，请确认系统是否正常
    		AlarmRecord alarmRecord = new AlarmRecord();
    		if(message.contains("请确认系统是否正常")){
    			//报警时间：20160904233323，1分钟内支付成功数：4，请确认系统是否正常
    			String pay = StrUtil.splitStr(StrUtil.splitStr(message, "，", 1), "：", 0);
    			String confirm = StrUtil.splitStr(message, "，", 2);
    			String num = StrUtil.splitStr(StrUtil.splitStr(message, "，", 1), "：", 1);
    			String time = StrUtil.splitStr(StrUtil.splitStr(message, "，", 0), "：", 1);
    			Date date;
    			try {
					date = StrUtil.parseTimeToDate(time, "yyyyMMddHHmmss");
					String alarmTime = StrUtil.formatDateToTime(date, "yyyy-MM-dd HH:mm:ss");
					Date start = StrUtil.getOneDayStart(date);
					Date end = StrUtil.getOneDayEnd(date);
					String startTime = StrUtil.formatDateToTime(start, "yyyy-MM-dd HH:mm:ss");
					String endTime = StrUtil.formatDateToTime(end, "yyyy-MM-dd HH:mm:ss");
					alarmRecord.setStartTime(startTime);
					alarmRecord.setEndTime(endTime);
					alarmRecord.setContent("确认系统是否正常");
					alarmRecord.setType("finance");
					System.out.println(date);
					List<AlarmRecord> alarmRecordList = recordService.findAlarmRecordListByConditions(alarmRecord);
					alarmRecord = new AlarmRecord();
					alarmRecord.setSendTime(date);
					alarmRecord.setType("finance");
					if(alarmRecordList.size() <= 0){
						//报警级别设置为三级
						alarmRecord.setLevel(3);
					}else if(alarmRecordList.size() == 1){
						//报警级别设置为二级
						alarmRecord.setLevel(2);
					}else{
						//报警记录设置为一级
						alarmRecord.setLevel(1);
					}
					//监控组，您好！</br></br>报警时间  : 2016-09-04 23:33:23</br></br>描述  : 1分钟内支付成功数&nbsp:&nbsp<font color='#FF0000'>4</font>&nbsp&nbsp&nbsp请确认系统是否正常</br></br>报警级别  : <font color='#FF0000'>三级</font>
					String content = "监控组，您好！</br></br>金融系统出现异常，请及时处理</br></br>报警时间  : <font color='#FF0000'>"+alarmTime+"</font>"
									+ "</br></br>描述  : "+pay+"&nbsp:&nbsp<font color='#FF0000'>"+num+""
									+ "</font>，&nbsp&nbsp&nbsp"+confirm+"</br></br>报警级别  : <font color='#FF0000'>"+alarmRecord.getLevelStr()+"</font>";
					
					alarmRecord.setContent(content);
					recordService.insertAlarmRecord(alarmRecord);
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}
    			System.out.println(time);
    			
    		}else if(message.contains("订单系统的支付成功订单达到")){
    			//报警时间：20160908162323，未成功通知订单系统的支付成功订单达到：6
    			String pay = StrUtil.splitStr(StrUtil.splitStr(message, "，", 1), "：", 0);
    			/*String confirm = StrUtil.splitStr(message, "，", 2);*/
    			String num = StrUtil.splitStr(StrUtil.splitStr(message, "，", 1), "：", 1);
    			String time = StrUtil.splitStr(StrUtil.splitStr(message, "，", 0), "：", 1);
    			Date date;
    			try {
					date = StrUtil.parseTimeToDate(time, "yyyyMMddHHmmss");
					String alarmTime = StrUtil.formatDateToTime(date, "yyyy-MM-dd HH:mm:ss");
					Date start = StrUtil.getOneDayStart(date);
					Date end = StrUtil.getOneDayEnd(date);
					String startTime = StrUtil.formatDateToTime(start, "yyyy-MM-dd HH:mm:ss");
					String endTime = StrUtil.formatDateToTime(end, "yyyy-MM-dd HH:mm:ss");
					alarmRecord.setStartTime(startTime);
					alarmRecord.setEndTime(endTime);
					alarmRecord.setContent("订单系统的支付成功订单达到");
					alarmRecord.setType("finance");
					System.out.println(date);
					List<AlarmRecord> alarmRecordList = recordService.findAlarmRecordListByConditions(alarmRecord);
					alarmRecord = new AlarmRecord();
					alarmRecord.setSendTime(date);
					alarmRecord.setType("finance");
					if(alarmRecordList.size() <= 0){
						//报警级别设置为三级
						alarmRecord.setLevel(3);
					}else if(alarmRecordList.size() == 1){
						//报警级别设置为二级
						alarmRecord.setLevel(2);
					}else{
						//报警记录设置为一级
						alarmRecord.setLevel(1);
					}
					//监控组，您好！</br></br>报警时间  : 2016-09-04 23:33:23</br></br>描述  : 1分钟内支付成功数&nbsp:&nbsp<font color='#FF0000'>4</font>&nbsp&nbsp&nbsp请确认系统是否正常</br></br>报警级别  : <font color='#FF0000'>三级</font>
					String content = "监控组，您好！</br></br>金融系统出现异常，请及时处理</br></br>报警时间  : <font color='#FF0000'>"+alarmTime+"</font>"
									+ "</br></br>描述  : "+pay+"&nbsp:&nbsp<font color='#FF0000'>"+num+""
									+ "</font></br></br>报警级别  : <font color='#FF0000'>"+alarmRecord.getLevelStr()+"</font>";
					
					alarmRecord.setContent(content);
					recordService.insertAlarmRecord(alarmRecord);
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}
    		}
    		
    	}
		
	}
	
}
