package com.gome.pricemonitor.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gome.presto.PrestoJdbcCli;
import com.gome.presto.entity.PriceLogVO;
import com.gome.pricemonitor.common.Page;
import com.gome.pricemonitor.common.util.ESUtils;
import com.gome.pricemonitor.common.util.JsonUtils;
import com.gome.pricemonitor.dao.PriceMonitorDao;
import com.gome.pricemonitor.domain.PriceLog;

/**
 * 价格监控接口实现类
 * @author caowei-ds1
 */
@Repository
public class PriceMonitorDaoImpl implements PriceMonitorDao {
	
	private static final Logger logger = LoggerFactory.getLogger(PriceMonitorDaoImpl.class);
	
	/** 索引 */
	private String indices = "price-log-monitor";
	
	/** 类型 */
	private String type = "logs";
	
	private Client client; 
	
	private DateFormat df;
	
	{
		df = new SimpleDateFormat("yyyy-MM-dd");
		String suffix = df.format(new Date());
		indices += "-" + suffix;
	}
	
	@Override
	public List<PriceLog> selectPriceLogListFromEsByConditionsPage(Page<PriceLog> page) {
		client = ESUtils.getClient(); 
		List<PriceLog> priceList = new ArrayList<PriceLog>();
		if(!ESUtils.indicesExists(client, indices)){
			//索引不存在
			client.close();
			return priceList;
		}
		PriceLog conditions = page.getConditions();
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		if(StringUtils.isNotEmpty(conditions.getId())){
			query.must(QueryBuilders.queryStringQuery(conditions.getId()).field("id"));
		}
		if(StringUtils.isNotEmpty(conditions.getSkuNo())){    //商品编码支持模糊查询
			query.must(QueryBuilders.queryStringQuery("*" + conditions.getSkuNo() + "*").field("skuNo"));
		}
		if(StringUtils.isNotEmpty(conditions.getType())){
			query.must(QueryBuilders.queryStringQuery(conditions.getType()).field("type"));
		}
		if(StringUtils.isNotEmpty(conditions.getAreaCode())){   //区域编码支持模糊查询
			query.must(QueryBuilders.queryStringQuery("*" + conditions.getAreaCode() + "*").field("areaCode"));
		}
		if(StringUtils.isNotEmpty(conditions.getNode())){
			//query.must(QueryBuilders.termQuery("node", conditions.getNode()));
			query.must(QueryBuilders.queryStringQuery(conditions.getNode()).field("node"));
		}
		if(StringUtils.isNotEmpty(conditions.getAction())){
			query.must(QueryBuilders.queryStringQuery(conditions.getAction()).field("action"));
		}
		if(StringUtils.isNotEmpty(conditions.getResult())){
			query.must(QueryBuilders.queryStringQuery(conditions.getResult()).field("result"));
		}
		if(StringUtils.isNotEmpty(conditions.getStatus())){
			query.must(QueryBuilders.queryStringQuery(conditions.getStatus()).field("status"));
		}
		if(conditions.getStartTimeStamp() != 0 && conditions.getEndTimeStamp() != 0){
			query.must(QueryBuilders.rangeQuery("timestamp").from(conditions.getStartTimeStamp()).to(conditions.getEndTimeStamp()));
		}
//		query.must(QueryBuilders.matchAllQuery());
//		System.out.println("from:" + page.getStart() + ";to:" + page.getPageSize());
		SearchResponse response = client.prepareSearch(indices).setTypes(type).setQuery(query).addSort("time", SortOrder.DESC).addSort("result", SortOrder.ASC).addSort("status", SortOrder.ASC).addSort("action", SortOrder.ASC).setFrom(page.getStart()).setSize(page.getPageSize()).execute().actionGet();
		SearchHits shs = response.getHits();  
        for(SearchHit hit : shs){  
        	PriceLog priceLog = (PriceLog) JsonUtils.Json2Object(hit.getSourceAsString(),PriceLog.class);
        	priceList.add(priceLog);
//            System.out.println("id:"+hit.getId()+":"+hit.getSourceAsString());  
        }  
        client.close();  
		return priceList;
	}

	@Override
	public int selectTotalResultFromEsByConditions(PriceLog conditions) {
		client = ESUtils.getClient(); 
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		if(StringUtils.isNotEmpty(conditions.getId())){
			query.must(QueryBuilders.queryStringQuery(conditions.getId()).field("id"));
		}
		if(StringUtils.isNotEmpty(conditions.getSkuNo())){
			query.must(QueryBuilders.queryStringQuery("*" + conditions.getSkuNo() + "*").field("skuNo"));
		}
		if(StringUtils.isNotEmpty(conditions.getType())){
			query.must(QueryBuilders.queryStringQuery(conditions.getType()).field("type"));
		}
		if(StringUtils.isNotEmpty(conditions.getAreaCode())){
			query.must(QueryBuilders.queryStringQuery("*" + conditions.getAreaCode() + "*").field("areaCode"));
		}
		if(StringUtils.isNotEmpty(conditions.getNode())){
			//query.must(QueryBuilders.termQuery("node", conditions.getNode()));
			query.must(QueryBuilders.queryStringQuery(conditions.getNode()).field("node"));
		}
		if(StringUtils.isNotEmpty(conditions.getAction())){
			query.must(QueryBuilders.queryStringQuery(conditions.getAction()).field("action"));
		}
		if(StringUtils.isNotEmpty(conditions.getResult())){
			query.must(QueryBuilders.queryStringQuery(conditions.getResult()).field("result"));
		}
		if(StringUtils.isNotEmpty(conditions.getStatus())){
			query.must(QueryBuilders.queryStringQuery(conditions.getStatus()).field("status"));
		}
		if(conditions.getStartTimeStamp() != 0 && conditions.getEndTimeStamp() != 0){
			query.must(QueryBuilders.rangeQuery("timestamp").from(conditions.getStartTimeStamp()).to(conditions.getEndTimeStamp()));
		}
		int count = (int) client.prepareCount(indices).setTypes(type).setQuery(query).execute().actionGet().getCount();
		client.close();
		return count;
	}

	@Override
	public List<PriceLog> selectPriceLogListFromSqlByConditionsPage(Page<PriceLog> page) {
		PriceLog conditions = page.getConditions();
		String sql = "select * from price_log_monitor where dt=" + "'" + conditions.getTimeStr() + "'";
		if(StringUtils.isNotEmpty(conditions.getId())){
			sql += " and id=" + "'" + conditions.getId() + "'";
		}
		if(StringUtils.isNotEmpty(conditions.getSkuNo())){    //商品编码支持模糊查询
			sql += " and skuno like " + "'%" + conditions.getSkuNo() + "%'";
		}
		if(StringUtils.isNotEmpty(conditions.getType())){
			sql += " and type=" + "'" + conditions.getType() + "'";
		}
		if(StringUtils.isNotEmpty(conditions.getAreaCode())){   //区域编码支持模糊查询
			sql += " and areacode like " + "'%" + conditions.getAreaCode() + "%'";
		}
		if(StringUtils.isNotEmpty(conditions.getNode())){
			sql += " and node=" + "'" + conditions.getNode() + "'";
		}
		if(StringUtils.isNotEmpty(conditions.getAction())){
			sql += " and action=" + conditions.getAction();
		}
		if(StringUtils.isNotEmpty(conditions.getResult())){
			sql += " and result=" + conditions.getResult();
		}
		if(StringUtils.isNotEmpty(conditions.getStatus())){
			sql += " and status=" + conditions.getStatus();
		}
		if(conditions.getStartTimeStamp() != 0 && conditions.getEndTimeStamp() != 0){
			sql += " and time between " + conditions.getStartTimeStamp() + " and " + conditions.getEndTimeStamp();
		}
		int resultNum = page.getPageNo() * page.getPageSize();
		sql += " order by time desc,result asc,status asc limit " + resultNum;
		List<PriceLogVO> result = PrestoJdbcCli.query(sql);
		List<PriceLog> priceList = new ArrayList<PriceLog>();
		List<PriceLogVO> subResult;
		if (result == null || result.isEmpty()) {
            return priceList;
        }
		if(page.getPageNo() > 1) {
			//分页
	        int startIndex = (page.getPageNo()-1)*page.getPageSize();
	        int endIndex = startIndex + page.getPageSize();
	        if (startIndex > endIndex || startIndex > result.size()) {
	            return priceList;
	        }
	        if (endIndex > result.size()) {
	            endIndex = result.size();
	        }
			subResult = result.subList(startIndex, endIndex);
		} else {
			subResult = result;
		}
		for (PriceLogVO vo : subResult) {
			PriceLog priceLog = new PriceLog();
			priceLog.setId(vo.getId());
			priceLog.setSkuNo(vo.getSkuno());
			priceLog.setType(vo.getType());
			priceLog.setAreaCode(vo.getAreacode());
			priceLog.setNode(vo.getNode());
			priceLog.setTimeStr(vo.getTime());
			priceLog.setAction(String.valueOf(vo.getAction()));
			priceLog.setResult(String.valueOf(vo.getResult()));
			priceLog.setStatus(String.valueOf(vo.getStatus()));
			priceList.add(priceLog);
		}
		return priceList;
	}

	@Override
	public int selectTotalResultFromSqlByConditions(PriceLog conditions) {
		String sql = "select count(*) from price_log_monitor where dt=" + "'" + conditions.getTimeStr() + "'";
		if(StringUtils.isNotEmpty(conditions.getId())){
			sql += " and id=" + "'" + conditions.getId() + "'";
		}
		if(StringUtils.isNotEmpty(conditions.getSkuNo())){    //商品编码支持模糊查询
			sql += " and skuno like " + "'%" + conditions.getSkuNo() + "%'";
		}
		if(StringUtils.isNotEmpty(conditions.getType())){
			sql += " and type=" + "'" + conditions.getType() + "'";
		}
		if(StringUtils.isNotEmpty(conditions.getAreaCode())){   //区域编码支持模糊查询
			sql += " and areacode like " + "'%" + conditions.getAreaCode() + "%'";
		}
		if(StringUtils.isNotEmpty(conditions.getNode())){
			sql += " and node=" + "'" + conditions.getNode() + "'";
		}
		if(StringUtils.isNotEmpty(conditions.getAction())){
			sql += " and action=" + conditions.getAction();
		}
		if(StringUtils.isNotEmpty(conditions.getResult())){
			sql += " and result=" + conditions.getResult();
		}
		if(StringUtils.isNotEmpty(conditions.getStatus())){
			sql += " and status=" + conditions.getStatus();
		}
		if(conditions.getStartTimeStamp() != 0 && conditions.getEndTimeStamp() != 0){
			sql += " and time between " + conditions.getStartTimeStamp() + " and " + conditions.getEndTimeStamp();
		}
		long count = PrestoJdbcCli.count(sql);
		return (int) count;
	}
	
}
