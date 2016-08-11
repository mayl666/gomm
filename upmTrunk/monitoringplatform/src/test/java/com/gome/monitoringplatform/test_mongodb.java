package com.gome.monitoringplatform;

import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class test_mongodb {
	public static void main(String args[]) throws Exception {
		MongoClient mongoClient = new MongoClient("10.126.44.51", 27027);// 建立连接
		DB db = mongoClient.getDB("ec_order_history_uat");// 数据库名
		db.addUser("pw_orderhis_uat", "zy09rmFCrAiNvvU6".toCharArray());
		
		DBCollection collection = db.getCollection("orderstatushistory201211");// 集合名，对应mysql中的表名
		BasicDBObject query = new BasicDBObject();
		// 建立查询条件,如果还有其他条件，类似的写即可
		// 如:version=3,filter_dbobject.put("version", 3),mongod区分String 和
		// Integer类型，所以要小心"3"!=3
//		query.put("status", "PENDING_POST_PROCESS");
//		  BasicDBObject query = new BasicDBObject();
	    query.put("_id", new ObjectId("569dab03e4b0f7fdceaf1af1"));
//	    DBObject dbObj = collection.findOne(query);
//	    return dbObj;

		// 下面执行查询，设置limit,只要10条数据,排序(类mysql orderby) 再建一个BasicDBObject即可，－1表示倒序
//		DBCursor cursor = collection.find(query).limit(10);//.limit(10).sort(new BasicDBObject("status", -1));
	    
	    DBObject dbObj = collection.findOne(query);

		// 把结果集输出成list类型
//		List<DBObject> list = cursor.toArray();
		System.out.println("--------------------");
//		System.out.println(list.size());// list的长度
//		System.err.println(cursor.count());// 计算结果的数量，类似于(mysql
											// count()函数),不受limit的影响
		// 遍历结果集
//		while (cursor.hasNext()) {
//			System.out.println(cursor.next());
//		}
	}
}
