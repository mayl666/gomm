package com.gome.pricemonitor.controler;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gome.pricemonitor.common.login.LoginUtil;
import com.gome.pricemonitor.common.util.AppConfigUtil;
import com.gome.pricemonitor.common.util.ResponsesDTO;
import com.gome.pricemonitor.constants.ReturnCode;
import com.gome.pricemonitor.controler.base.BaseController;
import com.gome.pricemonitor.domain.ManagerRole;
import com.gome.pricemonitor.domain.ManagerUser;
import com.gome.pricemonitor.service.ManagerFuncService;
import com.gome.pricemonitor.service.ManagerRoleService;
import com.gome.pricemonitor.service.ManagerUserRoleService;
import com.gome.pricemonitor.service.ManagerUserService;
import com.gome.pricemonitor.service.ManagerUserTokenService;

/**
 * 后台管理系统账号控制
 * 
 * @author zhangzhixiang
 *
 */
@Controller
@RequestMapping(value = "/manager/ajax/user")
public class ManagerUserAjaxController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(ManagerUserAjaxController.class);

	@Resource
	private ManagerUserService managerUserService;
	@Resource
	private ManagerUserRoleService managerUserRoleService;
	@Resource
	private ManagerRoleService managerRoleService;
	@Resource
	private ManagerFuncService managerFuncService;
	@Resource
	private ManagerUserTokenService managerUserTokenService;

	/**
	 * url: ip:port/manager/ajax/user/save 参数:
	 * {"userName":"zhangzhixiang","passwd":"wefwevev","realName":"张",
	 * "contactWay":"15010106579","headPath":"http://a.jpg","roleId":2,"state":
	 * 0} 管理员添加账号
	 * 
	 * @param request
	 * @param response
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponsesDTO save(HttpServletRequest request,
			HttpServletResponse response, String content) {

		logger.info("content:" + content);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		if (StringUtils.isBlank(content)) {
			res.setReturnCode(ReturnCode.ERROR_PARAMS_NOT_NULL);
			return res;
		}

		try {

			ManagerRole mrole = (ManagerRole) request.getSession()
					.getAttribute("mrole");
			boolean isAuth = managerRoleService.checkUserOperAuth("add", mrole,
					null);
			if (!isAuth) {
				res.setReturnCode(ReturnCode.ERROR_OPERATOR_AUTH);
				return res;
			}
			// 获取当前用户名
			String loginName = this.getUserInfo(request, "userName");
			// json字符串映射bean
			ManagerUser user = JSON.parseObject(content, ManagerUser.class);
			System.out.println(user.toString());
			// 账号数据入库
			managerUserService.save(user, loginName);
			managerUserRoleService.save(user.getUserId(), user.getRoleId());

		} catch (Exception e) {
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ERROR_SERVER);
		}

		return res;
	}

	/**
	 * 
	 * url: ip:port/manager/ajax/user/edit 参数:
	 * {"userId":1,"userName":"zhangzhixiang","passwd":"wefwevev","realName":"张"
	 * ,"contactWay":"15010106579","headPath":"http://a.jpg","roleId":1,"state":
	 * 0} 管理员对账号的编辑
	 * 
	 * @param request
	 * @param response
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponsesDTO edit(HttpServletRequest request,
			HttpServletResponse response, String content) {

		logger.info("content:" + content);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		if (StringUtils.isBlank(content)) {
			res.setReturnCode(ReturnCode.ERROR_PARAMS_NOT_NULL);
			return res;
		}

		try {

			// 获取当前用户名
			String loginName = this.getUserInfo(request, "userName");
			// json字符串映射bean
			ManagerUser user = JSON.parseObject(content, ManagerUser.class);
			System.out.println(user.toString());

			// 判断权限
			ManagerRole mrole = (ManagerRole) request.getSession()
					.getAttribute("mrole");
			boolean isAuth = managerRoleService.checkUserOperAuth("edit",
					mrole, user.getUserId());
			if (!isAuth) {
				res.setReturnCode(ReturnCode.ERROR_OPERATOR_AUTH);
				return res;
			}

			// 账号数据入库
			managerUserService.edit(user, loginName);
			// 删除数据
			managerUserRoleService.delByUser(user.getUserId());
			// 新增数据
			managerUserRoleService.save(user.getUserId(), user.getRoleId());

		} catch (Exception e) {
			logger.info("系统出现异常", e);
			res.setReturnCode(ReturnCode.ERROR_SERVER);
		}

		return res;
	}

	/**
	 * url:ip:port/manager/ajax/user/del/1 参数:
	 * 
	 * @param request
	 * @param response
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/del/{userId}", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json;charset=utf-8")
	public ResponsesDTO del(HttpServletRequest request,
			HttpServletResponse response, @PathVariable("userId") Long userId) {

		logger.info("userId:" + userId);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		if (userId == null) {
			res.setReturnCode(ReturnCode.ERROR_PARAMS_NOT_NULL);
			return res;
		}

		try {
			// 判断权限
			ManagerRole mrole = (ManagerRole) request.getSession()
					.getAttribute("mrole");
			boolean isAuth = managerRoleService.checkUserOperAuth("del", mrole,
					userId);
			if (!isAuth) {
				res.setReturnCode(ReturnCode.ERROR_OPERATOR_AUTH);
				return res;
			}
			// 获取当前用户名
			String loginName = this.getUserInfo(request, "loginName");
			managerUserService.del(userId, loginName);
		} catch (Exception e) {
			logger.info("系统出现异常", e);
			res.setReturnCode(ReturnCode.ERROR_SERVER);
		}

		return res;
	}

	/**
	 * 登录 http://127.0.0.1:8080/manager/ajax/user/login
	 * {"userName":"admin","passwd":"wefwevev"}
	 * 
	 * @param request
	 * @param response
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = { RequestMethod.POST }, produces = "application/json;charset=utf-8")
	public ResponsesDTO login(HttpServletRequest request,
			HttpServletResponse response, HttpSession hsession, String content,
			boolean checked) {
		Map<String, Object> map = new HashMap<String, Object>();
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		logger.info("content:" + content);
		try {
			ManagerUser user = new ManagerUser();
			//String loginNameCookie = this.getUserInfo(request, "userName");//cookie存储
			JSONObject obj = JSON.parseObject(content);
			String loginName = obj.getString("userName");//传入参数
			String passwd = obj.getString("passwd");
			boolean checkLogin = LoginUtil.checkLogin(loginName, passwd);
			if (checkLogin) {
				// 账号数据入库
				user.setUserName(loginName);
				user.setRealName(loginName);
				user.setPasswd(passwd);
				user.setState(0);
				ManagerUser managerUser = managerUserService.selectByUserName(loginName);
				if(managerUser != null){
					managerUserService.updatePasswd(passwd, managerUser.getUserId());
					user.setUserId(managerUser.getUserId());
				}else{
					long userId = managerUserService.save(user, loginName);
					user.setUserId(userId);
				}
				//map = managerUserService.login(content, checked);
				map.put("result", "success");
				map.put("user",user);
			}
			String result = map.get("result").toString();
			
			if(result.equals("success")){
				
			}else{
				res.setReturnCode(ReturnCode.ACTIVE_FAILURE);
				return res;
			}
			
			// 登录相关信息设置
			int cookieExistsTime = AppConfigUtil.getIntValue("cookieExists");;
			user = (ManagerUser) map.get("user");

			setUserInfo("userId", user.getUserId() + "", null,
					cookieExistsTime, "/", response);
			setUserInfo("userName", user.getUserName() + "", null,
					cookieExistsTime, "/", response);
			
			hsession.setAttribute("userName", user.getUserName());
			hsession.setAttribute("userId", user.getUserId());
			hsession.setAttribute("muser", user);
			hsession.setAttribute("superUserId",
					AppConfigUtil.getLongValue("superUserId"));
			hsession.setAttribute("superRoleId",
					AppConfigUtil.getLongValue("superRoleId"));
			// hsession.setAttribute("listMenu", listMenu);
		} catch (Exception e) {
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ERROR_SERVER);
		}

		return res;
	}

	/**
	 * url:http://127.0.0.1:8080/gome-manager-web/manager/ajax/user/uppasswd
	 * 参数:{"oldPasswd":"wefwevev","passwd":"123456","confirmPasswd":"123456"}
	 * 
	 * 
	 * @param request
	 * @param response
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/upPasswd", method = { RequestMethod.POST }, produces = "application/json;charset=utf-8")
	public ResponsesDTO uppasswd(HttpServletRequest request,
			HttpServletResponse response, String content) {
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		logger.info("content:" + content);

		try {
			String userId = this.getUserInfo(request, "userId");
			if (StringUtils.isBlank(userId)) {
				res.setReturnCode(ReturnCode.ERROR_NO_LOGIN);// 用户未登录
				return res;
			}

			res = managerUserService.updatePasswd(content,
					Long.parseLong(userId));

		} catch (Exception e) {
			logger.info("系统出现异常", e);
			res.setReturnCode(ReturnCode.ERROR_SERVER);
		}

		return res;
	}

	/**
	 * 检查用户名称是否被占用
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkUserName", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json;charset=utf-8")
	public ResponsesDTO checkUserName(HttpServletRequest request,
			HttpServletResponse response) {
		// public ResponsesDTO checkUserName(HttpServletRequest request,
		// HttpServletResponse response,String userName) {
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		String userName = request.getParameter("userName");

		try {

			ManagerUser user = managerUserService.selectByUserName(userName);
			if (user != null) {
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
	 * 修改个人账号信息
	 * 
	 * @param request
	 * @param response
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/editMyAccount", method = { RequestMethod.POST }, produces = "application/json;charset=utf-8")
	public ResponsesDTO editMyAccount(HttpServletRequest request,
			HttpServletResponse response, String content) {
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		logger.info("content:" + content);

		try {

			String userId = this.getUserInfo(request, "userId");
			ManagerUser user = JSON.parseObject(content, ManagerUser.class);
			user.setUserId(Long.parseLong(userId));
			user = managerUserService.edit(user, getUserInfo(request, "userName"));
			request.getSession().setAttribute("muser", user);
		} catch (Exception e) {
			logger.info("系统出现异常", e);
			res.setReturnCode(ReturnCode.ERROR_SERVER);
		}

		return res;
	}

}
