package com.gome.pricemonitor.controler;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gome.pricemonitor.common.util.AppConfigUtil;
import com.gome.pricemonitor.common.util.Page;
import com.gome.pricemonitor.common.util.ResponsesDTO;
import com.gome.pricemonitor.constants.ReturnCode;
import com.gome.pricemonitor.controler.base.BaseController;
import com.gome.pricemonitor.domain.ManagerRole;
import com.gome.pricemonitor.service.ManagerRoleService;

@Controller
@RequestMapping(value = "/manager/ajax/role")
public class ManagerAjaxRoleController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(ManagerAjaxRoleController.class);

	@Resource
	private ManagerRoleService managerRoleService;

	/**
	 * url: ip:port/manager/role/save 参数: {"roleName":"普通管理员"} 新增角色
	 * 
	 * @param request
	 * @param response
	 * @param content
	 * @return
	 */
/*	@ResponseBody
	@RequestMapping(value = "/save2", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponsesDTO save2(HttpServletRequest request, HttpServletResponse response, String content) {

		logger.info("content:" + content);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		if (StringUtils.isBlank(content)) {
			res.setReturnCode(ReturnCode.ERROR_PARAMS_NOT_NULL);
			return res;
		}

		try {
			// 获取当前用户名
			String loginName = this.getUserInfo(request, "loginName");
			if (StringUtils.isBlank(loginName)) {
				loginName = "admin";
			}
			// json字符串映射bean
			ManagerRole role = JSON.parseObject(content, ManagerRole.class);
			System.out.println(role.toString());
			// 账号数据入库
			managerRoleService.save2(role);

		} catch (Exception e) {
			logger.info("系统出现异常", e);
			res.setReturnCode(ReturnCode.ERROR_SERVER);
		}

		return res;
	}*/

	/**
	 * http://127.0.0.1:8080/gome-manager-web/manager/ajax/role/save
	 * {"roleName":"普通管理员","state":0,"desc":"hehe","functions":[{"funcId":"10","parentId":0},{"funcId":"11","parentId":10},{"funcId":"12","parentId":10}]}
	 * 
	 * @param request
	 * @param response
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponsesDTO save(HttpServletRequest request, HttpServletResponse response, String content) {

		logger.info("content:" + content);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		if (StringUtils.isBlank(content)) {
			res.setReturnCode(ReturnCode.ERROR_PARAMS_NOT_NULL);
			return res;
		}

		try {

			// 获取当前用户名
			String loginName = this.getUserInfo(request, "userName");
			//判断权限
			ManagerRole mrole = (ManagerRole) request.getSession().getAttribute("mrole");
			boolean isAuth = managerRoleService.checkRoleOperAuth("add", mrole, null);
			if(!isAuth){
				res.setReturnCode(ReturnCode.ERROR_OPERATOR_AUTH);
				return res;
			}
			
			managerRoleService.save(content, loginName);

		} catch (Exception e) {
			logger.info("系统出现异常", e);
			res.setReturnCode(ReturnCode.ERROR_SERVER);
		}

		return res;
	}
	
	

	/**
	 * http://127.0.0.1:8080/gome-manager-web/manager/ajax/role/edit
	 * {"roleId":1,"roleName":"普通管理员","state":0,"desc":"hehe","functions":[{"funcId":"10","parentId":0},{"funcId":"11","parentId":10},{"funcId":"12","parentId":10}]} 编辑角色
	 * 
	 * @param request
	 * @param response
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponsesDTO edit(HttpServletRequest request, HttpServletResponse response, String content) {

		logger.info("content:" + content);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		if (StringUtils.isBlank(content)) {
			res.setReturnCode(ReturnCode.ERROR_PARAMS_NOT_NULL);
			return res;
		}

		try {

			// 获取当前用户名
			String loginName = this.getUserInfo(request, "userName");
			ManagerRole mrole = (ManagerRole) request.getSession().getAttribute("mrole");
			managerRoleService.edit(mrole, content, loginName);

		} catch (Exception e) {
			logger.info("系统出现异常", e);
			res.setReturnCode(ReturnCode.ERROR_SERVER);
		}

		return res;
	}

	/**
	 * url:http://127.0.0.1:8080/gome-manager-web/manager/ajax/role/query
	 * 参数:{"roleName":"管理员","pageNo":1,"pageSize":1}
	 * @param request
	 * @param response
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/query", method = { RequestMethod.POST }, produces = "application/json;charset=utf-8")
	public ResponsesDTO query(HttpServletRequest request, HttpServletResponse response, String content) {

		logger.info("content:" + content);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		if (StringUtils.isBlank(content)) {
			res.setReturnCode(ReturnCode.ERROR_PARAMS_NOT_NULL);
			return res;
		}

		try {

			String roleName = null;
			Integer pageNo = AppConfigUtil.getIntValue("pageNo");
			Integer pageSize = AppConfigUtil.getIntValue("pageSize");
			JSONObject obj = JSON.parseObject(content);
			if (obj.containsKey("roleName")) {
				roleName = obj.getString("roleName");
			}

			if (obj.containsKey("pageNo")) {
				pageNo = obj.getInteger("pageNo");
			}
			if (obj.containsKey("pageSize")) {
				pageSize = obj.getInteger("pageSize");
			}

			Page<ManagerRole> page = managerRoleService.query(roleName, pageNo, pageSize);
			res.setAttach(page);

		} catch (Exception e) {
			logger.info("系统出现异常", e);
			res.setReturnCode(ReturnCode.ERROR_SERVER);
		}

		return res;
	}
	
	
	
	/**
	 * 检查角色名称是否被占用
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkRoleName", method = { RequestMethod.POST,RequestMethod.GET }, produces = "application/json;charset=utf-8")
	public ResponsesDTO checkUserName(HttpServletRequest request, HttpServletResponse response,@RequestParam(value="roleName", required=true) String roleName) {
		//public ResponsesDTO checkUserName(HttpServletRequest request, HttpServletResponse response,String userName) {
		logger.info("roleName:"+roleName);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		
		try {
			
			ManagerRole role = managerRoleService.selectByRoleName(roleName);
			if(role!=null){
				res.setReturnCode(ReturnCode.ERROR_NAME_HAS_USED);
				return res;
			}
			
		} catch (Exception e) {
			logger.info("系统出现异常", e);
			res.setReturnCode(ReturnCode.ERROR_SERVER);
		}

		return res;
	}

	
	
	
	/**
	 * 删除操作
	 * @param request
	 * @param response
	 * @param roleId
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/del/{roleId}", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=utf-8")
	public ResponsesDTO del(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("roleId") Long roleId) {

		logger.info("roleId:" + roleId);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		if (roleId == null) {
			res.setReturnCode(ReturnCode.ERROR_PARAMS_NOT_NULL);
			return res;
		}

		try {
			String loginName = this.getUserInfo(request, "userName");
			
			ManagerRole mrole = (ManagerRole) request.getSession().getAttribute("mrole");
			boolean isAuth = managerRoleService.checkRoleOperAuth("del", mrole, roleId);
			if(!isAuth){
				res.setReturnCode(ReturnCode.ERROR_OPERATOR_AUTH);
				return res;
			}
			
			
			res = managerRoleService.del(roleId, loginName);
			//Page<ManagerRole> page	= managerRoleService.query(null, 1, 20);
			//model.addObject("page", page);
			//model.addObject("roleName", "");
			//String loginName = this.getUserInfo(request, "loginName");

			//managerRoleService.del(roleId);
		} catch (Exception e) {
			logger.info("系统出现异常", e);
			res.setReturnCode(ReturnCode.ERROR_SERVER);
		}

		return res;
	}
	

}
