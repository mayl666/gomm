package com.gome.pricemonitor.controler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gome.pricemonitor.controler.base.BaseController;

/**
 * 首页控制器.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月16日    caowei    新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/index")
public class IndexController extends BaseController{

	/**
	 * 跳转到首页.
	 *
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月16日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/toIndex", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView toAddAdView(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/index");
		return model;
	}
}
