package com.gome.pricemonitor.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.pricemonitor.common.Page;
import com.gome.pricemonitor.dao.GoodsCategoryMapper;
import com.gome.pricemonitor.domain.GoodsCategory;
import com.gome.pricemonitor.service.GoodsCategoryService;

/**
 * 
 * 商品类别service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月05日    caowei    新建
 * </pre>
 */
@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {

	@Resource
	private GoodsCategoryMapper goodsCategoryMapper;
	
	public int addGoodsCategory(GoodsCategory goodsCategory) {
		return goodsCategoryMapper.insertGoodsCategory(goodsCategory);
	}

	public Page<GoodsCategory> findGoodsCategoryListByPage(Page<GoodsCategory> page) {
		List<GoodsCategory> goodsCategoryList = goodsCategoryMapper.selectGoodsCategoryListByPage(page);
		int totalResult = goodsCategoryMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<GoodsCategory>(page.getPageNo(), page.getPageSize(), totalResult, goodsCategoryList, page.getConditions());
	}

	public int editGoodsCategory(GoodsCategory goodsCategory) {
		return goodsCategoryMapper.updateGoodsCategory(goodsCategory);
	}

	public int deleteGoodsCategoryById(Long id) {
		return goodsCategoryMapper.deleteGoodsCategoryById(id);
	}

	public GoodsCategory findGoodsCategoryById(Long id) {
		return goodsCategoryMapper.selectGoodsCategoryById(id);
	}

	public List<GoodsCategory> findGoodsCategoryListByGoodsCategory(GoodsCategory goodsCategory) {
		return goodsCategoryMapper.selectGoodsCategoryListByGoodsCategory(goodsCategory);
	}

}
