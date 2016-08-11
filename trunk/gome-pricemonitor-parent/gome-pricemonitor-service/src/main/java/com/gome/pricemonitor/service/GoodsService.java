package com.gome.pricemonitor.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.gome.pricemonitor.common.Page;
import com.gome.pricemonitor.domain.Goods;

/**
 * 商品service接口.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface GoodsService {

	/**
	 * 
	 * 新增商品.
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
	int addGoods(Goods goods);

	/**
	 * 
	 * 分页查询商品列表.
	 *
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			分页数据
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 */
	Page<Goods> findGoodsListByPage(Page<Goods> page);

	/**
	 *
	 * 编辑商品.
	 *
	 * @param goods
	 * 			商品信息
	 * @return
	 * 			修改记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 */
	int editGoods(Goods goods);

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
	 * 根据ID查询商品.
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
	Goods findGoodsById(Long id);

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
	List<Goods> findGoodsListByGoods(Goods goods);

	void exportExcel(List<Goods> goodsList, HttpServletResponse response);
	
}
