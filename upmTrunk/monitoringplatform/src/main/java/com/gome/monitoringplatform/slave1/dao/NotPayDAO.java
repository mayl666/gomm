package com.gome.monitoringplatform.slave1.dao;

import org.springframework.stereotype.Repository;

import com.gome.monitoringplatform.model.bo.MoOrderNotRechargeBO;
@Repository("notPayDAO")
public interface NotPayDAO {
	Integer searchCountByTime(MoOrderNotRechargeBO monitoVO);
}
