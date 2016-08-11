package com.gome.pricemonitor.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gome.pricemonitor.common.util.DateUtils;
import com.gome.pricemonitor.common.util.ExcelUtil;
import com.gome.pricemonitor.common.util.Page;
import com.gome.pricemonitor.common.util.ResponsesDTO;
import com.gome.pricemonitor.constants.ReturnCode;
import com.gome.pricemonitor.dao.ManagerUserMapper;
import com.gome.pricemonitor.dao.ManagerUserRoleMapper;
import com.gome.pricemonitor.domain.ManagerRole;
import com.gome.pricemonitor.domain.ManagerUser;
import com.gome.pricemonitor.domain.ManagerUserToken;
import com.gome.pricemonitor.service.ManagerFuncService;
import com.gome.pricemonitor.service.ManagerRoleService;
import com.gome.pricemonitor.service.ManagerUserService;
import com.gome.pricemonitor.service.ManagerUserTokenService;

@Service("managerUserService")
public class ManagerUserServiceImpl implements ManagerUserService {
	
	@Resource
	private ManagerUserMapper managerUserMapper;
	@Resource
	private ManagerUserRoleMapper managerUserRoleMapper;
    @Resource
    private ManagerUserTokenService managerUserTokenService;
	@Resource
	private ManagerRoleService managerRoleService;
	@Resource
	private ManagerFuncService managerFuncService;
	
	@Override
	public int save(ManagerUser user, String operatorName) {
		user.setCreateTime(new Date());
		//user.setUpdateTime(new Date());
		user.setOperatorName(operatorName);
		return managerUserMapper.insertSelective(user);
	}

	@Override
	public ManagerUser edit(ManagerUser user, String operatorName) {
		user.setUpdateTime(new Date());
		user.setOperatorName(operatorName);
		managerUserMapper.updateByPrimaryKeySelective(user);
		return user;
	}

	@Override
	public Page<ManagerUser> query(String condition, Long roleId, int pageNo, int pageSize) {
	
		Long totalcount = managerUserMapper.queryCount(condition, roleId);
		List<ManagerUser> list = managerUserMapper.query(condition, roleId, null, (pageNo-1)*pageSize, pageSize);
		for(ManagerUser user : list){
			Date date = user.getCreateTime();
			if(date != null){
				user.setCreateTimeView(DateUtils.format(date));
			}
		}
		Page<ManagerUser> page = new Page<ManagerUser>(totalcount.intValue(),pageSize,pageNo);
		page.setList(list);
		return page;
	}

	@Override
	public void del(Long userId, String operatorName) {
		//物理删除
		managerUserRoleMapper.delByUser(userId);//删除用户角色关系
		managerUserMapper.deleteByPrimaryKey(userId);//删除用户
//		ManagerUser user = new ManagerUser();
//		user.setOperatorName(operatorName);
//		user.setUserId(userId);
//		user.setState(ManagerUser.USER_DELETE);
//		user.setUpdateTime(new Date());
//		managerUserMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public ResponsesDTO login(String userName, String passwd) {
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		ManagerUser user = managerUserMapper.selectByUserName(userName);
		
		if(user == null){
			res.setReturnCode(ReturnCode.ERROR_USER_NONE);//用户不存在
		}
		
		if(!user.getPasswd().equals(passwd)){
			res.setReturnCode(ReturnCode.ERROR_USER_PASSWORD);
		}
		
		return res;
	}

	@Override
	public ManagerUser selectByUserName(String userName) {
		return managerUserMapper.selectByUserName(userName);
	}

	@Override
	public ManagerUser selectById(Long userId) {
		return managerUserMapper.selectByPrimaryKey(userId);
	}

	@Override
	public ResponsesDTO updatePasswd(String passwd, Long userId) {
		ResponsesDTO res = new ResponsesDTO(); 
		ManagerUser user = managerUserMapper.selectByPrimaryKey(userId);
		user.setUserId(userId);
		user.setPasswd(passwd);
		managerUserMapper.updateByPrimaryKeySelective(user);
		return res;
	}

	@Override
	public void exportExcel(HttpServletResponse response, String condition, Long roleId, int pageNo,
			int pageSize) {
		List<ManagerUser> list = managerUserMapper.query(condition, roleId, null, (pageNo-1)*pageSize, pageSize);
		if(list == null || list.size() < 1){
			return;
		}
		
		int num=0;
		for(ManagerUser user : list){
			num++;
			user.setNum(num);
			if(user.getState()==0){
				user.setStateVal("正常");
			}else{
				user.setStateVal("锁定");
			}
			if(user.getCreateTime() != null){
				user.setCreateTimeView(DateUtils.format(user.getCreateTime()));
			}
			
		}
		
		String[] title ={"序号","账号","角色","状态","真实姓名","联系方式","创建时间"};
        String[] header = {"num","userName","roleName","stateVal","realName","contactWay","createTimeView"};
		String fileName = "账号数据表格.xls";
		JSONArray array = (JSONArray) JSONArray.toJSON(list);
		ExcelUtil.exportExcel(response, array, title, header, fileName);
		
	}

	@Override
	public Map<String, Object> rememberLogin(String token) {
		Map<String, Object> map = new HashMap<String,Object>();
		String result = "fail";
		ManagerUserToken userToken = managerUserTokenService.getByUserToken(null, token);
		if(userToken != null){
//			Long createTime = userToken.getCreateTime().getTime();
//			Long expireTime = AppConfigUtil.getLongValue("cookieExists")*1000;
//			Long currentTime = System.currentTimeMillis();
//			if((currentTime - createTime) < expireTime){
				
					//Long userId = userToken.getUserId();
					ManagerUser user = this.selectById(userToken.getUserId());
					
					if (user == null) {
						result = "notExists";
						map.put("result", result);
						return map;
					}
					
					if(user.getState() != 0){
						result = "userLock";
						map.put("result", result);
						return map;
					}
					
					result = "success";
					// 保存免登陆信息
					//String uuuid = UUID.randomUUID().toString();
				//	managerUserTokenService.save(user.getUserId(), uuuid);
					
					ManagerRole role = managerRoleService.selectByUserId(user
							.getUserId());// 用户角色信息
					String funcIds = managerFuncService.queryForRoleIdNot(role
							.getRoleId());
					map.put("token", token);
				    map.put("user", user);
				    map.put("role", role);
				    map.put("funcIds", funcIds);
			}
		//}
		
		map.put("result", result);
		return map;
	}

	@Override
	public Map<String, Object> login(String content, boolean checked) {
		Map<String, Object> map = new HashMap<String,Object>();
		String result = "fail";
		JSONObject obj = JSON.parseObject(content);
		String userName = obj.getString("userName");
		String passwd = obj.getString("passwd");

		ManagerUser user = this.selectByUserName(userName);

		if (user == null) {
			result = "notExists";
			map.put("result", result);
			return map;
		}

		if (!user.getPasswd().equals(passwd)) {
			result = "errorPasswd";
			map.put("result", result);
			return map;
		}

		if (user.getState() != 0) {
			result = "userLock";
			map.put("result", result);
			return map;
		}
		
		String token = "";
		String uuuid = UUID.randomUUID().toString();
		ManagerUserToken userToken = managerUserTokenService.getByUserToken(user.getUserId(), null);
		
		if(userToken == null || checked){
			managerUserTokenService.save(user.getUserId(), uuuid);
			token = uuuid;
		}else{
			token = userToken.getToken();
		}
		
		
//		if(userToken == null){
//			managerUserTokenService.save(user.getUserId(), uuuid);//没有token生成一个
//		}else{
//			if(checked){
//				//更新token值
//				managerUserTokenService.save(user.getUserId(), uuuid);
//			}
//		}
		
		map.put("token", token);
		ManagerRole role = managerRoleService.selectByUserId(user
				.getUserId());// 用户角色信息
		String funcIds = managerFuncService.queryForRoleIdNot(role
				.getRoleId());
	    map.put("user", user);
	    map.put("role", role);
	    map.put("funcIds", funcIds);
		map.put("result", "success");
		return map;
	}



}
