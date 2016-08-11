package com.gome.upm.mq;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.gome.upm.common.util.JsonUtils;
import com.taobao.diamond.utils.JSONUtils;


public class UpmMessage {
	String name;
	String type;
	Long   count;
	/**
	 * 推送时间
	 */
	String   date;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public static void main(String[] args) {
		List<UpmMessage> list=new ArrayList<UpmMessage>();
		UpmMessage u=new UpmMessage();
		u.setName("DRAGON 正向单停在OD的订单");
		u.setType("DRAGON-z-OD");
		u.setCount(new Long(4654));
		u.setDate("2016-08-05 17:21:27");
		list.add(u);
		//DRAGON 逆向单停在OD的订单	DRAGON-n-OD
		UpmMessage u1=new UpmMessage();
		u1.setName("DRAGON 逆向单停在OD的订单");
		u1.setType("DRAGON-n-OD");
		u1.setCount(new Long(576156));
		u1.setDate("2016-08-05 17:21:27");
		list.add(u1);
		String json=JSON.toJSONString(list);
		List<UpmMessage>  wwww=(List<UpmMessage>) JSON.parseArray(json, UpmMessage.class);
		
		for(UpmMessage u11:wwww){
			System.out.println(u11.date);
		}
//		System.out.println(json);
		//message保存的数据 
		//[{"count":4654,"date":1470812342201,"name":"DRAGON 正向单停在OD的订单","type":"DRAGON-z-OD"},{"count":576156,"date":1470812342201,"name":"DRAGON 逆向单停在OD的订单","type":"DRAGON-n-OD"}]
		//[{"count":4654,"date":"2016-08-05 17:21:27","name":"DRAGON 正向单停在OD的订单","type":"DRAGON-z-OD"},{"count":576156,"date":"2016-08-05 17:21:27","name":"DRAGON 逆向单停在OD的订单","type":"DRAGON-n-OD"}]

		//[{"name":"DRAGON 正向单停在OD的订单","type":"DRAGON-z-OD","count":4654,"date":"2016-08-05 17:21:27"},{"name":"DRAGON 逆向单停在OD的订单","type":"DRAGON-n-OD","count":576156,"date":"2016-08-05 17:21:27"}]
	}
}
