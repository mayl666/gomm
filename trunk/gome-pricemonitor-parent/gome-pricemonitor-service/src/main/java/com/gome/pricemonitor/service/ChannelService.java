package com.gome.pricemonitor.service;

import java.util.List;

import com.gome.pricemonitor.common.Page;
import com.gome.pricemonitor.domain.Channel;

/**
 * 频道service接口.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年10月27日    caowei    新建
 * </pre>
 */
public interface ChannelService {

	/**
	 * 
	 * 新增频道.
	 *
	 * @param channel
	 * 			频道信息
	 * @return
	 * 			插入记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月27日    caowei    新建
	 * </pre>
	 */
	int addChannel(Channel channel);

	/**
	 * 
	 * 分页查询频道列表.
	 *
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			分页数据
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月27日    caowei    新建
	 * </pre>
	 */
	Page<Channel> findChannelListByPage(Page<Channel> page);

	/**
	 *
	 * 编辑频道.
	 *
	 * @param channel
	 * 			频道信息
	 * @return
	 * 			修改记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月28日    caowei    新建
	 * </pre>
	 */
	int editChannel(Channel channel);

	/**
	 *
	 * 根据ID删除频道.
	 *
	 * @param id
	 * 			频道ID
	 * @return
	 * 			删除记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月28日    caowei    新建
	 * </pre>
	 */
	int deleteChannelById(Long id);

	/**
	 *
	 * 根据ID查询频道.
	 *
	 * @param id
	 * 			频道ID
	 * @return
	 * 			频道信息
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月29日    caowei    新建
	 * </pre>
	 */
	Channel findChannelById(Long id);

	/**
	 *
	 * 根据频道信息查询频道列表.
	 *
	 * @param channel
	 * 				频道信息
	 * @return
	 * 				频道列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月30日    caowei    新建
	 * </pre>
	 */
	List<Channel> findChannelListByChannel(Channel channel);
}
