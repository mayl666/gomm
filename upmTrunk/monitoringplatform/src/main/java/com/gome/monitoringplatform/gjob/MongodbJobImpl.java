package com.gome.monitoringplatform.gjob;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gome.job.util.ExecutorManager;
import com.gome.monitoringplatform.dao.MoOrderStateDAO;
import com.gome.monitoringplatform.model.bo.MoOrderStateBO;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MongodbJobImpl extends AbsJobImpl{
	@Autowired
	private MoOrderStateDAO moOrderStateDAO;
	public void setMoOrderStateDAO(MoOrderStateDAO moOrderStateDAO) {
		this.moOrderStateDAO = moOrderStateDAO;
	}
	/**
	 * 订单类型
	 */
	private static final String[] orderState=new String[]{
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
	private static final String[] shippingState=new String[]{
			"CO",
			"CL",
			"PR",
			"RCO"
	};
	@Override
	public void run() {
		ReObj boj=null;
		runResult = "订单状态监控Job运行成功";
		System.out.println("定时任务========================="+runResult);
		try {
			boj = getTime();
		} catch (ParseException e) {
		}
		MongoClient client= getClient();
		try {
			String databaseSuffix=sdfyyyyMM.format(boj.getStartTime());
			Map<String,Integer> map1=getCountForOrder(client,boj.getStartTime(), boj.getEndTime(), databaseSuffix);
			Map<String,Integer> map2=getCountForShipping(client,boj.getStartTime(), boj.getEndTime(), databaseSuffix);
			map2.putAll(map1);
			saveOjb(boj.getStartTime(), boj.getEndTime(), map2);
			ExecutorManager.callBack(logId, "订单状态监控Job运行成功", 1);
		} catch (Exception e) {
			ExecutorManager.callBack(logId, "订单状态监控Job运行失败", 1);
		}
		client.close();
	}
	private void saveOjb(Date startDate,Date endDate,Map<String,Integer> dataMap){
		Iterator<String>iterator=  dataMap.keySet().iterator();
		while(iterator.hasNext()){
			String state=iterator.next();
			MoOrderStateBO m=new MoOrderStateBO();
			m.setCount(dataMap.get(state));
			m.setStartTime(startDate);
			m.setEndTime(endDate);
			m.setMinute(endDate.getMinutes());
			m.setState(state);
			moOrderStateDAO.saveMoOrderState(m);
		}
	}
	private MongoClient getClient(){
		MongoCredential credential = MongoCredential.createScramSha1Credential(myUserName, databaseName,
				myPassword.toCharArray());
		ServerAddress serverAddress = new ServerAddress(ip, port);
		MongoClient mongoClient = new MongoClient(serverAddress, Arrays.asList(credential));
		return mongoClient;
	}
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
		//连接数据
		DBCollection coll = mongoClient.getDB(databaseName).getCollection(order+databaseSuffix);
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
	public Map<String,Integer> getCountForShipping(MongoClient mongoClient,Date start,Date end,String databaseSuffix){
		Map<String,Integer> dataMap=new HashMap<String,Integer>();
		//连接数据
		DBCollection coll = mongoClient.getDB(databaseName).getCollection(shippinggroup+databaseSuffix);
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
