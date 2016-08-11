package com.gome.monitoringplatform.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gome.framework.util.JsonUtil;
import com.gome.monitoringplatform.model.vo.MonitoVO;
@Controller
public class MonitoCpsController extends AbsBaseController{
	private Integer intervalTime=1000*60*30;
	@RequestMapping(value = "findCpsDataList", method = RequestMethod.POST)
	public void findCpsDataList(HttpServletRequest request, HttpServletResponse response) throws ParseException{
		Random random=new Random();
		List<Object[]> reList=new ArrayList<Object[]>();
		// 参数，开始时间，时间间隔
		// 开始时间
		String startTimeStr = request.getParameter("startTime");
		// 结束时间
		String endTimeStr = request.getParameter("endTime");
		System.out.println("cps-------开始时间："+startTimeStr);
		System.out.println("cps-------结束时间："+endTimeStr);
		
		
		Date startTime=formatter.parse(startTimeStr);
		//结束时间
		Date endTime=formatter.parse(endTimeStr);
//		Map<Long,Integer> timeMap=new HashMap<Long,Integer>();
//		getSeriesByTime(startTime, endTime, timeMap);
		while(startTime.getTime()<=endTime.getTime()){
			//添加数据
//			Integer v1=timeMap.get(startTime.getTime());
			Integer v1=random.nextInt(165324);
			reList.add(new Object[]{startTime.getTime(),v1==null?0:v1});
			//+30分钟1000*60*30
			startTime = new Date(startTime .getTime() + intervalTime);
		}
		String data = JsonUtil.toJson(reList);
		renderData(response, data);
	}
	@RequestMapping(value = "getCpsDataForTime", method = RequestMethod.POST)
	public void getCpsDataForTime(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		// 开始时间
		long startTimelong =Long.parseLong(request.getParameter("startTime"));
		//5分钟后
		Date endTime=new Date(new Date(startTimelong).getTime() + intervalTime);
		Map<Long,Integer> timeMap=new HashMap<Long,Integer>();
		System.out.println("getCpsDataForTime==============="+formatter.format(endTime));
		//今天
		MonitoVO notPayVO=new MonitoVO();
		notPayVO.setStartTime(new Date(startTimelong));
		notPayVO.setEndTime(endTime);
//		Integer v1=notPayDAO.searchCountByTime(notPayVO);
		Random random=new Random();
		Integer v1=random.nextInt(165324);
		Object[] reObj=new Object[]{endTime.getTime(),v1==null?0:v1};
		renderData(response, JsonUtil.toJson(reObj));
	}
}
