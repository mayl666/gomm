package com.gome.pricemonitor.dao;

import java.util.List;

import com.gome.pricemonitor.common.Page;
import com.gome.pricemonitor.domain.Goods;

/**
 * 
 * 商品dao接口.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface GoodsMapper {

	/**
	 *
	 * 插入商品.
	 *
	 * @param goods
	 * 			商品信息
	 * @return
	 * 			插入记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 */
	int insertGoods(Goods goods);

	/**
	 *
	 * 分页查询商品列表.
	 *
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			商品列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 */
	List<Goods> selectGoodsListByPage(Page<Goods> page);
	
	/**
	 *
	 * 根据搜索条件查询总记录数.
	 * @param goods
	 * 			搜索条件
	 * @return
	 * 			总记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 */
	Integer selectTotalResultByConditions(Goods goods);

	/**
	 *
	 * 更新商品.
	 *
	 * @param goods
	 * 			商品信息
	 * @return
	 * 			更新记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 */
	int updateGoods(Goods goods);

	/**
	 *
	 * 根据ID删除商品.
	 *
	 * @param id
	 * 			商品ID
	 * @return
	 * 			删除记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 */
	int deleteGoodsById(Long id);
	
	/**
	 *
	 * 根据ID查找商品.
	 *
	 * @param id
	 * 			商品ID
	 * @return
	 * 			商品信息
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 */
	Goods selectGoodsById(Long id);

	/**
	 *
	 * 根据商品信息查询商品列表.
	 *
	 * @param goods
	 * 			商品信息
	 * @return
	 * 			商品列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 */
	List<Goods> selectGoodsListByGoods(Goods goods);
}
