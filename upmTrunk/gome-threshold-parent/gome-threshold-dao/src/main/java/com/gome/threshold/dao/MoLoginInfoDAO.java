package com.gome.threshold.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gome.threshold.domain.MoCpsBO;
import com.gome.threshold.domain.MoLoginInfoBO;

@Repository("moLoginInfoDAO")
public interface MoLoginInfoDAO {
	/**
	 * @param omsOrder  开始时间  结束时间
	 * @return
	 */
	List<MoLoginInfoBO> searchMoLoginInfoList(MoLoginInfoBO moLoginInfoBO);
	/**
	 * @param omsOrder  开始时间  结束时间
	 * @return
	 */
	Integer searchCountByTime(MoLoginInfoBO moLoginInfoBO);
	/**
	 * 保存
	 * @param omsOrder
	 */
	void saveMoLoginInfo(MoLoginInfoBO moLoginInfoBO);
	/**
	 * 查询CPS历史数据
	 * @param moLoginInfoBO
	 * @return
	 */
	List<MoCpsBO> searchMoCpsList(MoCpsBO moCpsBO);
}
