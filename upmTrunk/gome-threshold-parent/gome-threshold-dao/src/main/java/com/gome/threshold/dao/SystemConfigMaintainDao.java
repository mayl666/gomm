package com.gome.threshold.dao;

import org.apache.ibatis.annotations.Param;

import com.gome.threshold.domain.SystemConfig;

public interface SystemConfigMaintainDao {
	SystemConfig querySystemConfigByKey(@Param("paramSQL")String sql) throws Exception;
}
