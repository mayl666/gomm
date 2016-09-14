package com.gome.threshold.dao;

import java.util.List;

import com.gome.threshold.domain.MoSynBO;
public interface MoSynDAO {
	void saveMoSyn(MoSynBO moSynBO);
	List<MoSynBO> searchMoSynList(MoSynBO moSynBO);
}
