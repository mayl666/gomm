package com.gome.threshold.service.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.gome.threshold.domain.AlarmRecord;

public class AlarmRecordConvertUtils {
	
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 属性转换
	 * @param alarmRecordList
	 * @return
	 */
	public static List<AlarmRecord> propertyConvert(List<AlarmRecord> alarmRecordList){
		if(alarmRecordList != null && alarmRecordList.size() > 0){
			for (AlarmRecord alarmRecord : alarmRecordList) {
				if(alarmRecord.getSendTime() != null){
					alarmRecord.setSendTimeStr(df.format(alarmRecord.getSendTime()));
				}
				
			}
		}
		return alarmRecordList;
	}
	
}
