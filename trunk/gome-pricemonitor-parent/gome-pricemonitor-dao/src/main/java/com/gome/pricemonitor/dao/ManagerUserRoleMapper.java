package com.gome.pricemonitor.dao;

import com.gome.pricemonitor.domain.ManagerUserRole;

public interface ManagerUserRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ManagerUserRole record);

    int insertSelective(ManagerUserRole record);

    ManagerUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ManagerUserRole record);

    int updateByPrimaryKey(ManagerUserRole record);
    
    public void delByUser(Long userId);
    
    public void delByRole(Long userId);
    
    public ManagerUserRole getByRoleId(Long userId);
    
    public Long getCountByRoleId(Long roleId);
}