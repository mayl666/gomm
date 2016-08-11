package com.gome.monitoringplatform.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gome.framework.util.JsonUtil;
@Controller
public class MonitoEcController extends AbsBaseController{
	@RequestMapping(value = "getEcData", method = RequestMethod.POST)
	public void getEcData(HttpServletRequest request, HttpServletResponse response) throws ParseException{
		Random random=new Random();
		List<Integer> reList=new ArrayList<Integer>();
		// 参数，开始时间，时间间隔
		// 开始时间
		String startTimeStr = request.getParameter("startTime");
		// 结束时间
		String endTimeStr = request.getParameter("endTime");
		Date startTime=formatter.parse(startTimeStr);
		//结束时间
		Date endTime=formatter.parse(endTimeStr);
		reList.add(random.nextInt(576416));
		reList.add(random.nextInt(576416));
		reList.add(random.nextInt(576416));
		reList.add(random.nextInt(576416));
		reList.add(random.nextInt(576416));
		String data = JsonUtil.toJson(reList);
		renderData(response, data);
	}
}
