package com.gome.upm.service.quartz;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import com.gome.upm.common.util.AppConfigUtil;
import com.gome.upm.common.web.httpClient.HttpClientUtils;
import com.gome.upm.dao.MoNetDeviceMapper;
import com.gome.upm.dao.MoNetSensorMapper;
import com.gome.upm.dao.MoNetSensorThresholdMapper;
import com.gome.upm.domain.prtg.MoNetDevice;
import com.gome.upm.domain.prtg.MoNetSensor;
import com.gome.upm.domain.prtg.MoNetSensorThreshold;
import com.gome.upm.domain.prtg.SensorChannel;
import com.gome.upm.service.NetWorkMonitorService;
import com.gome.upm.service.util.DateUtil;

public class NetWorkAlarm {
	// private Logger logger = Logger.getLogger(NetWorkAlarm.class);
	private static final Logger logger = LoggerFactory.getLogger(NetWorkAlarm.class);
	@Resource
	private NetWorkMonitorService netWorkMonitorService;
	@Resource
	private MoNetDeviceMapper moNetDeviceMapper;
	@Resource
	private MoNetSensorMapper moNetSensorMapper;
	@Resource
	private MoNetSensorThresholdMapper moNetSensorThresholdMapper;

	/**
	 * 6分钟后执行，频率10分钟
	 */
	@Scheduled(initialDelay=1000*360,fixedRate = 1000*600)
	//@Scheduled(initialDelay = 5000, fixedRate = 1000 * 30)
	public void work() {
		try {
			long start = System.currentTimeMillis();
			logger.info("网络监控报警start");

			this.alarmDevice();

			this.alarmSensor();

			long end = System.currentTimeMillis();
			logger.info("网络监控报警end,耗时(分钟):" + (end - start) / 60000);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void alarmDevice() {
		// 设备停机报警,获取停机状态设备
		MoNetDevice device = new MoNetDevice();
		device.setState(MoNetDevice.STOP_STATE);
		List<MoNetDevice> alarmDevicelist = moNetDeviceMapper.selectListCommon(device);
		for (MoNetDevice alarmDevice : alarmDevicelist) {
			alarmDevice.setState(MoNetDevice.ALARM_STATE);// 设置报警状态
			alarmDevice.setAlarmTime(new Date());
			alarmDevice.setAlarmContent("设备故障，下属传感器全部停机");
			// 发送报警信息
			String str = this.getDeviceAlarmContent(alarmDevice);
			boolean b = this.sendMessage("【网络报警】", str, "3");
			if(!b){
				continue;
			}
			// 修改信息
			moNetDeviceMapper.updateByPrimaryKeySelective(alarmDevice);
		}
	}

	public void alarmSensor() {
		// 传感器报警
		List<MoNetSensorThreshold> list = moNetSensorThresholdMapper.selectAll3();
		for (MoNetSensorThreshold sensorThre : list) {
			
			String alarmType = sensorThre.getAlarmType() == null ? "" : sensorThre.getAlarmType();//报警类型  overflow shutdown
			//停机故障处理
			if(sensorThre.getSensorState() == MoNetSensor.STOP_STATE){
				if(!alarmType.equals("shutDown")){
					String str = this.getSensorAlarmContent(sensorThre, "故障", "三级");
					boolean b = this.sendMessage("【网络报警】",str, "3");
					if(b){
						sensorThre.setState(MoNetSensorThreshold.ALARM_STATE);
						sensorThre.setAlarmTime(new Date());
						sensorThre.setAlarmType("shutDown");
						moNetSensorThresholdMapper.updateByPrimaryKeySelective(sensorThre);
					}
				}

				continue;
			}
			
			Double level1 = sensorThre.getLevel1() == null ? 0 : sensorThre.getLevel1() * 1024;//1级最高
			Double level2 = sensorThre.getLevel2() == null ? 0 : sensorThre.getLevel2() * 1024;//2级中等
			Double level3 = sensorThre.getLevel3() == null ? 0 : sensorThre.getLevel3() * 1024;//3级最低
			
			Integer alarmInlevel = sensorThre.getAlarmInLevel() == null ? 0 : sensorThre.getAlarmInLevel();//入站报警级别
			Integer alarmOutLevel = sensorThre.getAlarmOutLevel() == null ? 0 : sensorThre.getAlarmOutLevel();//出站报警级别
			//MoNetSensor sensor = moNetSensorMapper.selectByPrimaryKey(sensorThre.getSensorId());
			
			//Integer state = sensorThre.getState() == null ? 0 : sensorThre.getState();
			//double threshold = sensorThre.getSensorThreshold() * 1024;
			List<SensorChannel> channelList = netWorkMonitorService.getSensorChannels(sensorThre.getSensorId() + "",
					null, null, "flow");
			double outFlow = 0;
			double inFlow = 0;
			for (SensorChannel channel : channelList) {
				String flow = channel.getLastValueSpeed();
				if (StringUtils.isEmpty(flow) || flow.contains("-") | flow.contains("<")) {
					flow = "0";
				} else {
					flow = flow.split(" ")[0].replaceAll(",", "");
				}
				if (channel.getName().equals("出站通信量")) {
					outFlow = Double.parseDouble(flow);
					// 出站
				} else if (channel.getName().equals("入站通信量")) {
					// 入站
					inFlow = Double.parseDouble(flow);
				}
			}

			boolean flagIn = true;
			boolean flagOut = true;
			//出站处理
			if((outFlow > level3 && level3 > 0) || (outFlow > level2 && level2 > 0) || (outFlow > level1 && level1 > 0)){
				flagOut = false;
				if(outFlow > level1 && level1 > 0){
					//1级警报
					if(alarmOutLevel != MoNetSensorThreshold.ALARM_LEVEL1){
						this.createSensorAlarm("out", outFlow, "一级", 1, sensorThre);
					}
				}else if(outFlow > level2 && level2 > 0){
					//二级警报
					if(alarmOutLevel != MoNetSensorThreshold.ALARM_LEVEL1 && alarmOutLevel != MoNetSensorThreshold.ALARM_LEVEL2){
						this.createSensorAlarm("out", outFlow, "二级", 2, sensorThre);
					}
				}else if(outFlow > level3 && level3 > 0){
					//三级警报
					if(alarmOutLevel != MoNetSensorThreshold.ALARM_LEVEL1 && alarmOutLevel != MoNetSensorThreshold.ALARM_LEVEL2 && alarmOutLevel != MoNetSensorThreshold.ALARM_LEVEL3){
						this.createSensorAlarm("out", outFlow, "三级", 3, sensorThre);
					}
				}

			}
			
			//入站处理
			if((inFlow > level3 && level3 > 0) || (inFlow > level2 && level2 > 0) || (inFlow > level1 && level1 > 0)){
				flagIn = false;
				if(inFlow > level1 && level1 > 0){
					//1级警报
					if(alarmInlevel != MoNetSensorThreshold.ALARM_LEVEL1){
						this.createSensorAlarm("in", inFlow, "一级", 1, sensorThre);
					}
				}else if(inFlow > level2 && level2 > 0){
					//二级警报
					if(alarmInlevel != MoNetSensorThreshold.ALARM_LEVEL1 && alarmInlevel != MoNetSensorThreshold.ALARM_LEVEL2){
						this.createSensorAlarm("in", inFlow, "二级", 2, sensorThre);
					}
				}else if(inFlow > level3 && level3 > 0){
					//三级警报
					if(alarmInlevel != MoNetSensorThreshold.ALARM_LEVEL1 && alarmInlevel != MoNetSensorThreshold.ALARM_LEVEL2 && alarmInlevel != MoNetSensorThreshold.ALARM_LEVEL3){
						this.createSensorAlarm("in", inFlow, "三级", 3, sensorThre);
					}
				}
			}
			
			
			
			if(flagIn || flagOut){
				// 正常,重置报警
				if(sensorThre.getState() != MoNetSensorThreshold.NORMAL_STATE){
					if(flagIn){
						sensorThre.setAlarmInLevel(MoNetSensorThreshold.ALARM_LEVEL0);	
					}
					if(flagOut){
						sensorThre.setAlarmOutLevel(MoNetSensorThreshold.ALARM_LEVEL0);
					}
					if(flagIn && flagOut){
						sensorThre.setState(MoNetSensorThreshold.NORMAL_STATE);
						sensorThre.setAlarmTime(null);
						sensorThre.setAlarmType(null);
					}
					moNetSensorThresholdMapper.updateByPrimaryKeySelective(sensorThre);
				}
			}


		}

	}
	

	private boolean sendMessage(String subject, String content, String level) {
		boolean b = false;
		String url = AppConfigUtil.getStringValue("prtg.alarm.url");
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("mail", "zhangzhixiang-ds@yolo24.com");
		paramMap.put("type", "network");
		paramMap.put("subject", subject);
		paramMap.put("content", content);
		paramMap.put("level", level);
		try {
			logger.info("发送报警邮件开始,content:" + content);
			String result = HttpClientUtils.post(url, paramMap);
			logger.info("result:" + result);
			if (result != null && result.contains("ok")) {
				b = true;
				logger.info("报警邮件发送成功");
			} else {
				logger.info("报警邮件发送失败");
			}

			logger.info("发送报警邮件结束");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return b;
	}
	
	
	public String getDeviceAlarmContent(MoNetDevice device){
		StringBuffer buffer = new StringBuffer();
		LocalDateTime time = LocalDateTime.now();
		buffer.append("监控组，您好！</br></br>");
		buffer.append("设备 “<font color='red'>"+device.getDeviceName()+"</font>”出现异常，请及时处理，谢谢。</br></br>");
		buffer.append("报警时间："+DateUtil.formatTime2Str(time, "yyyy-MM-dd HH:mm:ss")+"</br></br>");
		buffer.append("所属IDC："+device.getGroupName()+"</br></br>");
		buffer.append("设备：<font color='red'>"+device.getDeviceName()+"</font></br></br>");
		buffer.append("状态：<font color='red'>故障</font></br></br>");
		buffer.append("级别：<font color='red'>三级</font></br></br>");
		return buffer.toString();
	}
	
	
	public String getSensorAlarmContent(MoNetSensorThreshold sensorThre, String content, String level){
		String sensorName = sensorThre.getAliaName();
		if(StringUtils.isEmpty(sensorName)){
			sensorName = sensorThre.getSensorName();
		}
		StringBuffer buffer = new StringBuffer();
		LocalDateTime time = LocalDateTime.now();
		buffer.append("监控组，您好！</br></br>");
		buffer.append("端口 “<font color='red'>"+sensorName+"</font>”出现异常，请及时处理，谢谢。</br></br>");
		buffer.append("报警时间："+DateUtil.formatTime2Str(time, "yyyy-MM-dd HH:mm:ss")+"</br></br>");
		buffer.append("所属IDC："+sensorThre.getGroupName()+"</br></br>");
		buffer.append("设备："+sensorThre.getDeviceName()+"</br></br>");
		buffer.append("端口：<font color='red'>"+sensorName+"</font></br></br>");
		buffer.append("状态/描述：<font color='red'>"+content+"</font></br></br>");
		buffer.append("级别：<font color='red'>"+level+"</font></br></br>");
		return buffer.toString();
	}
	
    
	public void createSensorAlarm(String type, double flow, String alarmLevel, Integer emailLevel, MoNetSensorThreshold sensorThre){
		DecimalFormat df = new DecimalFormat("0.00");
		String alarmContent = "";
		if(type.equals("out")){
			alarmContent =  "端口出站流量超出阈值当前为"+df.format(flow/1024)+"MB";
		}
		if(type.equals("in")){
			alarmContent =  "端口入站流量超出阈值当前为"+df.format(flow/1024)+"MB";
		}
		String str = this.getSensorAlarmContent(sensorThre, alarmContent, alarmLevel);
		boolean b = this.sendMessage("【网络报警】", str, emailLevel+"");
		if(b){
			sensorThre.setState(MoNetSensorThreshold.ALARM_STATE);
			sensorThre.setAlarmTime(new Date());
			sensorThre.setAlarmType("overflow");
			if(type.equals("out")){
				sensorThre.setAlarmOutLevel(emailLevel);
			}
			if(type.equals("in")){
				sensorThre.setAlarmInLevel(emailLevel);
			}
			moNetSensorThresholdMapper.updateByPrimaryKeySelective(sensorThre);
		}
	}

}
