package com.gome.pricemonitor.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.gome.pricemonitor.common.Page;
import com.gome.pricemonitor.common.util.ExcelUtil;
import com.gome.pricemonitor.dao.GoodsMapper;
import com.gome.pricemonitor.domain.Goods;
import com.gome.pricemonitor.service.GoodsService;

/**
 * 
 * 商品service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
@Service
public class GoodsServiceImpl implements GoodsService {

	@Resource
	private GoodsMapper goodsMapper;
	
	public int addGoods(Goods goods) {
		return goodsMapper.insertGoods(goods);
	}

	public Page<Goods> findGoodsListByPage(Page<Goods> page) {
		List<Goods> goodsList = goodsMapper.selectGoodsListByPage(page);
		int totalResult = goodsMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<Goods>(page.getPageNo(), page.getPageSize(), totalResult, goodsList, page.getConditions());
	}

	public int editGoods(Goods goods) {
		return goodsMapper.updateGoods(goods);
	}

	public int deleteGoodsById(Long id) {
		return goodsMapper.deleteGoodsById(id);
	}

	public Goods findGoodsById(Long id) {
		return goodsMapper.selectGoodsById(id);
	}

	public List<Goods> findGoodsListByGoods(Goods goods) {
		return goodsMapper.selectGoodsListByGoods(goods);
	}

	@Override
	public void exportExcel(List<Goods> goodsList, HttpServletResponse response) {
		if(goodsList != null && goodsList.size() > 0){
			int num=0;
			for(Goods goods : goodsList){
				num++;
				goods.setNum(num);
				if(goods.getStatus() == 1){
					goods.setStatusStr("正常");
				}else{
					goods.setStatusStr("失效");
				}
			}
		}
		
		String[] title ={"序号","商品类别","商品名称","参考价格","状态","创建时间"};
        String[] header = {"num","categoryName","goodsName","price","statusStr","createTimeStr"};
		String fileName = "商品列表.xls";
		JSONArray array = (JSONArray) JSONArray.toJSON(goodsList);
		ExcelUtil.exportExcel(response, array, title, header, fileName);
		
	}

}
