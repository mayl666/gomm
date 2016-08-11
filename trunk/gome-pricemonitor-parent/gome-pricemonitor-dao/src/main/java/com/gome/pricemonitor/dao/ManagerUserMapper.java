package com.gome.pricemonitor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gome.pricemonitor.domain.ManagerUser;

public interface ManagerUserMapper {
	int deleteByPrimaryKey(Long userId);

	int insert(ManagerUser record);

	int insertSelective(ManagerUser record);

	ManagerUser selectByPrimaryKey(Long userId);

	int updateByPrimaryKeySelective(ManagerUser record);

	int updateByPrimaryKey(ManagerUser record);

	public List<ManagerUser> query(@Param("condition") String condition, @Param("roleId") Long roleId, @Param("ordery") String ordery, @Param("offset") int offset,@Param("count") int count);
	
	public Long queryCount(@Param("condition") String condition, @Param("roleId") Long roleId);
	
	public ManagerUser selectByUserName(String userName);
}