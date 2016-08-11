package com.gome.pricemonitor.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gome.pricemonitor.domain.ManagerFunc;

public interface ManagerFuncService {
	
	public List<ManagerFunc> query(Long userId, Long parentId);
	
    public String queryForRoleIdNot(Long roleId);
    public String queryForRoleId(Long roleId);

}
