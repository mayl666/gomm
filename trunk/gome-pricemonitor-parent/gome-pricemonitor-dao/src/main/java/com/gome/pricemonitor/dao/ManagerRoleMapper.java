package com.gome.pricemonitor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gome.pricemonitor.domain.ManagerRole;

public interface ManagerRoleMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(ManagerRole record);

    int insertSelective(ManagerRole record);

    ManagerRole selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(ManagerRole record);

    int updateByPrimaryKey(ManagerRole record);
    
    public List<ManagerRole> query(@Param("roleName") String roleName, @Param("ordery") String ordery, @Param("offset") int offset,@Param("count") int count);
    
    public Long queryCount(String roleName);
    
    public List<ManagerRole> getList(@Param("state") int state);
    
    public ManagerRole selectByRoleName(String roleName);
    
    public ManagerRole selectByUserId(@Param(value="userId") Long userId);
}