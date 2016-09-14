package com.gome.upm.dao;

import java.util.List;

import com.gome.upm.domain.MoSynBO;
public interface MoSynDAO {
	void saveMoSyn(MoSynBO moSynBO);
	int updateMoSyn(MoSynBO moSynBO);
	List<MoSynBO> searchMoSynList(MoSynBO moSynBO);
}
