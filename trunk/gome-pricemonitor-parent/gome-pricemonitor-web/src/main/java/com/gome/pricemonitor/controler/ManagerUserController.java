package com.gome.pricemonitor.controler;

import java.net.URLDecoder;
import java.util.Date;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gome.pricemonitor.common.util.AppConfigUtil;
import com.gome.pricemonitor.common.util.DateUtils;
import com.gome.pricemonitor.common.util.Page;
import com.gome.pricemonitor.common.util.ResponsesDTO;
import com.gome.pricemonitor.constants.ReturnCode;
import com.gome.pricemonitor.domain.ManagerRole;
import com.gome.pricemonitor.domain.ManagerUser;
import com.gome.pricemonitor.domain.ManagerUserRole;
import com.gome.pricemonitor.service.ManagerRoleService;
import com.gome.pricemonitor.service.ManagerUserRoleService;
import com.gome.pricemonitor.service.ManagerUserService;
import com.gome.pricemonitor.controler.base.BaseController;

@Controller
@RequestMapping(value = "/user")
public class ManagerUserController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(ManagerUserController.class);

	@Resource
	private ManagerUserService managerUserService;
	@Resource
	private ManagerRoleService managerRoleService;
	@Resource
	private ManagerUserRoleService managerUserRoleService;

	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView list(
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			HttpServletRequest request, HttpServletResponse response,
			ModelAndView model) {

		model.setViewName("/user/powerManagement");

		if (pageNo == null || pageNo < 1) {
			pageNo = AppConfigUtil.getIntValue("pageNo");
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = AppConfigUtil.getIntValue("pageSize");
		}

		try {
			List<ManagerRole> list = managerRoleService
					.getList(ManagerRole.ROLE_NORMAL);
			Page<ManagerUser> page = managerUserService.query(null, null,
					pageNo, pageSize);
			model.addObject("page", page);
			model.addObject("list", list);
			model.addObject("roleId", 0);
			model.addObject("condition", "");
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;
	}

	@RequestMapping(value = "/add", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response, ModelAndView model) {

		model.setViewName("/user/add-Account");

		try {
			String loginName = this.getUserInfo(request, "userName");

			List<ManagerRole> list = managerRoleService
					.getList(ManagerRole.ROLE_NORMAL);
			model.addObject("list", list);
			model.addObject("loginName", loginName);
			model.addObject("currentTime", DateUtils.format(new Date()));
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;
	}

	/*
	 * @RequestMapping(value = "/edit/{userId}", method = { RequestMethod.GET,
	 * RequestMethod.POST }, produces = "application/json;charset=utf-8") public
	 * ModelAndView edit(HttpServletRequest request, HttpServletResponse
	 * response, ModelAndView model, @PathVariable("userId") Long userId) {
	 */

	@RequestMapping(value = "/edit", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response, ModelAndView model,
			@RequestParam(value = "userId", required = true) Long userId) {

		model.setViewName("/user/edit-account");

		try {
			String loginName = this.getUserInfo(request, "userName");
			ManagerUserRole userRole = managerUserRoleService
					.getByUserId(userId);
			ManagerUser user = managerUserService.selectById(userId);
			List<ManagerRole> list = managerRoleService
					.getList(ManagerRole.ROLE_NORMAL);
			model.addObject("userRoleId",
					userRole == null ? 0 : userRole.getRoleId());
			model.addObject("user", user);
			model.addObject("list", list);
			model.addObject("loginName", loginName);
			if(user.getUpdateTime() != null){
				model.addObject("updateTime", DateUtils.format(user.getUpdateTime()));
			}
			model.addObject("createTime", DateUtils.format(user.getCreateTime()));
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;
	}

	/**
	 * url: ip:port/manager/ajax/user/query 查询
	 * {"condition":"xiaozhang","roleId":2,"pageNo":1,"pageSize":4}
	 * 
	 * @param request
	 * @param response
	 * @param content
	 * @return
	 */

	@RequestMapping(value = "/query", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView query(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "roleId", required = false) Long roleId,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			ModelAndView model) {

		model.setViewName("/user/powerManagement");

		try {
            String term = "";
			if (StringUtils.isBlank(condition)) {
				
				condition = null;
			}else{
				condition = URLDecoder.decode(condition,"UTF-8");
				term = condition;
				if(condition.contains("%")){
					condition = condition.replace("%", "\\%");
				}
				if(condition.contains("_")){
					condition = condition.replace("_", "\\_");
				}
			}

			if (roleId == null || roleId == 0) {
				roleId = null;
			}

			if (pageNo == null || pageNo < 1) {
				pageNo = AppConfigUtil.getIntValue("pageNo");
			}
			if (pageSize == null || pageSize < 1) {
				pageSize = AppConfigUtil.getIntValue("pageSize");
			}

			List<ManagerRole> list = managerRoleService
					.getList(ManagerRole.ROLE_NORMAL);
			Page<ManagerUser> page = managerUserService.query(condition,
					roleId, pageNo, pageSize);
			model.addObject("list", list);
			model.addObject("page", page);
			model.addObject("roleId", roleId);
			model.addObject("condition", term);
		} catch (Exception e) {
			logger.info("系统出现异常", e);
			model.setViewName("error");
		}

		return model;
	}

	@RequestMapping(value = "/upPasswd", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView upPasswd(HttpServletRequest request,
			HttpServletResponse response, ModelAndView model) {

		model.setViewName("/user/myPassword-edit");

		return model;
	}

	@RequestMapping(value = "/upMyAccount", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView upMyAccount(HttpServletRequest request,
			HttpServletResponse response, ModelAndView model) {

		model.setViewName("/user/myAccunt-edit");
		
		
		try {
			String userId = this.getUserInfo(request, "userId");
			ManagerUser muser =	managerUserService.selectById(Long.parseLong(userId));
			model.addObject("muser", muser);
		} catch (Exception e) {
			logger.info("系统出现异常", e);
			model.setViewName("error");
		}
		return model;
	}
	
	
	/**
	 * 退出登录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/loginOut", method = { RequestMethod.GET })
	public String loginOut(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {

		//model.setViewName("/login");

		try {

			//deleteUserInfo("token", null, request, response);
			//deleteUserInfo("userName", null, request, response);
//            request.getSession().removeAttribute("token");
//            request.getSession().removeAttribute("userName");
//            request.getSession().removeAttribute("passwd");
			request.getSession().invalidate();
		} catch (Exception e) {
			logger.info("系统出现异常", e);
			model.setViewName("error");
		}

		return "redirect:/login";
	}

	
	
	
	@RequestMapping(value = "/exportExcel", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView exportExcel(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "roleId", required = false) Long roleId,ModelAndView model) {

		
		try {

			if (StringUtils.isBlank(condition)) {
				condition = null;
			}else{
				condition = URLDecoder.decode(condition,"UTF-8");
				if(condition.contains("%")){
					condition = condition.replace("%", "\\%");
				}
				if(condition.contains("_")){
					condition = condition.replace("_", "\\_");
				}
			}

			if (roleId == null || roleId == 0) {
				roleId = null;
			}

			//Page<ManagerUser> page = managerUserService.query(condition,roleId, pageNo, pageSize);
			managerUserService.exportExcel(response, condition, roleId, 1, 99999);
			
			
		} catch (Exception e) {
			logger.info("系统出现异常", e);
		}

		return null;
	}
	
	
}
