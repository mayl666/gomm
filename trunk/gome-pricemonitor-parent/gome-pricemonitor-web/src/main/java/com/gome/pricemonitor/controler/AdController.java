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
import com.alibaba.fastjson.JSONObject;
import com.gome.pricemonitor.common.Constant;
import com.gome.pricemonitor.common.Page;
import com.gome.pricemonitor.common.util.ResponsesDTO;
import com.gome.pricemonitor.constants.ReturnCode;
import com.gome.pricemonitor.domain.Ad;
import com.gome.pricemonitor.domain.Channel;
import com.gome.pricemonitor.service.AdService;
import com.gome.pricemonitor.service.ChannelService;
import com.gome.pricemonitor.controler.base.BaseController;

/**
 * 广告控制器.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年10月31日    caowei    新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/ad")
public class AdController extends BaseController {
	
	@Resource
	private AdService adService;

	@Resource
	private ChannelService channelService;
	
	/**
	 * 跳转到新增广告页.
	 *
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月31日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/toAddAdView", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView toAddAdView(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/ad/addAd");
		
		List<Channel> channelList = channelService.findChannelListByChannel(new Channel());
		int picNum = 0;
		Ad ada = new Ad();
		for (Channel channel : channelList) {
			picNum = channel.getPicNum();
			ada.setChannelId(channel.getId());
			List<Ad> adList = adService.findAdListByAd(ada);
			if(adList != null && adList.size() >= picNum){
				//广告位已满
				channel.setIsFull(1);
			}else{
				channel.setIsFull(0);
			}
		}
		model.addObject("channelList", channelList);
		String userName = getUserInfo(request, "userName");
		model.addObject("operateUser", userName);
		return model;
	}
	
	/**
	 * 
	 * 新增广告.
	 *
	 * @param content
	 * 				广告信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月31日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/addAd", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO addAd(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(content)){
				Ad ad = JSON.parseObject(content, Ad.class);
				if(ad != null){
					//检查是否可以添加
					responses = checkEnableAdd(ad);
					if(responses.getCode() == 7 || responses.getCode() == 10){
						return responses;
					}
					ad.setCreateTime(new Date());
					ad.setCreateUser(ad.getOperateUser());
					ad.setUpdateTime(new Date());
					ad.setUpdateUser(ad.getOperateUser());
					int record = adService.addAd(ad);
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
	 * 检查是否可以添加(如果所选频道广告位已满或所选频道相应位置已存在广告位，将不能添加).
	 *
	 * @param ad
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月3日    caowei    新建
	 * </pre>
	 */
	public ResponsesDTO checkEnableAdd(Ad ad){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		Channel channel = channelService.findChannelById(ad.getChannelId());
		int picNum = channel.getPicNum();
		Ad ada = new Ad();
		ada.setChannelId(ad.getChannelId());
		List<Ad> adList = adService.findAdListByAd(ada);
		if(adList != null && adList.size() >= picNum){
			//广告位已满
			responses.setReturnCode(ReturnCode.ERROR_CHANNEL_HAS_FULL);
		} else {
			ada.setSort(ad.getSort());
			adList = adService.findAdListByAd(ada);
			if(adList != null && adList.size() > 0){
				//该频道在该位置已经存在广告位了
				responses.setReturnCode(ReturnCode.ERROR_CHANNEL_SORT_HAS_EXIST);
			}
		}
		return responses;
	}
	
	@RequestMapping(value="/checkIsFull", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO checkIsFull(@Param(value="channelId")String channelId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		Channel channel = channelService.findChannelById(Long.parseLong(channelId));
		int picNum = channel.getPicNum();
		Ad ada = new Ad();
		ada.setChannelId(Long.parseLong(channelId));
		List<Ad> adList = adService.findAdListByAd(ada);
		if(adList != null && adList.size() >= picNum){
			//广告位已满
			responses.setReturnCode(ReturnCode.ERROR_CHANNEL_HAS_FULL);
		}
		return responses;
	}
	
	/**
	 * 
	 * 跳转到广告列表页.
	 *
	 * @param content
	 * 				搜索条件
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
	@RequestMapping(value="/toAdListView", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toAdListView(@Param(value="content")String content, @Param(value="page")String page, @Param(value="size")String size, HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws UnsupportedEncodingException{
		if(StringUtils.isEmpty(content)){
			model.setViewName("/ad/adList");
		} else {
			model.setViewName("/ad/adTable");
		}
		
		int pageNo = 1;
		if(StringUtils.isNotEmpty(page)){
			pageNo = Integer.parseInt(page);
		}
		
		int pageSize = Constant.PAGE_SIZE;
		if(StringUtils.isNotEmpty(size)){
			pageSize = Integer.parseInt(size);
		}
		Page<Ad> p = new Page<Ad>(pageNo, pageSize);
		
		Ad ad = null;
		if(StringUtils.isNotEmpty(content)){
			ad = JSON.parseObject(content, Ad.class);
		} else {
			ad = new Ad();
		}
		p.setConditions(ad);
		Page<Ad> pageAd = adService.findAdListByPage(p);
		model.addObject("page", pageAd);
		return model;
	}
	
	/**
	 *
	 * 检查名称是否被使用.
	 *
	 * @param id
	 * 			广告ID（编辑时传递，添加时不传递）
	 * @param AdName
	 * 			广告名称
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月31日    caowei    新建
	 * </pre>
	 */
	/*
	@RequestMapping(value="/checkIsUsed", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO checkIsUsed(@Param(value="id")String id, @Param(value="AdName")String AdName, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try {
			if(StringUtils.isNotBlank(AdName)){
				Ad Ad = new Ad();
				Ad.setAdName(AdName);
				if(StringUtils.isNotEmpty(id)){
					Ad.setId(Long.parseLong(id));
				}
				List<Ad> AdList = AdService.findAdListByAd(Ad);
				if(AdList.size() > 0){
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
	*/
	
	/**
	 * 
	 * 跳转到编辑广告页.
	 *
	 * @param id
	 * 			广告ID
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月31日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/toEditAdView", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toEditAdView(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/ad/editAd");
		if(StringUtils.isNotEmpty(id)){
			Ad ad = adService.findAdById(Long.parseLong(id));
			if(ad != null){
				Channel channel = channelService.findChannelById(ad.getChannelId());
				model.addObject("ad", ad);
				Ad ad2 = new Ad();
				ad2.setChannelId(ad.getChannelId());
				List<Ad> adList = adService.findAdListByAd(ad2);
				StringBuffer sb = new StringBuffer();
				if(adList != null && adList.size() > 0){
					for (Ad ad3 : adList) {
						sb.append(ad3.getSort());
						sb.append(",");
					}
				}
				model.addObject("disArray", sb.toString());
				model.addObject("initPicNum", channel.getPicNum());
				String userName = getUserInfo(request, "userName");
				model.addObject("operateUser", userName);
			}else{
				//广告不存在
				model.addObject("moduleName", "广告列表");
				model.addObject("message", "对不起，广告不存在！");
				model.setViewName("/common/error");
				return model;
			}
			List<Channel> channelList = channelService.findChannelListByChannel(new Channel());
			int picNum = 0;
			Ad ada = new Ad();
			for (Channel channel : channelList) {
				picNum = channel.getPicNum();
				ada.setChannelId(channel.getId());
				List<Ad> adList = adService.findAdListByAd(ada);
				if(adList != null && adList.size() >= picNum){
					//广告位已满
					channel.setIsFull(1);
				}else{
					channel.setIsFull(0);
				}
			}
			model.addObject("channelList", channelList);
		}
		return model;
	}
	
	/**
	 * 
	 * 编辑广告.
	 *
	 * @param content
	 * 			广告信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月31日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/editAd", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO editAd(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			Ad ad = JSON.parseObject(content, Ad.class);
			if(ad != null){
				//检查是否可以修改
				responses = checkEnableEdit(ad);
				if(responses.getCode() == 7 || responses.getCode() == 10){
					return responses;
				}
				ad.setUpdateUser(ad.getOperateUser());
				ad.setUpdateTime(new Date());
				int record = adService.editAd(ad);
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
	 * 检查是否可以编辑(如果所选频道广告位已满或所选频道相应位置已存在广告位，将不能编辑).
	 *
	 * @param ad
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月3日    caowei    新建
	 * </pre>
	 */
	public ResponsesDTO checkEnableEdit(Ad ad){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		Ad currentAd = adService.findAdById(ad.getId());
		if(currentAd.getChannelId() != ad.getChannelId()){
			Channel channel = channelService.findChannelById(ad.getChannelId());
			int picNum = channel.getPicNum();
			Ad ada = new Ad();
			ada.setChannelId(ad.getChannelId());
			List<Ad> adList = adService.findAdListByAd(ada);
			if(adList != null && adList.size() >= picNum){
				//广告位已满
				responses.setReturnCode(ReturnCode.ERROR_CHANNEL_HAS_FULL);
			} else {
				ada.setSort(ad.getSort());
				adList = adService.findAdListByAd(ada);
				if(adList != null && adList.size() > 0){
					//该频道在该位置已经存在广告位了
					responses.setReturnCode(ReturnCode.ERROR_CHANNEL_SORT_HAS_EXIST);
				}
			}
		} else if(currentAd.getSort() != ad.getSort()){
			Ad ada = new Ad();
			ada.setChannelId(ad.getChannelId());
			ada.setSort(ad.getSort());
			List<Ad> adList = adService.findAdListByAd(ada);
			if(adList != null && adList.size() > 0){
				//该频道在该位置已经存在广告位了
				responses.setReturnCode(ReturnCode.ERROR_CHANNEL_SORT_HAS_EXIST);
			}
		}
		
		return responses;
	}
	
	/**
	 *
	 * 删除广告.
	 *
	 * @param id
	 * 			广告ID
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月31日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/deleteAd", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO deleteAd(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(id)){
				Long adId = Long.parseLong(id);
				int record = adService.deleteAdById(adId);
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
	 * 获取对应频道下的展示图片数.
	 *
	 * @param id
	 * 			频道ID
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月03日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/getChannelPicNum", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO getChannelPicNum(@Param(value="channelId")String channelId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try {
			if(StringUtils.isNotEmpty(channelId)){
				Channel channel = channelService.findChannelById(Long.parseLong(channelId));
				if(channel != null){
					Ad ad = new Ad();
					ad.setChannelId(Long.parseLong(channelId));
					List<Ad> adList = adService.findAdListByAd(ad);
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("picNum", channel.getPicNum());
					StringBuffer sb = new StringBuffer();
					if(adList != null && adList.size() > 0){
						for (Ad ad2 : adList) {
							sb.append(ad2.getSort());
							sb.append(",");
						}
					}
					jsonObj.put("disArray", sb.toString());
					responses.setCode(1);
					responses.setAttach(jsonObj);
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
	 * 获取对应频道下的对应广告位的图片地址.
	 *
	 * @param channelId
	 * 			频道ID
	 * @param sort
	 * 			位置
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月10日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/getPicPath", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO getPicPath(@Param(value="channelId")String channelId, @Param(value="sort")String sort, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try {
			if(StringUtils.isNotEmpty(channelId) && StringUtils.isNotEmpty(sort)){
				Ad ad = new Ad();
				ad.setChannelId(Long.parseLong(channelId));
				ad.setSort(Integer.parseInt(sort));
				List<Ad> adList = adService.findAdListByAd(ad);
				if(adList != null && adList.size() > 0){
					responses.setCode(1);
					responses.setAttach(adList.get(0).getPicPath());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
	
	/**
	 * 跳转到调换广告页.
	 *
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月04日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/toExchangeAdView", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView toExchangeAdView(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/ad/exchangeAd");
		
		List<Channel> channelList = channelService.findChannelListByChannel(new Channel());
		model.addObject("channelList", channelList);
		return model;
	}
	
	/**
	 * 
	 * 调换广告位.
	 *
	 * @param content
	 * 			广告信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月04日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/exchangeAd", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO exchangeAd(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			List<Ad> adList = JSON.parseArray(content, Ad.class);
			if(adList != null && adList.size() > 0){
				Ad ad1 = adList.get(0);
				Ad ad2 = adList.get(1);
				List<Ad> ad1List = adService.findAdListByAd(ad1);
				List<Ad> ad2List = adService.findAdListByAd(ad2);
				if(ad1List != null && ad1List.size() > 0){
					if(ad2List != null && ad2List.size() > 0){  //广告1和广告2都存在，同时更新
						ad1 = ad1List.get(0);
						ad2 = ad2List.get(0);
						Long channelId1 = ad1.getChannelId();
						Integer sort1 = ad1.getSort();
						ad1.setChannelId(ad2.getChannelId());
						ad1.setSort(ad2.getSort());
						ad2.setChannelId(channelId1);
						ad2.setSort(sort1);
						boolean result = adService.exchangeAd(ad1, ad2);
						if(result){
							responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
						}
					} else {  
						responses.setReturnCode(ReturnCode.ERROR_AD1_EXIST);
						//广告1存在，广告2不存在，只更新广告1
						/*
						ad1 = ad1List.get(0);
						ad1.setChannelId(ad2.getChannelId());
						ad1.setSort(ad2.getSort());
						int record = adService.editAd(ad1);
						if(record > 0){
							responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
						}
						*/
					}
					
				} else {
					if(ad2List != null && ad2List.size() > 0){  
						responses.setReturnCode(ReturnCode.ERROR_AD2_EXIST);
						//广告1不存在，广告2存在，只更新广告2
						/*
						ad2 = ad2List.get(0);
						ad2.setChannelId(ad1.getChannelId());
						ad2.setSort(ad1.getSort());
						int record = adService.editAd(ad2);
						if(record > 0){
							responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
						}
						*/
					} else {   
						//广告1广告2都不存在，不更新
						//responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
						responses.setReturnCode(ReturnCode.ERROR_AD1_AD2_NOT_EXIST);
					}
				}
			
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
	
}
