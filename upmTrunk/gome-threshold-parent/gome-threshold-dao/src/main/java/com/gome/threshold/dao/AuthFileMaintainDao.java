package com.gome.threshold.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.gome.threshold.domain.ConfigFile;

public interface AuthFileMaintainDao {
	List<ConfigFile> queryConfigFileByAuthType(@Param("paramSQL")String sql);
}
