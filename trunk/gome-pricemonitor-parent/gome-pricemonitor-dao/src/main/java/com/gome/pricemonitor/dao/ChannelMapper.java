package com.gome.pricemonitor.dao;

import java.util.List;

import com.gome.pricemonitor.common.Page;
import com.gome.pricemonitor.domain.Channel;

/**
 * 
 * 频道dao接口.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年10月28日    caowei    新建
 * </pre>
 */
public interface ChannelMapper {

	/**
	 *
	 * 插入频道.
	 *
	 * @param channel
	 * 			频道信息
	 * @return
	 * 			插入记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月28日    caowei    新建
	 * </pre>
	 */
	int insertChannel(Channel channel);

	/**
	 *
	 * 分页查询频道列表.
	 *
	 * @param channel
	 * 			频道信息（封装了查询条件）
	 * @return
	 * 			频道列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月28日    caowei    新建
	 * </pre>
	 */
	List<Channel> selectChannelListByPage(Page<Channel> page);
	
	/**
	 *
	 * 根据搜索条件查询总记录数.
	 * @param channel
	 * 			搜索条件
	 * @return
	 * 			总记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月29日    caowei    新建
	 * </pre>
	 */
	Integer selectTotalResultByConditions(Channel channel);

	/**
	 *
	 * 更新频道.
	 *
	 * @param channel
	 * 			频道信息
	 * @return
	 * 			更新记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月28日    caowei    新建
	 * </pre>
	 */
	int updateChannel(Channel channel);

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
	 * 根据ID查找频道.
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
	Channel selectChannelById(Long id);

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
	List<Channel> selectChannelListByChannel(Channel channel);
}
