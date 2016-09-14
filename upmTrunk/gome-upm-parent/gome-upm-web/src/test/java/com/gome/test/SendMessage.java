package com.gome.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.gome.upm.mq.UpmMessage;
public class SendMessage {
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static void main(String[] args) {
		String date =formatter.format(new Date());
		System.out.println(date);
		date="2016-09-13 10:21:07";
		
		List<UpmMessage> list = new ArrayList<UpmMessage>();
		UpmMessage u = null;

		u = new UpmMessage();
		u.setName("DRAGON 正向单停在OD的订单");
		u.setType("DRAGON-z-OD");
		u.setCount(45l);
		u.setDate(date);
		list.add(u);

		u = new UpmMessage();
		u.setName("DRAGON 逆向单停在OD的订单");
		u.setType("DRAGON-n-OD");
		u.setCount(46l);
		u.setDate(date);
		list.add(u);

		u = new UpmMessage();
		u.setName("OMS-DRG正向订单状态差异");
		u.setType("OMS-DRG-z");
		u.setCount(46l);
		u.setDate(date);
		list.add(u);

		u = new UpmMessage();
		u.setName("OMS-DRG逆向订单状态差异");
		u.setType("OMS-DRG-n");
		u.setCount(46l);
		u.setDate(date);
		list.add(u);

		u = new UpmMessage();
		u.setName("OMS-POP正向订单状态差异");
		u.setType("OMS-POP-z");
		u.setCount(46l);
		u.setDate(date);
		list.add(u);

		u = new UpmMessage();
		u.setName("OMS-POP逆向订单状态差异");
		u.setType("OMS-POP-n");
		u.setCount(46l);
		u.setDate(date);
		list.add(u);

		u = new UpmMessage();
		u.setName("正向单停在CO的订单--G3PP返回状态不正确");
		u.setType("Z-CO-G3PP");
		u.setCount(46l);
		u.setDate(date);
		list.add(u);

		u = new UpmMessage();
		u.setName("正向单停在CO的订单--已发送SO至DRG");
		u.setType("Z-CO-SO-R-DRG");
		u.setCount(46l);
		u.setDate(date);
		list.add(u);

		u = new UpmMessage();
		u.setName("正向单停在CO的订单--已发送SO至POP");
		u.setType("Z-CO-SO-R-POP");
		u.setCount(46l);
		u.setDate(date);
		list.add(u);

		u = new UpmMessage();
		u.setName("正向单停在CO的订单--待客服处理");
		u.setType("Z-CO-SO-kf");
		u.setCount(46l);
		u.setDate(date);
		list.add(u);

		u = new UpmMessage();
		u.setName("正向单停在CO的订单--总数");
		u.setType("Z-CO-SO-c");
		u.setCount(46l);
		u.setDate(date);
		list.add(u);

		u = new UpmMessage();
		u.setName("正向单停在CO的订单--未发SO至DRG");
		u.setType("Z-CO-SO-N-DRG");
		u.setCount(46l);
		u.setDate(date);
		list.add(u);

		u = new UpmMessage();
		u.setName("正向单停在CO的订单--未发SO至POP");
		u.setType("Z-CO-SO-N-POP");
		u.setCount(46l);
		u.setDate(date);
		list.add(u);

		String str = JSON.toJSONString(list);
		System.out.println(str);
		try {
			System.out.println(GomeRocketMQProducer.SENDER_ORDER_INC.send("upm_syn_group", str.getBytes()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
