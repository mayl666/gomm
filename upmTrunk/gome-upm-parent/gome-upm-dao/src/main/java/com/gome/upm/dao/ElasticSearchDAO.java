package com.gome.upm.dao;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.PortRecord;
import com.gome.upm.domain.UrlRecord;

public interface ElasticSearchDAO {

	public void insertUrlRecord(List<UrlRecord> list);
	
	public void insertPortRecord(List<PortRecord> list);
	
	public Page<UrlRecord> searchUrlRecord(String id,int start,int pageSize);
	
	public Page<PortRecord> searchPortRecord(String id,int start,int pageSize);
	
	public int deleteRecord(String indexName);
}
