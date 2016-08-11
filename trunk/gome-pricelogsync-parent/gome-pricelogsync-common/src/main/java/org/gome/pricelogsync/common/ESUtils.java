package org.gome.pricelogsync.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

/**
 * ES工具类
 * @author caowei-ds1
 *
 */
public class ESUtils {
	/** 集群名称 */
	public static final String FROM_CLUSTER_NAME = "logger_elasticsearch";
//	public static final String TO_CLUSTER_NAME = "elasticsearch";
	public static final String TO_CLUSTER_NAME = "logger_elasticsearch";
	
	/** 节点地址 */
	private static final String FROM_HOSTS = "10.58.50.66,10.58.50.67";
//	private static final String TO_HOSTS = "127.0.0.1";
	private static final String TO_HOSTS = "10.58.50.66,10.58.50.67";
	
	/** 端口 */
	private static final int PORT = 9300; 
	
	private static Client client;
	
	/**
	 * 关闭对应client
	 * @param client
	 */
    public static void close(Client client) {
        if (client != null) {
            try {
             client.close();
            } catch (Exception e) {
            }
            client = null;
        }
    }

    public static void flush(Client client, String indexName, String indexType) {
		try{
			client.admin().indices().flush(new FlushRequest(indexName.toLowerCase(), indexType)).actionGet();
		}catch(Exception e){};
	}
	
	/**
	 * 根据默认系统默认配置初始化库,如果已经有连接则使用该连接
	 * @return
	 */
	public static Client getFromClient() {
		
		if(client!=null) {
			return client;
		}
		Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", FROM_CLUSTER_NAME)
				.put("client.transport.sniff", true)
				.build();
		
		TransportClient transportClient = new TransportClient(settings);
		if(!(FROM_HOSTS.indexOf(",") < 0)){
			String[] hostArr = FROM_HOSTS.split(",");
			for(String host: hostArr) {
				transportClient.addTransportAddress(new InetSocketTransportAddress(host, PORT));
			}
			return transportClient;
		}
		return transportClient.addTransportAddress(new InetSocketTransportAddress(FROM_HOSTS, PORT));
	}
	
	public static Client getToClient() {
		
		if(client!=null) {
			return client;
		}
		Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", TO_CLUSTER_NAME)
				.put("client.transport.sniff", true)
				.build();
		
		TransportClient transportClient = new TransportClient(settings);
		if(!(TO_HOSTS.indexOf(",") < 0)){
			String[] hostArr = TO_HOSTS.split(",");
			for(String host: hostArr) {
				transportClient.addTransportAddress(new InetSocketTransportAddress(host, PORT));
			}
			return transportClient;
		}
		return transportClient.addTransportAddress(new InetSocketTransportAddress(TO_HOSTS, PORT));
	}
	
	/**
	 * 判断索引是否存在
	 * @param client
	 * @param indexName
	 * @return
	 */
	public static boolean indicesExists(Client client, String indexName){
		IndicesExistsRequest ier = new IndicesExistsRequest();
		ier.indices(new String[]{indexName.toLowerCase()});
		
		return client.admin().indices().exists(ier).actionGet().isExists();
	}
	
	/**
	 * 判断类型是否存在
	 * @param client
	 * @param indexName
	 * @param indexType
	 * @return
	 */
	public static boolean typesExists(Client client, String indexName, String indexType){
		if(indicesExists(client, indexName)) {
			TypesExistsRequest ter = new TypesExistsRequest(new String[]{indexName.toLowerCase()}, indexType);
			return client.admin().indices().typesExists(ter).actionGet().isExists();
		}
		return false;
	}
	
	/**
	 * 根据索引数据id删除索引
	 * @param indexName 索引名称
	 * @param indexType 索引类型
	 * @param id 对应数据ID
	 */
	public static void deleteIndex(Client client, String indexName, String indexType, String id){
		try {
			client.prepareDelete(indexName.toLowerCase(), indexType.toLowerCase(), id).execute().actionGet();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据索引名称删除索引
	 * @param indexName 索引名称 
	
	public static void deleteIndex(String indexName){
		try {
			IndicesExistsRequest ier = new IndicesExistsRequest();
			ier.indices(new String[]{indexName.toLowerCase()});
			
			boolean exists = getClient().admin().indices().exists(ier).actionGet().isExists();
			if(exists){
				getClient().admin().indices().prepareDelete(indexName.toLowerCase()).execute().actionGet();
			}
			
		}
		catch(IndexMissingException ime){}
	}
	 */
	/*
	public static SearchHits search(String indexName, List<String> indexTypes, QueryBuilder query, List<FieldSortBuilder> sortBuilders, int from, int size) throws NoNodeAvailableException, IndexMissingException {
		if(getClient() == null ) {
			return null;
		}
		indexName = indexName.toLowerCase();
		
		// 去掉不存在的索引
		IndicesExistsRequest ier = new IndicesExistsRequest();
		ier.indices(new String[]{indexName});
		boolean exists = getClient().admin().indices().exists(ier).actionGet().isExists();
		if(exists){
			getClient().admin().indices().open(new OpenIndexRequest(indexName)).actionGet();
		}else{
			Index index = new Index(indexName);
			//throw new IndexMissingException(index);
			return null;
		}
		
		try {
			getClient().admin().indices().refresh(new RefreshRequest(indexName)).actionGet();
		} catch (IndexMissingException e) {
			e.printStackTrace();
		}
		
		SearchRequestBuilder searchRequestBuilder = getClient().prepareSearch(indexName);
		
		if(indexTypes != null && indexTypes.size() > 0) {
			String[] types = new String[indexTypes.size()];
			for(int i=0; i<indexTypes.size(); i++) {
				types[i] = indexTypes.get(i).toLowerCase();
			}
			searchRequestBuilder.setTypes(types);
		}
		
		searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		searchRequestBuilder.setFrom(from);
		searchRequestBuilder.setSize(size);
		searchRequestBuilder.setExplain(false);
		searchRequestBuilder.setQuery(query);
		if(sortBuilders!=null && sortBuilders.size()>0){
			for(FieldSortBuilder sortBuilder: sortBuilders){
				searchRequestBuilder.addSort(sortBuilder);
			}
		}
		
		return searchRequestBuilder.execute().actionGet().getHits();
	}
	*/
	
	/**
	 * 查询数据
	 * @param indexName 索引名称
	 * @param indexType 索引类型
	 * @param id 数据id
	 * @return 如果不存在，返回<code>null</code>
	 */
	/*
	public static Map<String, Object> query(String indexName, String indexType, String id) {
		if(getClient() == null ) {
			return null;
		}
		if( StringUtil.isEmpty(indexName) || StringUtil.isEmpty(indexType) ||  StringUtil.isEmpty(id)) {
			return null;
		}
		indexName = indexName.toLowerCase();
		indexType = indexType.toLowerCase();
		
		IndicesExistsRequest ier = new IndicesExistsRequest();
		ier.indices(new String[]{indexName});
		boolean exists = getClient().admin().indices().exists(ier).actionGet().isExists();
		if(!exists){
			// 索引不存在
			return null; 
		}
		
		getClient().admin().indices().open(new OpenIndexRequest(indexName)).actionGet();
		getClient().admin().indices().refresh(new RefreshRequest(indexName)).actionGet();
		
		GetRequest gr = new GetRequest(indexName, indexType, id);
		
		ActionFuture<GetResponse> future = getClient().get(gr);
		GetResponse response = future.actionGet();
		return swapResult(response);
	}
	*/
	
	/**
	 * 初始化索引
	 * @param client
	 * @param indexName
	 * @param indexType
	 * @param cols
	 * @return 初始化成功,返回true；否则返回false
	 * @throws Exception
	 */
	/*
	public static boolean initIndex(Client client, String indexName, String indexType, List<Column> cols) throws Exception {
		if(StringUtil.isEmpty(indexName) || StringUtil.isEmpty(indexType) || CollectionUtils.isEmpty(cols)) {
			return false;
		}
		
		indexName = indexName.toLowerCase();
        indexType = indexType.toLowerCase();
		
		if(indicesExists(client, indexName)) {
			 OpenIndexRequestBuilder openIndexBuilder = new OpenIndexRequestBuilder(client.admin().indices());
             openIndexBuilder.setIndices(indexName).execute().actionGet();
		}else{
			 client.admin().indices().prepareCreate(indexName).execute().actionGet();
		}
		
		TypesExistsRequest ter = new TypesExistsRequest(new String[]{indexName.toLowerCase()}, indexType);
		boolean typeExists = client.admin().indices().typesExists(ter).actionGet().isExists();
		
		if(typeExists) {
			return true;
		}

		XContentBuilder mapping = jsonBuilder()
                .startObject()
                .startObject(indexType)
                .startObject("properties");
//		mapping.startObject("_all").field("type", "string").field("store", "yes").field("term_vector", "no").field("analyzer", "ik").endObject();
		for (Column col : cols) {
        	//(varchar、numeric、timestamp)
        	String colName = col.getName().toLowerCase();
        	String colType = col.getData_type().toLowerCase().trim();
        	if("varchar".equals(colType)) {
        		mapping.startObject(colName).field("type", "string").field("store", "yes").field("analyzer", "ik").field("include_in_all", true).endObject();
        	}else if("numeric".equals(colType)) {
        		if(col.getData_scale()>0) {
        			mapping.startObject(colName).field("type", "float").field("index", "not_analyzed").field("include_in_all", false).endObject();
        		}else{
        			mapping.startObject(colName).field("type", "long").field("index", "not_analyzed").field("include_in_all", false).endObject();
        		}
        	}else if("timestamp".equals(colType)) {
        		mapping.startObject(colName).field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd").field("index", "not_analyzed").field("include_in_all", false).endObject();
        	}else {
        		mapping.startObject(colName).field("type", "string").field("store", "yes").field("analyzer", "ik").field("include_in_all", true).endObject();
        	}
            
        }
        mapping.endObject()
                .endObject()
                 .endObject();

System.out.println(mapping.string());

        PutMappingRequest mappingRequest = Requests.putMappingRequest(indexName).type(indexType).source(mapping);
        PutMappingResponse response = client.admin().indices().putMapping(mappingRequest).actionGet();
        
		return response.isAcknowledged();
	}
	*/
	
//	public static SearchHits search(String indexName, String indexType, String[] keywords, String[] channelIdArr, int from, int size) throws NoNodeAvailableException, IndexMissingException {
//		if(getClient() == null ) {
//			return null;
//		}
//		
//		// 去掉不存在的索引
//		IndicesExistsRequest ier = new IndicesExistsRequest();
//		ier.indices(new String[]{indexName});
//		boolean exists = getClient().admin().indices().exists(ier).actionGet().isExists();
//		if(exists){
//			getClient().admin().indices().open(new OpenIndexRequest(indexName)).actionGet();
//		}else{
//			Index index = new Index(indexName);
//			throw new IndexMissingException(index);
//		}
//		
//		try {
//			getClient().admin().indices().refresh(new RefreshRequest(indexName)).actionGet();
//		} catch (IndexMissingException e) {
//			e.printStackTrace();
//		}
//		
//		SearchRequestBuilder searchRequestBuilder = getClient().prepareSearch(indexName);
//		
//		searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
//		searchRequestBuilder.setFrom(from);
//		searchRequestBuilder.setSize(size);
//		searchRequestBuilder.setExplain(true);
//		
//		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//		
//		StringBuffer totalKeys = new StringBuffer();
//		for(String keyword: keywords) {
//			totalKeys.append(keyword);
//		}
//		
//		if(!totalKeys.toString().equals("*")){
//			for(String keyword: keywords) {
//				if( keyword == null || keyword.trim().length() == 0 ) {
//					continue;
//				}
//				keyword = badChars.matcher(keyword).replaceAll("");
//				if( keyword == null || keyword.trim().length() == 0 ) {
//					continue;
//				}
//				
//				if(keyword.indexOf("*")!=-1 || keyword.indexOf("×")!=-1 || keyword.indexOf("?")!=-1 || keyword.indexOf("？")!=-1){
//					keyword = keyword.replaceAll("×", "*").replaceAll("？", "?");
//					BoolQueryBuilder subBoolQuery = QueryBuilders.boolQuery();
//					for(String indexColumnName: Content.indexColumnNames) {
//						subBoolQuery.should(QueryBuilders.wildcardQuery(indexColumnName.toLowerCase(), keyword));
//					}
//					boolQuery.must(subBoolQuery);
//				}else{
//					QueryStringQueryBuilder qb = QueryBuilders.queryString("\""+keyword+"\""); 
//					boolQuery.must(qb);
//				}
//			}
//		}else {
//			//boolQuery.should(QueryBuilders.queryString("*"));
//		}
//		
//		if(channelIdArr!=null && channelIdArr.length>0){
//			TermsQueryBuilder inQuery = QueryBuilders.inQuery("channelid_", channelIdArr);
//			boolQuery.must(inQuery);
//		}
//		
//		searchRequestBuilder.setQuery(boolQuery);
//		
//		
//		return searchRequestBuilder.execute().actionGet().getHits();
//	}
	
	public static String preReadString(String read, int maxLength) {
		if(read==null||read.trim().length()==0){
			return "";
		}
		read = read.trim();
		
		if(read.length()<=maxLength){
			return read;
		}
		
//		if(keywords!=null && keywords.length>0){
//			for(String keyword: keywords) {
//				if( keyword == null || keyword.trim().length() == 0 ) {
//					continue;
//				}
//				keyword = badChars.matcher(keyword).replaceAll("");
//				int loc = read.indexOf(keyword);
//				if(loc != -1){
//					if(loc <= maxLength) {
//						return read.substring(0, maxLength);
//					}else{
//						int aft = read.length()-loc;
//						if(aft>(maxLength/2)){
//							return read.substring(loc-maxLength/2, loc+maxLength/2+1);
//						}else{
//							return read.substring(loc-maxLength+aft, loc+aft);
//						}
//					}
//				}
//			}
//		}
		return read.substring(0, maxLength);

	}
	
	public static List<Map<String, Object>> swapResult(SearchHits hits) {
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		
		if(hits == null || hits.getTotalHits() <= 0) {
			return datas;
		}
		
		for(int i=0; i<hits.hits().length; i++) {
			SearchHit hit = hits.getAt(i);
			
			Map<String, Object> rowData = hit.sourceAsMap(); 
			rowData.put("_index", hit.getIndex());
			rowData.put("_type", hit.getType());
			rowData.put("_id", hit.getId());
			
			datas.add(rowData);
		}
		
		return datas;
	}
	
	public static Map<String, Object> swapResult(GetResponse response) {
		if(response == null || !response.isExists()) {
			return null;
		}
			
		Map<String, Object> rowData = response.getSourceAsMap();
		rowData.put("_index", response.getIndex());
		rowData.put("_type", response.getType());
		rowData.put("_id", response.getId());
		
		return rowData;
	}
	
	
}
