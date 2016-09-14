package com.gome.upm.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gome.upm.common.util.StrUtil;
import com.gome.upm.domain.AlarmRecord;
import com.gome.upm.service.AlarmRecordService;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class KafkaConsumer extends Thread {

	private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumer.class);
	
	private final ConsumerConnector consumer;  
    private final String topic;  
    
    @Resource
    private AlarmRecordService recordService;

      
    public KafkaConsumer(String topic) {  
        consumer =kafka.consumer.Consumer.createJavaConsumerConnector(createConsumerConfig());  
        this.topic =topic;  
    }  

private static ConsumerConfig createConsumerConfig() {  
    Properties props = new Properties();  
    props.put("auto.offset.reset", "smallest");
    // 设置zookeeper的链接地址
    props.put("zookeeper.connect","10.58.56.45:2181");  
    // 设置group id
    props.put("group.id", "test-consumer-group");  
    // kafka的group 消费记录是保存在zookeeper上的, 但这个信息在zookeeper上不是实时更新的, 需要有个间隔时间更新
    props.put("auto.commit.interval.ms", "1000");
    props.put("bootstrap.servers", "10.58.56.45:9092");
    props.put("zookeeper.session.timeout.ms","4000");  
    props.put("serializer.class", "kafka.serializer.StringEncoder");
    return new ConsumerConfig(props);  
}  

	public void run(){  
	     //设置Topic=>Thread Num映射关系, 构建具体的流
	    Map<String,Integer> topickMap = new HashMap<String, Integer>();  
	    topickMap.put(topic, 1);  
	    Map<String, List<KafkaStream<byte[],byte[]>>>  streamMap=consumer.createMessageStreams(topickMap);  
	 
	    KafkaStream<byte[],byte[]> stream = streamMap.get(topic).get(0);  
	    ConsumerIterator<byte[],byte[]> it =stream.iterator();  
	    System.out.println("*********KafkaConsumer Results********");  
	    while(it.hasNext() ){  
	    	byte[] message = it.next().message();
	    	String string = new String(message);
	    	if(string.length() > 0){
	    		//报警时间：20160904233323，1分钟内支付成功数：4，请确认系统是否正常
	    		AlarmRecord alarmRecord = new AlarmRecord();
	    		if(string.contains("请确认系统是否正常")){
	    			//报警时间：20160904233323，1分钟内支付成功数：4，请确认系统是否正常
	    			String str = StrUtil.splitStr(string, "，", 0);
	    			String time = StrUtil.splitStr(str, "：", 1);
	    			Date date;
	    			try {
						date = StrUtil.parseTimeToDate(time, "yyyyMMddhhmmss");
						String timeStr = StrUtil.formatDateToTime(date, "yyyy-MM-dd");
						timeStr = timeStr + "%";
						//alarmRecord.setTimeStr(timeStr);
						alarmRecord.setContent("确认系统是否正常");
						alarmRecord.setType("finance");
						System.out.println(date);
						List<AlarmRecord> alarmRecordList = recordService.findAlarmRecordListByConditions(alarmRecord);
						alarmRecord = new AlarmRecord();
						alarmRecord.setSendTime(date);
						alarmRecord.setType("finance");
						alarmRecord.setContent(string);
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
						recordService.insertAlarmRecord(alarmRecord);
					} catch (Exception e) {
						LOG.error("转换时间出错",e.getMessage());
					}
	    			System.out.println(time);
	    			
	    		}else if(string.contains("订单系统的支付成功订单")){
	    			//报警时间：20160908162323，未成功通知订单系统的支付成功订单达到：6
	    			
	    		}
	    	}
	        System.err.println("KafkaConsumer get data:" +new String(string));  
	        try {  
	            Thread.sleep(1000);  
	        } catch (InterruptedException e) {  
	        }  
	    }  
	}
	
	public static void main(String[] args) {  
    	/*KafkaConsumer consumerThread = new KafkaConsumer("mykafka");  
        consumerThread.start();*/
		String message = "报警时间：20160907120346，未成功通知订单系统的支付成功订单达到：6";
		String pay = StrUtil.splitStr(StrUtil.splitStr(message, "，", 1), "：", 0);
		//String confirm = StrUtil.splitStr(message, "，", 2);
		String num = StrUtil.splitStr(StrUtil.splitStr(message, "，", 1), "：", 1);
		String time = StrUtil.splitStr(StrUtil.splitStr(message, "，", 0), "：", 1);
		
		System.out.println(pay+":");
		//System.out.println(confirm);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		try {
			date = sdf.parse(time);
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String format = sd.format(date);
			System.out.println(date);
			
			Date start = StrUtil.getOneDayStart(date);
			Date end = StrUtil.getOneDayEnd(date);
			String startTime = StrUtil.formatDateToTime(start, "yyyy-MM-dd HH:mm:ss");
			String endTime = StrUtil.formatDateToTime(end, "yyyy-MM-dd HH:mm:ss");
			System.out.println(startTime);
			System.out.println(endTime);
			
		} catch (ParseException e) {
			LOG.error("转换时间出错",e.getMessage());
		}
		String alarmTime = StrUtil.formatDateToTime(date, "yyyy-MM-dd HH:mm:ss");
		String content = "监控组，您好！</br></br>金融系统出现异常，请及时处理</br></br>报警时间  : <font color='#FF0000'>"+alarmTime+"</font>"
				+ "</br></br>描述  : "+pay+":&nbsp:&nbsp<font color='#FF0000'>"+num+""
				+ "</font></br></br>报警级别  : <font color='#FF0000'>"+"三级"+"</font>";
		System.out.println(content);
		
    }
	
}
