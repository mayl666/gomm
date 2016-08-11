package com.gome.pricemonitor.service;

import java.util.List;

import com.gome.pricemonitor.common.Page;
import com.gome.pricemonitor.domain.GoodsCategory;

/**
 * 商品类别service接口.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月05日    caowei    新建
 * </pre>
 */
public interface GoodsCategoryService {

	/**
	 * 
	 * 新增商品类别.
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
	int addGoodsCategory(GoodsCategory goodsCategory);

	/**
	 * 
	 * 分页查询商品类别列表.
	 *
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			分页数据
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月05日    caowei    新建
	 * </pre>
	 */
	Page<GoodsCategory> findGoodsCategoryListByPage(Page<GoodsCategory> page);

	/**
	 *
	 * 编辑商品类别.
	 *
	 * @param goodsCategory
	 * 			商品类别信息
	 * @return
	 * 			修改记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月05日    caowei    新建
	 * </pre>
	 */
	int editGoodsCategory(GoodsCategory goodsCategory);

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
	 * 根据ID查询商品类别.
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
	GoodsCategory findGoodsCategoryById(Long id);

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
	List<GoodsCategory> findGoodsCategoryListByGoodsCategory(GoodsCategory goodsCategory);
}
