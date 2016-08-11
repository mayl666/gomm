package com.gome.pricemonitor.controler;

import java.net.URLDecoder;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.gome.pricemonitor.common.util.AppConfigUtil;
import com.gome.pricemonitor.common.util.DateUtils;
import com.gome.pricemonitor.common.util.Page;
import com.gome.pricemonitor.common.util.ResponsesDTO;
import com.gome.pricemonitor.constants.ReturnCode;
import com.gome.pricemonitor.domain.ManagerRole;
import com.gome.pricemonitor.service.ManagerRoleService;
import com.gome.pricemonitor.controler.base.BaseController;


@Controller
@RequestMapping(value="/role")
public class ManagerRoleController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(ManagerRoleController.class);
	
	@Resource
	private ManagerRoleService managerRoleService;
	
	/**
	 * 跳转角色列表界面
	 * @param request
	 * @param response
	 * @param model
	 * @param condition
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response, ModelAndView model, @RequestParam(value="condition", required=false) String condition,@RequestParam(value="pageNo", required=false) Integer pageNo,@RequestParam(value="pageSize", required=false) Integer pageSize){
		model.setViewName("/user/roleList");
		try{
			String term = "";
			if(StringUtils.isBlank(condition)){
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
			if (pageNo == null || pageNo < 1) {
				pageNo = AppConfigUtil.getIntValue("pageNo");
			}
			if (pageSize == null || pageSize < 1) {
				pageSize = AppConfigUtil.getIntValue("pageSize");
			}
			
			Page<ManagerRole> page	= managerRoleService.query(condition, pageNo, pageSize);
			model.addObject("page", page);
			model.addObject("condition", term == null ? "" : term);
			
		}catch(Exception e){
			logger.error("获取角色列表出现异常:",e);
			model.setViewName("error");
		}
		
		return model;
		
	}
	
	/**
	 * 跳转角色添加页面
	 * @param request
	 * @param response
	 * @param model
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value="/add", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/user/add-roleList");
		try{
			String loginName = this.getUserInfo(request, "userName");
			model.addObject("loginName", loginName);
			model.addObject("currentTime", DateUtils.format(new Date()));
		}catch(Exception e){
			logger.error("编辑出现异常:",e);
			model.setViewName("error");
		}
		
		return model;
		
	}
	
	/**
	 * 跳转角色编辑页面
	 * @param request
	 * @param response
	 * @param model
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value="/edit", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response, ModelAndView model, @RequestParam(value="roleId", required=true) Long roleId){
		model.setViewName("/user/edit-roleList");
		try{
			ManagerRole role = managerRoleService.selectById(roleId);
			String loginName = this.getUserInfo(request, "userName");
			model.addObject("role", role);
			model.addObject("loginName", loginName);
			model.addObject("createTime", DateUtils.format(role.getCreateTime()));
			if(role.getUpdateTime() != null){
				model.addObject("updateTime", DateUtils.format(role.getUpdateTime()));
			}
		}catch(Exception e){
			logger.error("编辑出现异常:",e);
			model.setViewName("error");
		}
		
		return model;
		
	}
	
	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 * @param model
	 * @param condition
	 * @return
	 */
	@RequestMapping(value="/exportExcel", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView exportExcel(HttpServletRequest request, HttpServletResponse response, ModelAndView model, @RequestParam(value="condition", required=false) String condition){
		//model.setViewName("/user/roleList");
		try{
			if(StringUtils.isBlank(condition)){
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
			managerRoleService.exportExcel(response, condition, 1, 99999);
		}catch(Exception e){
			logger.error("获取角色列表出现异常:",e);
			//model.setViewName("error");
		}
		
		return null;
		
	}
	



}
