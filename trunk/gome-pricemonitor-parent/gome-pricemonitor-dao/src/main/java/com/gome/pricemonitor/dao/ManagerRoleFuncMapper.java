package com.gome.pricemonitor.dao;

import com.gome.pricemonitor.domain.ManagerRoleFunc;

public interface ManagerRoleFuncMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ManagerRoleFunc record);

    int insertSelective(ManagerRoleFunc record);

    ManagerRoleFunc selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ManagerRoleFunc record);

    int updateByPrimaryKey(ManagerRoleFunc record);
    
    public void deleteByRoleId(Long roleId);
}