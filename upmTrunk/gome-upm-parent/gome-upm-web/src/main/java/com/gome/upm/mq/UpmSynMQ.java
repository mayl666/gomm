package com.gome.upm.mq;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.gome.rocketmq.client.extension.MessageConsumer;
import com.gome.upm.common.util.AppConfigUtil;
import com.gome.upm.common.web.httpClient.HttpClientUtils;
import com.gome.upm.dao.AlarmRangeMapper;
import com.gome.upm.dao.MoSynDAO;
import com.gome.upm.domain.AlarmRange;
import com.gome.upm.domain.MoSynBO;
import com.gome.upm.service.util.DBContextHolder;

public class UpmSynMQ implements MessageConsumer {
	Map<String, Check> map = new HashMap<String, Check>();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private AlarmRangeMapper alarmRangeMapper;
	private static String business ="business";
	
	private static  List<AlarmRange> rangeList;
	class Check {
		public Check(String name, String type, Integer count) {
			this.name = name;
			this.type = type;
			this.count = count;
		}
		String name;
		String type;
		Integer count;
	}

	public UpmSynMQ() {
		map.put("DRAGON-z-OD", new Check("DRAGON 正向单停在OD的订单", "dragon", 50));
		map.put("DRAGON-n-OD", new Check("DRAGON 逆向单停在OD的订单", "dragon", 50));
		map.put("OMS-DRG-z", new Check("OMS-DRG正向订单状态差异", "oms", 50));
		map.put("OMS-DRG-n", new Check("OMS-DRG逆向订单状态差异", "oms", 50));
		map.put("OMS-POP-z", new Check("OMS-POP正向订单状态差异", "oms", 50));
		map.put("OMS-POP-n", new Check("OMS-POP逆向订单状态差异", "oms", 50));
		map.put("Z-CO-G3PP", new Check("正向单停在CO的订单--G3PP返回状态不正确", "forward", 10));
		map.put("Z-CO-SO-R-DRG", new Check("正向单停在CO的订单--已发送SO至DRG", "forward", 10));
		map.put("Z-CO-SO-R-POP", new Check("正向单停在CO的订单--已发送SO至POP", "forward", 10));
		map.put("Z-CO-SO-kf", new Check("正向单停在CO的订单--待客服处理", "forward", 10));
		map.put("Z-CO-SO-N-DRG", new Check("正向单停在CO的订单--未发SO至DRG", "forward", 10));
		map.put("Z-CO-SO-N-POP", new Check("正向单停在CO的订单--未发SO至POP", "forward", 10));
	}

	@Autowired
	private MoSynDAO moSynDAO;

	public MoSynDAO getMoSynDAO() {
		return moSynDAO;
	}

	public void setMoSynDAO(MoSynDAO moSynDAO) {
		this.moSynDAO = moSynDAO;
	}
	@Override
	public void consume(Serializable messageObject) throws Exception {
		System.out.println("#######################MQ接收到了数据#################################");
		DBContextHolder.setDataSource("dataSourceOne");
		AlarmRange a = new AlarmRange();
		a.setBusinessType("business");
		rangeList =alarmRangeMapper.selectByBusinessType(a);
		List<UpmMessage> wwww = (List<UpmMessage>) JSON.parseArray(messageObject.toString(), UpmMessage.class);
		for (UpmMessage u11 : wwww) {
			String startTimeStr = u11.getDate().split(" ")[0];
			MoSynBO searchBo = new MoSynBO();
			searchBo.setSynTime(startTimeStr);
			searchBo.setType(u11.getType());
			List<MoSynBO> list = moSynDAO.searchMoSynList(searchBo);
			if (list.size() > 0) {
				MoSynBO moSynBO = list.get(0);
				// DRAGON 正向单停在OD的订单
				moSynBO.setReTime(formatter.parse(u11.getDate()));
				moSynBO.setCount(u11.getCount());
				moSynDAO.updateMoSyn(moSynBO);
				// 判断是否发送邮件
				if (map.get(moSynBO.getType()) != null) {
					Check check = map.get(moSynBO.getType());
					if(u11.getCount()>check.count){
						sendMessage(check.name, check.type, u11.getCount());
					}
				}
			}else{
				MoSynBO moSynBO = new MoSynBO();
				moSynBO.setSynTime(startTimeStr);
				moSynBO.setType(u11.getType());
				moSynBO.setName(u11.getName());
				// DRAGON 正向单停在OD的订单
				moSynBO.setReTime(formatter.parse(u11.getDate()));
				moSynBO.setCount(u11.getCount());
				moSynDAO.saveMoSyn(moSynBO);
				// 判断是否发送邮件
				if (map.get(moSynBO.getType()) != null) {
					Check check = map.get(moSynBO.getType());
					if(u11.getCount()>check.count){
						sendMessage(check.name, check.type, u11.getCount());
					}
				}
			}
		}
	}
	void sendMessage(String subject, String type, Long count) {
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
		}
	}
}
