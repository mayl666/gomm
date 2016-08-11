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
import com.gome.pricemonitor.domain.Goods;
import com.gome.pricemonitor.domain.GoodsCategory;
import com.gome.pricemonitor.service.GoodsCategoryService;
import com.gome.pricemonitor.service.GoodsService;
import com.gome.pricemonitor.controler.base.BaseController;

/**
 * 商品类别控制器.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月05日    caowei    新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/goodsCategory")
public class GoodsCategoryController extends BaseController {
	
	@Resource
	private GoodsCategoryService goodsCategoryService;
	
	@Resource
	private GoodsService goodsService;

	/**
	 * 跳转到新增商品类别页.
	 *
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月05日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/toAddGoodsCategoryView", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView toAddGoodsCategoryView(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/goodsCategory/addGoodsCategory");
		
		String userName = getUserInfo(request, "userName");
		model.addObject("operateUser", userName);
		return model;
	}
	
	/**
	 * 
	 * 新增商品类别.
	 *
	 * @param content
	 * 				商品类别信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月05日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/addGoodsCategory", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO addGoodsCategory(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(content)){
				GoodsCategory goodsCategory = JSON.parseObject(content, GoodsCategory.class);
				if(goodsCategory != null){
					goodsCategory.setCreateTime(new Date());
					goodsCategory.setCreateUser(goodsCategory.getOperateUser());
					goodsCategory.setUpdateTime(new Date());
					goodsCategory.setUpdateUser(goodsCategory.getOperateUser());
					int record = goodsCategoryService.addGoodsCategory(goodsCategory);
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
	 * 跳转到商品类别列表页.
	 *
	 * @param goodsCategoryName
	 * 				商品类别名称
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月05日    caowei    新建
	 * </pre>
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/toGoodsCategoryListView", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toGoodsCategoryListView(@Param(value="content")String content, @Param(value="page")String page, @Param(value="size")String size, HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws UnsupportedEncodingException{
		if(StringUtils.isEmpty(content)){
			model.setViewName("/goodsCategory/goodsCategoryList");
		} else {
			model.setViewName("/goodsCategory/goodsCategoryTable");
		}
		
		int pageNo = 1;
		if(StringUtils.isNotEmpty(page)){
			pageNo = Integer.parseInt(page);
		}
		
		int pageSize = Constant.PAGE_SIZE;
		if(StringUtils.isNotEmpty(size)){
			pageSize = Integer.parseInt(size);
		}
		Page<GoodsCategory> p = new Page<GoodsCategory>(pageNo, pageSize);
		
		GoodsCategory goodsCategory = null;
		//通过商品类别名称查找
		if(StringUtils.isNotBlank(content)){
			goodsCategory = JSON.parseObject(content, GoodsCategory.class);
		} else {
			goodsCategory = new GoodsCategory();
		}
		p.setConditions(goodsCategory);
		Page<GoodsCategory> pageGoodsCategory = goodsCategoryService.findGoodsCategoryListByPage(p);
		model.addObject("page", pageGoodsCategory);
		return model;
	}
	
	/**
	 *
	 * 检查名称是否被使用.
	 *
	 * @param id
	 * 			商品类别ID（编辑时传递，添加时不传递）
	 * @param categoryName
	 * 			商品类别名称
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月05日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/checkIsUsed", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO checkIsUsed(@Param(value="id")String id, @Param(value="categoryName")String categoryName, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try {
			if(StringUtils.isNotBlank(categoryName)){
				GoodsCategory goodsCategory = new GoodsCategory();
				goodsCategory.setCategoryName(categoryName);
				if(StringUtils.isNotEmpty(id)){
					goodsCategory.setId(Long.parseLong(id));
				}
				List<GoodsCategory> goodsCategoryList = goodsCategoryService.findGoodsCategoryListByGoodsCategory(goodsCategory);
				if(goodsCategoryList.size() > 0){
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
	 * 检查商品类别是否可以删除.
	 *
	 * @param id
	 * 			商品类别ID
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月06日    caowei    新建
	 * </pre>
	 */
	/*
	@RequestMapping(value="/checkEnableDelete", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO checkEnableDelete(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try {
			if(StringUtils.isNotBlank(id)){
				Goods goods = new Goods();
				goods.setCategoryId(Long.parseLong(id));
				List<Ad> adList = goodsService.findAdListByAd(ad);
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
	*/
	
	/**
	 * 
	 * 跳转到编辑商品类别页.
	 *
	 * @param id
	 * 			商品类别ID
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月05日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/toEditGoodsCategoryView", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toEditGoodsCategoryView(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/goodsCategory/editGoodsCategory");
		if(StringUtils.isNotEmpty(id)){
			GoodsCategory goodsCategory = goodsCategoryService.findGoodsCategoryById(Long.parseLong(id));
			if(goodsCategory != null){
				model.addObject("goodsCategory", goodsCategory);
				String userName = getUserInfo(request, "userName");
				model.addObject("operateUser", userName);
			}else{
				//商品类别不存在
				model.addObject("moduleName", "商品类别列表");
				model.addObject("message", "对不起，商品类别不存在！");
				model.setViewName("/common/error");
			}
		}
		return model;
	}
	
	/**
	 * 
	 * 编辑商品类别.
	 *
	 * @param content
	 * 			商品类别信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月05日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/editGoodsCategory", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO editGoodsCategory(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			GoodsCategory goodsCategory = JSON.parseObject(content, GoodsCategory.class);
			if(goodsCategory != null){
				goodsCategory.setUpdateUser(goodsCategory.getOperateUser());
				goodsCategory.setUpdateTime(new Date());
				int record = goodsCategoryService.editGoodsCategory(goodsCategory);
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
	 * 检查商品类别是否可以删除.
	 *
	 * @param id
	 * 			商品类别ID
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/checkEnableDelete", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO checkEnableDelete(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try {
			if(StringUtils.isNotBlank(id)){
				Goods goods = new Goods();
				goods.setCategoryId(Long.parseLong(id));
				List<Goods> goodsList = goodsService.findGoodsListByGoods(goods);
				if(goodsList.size() > 0){
					//商品分类下已存在商品，商品分类不为空，商品分类将不能被删除
					responses.setReturnCode(ReturnCode.ERROR_CATEGORY_NOT_EMPTY);
				} else {
					//商品分类下不存在商品，商品分类为空，商品分类可以被删除
					responses.setReturnCode(ReturnCode.ERROR_CATEGORY_EMPTY);
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
	 * 删除商品类别.
	 *
	 * @param id
	 * 			商品类别ID
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月05日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/deleteGoodsCategory", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO deleteGoodsCategory(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(id)){
				Long goodsCategoryId = Long.parseLong(id);
				int record = goodsCategoryService.deleteGoodsCategoryById(goodsCategoryId);
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
}
