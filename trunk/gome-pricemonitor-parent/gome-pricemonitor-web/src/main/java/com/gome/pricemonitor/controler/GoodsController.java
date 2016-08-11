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
 * 商品控制器.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/goods")
public class GoodsController extends BaseController {
	
	@Resource
	private GoodsService goodsService;

	@Resource
	private GoodsCategoryService goodsCategoryService;
	
	/**
	 * 跳转到新增商品页.
	 *
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/toAddGoodsView", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView toAddGoodsView(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/goods/addGoods");
		
		List<GoodsCategory> goodsCategoryList = goodsCategoryService.findGoodsCategoryListByGoodsCategory(new GoodsCategory());
		model.addObject("goodsCategoryList", goodsCategoryList);
		String userName = getUserInfo(request, "userName");
		model.addObject("operateUser", userName);
		return model;
	}
	
	/**
	 * 
	 * 新增商品.
	 *
	 * @param content
	 * 				商品信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/addGoods", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO addGoods(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(content)){
				Goods goods = JSON.parseObject(content, Goods.class);
				if(goods != null){
					goods.setCreateTime(new Date());
					goods.setCreateUser(goods.getOperateUser());
					goods.setUpdateTime(new Date());
					goods.setUpdateUser(goods.getOperateUser());
					int record = goodsService.addGoods(goods);
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
	 * 跳转到商品列表页.
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
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/toGoodsListView", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toGoodsListView(@Param(value="content")String content, @Param(value="page")String page, @Param(value="size")String size, HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws UnsupportedEncodingException{
		if(StringUtils.isEmpty(content)){
			model.setViewName("/goods/goodsList");
		} else {
			model.setViewName("/goods/goodsTable");
		}
		
		int pageNo = 1;
		if(StringUtils.isNotEmpty(page)){
			pageNo = Integer.parseInt(page);
		}
		
		int pageSize = Constant.PAGE_SIZE;
		if(StringUtils.isNotEmpty(size)){
			pageSize = Integer.parseInt(size);
		}
		Page<Goods> p = new Page<Goods>(pageNo, pageSize);
		
		Goods goods = null;
		if(StringUtils.isNotEmpty(content)){
			goods = JSON.parseObject(content, Goods.class);
		} else {
			goods = new Goods();
		}
		p.setConditions(goods);
		Page<Goods> pageGoods = goodsService.findGoodsListByPage(p);
		model.addObject("page", pageGoods);
		List<GoodsCategory> goodsCategoryList = goodsCategoryService.findGoodsCategoryListByGoodsCategory(new GoodsCategory());
		model.addObject("goodsCategoryList", goodsCategoryList);
		return model;
	}
	
	/**
	 *
	 * 检查名称是否被使用.
	 *
	 * @param id
	 * 			商品ID（编辑时传递，添加时不传递）
	 * @param goodsName
	 * 			商品名称
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/checkIsUsed", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO checkIsUsed(@Param(value="id")String id, @Param(value="goodsName")String goodsName, @Param(value="categoryId")String categoryId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try {
			if(StringUtils.isNotBlank(goodsName) && StringUtils.isNotBlank(categoryId)){
				Goods goods = new Goods();
				goods.setGoodsName(goodsName);
				goods.setCategoryId(Long.parseLong(categoryId));
				if(StringUtils.isNotEmpty(id)){
					goods.setId(Long.parseLong(id));
				}
				List<Goods> goodsList = goodsService.findGoodsListByGoods(goods);
				if(goodsList.size() > 0){
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
	 * 跳转到编辑商品页.
	 *
	 * @param id
	 * 			商品ID
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/toEditGoodsView", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toEditGoodsView(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/goods/editGoods");
		if(StringUtils.isNotEmpty(id)){
			Goods goods = goodsService.findGoodsById(Long.parseLong(id));
			if(goods != null){
				model.addObject("goods", goods);
				String userName = getUserInfo(request, "userName");
				model.addObject("operateUser", userName);
				List<GoodsCategory> goodsCategoryList = goodsCategoryService.findGoodsCategoryListByGoodsCategory(new GoodsCategory());
				model.addObject("goodsCategoryList", goodsCategoryList);
			}else{
				//商品不存在
				model.addObject("moduleName", "商品列表");
				model.addObject("message", "对不起，商品不存在！");
				model.setViewName("/common/error");
			}
		}
		return model;
	}
	
	/**
	 * 
	 * 编辑商品.
	 *
	 * @param content
	 * 			商品信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/editGoods", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO editGoods(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			Goods goods = JSON.parseObject(content, Goods.class);
			if(goods != null){
				goods.setUpdateUser(goods.getOperateUser());
				goods.setUpdateTime(new Date());
				int record = goodsService.editGoods(goods);
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
	 * 删除商品.
	 *
	 * @param id
	 * 			商品ID
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/deleteGoods", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO deleteGoods(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(id)){
				Long goodsId = Long.parseLong(id);
				int record = goodsService.deleteGoodsById(goodsId);
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
	 * 商品导出.
	 *
	 * @param conditions
	 * 			搜索条件
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月12日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/exportExcel", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView exportExcel(@Param(value="conditions")String conditions, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		Goods goods = null;
		if(StringUtils.isNotEmpty(conditions)){
			goods = JSON.parseObject(conditions, Goods.class);
		}else{
			goods = new Goods();
		}
		
		Page<Goods> p = new Page<Goods>(1, 999999);
		p.setConditions(goods);
		Page<Goods> pageGoods = goodsService.findGoodsListByPage(p);
		
		List<Goods> goodsList = pageGoods.getData();
		goodsService.exportExcel(goodsList, response);
		return null;
	}
	
}
