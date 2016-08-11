package com.gome.pricemonitor.controler;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gome.pricemonitor.common.util.CookieUtil;
import com.gome.pricemonitor.common.util.ResponsesDTO;
import com.gome.pricemonitor.constants.ReturnCode;
import com.gome.pricemonitor.domain.ManagerFunc;
import com.gome.pricemonitor.service.ManagerRoleService;
import com.gome.pricemonitor.controler.base.BaseController;

@Controller
//@RequestMapping(value = "/")
public class HomeController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Resource
	private ManagerRoleService managerRoleService;
	private CookieUtil cookieUtil=new CookieUtil();
	
	@RequestMapping(value = "/", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		model.setViewName("redirect:/login");
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = "/left", method = { RequestMethod.POST, RequestMethod.GET })
	public ResponsesDTO del(HttpServletRequest request, HttpServletResponse response) {

		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		
		try {
			String userId = this.getUserInfo(request, "userId");
			if(StringUtils.isBlank(userId)){
				res.setReturnCode(ReturnCode.ERROR_NO_LOGIN);
			}
			
			List<ManagerFunc> list = managerRoleService.getFuncForLeftMenu(Long.parseLong(userId));
			res.setAttach(list);
			//String loginName = this.getUserInfo(request, "loginName");

			//managerRoleService.del(roleId);
		} catch (Exception e) {
			logger.info("系统出现异常", e);
			res.setReturnCode(ReturnCode.ERROR_SERVER);
		}

		return res;
	}
	
	/**
	 * 登录
	 * @param request
	 * @param response
	 * @param condition
	 * @param roleId
	 * @param pageNo
	 * @param pageSize
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		model.setViewName("/login");
		//String cookieToken = cookieUtil.getCookieValues(request, "token");
		String accountNameStr = (String) request.getSession().getAttribute("userName");
		//String accountNameStr=cookieUtil.getCookieValues(request, "userName");
		if(!StringUtils.isEmpty(accountNameStr)){
			model.setViewName("redirect:/price/monitor/index");
		}
    	//是否已经登录
//    	if(StringUtils.isBlank(accountNameStr)){
//    		request.getSession().setAttribute("userName", accountNameStr);
//    		//是否是记住密码(是否有token)
//	    	if(!StringUtils.isBlank(cookieToken)){
//	    		request.getSession().setAttribute("passwd", "******");
//	    	}
//    	}else{
//    		model.setViewName("redirect:/index/toIndex");
//    	}
    	
    	
    	return model;
	}

}
