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
import com.gome.monitoringplatform.dao.MoLoginInfoDAO;
import com.gome.monitoringplatform.model.bo.MoLoginInfoBO;
import com.gome.monitoringplatform.slave1.dao.GomeLoginInfoDAO;

/**
 * 
 *
 * @author fangjinwei
 */
@Controller
public class MonitoLoginController extends AbsBaseController {
	@Autowired
	private MoLoginInfoDAO moLoginInfoDAO;
	@Autowired
	private GomeLoginInfoDAO gomeLoginInfoDAO;
	public void setMoLoginInfoDAO(MoLoginInfoDAO moLoginInfoDAO) {
		this.moLoginInfoDAO = moLoginInfoDAO;
	}
	public void setGomeLoginInfoDAO(GomeLoginInfoDAO gomeLoginInfoDAO) {
		this.gomeLoginInfoDAO = gomeLoginInfoDAO;
	}
	@RequestMapping(value = "findLoginInfoList", method = RequestMethod.POST)
	public void findLoginInfoList(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		List<Object[]> reList=new ArrayList<Object[]>();
		// 参数，开始时间，时间间隔
		// 开始时间
		String startTimeStr = request.getParameter("startTime");
		// 结束时间
		String endTimeStr = request.getParameter("endTime");
		Date startTime=formatter.parse(startTimeStr);
		//结束时间
		Date endTime=formatter.parse(endTimeStr);
		Map<Long,Integer> timeMap=new HashMap<Long,Integer>();
		getSeriesByTime(startTime, endTime, timeMap);
		while(startTime.getTime()<=endTime.getTime()){
			//添加数据
			Integer v1=timeMap.get(startTime.getTime());
			reList.add(new Object[]{startTime.getTime(),v1==null?0:v1});
			//+5分钟
			startTime = new Date(startTime .getTime() + 300000);
		}
		String data = JsonUtil.toJson(reList);
		renderData(response, data);
	}
	@RequestMapping(value = "getLoginInfoForTime", method = RequestMethod.POST)
	public void getLoginInfoForTime(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		// 开始时间
		long startTimelong =Long.parseLong(request.getParameter("startTime"));
		//计算5分钟后的时间
		Date endTime=new Date(new Date(startTimelong).getTime() + 300000);
		
		System.out.println("getLoginInfoForTime==============="+formatter.format(endTime));
		
		Map<Long,Integer> timeMap=new HashMap<Long,Integer>();
		getObject(endTime, timeMap);
		Integer v3=timeMap.get(endTime.getTime());
		String data = JsonUtil.toJson(new Object[]{endTime.getTime(),v3==null?0:v3});
		renderData(response, data);
	}
	private void getSeriesByTime(Date startTime,Date endTime,Map<Long,Integer> map) throws ParseException{
		MoLoginInfoBO searchbo=new MoLoginInfoBO();
		searchbo.setStartTime(startTime);
		searchbo.setEndTime(endTime);
		List<MoLoginInfoBO> li=moLoginInfoDAO.searchMoLoginInfoList(searchbo);
		for(MoLoginInfoBO bo:li){
			map.put(bo.getStartTime().getTime(), bo.getCount());
		}
	}
	private void getObject(Date endTime,Map<Long,Integer> timeMap) throws ParseException{
//		MoLoginInfoBO searchBo=new MoLoginInfoBO();
//		searchBo.setStartTime(endTime);
//		Integer count= moLoginInfoDAO.searchCountByTime(searchBo);
		MoLoginInfoBO gomeLoginInfoVO=new MoLoginInfoBO();
		Date startTime=new Date(endTime.getTime()-300000);
		gomeLoginInfoVO.setStartTime(startTime);
		gomeLoginInfoVO.setEndTime(endTime);
		Integer count1=gomeLoginInfoDAO.searchCountByTime(gomeLoginInfoVO);
		timeMap.put(endTime.getTime(), count1);
	}
}
