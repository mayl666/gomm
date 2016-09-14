package com.gome.threshold.dao;

import org.springframework.stereotype.Repository;

import com.gome.threshold.domain.MoLoginInfoBO;


@Repository("gomeLoginInfoDAO")
public interface GomeLoginInfoDAO {
	Integer searchCountByTime(MoLoginInfoBO gomeLoginInfoVO);
}
