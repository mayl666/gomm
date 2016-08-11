package com.gome.pricemonitor.controler;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.gome.pricemonitor.common.Constant;
import com.gome.pricemonitor.common.Page;
import com.gome.pricemonitor.common.util.ResponsesDTO;
import com.gome.pricemonitor.constants.ReturnCode;
import com.gome.pricemonitor.domain.Ad;
import com.gome.pricemonitor.domain.Channel;
import com.gome.pricemonitor.domain.PriceLog;
import com.gome.pricemonitor.service.AdService;
import com.gome.pricemonitor.service.ChannelService;
import com.gome.pricemonitor.service.PriceMonitorService;
import com.gome.pricemonitor.controler.base.BaseController;

/**
 * 广告频道控制器.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年10月27日    caowei    新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/channel")
public class ChannelController extends BaseController {
	
	@Resource
	private ChannelService channelService;
	
	@Resource
	private AdService adService;

	/**
	 * 跳转到新增频道页.
	 *
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月27日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/toAddChannelView", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView toAddChannelView(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/channel/addChannel");
		
		String userName = getUserInfo(request, "userName");
		model.addObject("operateUser", userName);
		return model;
	}
	
	/**
	 * 
	 * 新增频道.
	 *
	 * @param content
	 * 				频道信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月27日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/addChannel", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO addChannel(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(content)){
				Channel channel = JSON.parseObject(content, Channel.class);
				if(channel != null){
					channel.setChannelType(1);
					channel.setCreateTime(new Date());
					channel.setCreateUser(channel.getOperateUser());
					channel.setUpdateTime(new Date());
					channel.setUpdateUser(channel.getOperateUser());
					int record = channelService.addChannel(channel);
					if(record > 0){
						responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
					} else {
						responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
	
	/**
	 * 
	 * 跳转到频道列表页.
	 *
	 * @param channelName
	 * 				频道名称
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月28日    caowei    新建
	 * </pre>
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/toChannelListView", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toChannelListView(@Param(value="content")String content, @Param(value="page")String page, @Param(value="size")String size, HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws UnsupportedEncodingException{
		if(StringUtils.isEmpty(content)){
			model.setViewName("/channel/channelList");
		} else {
			model.setViewName("/channel/channelTable");
		}
		
		int pageNo = 1;
		if(StringUtils.isNotEmpty(page)){
			pageNo = Integer.parseInt(page);
		}
		
		int pageSize = Constant.PAGE_SIZE;
		if(StringUtils.isNotEmpty(size)){
			pageSize = Integer.parseInt(size);
		}
		Page<Channel> p = new Page<Channel>(pageNo, pageSize);
		
		Channel channel = null;
		//通过频道名称查找
		if(StringUtils.isNotBlank(content)){
			channel = JSON.parseObject(content, Channel.class);
		} else {
			channel = new Channel();
		}
		p.setConditions(channel);
		Page<Channel> pageChannel = channelService.findChannelListByPage(p);
		model.addObject("page", pageChannel);
		return model;
	}
	
	/**
	 *
	 * 检查名称是否被使用.
	 *
	 * @param id
	 * 			频道ID（编辑时传递，添加时不传递）
	 * @param channelName
	 * 			频道名称
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月31日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/checkIsUsed", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO checkIsUsed(@Param(value="id")String id, @Param(value="channelName")String channelName, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try {
			if(StringUtils.isNotBlank(channelName)){
				Channel channel = new Channel();
				channel.setChannelName(channelName);
				if(StringUtils.isNotEmpty(id)){
					channel.setId(Long.parseLong(id));
				}
				List<Channel> channelList = channelService.findChannelListByChannel(channel);
				if(channelList.size() > 0){
					//名称已被使用
					responses.setReturnCode(ReturnCode.ERROR_NAME_HAS_USED);
				} else {
					//名称未被使用
					responses.setReturnCode(ReturnCode.ERROR_NAME_NOT_USED);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
	
	/**
	 *
	 * 检查频道是否可以删除.
	 *
	 * @param id
	 * 			频道ID
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月06日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/checkEnableDelete", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO checkEnableDelete(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try {
			if(StringUtils.isNotBlank(id)){
				Ad ad = new Ad();
				ad.setChannelId(Long.parseLong(id));
				List<Ad> adList = adService.findAdListByAd(ad);
				if(adList.size() > 0){
					//频道下已存在广告位，频道不为空，频道将不能被删除
					responses.setReturnCode(ReturnCode.ERROR_CHANNEL_NOT_EMPTY);
				} else {
					//频道下不存在广告位，频道为空，可以删除
					responses.setReturnCode(ReturnCode.ERROR_CHANNEL_EMPTY);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
	
	/**
	 * 
	 * 跳转到编辑频道页.
	 *
	 * @param id
	 * 			频道ID
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月28日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/toEditChannelView", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toEditChannelView(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/channel/editChannel");
		if(StringUtils.isNotEmpty(id)){
			Channel channel = channelService.findChannelById(Long.parseLong(id));
			if(channel != null){
				model.addObject("channel", channel);
				String userName = getUserInfo(request, "userName");
				model.addObject("operateUser", userName);
			}else{
				//频道不存在
				model.addObject("moduleName", "频道列表");
				model.addObject("message", "对不起，频道不存在！");
				model.setViewName("/common/error");
			}
		}
		return model;
	}
	
	/**
	 * 
	 * 编辑频道.
	 *
	 * @param content
	 * 			频道信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月28日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/editChannel", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO editChannel(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			Channel channel = JSON.parseObject(content, Channel.class);
			if(channel != null){
				channel.setUpdateUser(channel.getOperateUser());
				channel.setUpdateTime(new Date());
				int record = channelService.editChannel(channel);
				if(record > 0){
					responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
				} else {
					responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
	
	/**
	 *
	 * 删除频道.
	 *
	 * @param id
	 * 			频道ID
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月28日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/deleteChannel", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO deleteChannel(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(id)){
				Long channelId = Long.parseLong(id);
				int record = channelService.deleteChannelById(channelId);
				if(record > 0){
					responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
				} else {
					responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
	
	@Resource
	private PriceMonitorService priceService;
	
	@RequestMapping(value="/test", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView test(@Param(value="content")String content, @Param(value="page")String page, @Param(value="size")String size, HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws UnsupportedEncodingException{
		if(StringUtils.isEmpty(content)){
			model.setViewName("/channel/channelList");
		} else {
			model.setViewName("/channel/channelTable");
		}
		
		int pageNo = 1;
		if(StringUtils.isNotEmpty(page)){
			pageNo = Integer.parseInt(page);
		}
		
		int pageSize = Constant.PAGE_SIZE;
		if(StringUtils.isNotEmpty(size)){
			pageSize = Integer.parseInt(size);
		}
		Page<PriceLog> p = new Page<PriceLog>(pageNo, pageSize);
		
		PriceLog priceLog = new PriceLog();
//		priceLog.setAction("1");
//		priceLog.setNode("AUX");
//		priceLog.setResult("TRUE");
//		priceLog.setId("8d208a4ad2ae40418fce5f8f0c901c48_01_8007703662_00000000");
		p.setConditions(priceLog);
		System.out.println("pageNo:" + p.getPageNo());
		Page<PriceLog> pricePage = priceService.selectPriceLogListByConditionsPage(p);
		model.addObject("page", pricePage);
		return model;
	}
}
