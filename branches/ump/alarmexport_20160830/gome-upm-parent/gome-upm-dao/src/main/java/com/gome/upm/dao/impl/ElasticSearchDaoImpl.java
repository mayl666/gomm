package com.gome.upm.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Repository;

import com.gome.upm.common.Page;
import com.gome.upm.dao.ElasticSearchDAO;
import com.gome.upm.dao.util.ElasticSearchUtils;
import com.gome.upm.domain.PortRecord;
import com.gome.upm.domain.UrlRecord;
@Repository("elasticSearchDao")
public class ElasticSearchDaoImpl implements ElasticSearchDAO{

	@Resource(name="esUtils")
    private ElasticSearchUtils elasticSearchUtils;

	@Override
	public void insertUrlRecord(List<UrlRecord> list) {
		Client client =elasticSearchUtils.getEsClient2();
		for(UrlRecord url :list){
			client.prepareIndex("urlrecord", "url")
			.setSource(urlToJson(url)).execute().actionGet();
		}
		 client.close();
	}
	
	public void insertPortRecord(List<PortRecord> list){
		Client client =elasticSearchUtils.getEsClient2();
		for(PortRecord port:list){
			client.prepareIndex("portrecord", "port")
			.setSource(portToJson(port)).execute().actionGet();
		}
		client.close();
	}
	
	

	@Override
	public Page<UrlRecord> searchUrlRecord(String id, int start, int pageSize) {
		Client client=elasticSearchUtils.getEsClient2();
		SearchResponse searchResponse;
		QueryBuilder query = QueryBuilders.termQuery("id", id);
		if(StringUtils.isEmpty(id)){
			searchResponse =client.prepareSearch("urlrecord")
					.setTypes("url")
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
					.addSort("visitTime", SortOrder.DESC)
					.setFrom(start).setSize(pageSize).execute().actionGet();
		}else{
			searchResponse =client.prepareSearch("urlrecord")
					.setTypes("url")
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(query)
					.addSort("visitTime", SortOrder.DESC)
					.setFrom(start).setSize(pageSize).execute().actionGet();
		}
		
		System.out.println(searchResponse.toString());
		SearchHits hits = searchResponse.getHits();
		Page<UrlRecord> page = new Page<UrlRecord>(start,pageSize);
		page.setTotalResult(Integer.valueOf(hits.getTotalHits()+""));
		List<UrlRecord> list=  new ArrayList<UrlRecord>();
		SearchHit[] searchHists = hits.getHits();
		
		if (searchHists.length > 0) {
			for (SearchHit hit : searchHists) {
				list.add(warpToUrlRecord(hit));
			}
			page.setData(list);
		}
		client.close();
		return page;
	}
	
	@Override
	public Page<PortRecord> searchPortRecord(String id, int start, int pageSize) {
		Client client=elasticSearchUtils.getEsClient2();
		SearchResponse searchResponse;
		QueryBuilder query = QueryBuilders.termQuery("id", id);
		if(StringUtils.isEmpty(id)){
			searchResponse =client.prepareSearch("portrecord")
					.setTypes("port")
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
					.addSort("visitTime", SortOrder.DESC)
					.setFrom(start).setSize(pageSize).execute().actionGet();
		}else{
			searchResponse =client.prepareSearch("portrecord")
					.setTypes("port")
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(query)
					.addSort("visitTime", SortOrder.DESC)
					.setFrom(start).setSize(pageSize).execute().actionGet();
		}
		
		System.out.println(searchResponse.toString());
		SearchHits hits = searchResponse.getHits();
		Page<PortRecord> page = new Page<PortRecord>(start,pageSize);
		page.setTotalResult(Integer.valueOf(hits.getTotalHits()+""));
		List<PortRecord> list=  new ArrayList<PortRecord>();
		SearchHit[] searchHists = hits.getHits();
		
		if (searchHists.length > 0) {
			for (SearchHit hit : searchHists) {
				list.add(warpToPortRecord(hit));
			}
			page.setData(list);
		}
		client.close();
		return page;
	}
	public UrlRecord warpToUrlRecord(SearchHit hit){
		UrlRecord url = new UrlRecord();
		url.setId(hit.getSource().get("id")!=null?Long.parseLong(hit.getSource().get("id").toString()):0);
		url.setVisitTime(hit.getSource().get("visitTime")!=null?new Date(Long.parseLong(hit.getSource().get("visitTime").toString())):null);
		url.setVisitTake(hit.getSource().get("visitTake")!=null?(Integer)hit.getSource().get("visitTake"):0);
		url.setReturnCode(hit.getSource().get("returnCode")!=null?(String)hit.getSource().get("returnCode"):"");
		url.setSurvival(hit.getSource().get("survival")!=null?(Integer)hit.getSource().get("survival"):0);
		url.setMatching(hit.getSource().get("matching")!=null?(Integer)hit.getSource().get("matching"):0);
		url.setUid(hit.getSource().get("uid")!=null?Long.parseLong(hit.getSource().get("uid").toString()):0);

		return url;
	}
	
	public PortRecord warpToPortRecord(SearchHit hit){
		PortRecord port = new PortRecord();
		port.setId(hit.getSource().get("id")!=null?Long.parseLong(hit.getSource().get("id").toString()):0);
		port.setVisitTime(hit.getSource().get("visitTime")!=null?new Date(Long.parseLong(hit.getSource().get("visitTime").toString())):null);
		port.setVisitTake(hit.getSource().get("visitTake")!=null?(Integer)hit.getSource().get("visitTake"):0);
		port.setSurvival(hit.getSource().get("survival")!=null?(Integer)hit.getSource().get("survival"):0);
		port.setPid(hit.getSource().get("pid")!=null?Long.parseLong(hit.getSource().get("pid").toString()):0);

		return port;
	}
	
	public String urlToJson(UrlRecord url){
		String json = "";
		try {
			XContentBuilder cb = XContentFactory.jsonBuilder().startObject();
			cb.field("id",url.getId());
			cb.field("visitTime",url.getVisitTime().getTime());
			cb.field("visitTake",url.getVisitTake());
			cb.field("returnCode",url.getReturnCode());
			cb.field("survival",url.getSurvival());
			cb.field("matching",url.getMatching());
			cb.field("uid",url.getUid());
			json = cb.endObject().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public String portToJson(PortRecord port){
		String json = "";
		try {
			XContentBuilder cb = XContentFactory.jsonBuilder().startObject();
			cb.field("id",port.getId());
			cb.field("visitTime",port.getVisitTime().getTime());
			cb.field("visitTake",port.getVisitTake());
			cb.field("survival",port.getSurvival());
			cb.field("pid",port.getPid());
			json = cb.endObject().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}



	@Override
	public int deleteRecord(String indexName) {
		Client client=elasticSearchUtils.getEsClient2();
		client.admin().indices().prepareDelete(indexName)
                .execute().actionGet();
		client.close();
		return 0;
	}
}
