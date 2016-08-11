package com.gome.pricemonitor.dao;

import java.util.List;

import com.gome.pricemonitor.common.Page;
import com.gome.pricemonitor.domain.GoodsCategory;

/**
 * 
 * 商品类别dao接口.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月05日    caowei    新建
 * </pre>
 */
public interface GoodsCategoryMapper {

	/**
	 *
	 * 插入商品类别.
	 *
	 * @param goodsCategory
	 * 			商品类别信息
	 * @return
	 * 			插入记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月05日    caowei    新建
	 * </pre>
	 */
	int insertGoodsCategory(GoodsCategory goodsCategory);

	/**
	 *
	 * 分页查询商品类别列表.
	 *
	 * @param goodsCategory
	 * 			商品类别信息（封装了查询条件）
	 * @return
	 * 			商品类别列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月05日    caowei    新建
	 * </pre>
	 */
	List<GoodsCategory> selectGoodsCategoryListByPage(Page<GoodsCategory> page);
	
	/**
	 *
	 * 根据搜索条件查询总记录数.
	 * @param goodsCategory
	 * 			搜索条件
	 * @return
	 * 			总记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月05日    caowei    新建
	 * </pre>
	 */
	Integer selectTotalResultByConditions(GoodsCategory goodsCategory);

	/**
	 *
	 * 更新商品类别.
	 *
	 * @param goodsCategory
	 * 			商品类别信息
	 * @return
	 * 			更新记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月05日    caowei    新建
	 * </pre>
	 */
	int updateGoodsCategory(GoodsCategory goodsCategory);

	/**
	 *
	 * 根据ID删除商品类别.
	 *
	 * @param id
	 * 			商品类别ID
	 * @return
	 * 			删除记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月05日    caowei    新建
	 * </pre>
	 */
	int deleteGoodsCategoryById(Long id);

	/**
	 *
	 * 根据ID查找商品类别.
	 *
	 * @param id
	 * 			商品类别ID
	 * @return
	 * 			商品类别信息
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月05日    caowei    新建
	 * </pre>
	 */
	GoodsCategory selectGoodsCategoryById(Long id);

	/**
	 *
	 * 根据商品类别信息查询商品类别列表.
	 *
	 * @param goodsCategory
	 * 				商品类别信息
	 * @return
	 * 				商品类别列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月05日    caowei    新建
	 * </pre>
	 */
	List<GoodsCategory> selectGoodsCategoryListByGoodsCategory(GoodsCategory goodsCategory);
}
