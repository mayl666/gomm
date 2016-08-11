package com.gome.pricemonitor.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gome.pricemonitor.common.Page;
import com.gome.pricemonitor.dao.AdMapper;
import com.gome.pricemonitor.dao.ChannelMapper;
import com.gome.pricemonitor.domain.Ad;
import com.gome.pricemonitor.service.AdService;

/**
 * 
 * 广告service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年10月31日    caowei    新建
 * </pre>
 */
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service
public class AdServiceImpl implements AdService {

	@Resource
	private AdMapper adMapper;
	
	@Resource
	private ChannelMapper channelMapper;
	
	public int addAd(Ad ad) {
		return adMapper.insertAd(ad);
	}

	public Page<Ad> findAdListByPage(Page<Ad> page) {
		List<Ad> adList = adMapper.selectAdListByPage(page);
		int totalResult = adMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<Ad>(page.getPageNo(), page.getPageSize(), totalResult, adList, page.getConditions());
	}

	public int editAd(Ad ad) {
		return adMapper.updateAd(ad);
	}

	public int deleteAdById(Long id) {
		return adMapper.deleteAdById(id);
	}

	public Ad findAdById(Long id) {
		return adMapper.selectAdById(id);
	}

	public List<Ad> findAdListByAd(Ad ad) {
		return adMapper.selectAdListByAd(ad);
	}

	@Override
	public boolean exchangeAd(Ad ad1, Ad ad2) {
		int record1 = adMapper.updateAd(ad1);
		int record2 = adMapper.updateAd(ad2);
		if(record1 > 0 && record2 > 0){
			return true;
		}
		return false;
	}

}
