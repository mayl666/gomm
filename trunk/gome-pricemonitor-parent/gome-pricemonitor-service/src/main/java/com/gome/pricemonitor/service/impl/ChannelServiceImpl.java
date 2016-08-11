package com.gome.pricemonitor.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.pricemonitor.common.Page;
import com.gome.pricemonitor.dao.ChannelMapper;
import com.gome.pricemonitor.domain.Channel;
import com.gome.pricemonitor.service.ChannelService;

/**
 * 
 * 频道service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年10月27日    caowei    新建
 * </pre>
 */
@Service
public class ChannelServiceImpl implements ChannelService {

	@Resource
	private ChannelMapper channelMapper;
	
	public int addChannel(Channel channel) {
		return channelMapper.insertChannel(channel);
	}

	public Page<Channel> findChannelListByPage(Page<Channel> page) {
		List<Channel> channelList = channelMapper.selectChannelListByPage(page);
		int totalResult = channelMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<Channel>(page.getPageNo(), page.getPageSize(), totalResult, channelList, page.getConditions());
	}

	public int editChannel(Channel channel) {
		return channelMapper.updateChannel(channel);
	}

	public int deleteChannelById(Long id) {
		return channelMapper.deleteChannelById(id);
	}

	public Channel findChannelById(Long id) {
		return channelMapper.selectChannelById(id);
	}

	public List<Channel> findChannelListByChannel(Channel channel) {
		return channelMapper.selectChannelListByChannel(channel);
	}

}
