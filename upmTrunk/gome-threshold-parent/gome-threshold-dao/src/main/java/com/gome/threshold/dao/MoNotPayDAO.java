package com.gome.threshold.dao;

import org.springframework.stereotype.Repository;

import com.gome.threshold.domain.MoOrderNotRechargeBO;
@Repository("moNotPayDAO")
public interface MoNotPayDAO {
	Integer searchMoNotPayCountByTime(MoOrderNotRechargeBO monitoVO);
}
