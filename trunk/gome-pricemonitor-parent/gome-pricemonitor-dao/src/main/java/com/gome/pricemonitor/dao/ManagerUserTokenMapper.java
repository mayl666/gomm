package com.gome.pricemonitor.dao;

import com.gome.pricemonitor.domain.ManagerUserToken;

public interface ManagerUserTokenMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ManagerUserToken record);

    int insertSelective(ManagerUserToken record);

    ManagerUserToken selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ManagerUserToken record);

    int updateByPrimaryKey(ManagerUserToken record);
    
    ManagerUserToken selectByUserToken(ManagerUserToken record);
}