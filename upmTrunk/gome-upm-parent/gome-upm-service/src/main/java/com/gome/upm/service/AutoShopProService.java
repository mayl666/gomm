package com.gome.upm.service;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.mongo.model.GtpTaskReport;
import com.gome.upm.mongo.model.GtpTaskReportDetaiView;
import com.gome.upm.mongo.model.GtpTaskReportNode;
import com.gome.upm.mongo.model.GtpTaskReportViewIndex;

/**
 * 自动化购物流程service
 * @author zhangzhixiang-ds
 *
 */
public interface AutoShopProService {
	

	/**
	 * 获取列表数据
	 * @param startTime 开始时间
	 * @param type   normal 主流程下单  search搜索下单
	 * @return
	 */
	public List<GtpTaskReport> queryList2List(Long startTime, Long endTime, String type);
	
	/**
	 * 获取自动化购物测试首页数据
	 * @param startTime  开始时间
	 * @return
	 */
	public List<GtpTaskReport> queryList2Index(Long startTime);
	
	/**
	 * 获取自动化购物流程首页展示
	 * @return
	 */
	public GtpTaskReportViewIndex getIndex();
	
	/**
	 * 获取自动化购物流程列表
	 * @param type  normal 主流程下单  search搜索下单
	 * @return      all 全部     pass 通过   fail 失败
	 */
	public List<GtpTaskReportDetaiView> getAspList(String type, String status, Long startTime, Long endTime);
	
	
	/**
	 * 获取自动化购物流程列表展示(当天数据)
	 * @param type  normal 主流程下单  search搜索下单
	 * @return      all 全部     pass 通过   fail 失败
	 */
	public List<GtpTaskReportDetaiView> getAspListView(String type, String status);
	
	
	/**
	 * 分页获取历史数据
	 * @param type         normal 主流程下单  search搜索下单
	 * @param status       all 全部     pass 通过   fail 失败
	 * @param startTime    startTime  开始时间
	 * @param endTime      endTime  结束时间
	 * @param pageNo       分页页码   not null
	 * @param pageSize	      分页大小   not null
	 * @return
	 */
	public Page<GtpTaskReportDetaiView> getPage(String type, String status, Long startTime, Long endTime, Integer pageNo, Integer pageSize);
	
	/**
	 * id查询单挑记录
	 * @param id     主键id
	 * @param type   normal 主流程下单  search搜索下单
	 * @return
	 */
	public List<GtpTaskReportNode> getNodesById(String id, String type);

}
