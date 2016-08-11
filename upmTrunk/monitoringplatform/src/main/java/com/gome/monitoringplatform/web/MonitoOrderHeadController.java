package com.gome.monitoringplatform.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gome.framework.util.JsonUtil;

@Controller
public class MonitoOrderHeadController extends AbsBaseController{
	/**
	 * 热销商品排行榜
	 * @param request
	 * @param response
	 * @throws ParseException
	 */
	@RequestMapping(value = "findHotGoodList", method = RequestMethod.POST)
	public void findHotGoodList(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		List<Object[]> reList=new ArrayList<Object[]>();
		String date=formatter.format(new Date()).split(" ")[0];
		Date startTime=formatter.parse(date+" 00:00:00");
		//结束时间
		Date endTime=formatter.parse(date+" 23:59:59");
		Map<Long,Integer> timeMap=new HashMap<Long,Integer>();
//		getSeriesByTime(startTime, endTime, timeMap);
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
}
