package com.gome.upm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.gome.upm.common.Page;
import com.gome.upm.mongo.model.GtpTaskReport;
import com.gome.upm.mongo.model.GtpTaskReportDetaiView;
import com.gome.upm.mongo.model.GtpTaskReportDetail;
import com.gome.upm.mongo.model.GtpTaskReportNode;
import com.gome.upm.mongo.model.GtpTaskReportViewIndex;
import com.gome.upm.mongo.repository.GtpTaskReportRepository;
import com.gome.upm.service.AutoShopProService;
import com.gome.upm.service.util.DateUtil;

@Service("autoShopProService")
public class AutoShopProServiceImpl implements AutoShopProService {

	@Resource
	private MongoTemplate mongoTemplate;
	@Resource
	private GtpTaskReportRepository gtpTaskReportRepository;

	@Override
	public List<GtpTaskReport> queryList2Index(Long startTime) {
		Query query = new Query();
		if (startTime == null) {
			query.addCriteria(new Criteria("startTime").gte(1472832000000l));// 20160903
																				// 时间戳
		} else {
			query.addCriteria(new Criteria("startTime").gte(startTime));
		}

		query.fields().include("details").include("startTime");
		return mongoTemplate.find(query, GtpTaskReport.class);
	}

	@Override
	public GtpTaskReportViewIndex getIndex() {
		Long startTime = DateUtil.getLongTimeDayStart2();
		List<GtpTaskReport> list = this.queryList2Index(startTime);
		GtpTaskReportViewIndex index = this.dealReportDetail(list);
		return index;
	}

	private GtpTaskReportViewIndex dealReportDetail(List<GtpTaskReport> list) {
		GtpTaskReportViewIndex view = new GtpTaskReportViewIndex();
		String[] createTime = new String[list.size()];// 折线图横
		int[] totalProTime1 = new int[list.size()];// 折线图纵
		int[] totalProTime2 = new int[list.size()];// 折线图纵；
		int totalFailCount1 = 0;
		int totalFailCount2 = 0;
		int[] nodeFailCount1 = new int[9];
		int[] nodeFailCount2 = new int[9];
		int node11 = 0;
		int node12 = 0;
		int node13 = 0;
		int node14 = 0;
		int node15 = 0;
		int node16 = 0;
		int node17 = 0;
		int node18 = 0;
		int node19 = 0;
		int node21 = 0;
		int node22 = 0;
		int node23 = 0;
		int node24 = 0;
		int node25 = 0;
		int node26 = 0;
		int node27 = 0;
		int node28 = 0;
		int node29 = 0;
		for (int i = 0; i < list.size(); i++) {
			GtpTaskReport report = list.get(i);
			Long startTime = report.getStartTime();
			createTime[i] = DateUtil.LongToStr(startTime, "HH:mm");
			int duration1 = 0;
			int duration2 = 0;
			List<GtpTaskReportDetail> listDetail = report.getDetails();
			int oneFailCount1 = 0;
			int oneFailCount2 = 0;
			for (GtpTaskReportDetail detail : listDetail) {
				String caseDesc = detail.getCaseDesc();// 测试节点名称
				String testResult = detail.getTestResult();// 测试结果
				int durationDetail = detail.getDuration();
				if (StringUtils.isEmpty(caseDesc) || StringUtils.isEmpty(testResult)) {
					continue;// 结束本次循环
				}

				switch (caseDesc) {
				case "1-1ClearCooik":
					duration1 = duration1 + durationDetail;
					if (testResult.equals("fail")) {
						node11++;
						oneFailCount1++;

					}
					break;
				case "1-2Login":
					duration1 = duration1 + durationDetail;
					if (testResult.equals("fail")) {
						node12++;
						oneFailCount1++;

					}
					break;
				case "1-3ClearCart":
					duration1 = duration1 + durationDetail;
					if (testResult.equals("fail")) {
						node13++;
						oneFailCount1++;

					}
					break;
				case "1-4AddCart":
					duration1 = duration1 + durationDetail;
					if (testResult.equals("fail")) {
						node14++;
						oneFailCount1++;

					}
					break;
				case "1-5GoCartToPay":
					duration1 = duration1 + durationDetail;
					if (testResult.equals("fail")) {
						node15++;
						oneFailCount1++;

					}
					break;
				case "1-6 ChinaUnionPay":
					duration1 = duration1 + durationDetail;
					if (testResult.equals("fail")) {
						node16++;
						oneFailCount1++;

					}
					break;
				case "1-7Alipay":
					duration1 = duration1 + durationDetail;
					if (testResult.equals("fail")) {
						node17++;
						oneFailCount1++;

					}
					break;
				case "1-8KuaiQianPay":
					duration1 = duration1 + durationDetail;
					if (testResult.equals("fail")) {
						node18++;
						oneFailCount1++;

					}
					break;
				case "1-9CancelOrder":
					duration1 = duration1 + durationDetail;
					if (testResult.equals("fail")) {
						node19++;
						oneFailCount1++;

					}
					break;
				case "2-1ClearCooike":
					duration2 = duration2 + durationDetail;
					if (testResult.equals("fail")) {
						node21++;
						oneFailCount2++;

					}
					break;
				case "2-2Login":
					duration2 = duration2 + durationDetail;
					if (testResult.equals("fail")) {
						node22++;
						oneFailCount2++;

					}
					break;
				case "2-3ClearCart":
					duration2 = duration2 + durationDetail;
					if (testResult.equals("fail")) {
						node23++;
						oneFailCount2++;

					}
					break;
				case "2-4Search":
					duration2 = duration2 + durationDetail;
					if (testResult.equals("fail")) {
						node24++;
						oneFailCount2++;

					}
					break;
				case "2-5GoCartToPay":
					duration2 = duration2 + durationDetail;
					if (testResult.equals("fail")) {
						node25++;
						oneFailCount2++;

					}
					break;
				case "2-6 ChinaUnionPay":
					duration2 = duration2 + durationDetail;
					if (testResult.equals("fail")) {
						node26++;
						oneFailCount2++;

					}
					break;
				case "2-7Alipay":
					duration2 = duration2 + durationDetail;
					if (testResult.equals("fail")) {
						node27++;
						oneFailCount2++;

					}
					break;
				case "2-8KuaiQianPay":
					duration2 = duration2 + durationDetail;
					if (testResult.equals("fail")) {
						node28++;
						oneFailCount2++;

					}
					break;
				case "2-9CancelOrder":
					duration2 = duration2 + durationDetail;
					if (testResult.equals("fail")) {
						node29++;
						oneFailCount2++;

					}
					break;
				default:
					break;

				}

			}
			if (oneFailCount1 > 0) {
				totalFailCount1++;
			}
			if (oneFailCount2 > 0) {
				totalFailCount2++;
			}

			totalProTime1[i] = duration1 / 60000;
			totalProTime2[i] = duration2 / 60000;
		}
		nodeFailCount1[0] = node11;
		nodeFailCount1[1] = node12;
		nodeFailCount1[2] = node13;
		nodeFailCount1[3] = node14;
		nodeFailCount1[4] = node15;
		nodeFailCount1[5] = node16;
		nodeFailCount1[6] = node17;
		nodeFailCount1[7] = node18;
		nodeFailCount1[8] = node19;
		nodeFailCount2[0] = node21;
		nodeFailCount2[1] = node22;
		nodeFailCount2[2] = node23;
		nodeFailCount2[3] = node24;
		nodeFailCount2[4] = node25;
		nodeFailCount2[5] = node26;
		nodeFailCount2[6] = node27;
		nodeFailCount2[7] = node28;
		nodeFailCount2[8] = node29;
		view.setNodeFailCount1(nodeFailCount1);
		view.setNodeFailCount2(nodeFailCount2);
		view.setTotalFailCount1(totalFailCount1);
		view.setTotalFailCount2(totalFailCount2);
		view.setTotalCount(list.size());
		view.setProTime1(totalProTime1);
		view.setProTime2(totalProTime2);
		view.setCreateTime(createTime);
		return view;
	}

	@Override
	public List<GtpTaskReport> queryList2List(Long startTime, Long endTime, String type) {
		Query query = new Query();
		if(startTime == null){
			startTime = 1472832000000l;
		}
		if(endTime == null){
			query.addCriteria(new Criteria("startTime").gte(startTime));
		}else{
			query.addCriteria(new Criteria().andOperator(new Criteria("startTime").gte(startTime),new Criteria("startTime").lte(endTime)));
		}

		query.fields().include("details").include("startTime").include("endTime").include("resultFilePath");
		if (type != null) {
			if (type.equals("normal")) {
				query.fields().slice("details", 9);
			} else if (type.equals("search")) {
				query.fields().slice("details", -9);
			}
		}
		query.with(new Sort(Direction.DESC, "startTime"));
		return mongoTemplate.find(query, GtpTaskReport.class);
	}

	@Override
	public List<GtpTaskReportDetaiView> getAspList(String type, String status, Long startTime, Long endTime) {
		List<GtpTaskReportDetaiView> list = new ArrayList<GtpTaskReportDetaiView>();
		if (StringUtils.isEmpty(status)) {
			return list;
		}

		List<GtpTaskReport> listReport = this.queryList2List(startTime, endTime, type);
		GtpTaskReportDetaiView reportDetailView = null;
		for (GtpTaskReport report : listReport) {
			reportDetailView = new GtpTaskReportDetaiView();
			reportDetailView.setId(report.getId());
			reportDetailView.setEndTime(DateUtil.LongToStr(report.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
			reportDetailView.setStartTime(DateUtil.LongToStr(report.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
			reportDetailView.setLogAdress(this.getReportFtpAddress(report.getResultFilePath()));
			List<GtpTaskReportDetail> listdetails = report.getDetails();
			int state = GtpTaskReportDetail.NORMAL_STATE;
			int usedTime = 0;
			for (GtpTaskReportDetail reportDetail : listdetails) {
				int duration = reportDetail.getDuration();
				String testResult = reportDetail.getTestResult();
				usedTime = usedTime + duration;
				if (testResult.equals("fail")) {
					state = GtpTaskReportDetail.FAIL_STATE;
				}
			}
			reportDetailView.setState(state);
			reportDetailView.setUsedTime(usedTime / 1000);

			if (status.equals("all")) {
				list.add(reportDetailView);
			} else if (status.endsWith("pass")) {
				if (state == GtpTaskReportDetail.NORMAL_STATE) {
					list.add(reportDetailView);
				}

			} else if (status.endsWith("fail")) {
				if (state == GtpTaskReportDetail.FAIL_STATE) {
					list.add(reportDetailView);
				}
			}

		}
		return list;
	}

	@Override
	public List<GtpTaskReportDetaiView> getAspListView(String type, String status) {
		Long startTime = DateUtil.getLongTimeDayStart2();
		return this.getAspList(type, status, startTime, null);
	}

	private String getReportFtpAddress(String address) {
		String result = "";
		Pattern pattern = Pattern.compile("\\d+_\\d_\\d_\\d+");
		Matcher matcher = pattern.matcher(address);
		while (matcher.find()) {
			result = matcher.group(0);
			break;
		}
		return result;
	}

	@Override
	public Page<GtpTaskReportDetaiView> getPage(String type, String status, Long startTime, Long endTime,
			Integer pageNo, Integer pageSize) {
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = 1;
		}
		Page<GtpTaskReportDetaiView> page = new Page<GtpTaskReportDetaiView>(pageNo, pageSize);
		List<GtpTaskReportDetaiView> listView = new ArrayList<GtpTaskReportDetaiView>();
		List<GtpTaskReportDetaiView> list = this.getAspList(type, status, startTime, endTime);
		int totalCount = list.size();
		int start = (pageNo - 1) * pageSize;
		int end = start + pageSize;
		int totalPage = (totalCount + pageSize - 1)/pageSize;
		if(end > totalCount){
			end = totalCount;
		}
		if(start < totalCount && start < end){
			listView.addAll(list.subList(start, end));
		}
		page.setData(listView);
		page.setTotalResult(totalCount);
		page.setTotalPage(totalPage);
		return page;
	}

	@Override
	public List<GtpTaskReportNode> getNodesById(String id, String type) {
		List<GtpTaskReportNode> list = new ArrayList<GtpTaskReportNode>();
		GtpTaskReportNode node = null;
		int slice = 9;
		if(!StringUtils.isEmpty(type) && type.equals("search")){
			slice = -9;
		}
		GtpTaskReport report = gtpTaskReportRepository.getById(id, slice);
		List<GtpTaskReportDetail> listDetail = report.getDetails();
		for(GtpTaskReportDetail reportDetail : listDetail){
			node = new GtpTaskReportNode();
			node.setName(reportDetail.getCaseDesc());
			node.setResult(reportDetail.getTestResult());
			node.setUsedTime(reportDetail.getDuration()/1000);
			list.add(node);
		}
		return list;
	}

}
