package org.gome.pricelogsync.dao.Impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.gome.pricelogsync.common.Constant;
import org.gome.pricelogsync.common.ESUtils;
import org.gome.pricelogsync.common.JsonUtils;
import org.gome.pricelogsync.dao.PriceLogDao;
import org.gome.pricelogsync.domain.PriceLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * 价格监控接口实现类
 * @author caowei-ds1
 */
@Repository
public class PriceLogDaoImpl implements PriceLogDao {
	
	private static Logger log = LoggerFactory.getLogger(PriceLogDaoImpl.class);
	
	/** 索引 */
	private String[] indices = {"aux"};
	
	private String toIndex = "price-log-monitor";
	
	private DateFormat df;
	
	private Date date;
	
	{
		df = new SimpleDateFormat("yyyy-MM-dd");
		String suffix = df.format(new Date());
		for(int i=0; i<indices.length; i++){
			indices[i] += "-" + suffix;
		}
		
		toIndex += "-" + suffix;
	}
	
	/** 类型 */
	private String type = "logs";
	
	private Client client;
	
	private SearchResponse response;

	public List<PriceLog> selectPriceLogList() {
		date = new Date();
		long begin = date.getTime();
		client = ESUtils.getFromClient(); 
		List<PriceLog> priceList = new ArrayList<PriceLog>();
		List<String> indexsList = new ArrayList<String>();
		//过滤出存在的索引
		for(int i=0; i < indices.length; i++){
			if(ESUtils.indicesExists(client, indices[i])){
				indexsList.add(indices[i]);
			}
		}
		String[] exist = new String[indexsList.size()];
		indexsList.toArray(exist);
		int count = (int) client.prepareCount(exist).setTypes(type).execute().actionGet().getCount();
		if(count == 0){
			//无数据
			client.close();
			return priceList;
		}
		if("true".equals(Constant.flag)){
			response = client.prepareSearch(exist).setTypes(type).addSort("@timestamp", SortOrder.DESC).setFrom(0).setSize(count).execute().actionGet();
			Constant.flag = "false";
			if(response.getHits().getHits().length != 0){
				Constant.lastTime = (String) response.getHits().getHits()[0].getSource().get("@timestamp");
			}
			
		} else {
			QueryBuilder query = QueryBuilders.rangeQuery("@timestamp").gt(Constant.lastTime);
			response = client.prepareSearch(exist).setTypes(type).setQuery(query).addSort("@timestamp", SortOrder.DESC).setFrom(0).setSize(count).execute().actionGet();
			if(response.getHits().getHits().length != 0){
				Constant.lastTime = (String) response.getHits().getHits()[0].getSource().get("@timestamp");
			}
			
		}
		log.info("查询到新数据：" + response.getHits().getHits().length + "条.");
		SearchHits shs = response.getHits();  
        for(SearchHit hit : shs){  
        	PriceLog priceLog = new PriceLog();
        	priceLog.setUuid(hit.getId());
        	priceLog.setIndex(hit.getIndex());
        	String message = (String) hit.getSource().get("@message");
//        	System.out.println("message:" + message);
        	
        	String regex = "ID:(.*?);NODE:(.*?);TIME:(.*?);ISBATCH:(.*?);STATUS:(.*?);ACTION:(.*?);RESULT:(.*?);";
    		Pattern p = Pattern.compile(regex);
    	    Matcher m = p.matcher(message);
    	    while(m.find()){
//    	    	System.out.println("--------------------");
//    	    	System.out.println("ID:" + m.group(1));
//    	    	System.out.println("NODE:" + m.group(2));
//    	    	System.out.println("TIME:" + m.group(3));
//    	    	System.out.println("ISBATCH:" + m.group(4));
//    	    	System.out.println("STATUS:" + m.group(5));
//    	    	System.out.println("ACTION:" + m.group(6));
//    	    	System.out.println("RESULT:" + m.group(7));
    	    	//设置ID
    	    	priceLog.setId(m.group(1));
    	    	//设置类型、商品编码、区域编码
    	    	String[] idArr = m.group(1).split("_");
				if(idArr.length == 2){
					priceLog.setType(idArr[1]);
				} else if(idArr.length == 4){
					priceLog.setType(idArr[1]);
					priceLog.setSkuNo(idArr[2]);
					priceLog.setAreaCode(idArr[3]);
				}
    	    	//设置节点
    	    	priceLog.setNode(m.group(2));
    	    	//设置时间
    	    	df = new SimpleDateFormat("yyyyMMddHHmm");
				try {
					date = df.parse(m.group(3));
					priceLog.setTimestamp(date);
					df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					priceLog.setTimeStr(df.format(date));
					df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
					priceLog.setTime(df.format(date));
//					System.out.println(df.format(date));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				//设置批处理
				if("TRUE".equals(m.group(4))){
					priceLog.setIsBatch((byte) 1);
				} else {
					priceLog.setIsBatch((byte) 0);
				}
				//设置状态
				if("START".equals(m.group(5))){
					priceLog.setStatus((byte) 1);
				} else if("CONTINUE".equals(m.group(5))) {
					priceLog.setStatus((byte) 2);
				} else {
					priceLog.setStatus((byte) 3);
				}
				//设置动作
				if("SEND".equals(m.group(6))){
					priceLog.setAction((byte) 0);
				} else {
					priceLog.setAction((byte) 1);
				}
				//设置结果
				if("TRUE".equals(m.group(7))){
					priceLog.setResult((byte) 1);
				} else {
					priceLog.setResult((byte) 0);
				}
				
				priceList.add(priceLog);
    	    }
        	
//          System.out.println("id:"+hit.getId()+":"+hit.getSourceAsString());  
        }
        client.close();
        date = new Date();
        long end = date.getTime();
        log.info("查询共耗时" + (end - begin)/1000 + "秒.");
        return priceList;
	}

	public void insertPriceLogList(List<PriceLog> logList) {
		date = new Date();
		long begin = date.getTime();
		client = ESUtils.getToClient();
		if(!ESUtils.indicesExists(client, toIndex)){
			createIndex(client, toIndex);
			createIndexType(client, toIndex, type);
		}
		for (PriceLog priceLog : logList) {
			String json = JsonUtils.Object2Json(priceLog);
			client.prepareIndex(toIndex, type) //index,type
						.setId(priceLog.getUuid()) //document
						.setSource(json)
						.execute()
						.actionGet();
		}
		client.close();
		date = new Date();
        long end = date.getTime();
        log.info("插入共耗时" + (end - begin)/1000 + "秒.");
	} 
	
	//创建索引库
	public void createIndex(Client client, String index){
	    client.admin().indices().prepareCreate(index).execute().actionGet();
	}

	//创建索引类型
	public void createIndexType(Client client, String index, String type){
	   XContentBuilder mapping;
		try {
			mapping = XContentFactory.jsonBuilder()
			           .startObject()
			             .startObject(type)
			             .startObject("properties")
			               .startObject("id").field("type", "string").field("store", "yes").field("index", "not_analyzed").endObject()
			               .startObject("type").field("type", "string").field("index", "not_analyzed").endObject()
			               .startObject("skuNo").field("type", "string").field("index", "not_analyzed").endObject()
			               .startObject("areaCode").field("type", "string").field("index", "not_analyzed").endObject()
			               .startObject("node").field("type", "string").field("index", "not_analyzed").endObject()
			               .startObject("time").field("type", "date").endObject()
			               .startObject("isBatch").field("type", "long").endObject()
			               .startObject("status").field("type", "long").endObject()
			               .startObject("action").field("type", "long").endObject()
			               .startObject("result").field("type", "long").endObject()
			               .startObject("timeStr").field("type", "string").field("index", "not_analyzed").endObject()
			               .startObject("uuid").field("type", "string").field("index", "not_analyzed").endObject()
			             .endObject()
			            .endObject()
			          .endObject();
			PutMappingRequest mappingRequest = Requests.putMappingRequest(index).type(type).source(mapping);
		    client.admin().indices().putMapping(mappingRequest).actionGet();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
