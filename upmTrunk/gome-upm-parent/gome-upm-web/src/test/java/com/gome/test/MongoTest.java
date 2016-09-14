package com.gome.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gome.upm.common.Page;
import com.gome.upm.mongo.model.GtpTaskReport;
import com.gome.upm.mongo.model.GtpTaskReportDetaiView;
import com.gome.upm.mongo.model.GtpTaskReportNode;
import com.gome.upm.mongo.model.GtpTaskReportViewIndex;
import com.gome.upm.mongo.repository.GtpTaskReportRepository;
import com.gome.upm.service.AutoShopProService;
import com.gome.upm.service.util.DateUtil;
import com.mysql.jdbc.StandardSocketFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-config.xml" })
public class MongoTest {
	
	@Resource
	private AutoShopProService autoShopProService;
	@Resource
	private GtpTaskReportRepository gtpTaskReportRepository;
	
	@Test
	public void getIndexTest(){
		GtpTaskReportViewIndex index = autoShopProService.getIndex();
		System.out.println(index.toString());
	}
	
	@Test
	public void queryListByTypeTest(){
		List<GtpTaskReport> index = autoShopProService.queryList2List(null, null,"normal");
		System.out.println(index.toString());
	}
	
	@Test
	public void getAspListViewTest(){
		List<GtpTaskReportDetaiView> list = autoShopProService.getAspListView(null,"all");
		for(GtpTaskReportDetaiView view : list){
			System.out.println(view.toString());
		}
		
	}
	
	@Test
	public void getAspListPaqgeTest(){
		Long startTime = DateUtil.getLongTimeDayStart2();
		Page<GtpTaskReportDetaiView> page = autoShopProService.getPage("normal", "fail", startTime, null, 1, 2);
		List<GtpTaskReportDetaiView> list = page.getData();
		for(GtpTaskReportDetaiView view : list){
			System.out.println(view.toString());
		}
		
	}
	
	@Test
	public void getNodesByIdTest(){
		List<GtpTaskReportNode> list = autoShopProService.getNodesById("57d5e40e1b142b914252c9fc","search");
		for(GtpTaskReportNode node : list){
			System.out.println(node.toString());
		}
	}
	
	@Test
	public void getNodesByIdTest2(){
		GtpTaskReport report = gtpTaskReportRepository.getById("57d5819a1b1423a08cf7cce3",10);
		System.out.println(report.getId());
	}


}
