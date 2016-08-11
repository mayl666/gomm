package com.gome.monitoringplatform.slave1.dao;

import org.springframework.stereotype.Repository;

import com.gome.monitoringplatform.model.bo.MoLoginInfoBO;

@Repository("gomeLoginInfoDAO")
public interface GomeLoginInfoDAO {
	Integer searchCountByTime(MoLoginInfoBO gomeLoginInfoVO);
}
