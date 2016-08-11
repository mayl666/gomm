package com.gome.pricemonitor.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gome.pricemonitor.common.util.AppConfigUtil;
import com.gome.pricemonitor.common.util.DateUtils;
import com.gome.pricemonitor.common.util.ExcelUtil;
import com.gome.pricemonitor.common.util.Page;
import com.gome.pricemonitor.common.util.ResponsesDTO;
import com.gome.pricemonitor.constants.ReturnCode;
import com.gome.pricemonitor.dao.ManagerFuncMapper;
import com.gome.pricemonitor.dao.ManagerRoleFuncMapper;
import com.gome.pricemonitor.dao.ManagerRoleMapper;
import com.gome.pricemonitor.dao.ManagerUserRoleMapper;
import com.gome.pricemonitor.domain.ManagerFunc;
import com.gome.pricemonitor.domain.ManagerRole;
import com.gome.pricemonitor.domain.ManagerRoleFunc;
import com.gome.pricemonitor.domain.ManagerUser;
import com.gome.pricemonitor.domain.ManagerUserRole;
import com.gome.pricemonitor.service.ManagerRoleService;

@Service("managerRoleService")
public class ManagerRoleServiceImpl implements ManagerRoleService {

	@Resource
	private ManagerRoleMapper managerRoleMapper;
	@Resource
	private ManagerRoleFuncMapper managerRoleFuncMapper;
	@Resource
	private ManagerFuncMapper managerFuncMapper;
	@Resource
	private ManagerUserRoleMapper managerUserRoleMapper;

	@Override
	public void save2(ManagerRole role) {
		role.setCreateTime(new Date());
		role.setState(0);
		role.setUpdateTime(new Date());
		managerRoleMapper.insertSelective(role);
	}

	@Override
	public ResponsesDTO save(String content, String userName) {
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		JSONObject objcon = JSON.parseObject(content);
		String roleName = objcon.getString("roleName");
		String desc = objcon.getString("desc");
		Integer state = objcon.getInteger("state");
		// 保存角色信息
		ManagerRole role = new ManagerRole();
		role.setDescription(desc);
		role.setCreateTime(new Date());
		role.setState(state);
		//role.setUpdateTime(new Date());
		role.setOperatorName(userName);
		role.setRoleName(roleName);
		managerRoleMapper.insertSelective(role);
		
		String functions = objcon.getString("functions")==null ? "" : objcon.getString("functions");

		// 对角色添加权限
		
		if(!StringUtils.isBlank(functions)){
			JSONArray array = JSON.parseArray(functions);
			ManagerRoleFunc mrf = null;
			for (int i = 0; i < array.size(); i++) {
				mrf = new ManagerRoleFunc();
				JSONObject obj = (JSONObject) array.get(i);
				Long parentId = obj.getLong("parentId");
				Long funcId = obj.getLong("funcId");
				mrf.setFuncId(funcId);
				mrf.setFuncParentId(parentId);
				mrf.setRoleId(role.getRoleId());
				managerRoleFuncMapper.insertSelective(mrf);
			}
		}
		

		return res;
	}

	@Override
	public ResponsesDTO edit(ManagerRole mrole, String content, String userName) {
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		JSONObject objcon = JSON.parseObject(content);
		String roleName = objcon.getString("roleName");
		Long roleId = objcon.getLong("roleId");
		String desc = objcon.getString("desc");
		Integer state = objcon.getInteger("state");
		
		//判断权限
		boolean result = this.checkRoleOperAuth("edit", mrole, roleId);
		if(!result){
			res.setReturnCode(ReturnCode.ERROR_OPERATOR_AUTH);
			return res;
		}
		
		
		
		// 保存角色信息
		ManagerRole role = new ManagerRole();
		role.setRoleId(roleId);
		role.setDescription(desc);
		role.setState(state);
		role.setUpdateTime(new Date());
		role.setOperatorName(userName);
		role.setRoleName(roleName);
		managerRoleMapper.updateByPrimaryKeySelective(role);

		// 删除该角色所有功能权限
		managerRoleFuncMapper.deleteByRoleId(roleId);

		// 对角色添加权限
		String functions = objcon.getString("functions")==null ? "" : objcon.getString("functions");;
		if(!StringUtils.isBlank(functions)){
			JSONArray array = JSON.parseArray(functions);
			ManagerRoleFunc mrf = null;
			for (int i = 0; i < array.size(); i++) {
				mrf = new ManagerRoleFunc();
				JSONObject obj = (JSONObject) array.get(i);
				Long parentId = obj.getLong("parentId");
				Long funcId = obj.getLong("funcId");
				mrf.setFuncId(funcId);
				mrf.setFuncParentId(parentId);
				mrf.setRoleId(role.getRoleId());
				managerRoleFuncMapper.insertSelective(mrf);
			}
		}
		

		return res;
	}

	@Override
	public Page<ManagerRole> query(String roleName, int pageNo, int pageSize) {
		Long count = managerRoleMapper.queryCount(roleName);
		List<ManagerRole> list = managerRoleMapper.query(roleName, null, (pageNo - 1) * pageSize, pageSize);
		for(ManagerRole role : list){
			if(role.getCreateTime() != null){
				role.setCreateTimeView(DateUtils.format(role.getCreateTime()));
			}
		}
		Page<ManagerRole> page = new Page<ManagerRole>(count.intValue(), pageSize, pageNo);
		page.setList(list);
		return page;
	}

	@Override
	public ResponsesDTO del(Long roleId, String userName) {
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		
		Long count = managerUserRoleMapper.getCountByRoleId(roleId);
		if(count > 0){
			res.setReturnCode(ReturnCode.ERROR_ROLE_HASED_USER);
			return res;
		}
		managerRoleMapper.deleteByPrimaryKey(roleId);//删除角色，物理删除
		// 删除角色

/*		ManagerRole role = new ManagerRole();
		role.setOperatorName(userName);
		role.setRoleId(roleId);
		role.setState(ManagerRole.ROLE_DELETE);
		role.setUpdateTime(new Date());
		managerRoleMapper.updateByPrimaryKeySelective(role);*/
         return res;
	}

	@Override
	public List<ManagerFunc> getAllFunc() {
		// 获取所有功能列表
		List<ManagerFunc> list = managerFuncMapper.query();//
//		ManagerFunc funcRoot = new ManagerFunc();
//		funcRoot.setFuncId(0l);
//		funcRoot.setParentId(-1l);
//		funcRoot.setFuncName("云智后台管理平台");
		//list.add(funcRoot);
		return list;
	}

	@Override
	public List<ManagerFunc> getFuncForLeftMenu(Long userId) {
		
		ManagerUserRole ur = managerUserRoleMapper.getByRoleId(userId);
		if(ur == null){
			return new ArrayList<ManagerFunc>();
		}
        Long roleId = ur.getRoleId();
		// 获取所有功能列表
		// List<ManagerFunc> list = managerFuncMapper.query();//
		// 获取一级菜单
		List<ManagerFunc> listRole = managerFuncMapper.queryForRole(roleId, 0l);//
		List<ManagerFunc> listRoleChilds = null;
		for (ManagerFunc func : listRole) {
			listRoleChilds = managerFuncMapper.queryForRole(roleId, func.getFuncId());// 获取二级菜单
			func.setChildNodes(listRoleChilds);
		}

		return listRole;
	}

	@Override
	public List<ManagerRole> getList(int state) {
		
		
		return managerRoleMapper.getList(state);
	}

	@Override
	public ManagerRole selectById(Long roleId) {
		// TODO Auto-generated method stub
		return managerRoleMapper.selectByPrimaryKey(roleId);
	}

	@Override
	public List<ManagerFunc> getAllFuncForEdit(Long roleId) {
		List<ManagerFunc> list = managerFuncMapper.query();//所有功能列表
		List<Long> listRole = managerFuncMapper.queryForRoleId(roleId, null);//获取改角色对应的功能列表
		for(ManagerFunc func : list){
			if(listRole.contains(func.getFuncId())){
				func.setChecked(true);
			}
		}
		return list;
	}

	@Override
	public ManagerRole selectByRoleName(String roleName) {
		// TODO Auto-generated method stub
		return managerRoleMapper.selectByRoleName(roleName);
	}

	@Override
	public ManagerRole selectByUserId(Long userId) {
		// TODO Auto-generated method stub
		return managerRoleMapper.selectByUserId(userId);
	}

	@Override
	public void exportExcel(HttpServletResponse response, String roleName, int pageNo, int pageSize) {
		List<ManagerRole> list = managerRoleMapper.query(roleName, null, (pageNo - 1) * pageSize, pageSize);
		int num=0;
		for(ManagerRole role : list){
			num++;
			role.setNum(num);
			if(role.getCreateTime() != null){
				role.setCreateTimeView(DateUtils.format(role.getCreateTime()));
			}
			if(role.getState()==0){
				role.setStateVal("正常");
			}else{
				role.setStateVal("失效");
			}
		}
		
		
		String[] title ={"序号","角色名称","状态","创建时间"};
        String[] header = {"num","roleName","stateVal","createTimeView"};
		String fileName = "角色数据表格.xls";
		JSONArray array = (JSONArray) JSONArray.toJSON(list);
		ExcelUtil.exportExcel(response, array, title, header, fileName);
		
		
	}

	@Override
	public boolean checkUserOperAuth(String type, ManagerRole role, Long toUserId) {
		if(role == null || StringUtils.isBlank(type)){
			return false;
		}
		Long superUserId = AppConfigUtil.getLongValue("superUserId");
		Long superRoleId = AppConfigUtil.getLongValue("superRoleId");
		//判断是否是超级管理员
		if(!role.getRoleId().equals(superRoleId)){
			return false;
		}
		
		if(type.equals("add")){
			return true;
		}
		
		if(toUserId == null){
			return false;
		}
		
		//修改删除权限
		//判断是否是admin账户，该账户不允许删除修改
		if(toUserId.equals(superUserId)){
			return false;
		}
		
		return true;
	}


	
	@Override
	public boolean checkRoleOperAuth(String type, ManagerRole role, Long toRoleId) {
		if(role == null || StringUtils.isBlank(type)){
			return false;
		}
		
		
		boolean result = this.checkRoleOperAuth(type, role.getRoleId(), toRoleId);
		return result;
	}
	
	
	public boolean checkRoleOperAuth(String type, Long roleId, Long toRoleId) {
		if(roleId == null || StringUtils.isBlank(type)){
			return false;
		}
		//Long superUserId = AppConfigUtil.getLongValue("superUserId");
		Long superRoleId = AppConfigUtil.getLongValue("superRoleId");
		//判断是否是超级管理员
		if(!roleId.equals(superRoleId)){
			return false;
		}
		
		if(type.equals("add")){
			return true;
		}
		
		if(toRoleId == null){
			return false;
		}
		
		//修改删除权限
		//判断是否是超级管理员角色，该角色不允许删除修改
		if(toRoleId.equals(superRoleId)){
			return false;
		}
		
		return true;
	}
	



}
