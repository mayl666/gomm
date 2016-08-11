package com.gome.pricemonitor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gome.pricemonitor.domain.ManagerFunc;

public interface ManagerFuncMapper {
    int deleteByPrimaryKey(Long funcId);

    int insert(ManagerFunc record);

    int insertSelective(ManagerFunc record);

    ManagerFunc selectByPrimaryKey(Long funcId);

    int updateByPrimaryKeySelective(ManagerFunc record);

    int updateByPrimaryKey(ManagerFunc record);
    
    /**
     * 获取全部数据
     * @return
     */
    public List<ManagerFunc> query();
    
    /**
     * 添加角色时的功能列表
     * @param record
     * @return
     */
    public List<ManagerFunc> queryForRole(@Param("roleId") Long roleId, @Param("parentId") Long parentId);
    
    
    public List<Long> queryForRoleId(@Param("roleId") Long roleId, @Param("parentId") Long parentId);
    
    //public List<Long> queryForSecondLevel(@Param("roleId") Long roleId, @Param("parentId") Long parentId); 
    
    public List<Long> queryByRoleIdAndParentId(@Param("roleId") Long roleId);
    
    /**
     * 获取所有正常的二级权限
     * @return
     */
    public List<Long> queryNormal();
    
}