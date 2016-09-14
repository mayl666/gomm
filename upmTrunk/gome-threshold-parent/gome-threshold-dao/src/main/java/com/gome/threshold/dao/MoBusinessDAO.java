package com.gome.threshold.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gome.threshold.domain.MapCoordinate;
import com.gome.threshold.domain.MoBusiness;
@Repository("moBusinessDAO")
public interface MoBusinessDAO {
	/**
	 * 查询种类排行
	 */
	public List<MoBusiness> searchGoodKindSort(MoBusiness moBusiness);
	/**
	 * 查询品牌排行
	 */
	public List<MoBusiness> searchGoodBrandSort(MoBusiness moBusiness);
	/**
	 * 查询商品属性分类
	 */
	public List<MoBusiness> searchGoodProperty(MoBusiness moBusiness);
	/**
	 * 充值订单
	 */
	public Integer getOrderAmountForPay(MoBusiness moBusiness);
	/**
	 * 非充值订单
	 */
	public Integer getOrderAmountForNotPay(MoBusiness moBusiness);
	/**
	 * 支付成功订单量		ALREADY
	 */
	public Integer getOrderAmountForAlreadyPay(MoBusiness moBusiness);
	/**
	 * 妥投成功订单
	 */
	public Integer getOrderAmountForDelivery(MoBusiness moBusiness);
	/**
	 * Web端
	 */
	public Integer getOrderAmountForWeb(MoBusiness moBusiness);
	/**
	 * Wap
	 */
	public Integer getOrderAmountForWap(MoBusiness moBusiness);
	/**
	 * App端
	 */
	public Integer getOrderAmountForApp(MoBusiness moBusiness);
	/**
	 * 查询订单量
	 */
	public void searchOrderCount();
	
	
	/**
	 * 查询所有订单数量
	 */
	public List<MoBusiness> searchAllOrderCountHistory(MoBusiness moBusiness);
	/**
	 * 查询成功在线支付的订单数量
	 */
	public List<MoBusiness> searchOneLineOrderCountHistory(MoBusiness moBusiness);
	
	/**
	 * 获取订单最多的前5个城市
	 * @param moBusiness
	 * @return
	 */
	public List<MoBusiness> getCityBySales(MoBusiness moBusiness);
	/**
	 * 获取城市坐标
	 * @return
	 */
	public List<MapCoordinate> getCityCoordinateList();

	/**
	 * 查询CPS数据
	 * @param monitoVO
	 * @return
	 */
	Integer searchCpsCount(MoBusiness monitoVO);
}
