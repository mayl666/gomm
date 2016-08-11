package com.gome.monitoringplatform.mongo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MongoTest {
	static String myUserName = "pw_orderhis_uat";
	static String myPassword = "zy09rmFCrAiNvvU6";
	static String databaseName = "ec_order_history_uat";
	static String ip = "10.126.44.51";
	static int port = 27027;
	
	/**
	 * 订单
	 */
	static String order="orderstatushistory";
	/**
	 * 配送单
	 */
	static String shippinggroup="shippinggroupstatushistory";
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMM");
	public static void main(String[] args) throws ParseException {
//		System.out.println(sdf2.format(new Date()));
		MongoClient client= getClient();
		try {
			Date start=formatter.parse("2016-04-01 01:00:00");
			Date end=formatter.parse("2016-04-10 20:00:00");
//			getCountForOrder(client,start, end, "201604");
			getCountForShipping(client,start, end, "201604");
		} catch (Exception e) {
			e.printStackTrace();
		}
		client.close();
	}
	private static MongoClient getClient(){
		MongoCredential credential = MongoCredential.createScramSha1Credential(myUserName, databaseName,
				myPassword.toCharArray());
		ServerAddress serverAddress = new ServerAddress(ip, port);
		MongoClient mongoClient = new MongoClient(serverAddress, Arrays.asList(credential));
		return mongoClient;
	}
	
	/**
	 * 订单类型
	 */
	static String[] orderState=new String[]{
			"PENDING_TECHNICIAN_NOTIFICATION",
			"PENDING_SUBMITTED",
			"PENDING_FINANCE_ACCEPT",
			"PENDING_PAYMENT",
			"PENDING_ORDER_CONFIRM",
			"PENDING_CUSTOMER_RETURN",
			"PENDING_CONTRACTMOBILE_APPROVE",
			"PENDING_CUSTOMER_ACTION",
			"PENDING_MERCHANT_ACTION",
			"PENDING_GOODS_IN_STORAGE",
			"PENDING_FINAL_PAYMENT",
			"NO_PENDING_ACTION",
			"DEPOSIT_PAYMENT",
			"REMOVED",
			"PENDING_DEPOSIT_PAYMENT",
			"PENDING_REMOVE",
			"PENDING_GIFTCARD_PAYMENT",
			"PROCESSING",
			"PENDING_3P_APPROVE_CONFIRM",
			"SUBMITTED",
			"TPSP_SUBMITTED",
			"ERROR",
			"PENDING_POST_PROCESS",
			"ALLOWANCE_PENDING_AUDIT",
			"PENDING_THIRD_AUDIT",
			"PENDING_ADD_RESOURCE"
	};
	/**
	 * 配送单类型
	 */
	static String[] shippingState=new String[]{
			"CO",
			"CL",
			"PR",
			"RCO"
	};
	
	/**
	 * 获取订单类型的数量
	 * @param start
	 * @param end
	 * @param database
	 * @param databaseSuffix
	 * @return
	 */
	public static Map<String,Integer> getCountForOrder(MongoClient mongoClient,Date start,Date end,String databaseSuffix){
		Map<String,Integer> dataMap=new HashMap<String,Integer>();
		DB db = mongoClient.getDB(databaseName);
		//连接数据
		DBCollection coll = db.getCollection(order+databaseSuffix);
		for(int i=0;i<orderState.length;i++){
			String state=orderState[i];
			List<BasicDBObject>pipeline=new ArrayList<BasicDBObject>();
			//时间过滤
			BasicDBObject head1=new BasicDBObject("$match",new BasicDBObject("status", state).append("statusDate", new BasicDBObject("$gte",start).append("$lt", end)));
			pipeline.add(head1);
			//分组
			BasicDBObject id2 = new BasicDBObject("_id", new BasicDBObject("orderId","$orderId").append("status", "$status"));
			BasicDBObject head2=new BasicDBObject("$group",id2);
			pipeline.add(head2);
			//计算数量
			BasicDBObject head3=new BasicDBObject("$group",new BasicDBObject("_id",null).append("count", new BasicDBObject("$sum",1)));
			pipeline.add(head3);
			AggregationOutput tt=coll.aggregate(pipeline);
			Iterator<DBObject>iterator=tt.results().iterator();
			while(iterator.hasNext()){
				dataMap.put(state, Integer.parseInt(iterator.next().get("count").toString()));
				System.out.println(state+"=============================="+dataMap.get(state));
			}
			if(dataMap.get(state)==null){
				dataMap.put(state, 0);
			}
		}
		return dataMap;
	}
	/**
	 * 获取配送单数量
	 * @param mongoClient
	 * @param start
	 * @param end
	 * @param databaseSuffix
	 * @return
	 */
	public static Map<String,Integer> getCountForShipping(MongoClient mongoClient,Date start,Date end,String databaseSuffix){
		Map<String,Integer> dataMap=new HashMap<String,Integer>();
		DB db = mongoClient.getDB(databaseName);
		//连接数据
		DBCollection coll = db.getCollection(shippinggroup+databaseSuffix);
		for(int i=0;i<shippingState.length;i++){
			String state=shippingState[i];
			List<BasicDBObject>pipeline=new ArrayList<BasicDBObject>();
			//时间过滤
			BasicDBObject head1=new BasicDBObject("$match",new BasicDBObject("status", state).append("statusDate", new BasicDBObject("$gte",start).append("$lt", end)));
			pipeline.add(head1);
			//分组
			BasicDBObject id2 = new BasicDBObject("_id", new BasicDBObject("orderId","$orderId").append("status", "$status"));
			BasicDBObject head2=new BasicDBObject("$group",id2);
			pipeline.add(head2);
			//计算数量
			BasicDBObject head3=new BasicDBObject("$group",new BasicDBObject("_id",null).append("count", new BasicDBObject("$sum",1)));
			pipeline.add(head3);
			AggregationOutput tt=coll.aggregate(pipeline);
			Iterator<DBObject>iterator=tt.results().iterator();
			while(iterator.hasNext()){
				dataMap.put(state, Integer.parseInt(iterator.next().get("count").toString()));
				System.out.println(state+"=============================="+dataMap.get(state));
			}
			if(dataMap.get(state)==null){
				dataMap.put(state, 0);
			}
		}
		return dataMap;
	}

}
