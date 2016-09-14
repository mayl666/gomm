package com.gome.upm.controler;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gome.upm.common.Page;
import com.gome.upm.common.util.ResponsesDTO;
import com.gome.upm.constants.ReturnCode;
import com.gome.upm.mongo.model.GtpTaskReportDetaiView;
import com.gome.upm.mongo.model.GtpTaskReportNode;
import com.gome.upm.mongo.model.GtpTaskReportViewIndex;
import com.gome.upm.service.AutoShopProService;
import com.gome.upm.service.util.DateUtil;

/**
 * 自动化购物测试控制类
 * @author zhangzhixiang-ds
 *
 */


@Controller
@RequestMapping(value = "/asp")
public class AutoShopProController {
	
	private final static Logger logger = LoggerFactory.getLogger(AutoShopProController.class);
	
	@Resource
	private AutoShopProService autoShopProService;
	
	/**
	 * 自动化测试首页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		try {
			model.setViewName("/shoptest/index");
			model.addObject("leftMenu", "business.aspMenu");
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;

	}
	
	
 
	/**
	 * 自动化测试结果列表页面s
	 * @param request
	 * @param response
	 * @param model
	 * @param type      normal(默认)  search
	 * @param error     all(默认) pass fail 
	 * @return
	 */
	@RequestMapping(value = "/list/{type}/{state}", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			@PathVariable("type") String type,@PathVariable("state") String state) {
		try {
			logger.info("type:"+type+"|state:"+state);
			model.setViewName("/shoptest/aspList");
			model.addObject("leftMenu", "business.aspMenu");
			if(type.equals("search")){
				type="search";
			}else{
				type="normal";
			}
			
			if(!state.equals("fail") && !state.equals("pass")){
				state = "all";
			}
			model.addObject("type", type);
			model.addObject("state", state);
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;

	}
	
	
	/**
	 * 自动化测试购物历史记录页面
	 * @param request
	 * @param response
	 * @param model
	 * @param type       normal(默认)  search
	 * @return
	 */
	@RequestMapping(value = "/history/list/{type}", method = RequestMethod.GET)
	public ModelAndView historyList(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			@PathVariable("type") String type) {
		try {
			logger.info("type:"+type);
			Long firstStartTime = 1472832000000l;
			String monthBefore = DateUtil.getMonthBefore();
			String datStart = DateUtil.getDayBefore();
			if(DateUtil.getLongTimeMonthBeforeStart2() < firstStartTime){
				monthBefore = "2016-09-03 00:00:00";
			}
			model.setViewName("/shoptest/aspListHistory");
			model.addObject("leftMenu", "business.aspMenu");
			if(type.equals("search")){
				type="search";
			}else{
				type="normal";
			}
			
			model.addObject("type", type);
			model.addObject("monthBefore", monthBefore);
			model.addObject("dayStart", datStart);
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;

	}
	
	
	
	/**
	 * 异步获取数据
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/index/ajax", method = { RequestMethod.POST }, produces = "application/json;charset=utf-8")
	public ResponsesDTO save(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try {
			GtpTaskReportViewIndex index = autoShopProService.getIndex();
			res.setAttach(index);
			
		} catch (Exception e) {
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}

		return res;

	}
	
	
	/**
	 * 获取列表table
	 * @param request
	 * @param response
	 * @param model
	 * @param type   normal search
	 * @param status pass fail all
	 * @return
	 */
	@RequestMapping(value = "/list/table", method = { RequestMethod.POST})
	public ModelAndView listTable(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			@RequestParam(value="type", required = true) String type, @RequestParam(value="state", required = true) String state) {
		    model.setViewName("/shoptest/aspListTable");
		try {
			
			List<GtpTaskReportDetaiView> list = autoShopProService.getAspListView(type, state);
			model.addObject("list", list);
			
		} catch (Exception e) {
			logger.error("系统出现异常", e);
			model.setViewName("/error");
		}

		return model;

	}
	
	
	
	/**
	 * 获取历史数据
	 * @param request
	 * @param response
	 * @param model
	 * @param type           normal search
	 * @param state          pass fail all
	 * @param startTime
	 * @param endTime
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/history/list/table", method = { RequestMethod.POST})
	public ModelAndView historyListTable(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			@RequestParam(value="type", required = true) String type, @RequestParam(value="state", required = true) String state
			, @RequestParam(value="startTime", required = false) String startTime, @RequestParam(value="endTime", required = false) String endTime
			, @RequestParam(value="pageNo", required = true) Integer pageNo, @RequestParam(value="pageSize", required = true) Integer pageSize) {
		
			logger.info("type:"+type+"|state:"+state+"|startTime:"+startTime+"|endTime:"+endTime+"|pageNo:"+pageNo+"|pageSize:"+pageSize);
		    model.setViewName("/shoptest/aspListHistoryTable");
		try {
			Long sTime = null;
			Long eTime = null;
			if(StringUtils.isNotEmpty(startTime)){
				sTime = DateUtil.getLongTimeFromStr(startTime, "yyyy-MM-dd HH:mm:ss");
			}
			if(StringUtils.isNotEmpty(endTime)){
				eTime = DateUtil.getLongTimeFromStr(endTime, "yyyy-MM-dd HH:mm:ss");
			} 
			Page<GtpTaskReportDetaiView> page = autoShopProService.getPage(type, state, sTime, eTime, pageNo, pageSize);
			model.addObject("page", page);
			model.addObject("startTime", startTime);
			model.addObject("endTime", endTime);
			model.addObject("state", state);
			
		} catch (Exception e) {
			logger.error("系统出现异常", e);
			model.setViewName("/error");
		}

		return model;

	}
	
	
	/**
	 * 获取流程图
	 * @param request
	 * @param response
	 * @param model
	 * @param id        主键id
	 * @param type      normal search
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getFlow", method = { RequestMethod.POST }, produces = "application/json;charset=utf-8")
	public ResponsesDTO getFlow(HttpServletRequest request, HttpServletResponse response, ModelAndView model, @RequestParam(value="id", required = true) String id
			, @RequestParam(value="type", required = true) String type) {
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try {
			List<GtpTaskReportNode> list = autoShopProService.getNodesById(id, type);
			res.setAttach(list);
			
		} catch (Exception e) {
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}

		return res;

	}
	
	
	
	

}
