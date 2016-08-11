package com.gome.pricemonitor.controler;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gome.pricemonitor.common.util.ResponsesDTO;
import com.gome.pricemonitor.constants.ReturnCode;
import com.gome.pricemonitor.domain.ManagerFunc;
import com.gome.pricemonitor.service.ManagerRoleService;
import com.gome.pricemonitor.controler.base.BaseController;

@Controller
@RequestMapping(value="/manager/ajax/func")
public class ManagerAjaxFuncController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(ManagerAjaxFuncController.class);
	@Resource
	private ManagerRoleService managerRoleService;
	
	/**
	 * http://127.0.0.1:8080/gome-manager-web/manager/ajax/func/left
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/left", method = { RequestMethod.POST, RequestMethod.GET })
	public ResponsesDTO del(HttpServletRequest request, HttpServletResponse response) {

		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		
		try {
			String userId = this.getUserInfo(request, "userId");
			if(StringUtils.isBlank(userId)){
				userId="14";
				//res.setReturnCode(ReturnCode.ERROR_NO_LOGIN);
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
	 * http://127.0.0.1:8080/gome-manager-web/manager/ajax/func/get
	 * 获取所有功能列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get", method = { RequestMethod.POST, RequestMethod.GET })
	public ResponsesDTO get(HttpServletRequest request, HttpServletResponse response) {

		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		
		try {
			
			List<ManagerFunc> list = managerRoleService.getAllFunc();
			
			res.setAttach(list);
			//String loginName = this.getUserInfo(request, "loginName");

			//managerRoleService.del(roleId);
		} catch (Exception e) {
			logger.info("系统出现异常", e);
			res.setReturnCode(ReturnCode.ERROR_SERVER);
		}

		return res;
	}

	
	
	@ResponseBody
	@RequestMapping(value = "/getForRoleEdit", method = { RequestMethod.POST, RequestMethod.GET })
	public ResponsesDTO getForRoleEdit(HttpServletRequest request, HttpServletResponse response,@RequestParam(value="roleId",required=true) Long roleId) {

		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		
		try {
			
			List<ManagerFunc> list = managerRoleService.getAllFuncForEdit(roleId);
			
			res.setAttach(list);
			//String loginName = this.getUserInfo(request, "loginName");

			//managerRoleService.del(roleId);
		} catch (Exception e) {
			logger.info("系统出现异常", e);
			res.setReturnCode(ReturnCode.ERROR_SERVER);
		}

		return res;
	}
	
	

}
