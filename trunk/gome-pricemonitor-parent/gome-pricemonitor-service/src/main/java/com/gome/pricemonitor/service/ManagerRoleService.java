package com.gome.pricemonitor.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;

import com.gome.pricemonitor.common.util.Page;
import com.gome.pricemonitor.common.util.ResponsesDTO;
import com.gome.pricemonitor.domain.ManagerFunc;
import com.gome.pricemonitor.domain.ManagerRole;
import com.gome.pricemonitor.domain.ManagerUser;

public interface ManagerRoleService {

	public void save2(ManagerRole role);

	public ResponsesDTO save(String content, String userName);

	public ResponsesDTO edit(ManagerRole mrole, String content, String userName);

	public Page<ManagerRole> query(String roleName, int pageNo, int pageSize);
	
	public ResponsesDTO del(Long roleId, String userName);
	
	public List<ManagerRole> getList(int state);
	
	/**
	 * 获取所有功能列表
	 * @param roleId
	 * @return
	 */
	public List<ManagerFunc> getAllFunc();
	
	public List<ManagerFunc> getAllFuncForEdit(Long roleId);
	
	/**
	 * 获取后端管理左侧菜单
	 * @param roleId
	 * @return
	 */
	public List<ManagerFunc> getFuncForLeftMenu(Long userId);
	
	
	
	public ManagerRole selectById(Long roleId);
	
	/**
	 * 查询名称
	 * @param roleName
	 * @return
	 */
	public ManagerRole selectByRoleName(String roleName);
	
	/**
	 * 用户id获取该用户角色
	 * @param userId
	 * @return
	 */
	public ManagerRole selectByUserId(Long userId);
	
	
	public void exportExcel(HttpServletResponse response, String roleName, int pageNo, int pageSize);
	

	/**
	 * 判断操作用户权限
	 * @param type
	 * @param role
	 * @param toUserId
	 * @return
	 */
    public boolean checkUserOperAuth(String type, ManagerRole role, Long toUserId);
	
    /**
     * 判断操作角色权限
     * @param type
     * @param role
     * @param toRoleId
     * @return
     */
    public boolean checkRoleOperAuth(String type, ManagerRole role, Long toRoleId);
}
