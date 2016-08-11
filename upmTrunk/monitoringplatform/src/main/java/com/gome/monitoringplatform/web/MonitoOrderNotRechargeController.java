package com.gome.monitoringplatform.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gome.framework.util.JsonUtil;
import com.gome.monitoringplatform.dao.MoOrderNotRechargeDAO;
import com.gome.monitoringplatform.model.bo.MoOrderNotRechargeBO;
import com.gome.monitoringplatform.slave1.dao.NotPayDAO;
/**
 * 非充值订单监控
 * @author fangjinwei
 */
@Controller
public class MonitoOrderNotRechargeController extends AbsBaseController{
	@Autowired
	private MoOrderNotRechargeDAO moOrderNotRechargeDAO;
	@Autowired
	private NotPayDAO notPayDAO;
	public void setMoOrderNotRechargeDAO(MoOrderNotRechargeDAO moOrderNotRechargeDAO) {
		this.moOrderNotRechargeDAO = moOrderNotRechargeDAO;
	}
	public void setNotPayDAO(NotPayDAO notPayDAO) {
		this.notPayDAO = notPayDAO;
	}
	@RequestMapping(value = "findNotRechargeOrderList", method = RequestMethod.POST)
	public void findNotRechargeOrderList(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		// 参数，开始时间，时间间隔
		// 开始时间
		String startTimeStr = request.getParameter("startTime");
		// 结束时间
		String endTimeStr = request.getParameter("endTime");
		List<List<Object[]>> reList=new ArrayList<List<Object[]>>();
		Date startTime=formatter.parse(startTimeStr);
		Date endTime=formatter.parse(endTimeStr);
		Map<Long,Integer> timeMap=new HashMap<Long,Integer>();
		List<Object[]> l1=new ArrayList<Object[]>();
		List<Object[]> l2=new ArrayList<Object[]>();
		List<Object[]> l3=new ArrayList<Object[]>();
		//今天----"当前5分钟非充值订单"
		getSeriesByTime(startTime, endTime, timeMap);
		//昨天--"昨天5分钟非充值订单"
		Date startYesterdayTime=new Date(startTime.getTime()-1000*60*60*24);
		Date endYesterdayTime=new Date(endTime.getTime()-1000*60*60*24);
		getSeriesByTime(startYesterdayTime, endYesterdayTime, timeMap);
		//一周前--"7天前5分钟非充值订单"
		Date startWeekTime=new Date(startTime.getTime()-1000*60*60*24*7);
		Date endWeekTime=new Date(endTime.getTime()-1000*60*60*24*7);
		getSeriesByTime(startWeekTime, endWeekTime, timeMap);
		//初始化数据
		while(startTime.getTime()<=endTime.getTime()){
			//添加数据
			Integer v1=timeMap.get(startTime.getTime());
			l1.add(new Object[]{startTime.getTime(),v1==null?0:v1});
			Integer v2=timeMap.get(startTime.getTime()-1000*60*60*24);
			l2.add(new Object[]{startTime.getTime(),v2==null?0:v2});
			Integer v3=timeMap.get(startTime.getTime()-1000*60*60*24*7);
			l3.add(new Object[]{startTime.getTime(),v3==null?0:v3});
			//+5分钟
			startTime = new Date(startTime .getTime() + 300000);
		}
		reList.add(l1);
		reList.add(l2);
		reList.add(l3);
		renderData(response, JsonUtil.toJson(reList));
	}
	@RequestMapping(value = "getNotRechargeOrderForTime", method = RequestMethod.POST)
	public void getNotRechargeOrderForTime(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		List<Object[]> reList=new ArrayList<Object[]>();
		// 开始时间
		long startTimelong =Long.parseLong(request.getParameter("startTime"));
		//5分钟后
		Date endTime=new Date(new Date(startTimelong).getTime() + 300000);
		Map<Long,Integer> timeMap=new HashMap<Long,Integer>();
		System.out.println("getNotRechargeOrderForTime==============="+formatter.format(endTime));
		//今天
		MoOrderNotRechargeBO notPayVO=new MoOrderNotRechargeBO();
		notPayVO.setStartTime(new Date(startTimelong));
		notPayVO.setEndTime(endTime);
		Integer v1=notPayDAO.searchCountByTime(notPayVO);
		//昨天
		Date startYesterdayTime=new Date(endTime.getTime()-1000*60*60*24);
		getObject(startYesterdayTime, timeMap);
		Integer v2=timeMap.get(startYesterdayTime.getTime());
		//前天
		Date startWeekTime=new Date(endTime.getTime()-1000*60*60*24*7);
		getObject(startWeekTime, timeMap);
		Integer v3=timeMap.get(startWeekTime.getTime());
		
		reList.add(new Object[]{endTime.getTime(),v1==null?0:v1});
		reList.add(new Object[]{endTime.getTime(),v2==null?0:v2});
		reList.add(new Object[]{endTime.getTime(),v3==null?0:v3});
		renderData(response, JsonUtil.toJson(reList));
	}
	private void getObject(Date startTime,Map<Long,Integer> timeMap) throws ParseException{
		MoOrderNotRechargeBO searchBo=new MoOrderNotRechargeBO();
		searchBo.setStartTime(startTime);
		Integer count= moOrderNotRechargeDAO.searchCountByTime(searchBo);
		timeMap.put(startTime.getTime(), count);
	}
	private void getSeriesByTime(Date startTime,Date endTime,Map<Long,Integer> map) throws ParseException{
		MoOrderNotRechargeBO searchbo=new MoOrderNotRechargeBO();
		searchbo.setStartTime(startTime);
		searchbo.setEndTime(endTime);
		List<MoOrderNotRechargeBO> li=moOrderNotRechargeDAO.searchMoOrderNotRechargeList(searchbo);
		for(MoOrderNotRechargeBO bo:li){
			map.put(bo.getStartTime().getTime(), bo.getCount());
		}
	}
}
