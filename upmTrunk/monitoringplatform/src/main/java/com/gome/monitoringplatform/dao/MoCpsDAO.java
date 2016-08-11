package com.gome.monitoringplatform.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gome.monitoringplatform.model.bo.MoCpsBO;
import com.gome.monitoringplatform.model.bo.MoLoginInfoBO;
@Repository("moCpsDAO")
public interface MoCpsDAO {
	void saveMoCps(MoCpsBO moCpsBO);
	List<MoCpsBO> searchMoCpsList(MoCpsBO moLoginInfoBO);
}
