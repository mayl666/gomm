package com.gome.pricemonitor.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

import com.gome.pricemonitor.common.util.CookieUtil;
import com.gome.pricemonitor.common.util.StringUtils;

import com.gome.pricemonitor.service.ManagerFuncService;
import com.gome.pricemonitor.service.ManagerRoleService;
import com.gome.pricemonitor.service.ManagerUserService;

public class UserLoginInterceptor extends HandlerInterceptorAdapter {
	
	@Resource
	private ManagerRoleService managerRoleService;
	@Resource
	private ManagerUserService managerUserService;
	@Resource
	private ManagerFuncService managerFuncService;
	
	    private CookieUtil cookieUtil=new CookieUtil();
	    private final Logger logger = LoggerFactory.getLogger(UserLoginInterceptor.class);  
	    /**  
	     * 在业务处理器处理请求之前被调用  
	     * 如果返回false  
	     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链 
	     * 如果返回true  
	     *    执行下一个拦截器,直到所有的拦截器都执行完毕  
	     *    再执行被拦截的Controller  
	     *    然后进入拦截器链,  
	     *    从最后一个拦截器往回执行所有的postHandle()  
	     *    接着再从最后一个拦截器往回执行所有的afterCompletion()  
	     */    
	    @Override    
	    public boolean preHandle(HttpServletRequest request,    
	            HttpServletResponse response, Object handler) throws Exception { 
	    	//从cookie中获取当前登录用户的信息
	    	try {
	    		//String cookieToken = cookieUtil.getCookieValues(request, "token");
	    		String userName = (String) request.getSession().getAttribute("userName");
	    		//String accountIdStr=cookieUtil.getCookieValues(request, "userId");
		    	//String accountNameStr=cookieUtil.getCookieValues(request, "userName");
	    		//是否已经登录
		    	if(StringUtils.isBlank(userName)){
		    		//是否是记住密码(是否有token)
//			    	if(cookieToken != null){
//			    		request.getSession().setAttribute("userName", accountNameStr);
//			    		request.getSession().setAttribute("passwd", "******");
//			    	}
			    	response.sendRedirect(request.getContextPath()+"/login");
		    		//request.RQ	++++++++++++++----("/index.jsp").forward(request, response);  
		    		return false;
		    	}
		    	
//		    	if(StringUtils.isNotEmpty(accountNameStr)&&StringUtils.isNotEmpty(accountIdStr)&&StringUtils.isNotEmpty(cookieToken)&&StringUtils.isNotEmpty(sessionCookie)){
//		    		ManagerRole role = 	(ManagerRole) request.getSession().getAttribute("mrole");
//		    		ManagerUser user = 	(ManagerUser) request.getSession().getAttribute("muser");
//		    		String funcIds = (String) request.getSession().getAttribute("funcIds");
//		    		Long superRoleId = (Long) request.getSession().getAttribute("superRoleId");
//		    		Long superUserId = (Long) request.getSession().getAttribute("superUserId");
//		    		if(role == null){
//		    			//查询数据库获取用户角色信息
//		    			role = managerRoleService.selectByUserId(Long.parseLong(accountIdStr));
//						if(role.getState() != 0){
//							//查询数据库，该用户权限
//							funcIds = "[]";
//							request.getSession().setAttribute("funcIds", funcIds);
//						}
//		    			request.getSession().setAttribute("mrole", role);
//		    		}
//		    		
//		    		if(user == null){
//		    			//查询数据库获取用户角色信息
//		    			user = managerUserService.selectById(Long.parseLong(accountIdStr));
//		    			request.getSession().setAttribute("muser", user);
//		    		}
//		    		
//		    		
//					if(user.getState() != 0){
//						request.getRequestDispatcher("/index.jsp").forward(request, response);
//						 return false;
//					}
//					
//
//					
//					if(funcIds == null){
//						//查询数据库，该用户权限
//						funcIds = managerFuncService.queryForRoleIdNot(role.getRoleId());
//						request.getSession().setAttribute("funcIds", funcIds);
//					}
//					
//					if(superRoleId == null){
//						superRoleId = AppConfigUtil.getLongValue("superRoleId");
//						request.getSession().setAttribute("superRoleId", superRoleId);
//					}
//					if(superUserId == null){
//						superUserId = AppConfigUtil.getLongValue("superUserId");
//						request.getSession().setAttribute("superUserId", superUserId);
//					}
//		    		
//		    		return true;
//		    	}else{
//		    		  request.getRequestDispatcher("/index.jsp").forward(request, response);  
//		    		  
//		    		  return false;
//		    	}
		    	
		    	
		    	
			} catch (Exception e) {
				e.printStackTrace();
				
			}
	    	return true;
	    	
	    }    
	    
	    /** 
	     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作    
	     * 可在modelAndView中加入数据，比如当前时间 
	     */  
	    @Override    
	    public void postHandle(HttpServletRequest request,    
	            HttpServletResponse response, Object handler,    
	            ModelAndView model) throws Exception {  
//	    	String accountIdStr=cookieUtil.getCookieValues(request, "userId");
//	    	String accountNameStr=cookieUtil.getCookieValues(request, "userName");
//	    	request.setAttribute("userId", accountIdStr);
//	    	request.setAttribute("userName", accountNameStr);
	    }    
	    
	    /**  
	     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等   
	     *   
	     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()  
	     */    
	    @Override    
	    public void afterCompletion(HttpServletRequest request,    
	            HttpServletResponse response, Object handler, Exception ex)    
	            throws Exception {
	    	
	    }    
	  

}
