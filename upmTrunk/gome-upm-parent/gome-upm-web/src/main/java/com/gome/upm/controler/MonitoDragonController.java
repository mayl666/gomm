package com.gome.upm.controler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gome.upm.common.util.DataSourceGetUtils;
import com.gome.upm.common.util.JsonUtils;
import com.gome.upm.dao.AlarmRangeMapper;
import com.gome.upm.dao.MoMonitorMapper;
import com.gome.upm.domain.AlarmRange;
import com.gome.upm.domain.ColumnVo;
import com.gome.upm.domain.OrderMap;
import com.gome.upm.service.util.DBContextHolder;

@Controller
@RequestMapping(value="/monitoDragon")
public class MonitoDragonController extends AbsBaseController{
	@Resource
	private MoMonitorMapper moMonitorMapper;
	@Resource
	private AlarmRangeMapper alarmRangeMapper;
	@RequestMapping("/get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		/*int g3pp_realy_na = moMonitorMapper.g3pp_realy_na_count();

		int g3pp_realy_dh = moMonitorMapper.g3pp_realy_dh_count();

		int g3pp_order_pr = moMonitorMapper.g3pp_order_pr_count();

		int g3pp_order_dh = moMonitorMapper.g3pp_order_dh_count();

		int g3pp_order_cco = moMonitorMapper.g3pp_order_cco_count();

		int smi_order_pr = moMonitorMapper.smi_order_pr_count();

		int order_od = moMonitorMapper.order_od_count();*/
		
		model.setViewName("/business/monitoDragon");
		model.addObject("leftMenu", "business.dragonMenu");
		return model;
	}
	
	@RequestMapping(value = "getOneDateList", method = RequestMethod.POST)
	public void getOmsDataList(HttpServletRequest request, HttpServletResponse response) throws ParseException{
		
		int g3pp_order_pr =0;
		int g3pp_order_dh = 0;
		int g3pp_order_cco =0;
		int smi_order_pr =0;
		try{
			DBContextHolder.setDataSource(DataSourceGetUtils.getDrgDataSource());
			g3pp_order_pr = moMonitorMapper.g3pp_order_pr_count();

			g3pp_order_dh = moMonitorMapper.g3pp_order_dh_count();

			g3pp_order_cco = moMonitorMapper.g3pp_order_cco_count();

			smi_order_pr = moMonitorMapper.smi_order_pr_count();
		}catch(Exception e){
			e.printStackTrace();
		}
		List<OrderMap> tempList = new ArrayList<OrderMap>();
		tempList.add(new OrderMap("G3PP订单状态大量停在PR",Long.valueOf(g3pp_order_pr)));
		tempList.add(new OrderMap("G3PP订单状态大量停在DH",Long.valueOf(g3pp_order_dh)));
		tempList.add(new OrderMap("G3PP订单状态大量停在CCO",Long.valueOf(g3pp_order_cco)));
		tempList.add(new OrderMap("SMI状态大量停在PR",Long.valueOf(smi_order_pr)));
		List<ColumnVo> reList = setColumnVo(tempList);
		String data = JsonUtils.Object2Json(reList);
		renderData(response, data);
	}
	
	@RequestMapping(value = "getOneWeekList", method = RequestMethod.POST)
	public void getOneWeekList(HttpServletRequest request, HttpServletResponse response) throws ParseException{
		
		int order_od =0;
		try{
			DBContextHolder.setDataSource(DataSourceGetUtils.getDrgDataSource());
			order_od = moMonitorMapper.order_od_count();
		}catch(Exception e){
			
		}
		List<OrderMap> tempList = new ArrayList<OrderMap>();
		tempList.add(new OrderMap("订单状态大量停留在OD",Long.valueOf(order_od)));
		List<ColumnVo> reList = setColumnVo(tempList);
		String data = JsonUtils.Object2Json(reList);
		renderData(response, data);
	}
	
	/**
	 * 填充页面柱形图
	 * @param tempList
	 * @return
	 */
	public List<ColumnVo> setColumnVo(List<OrderMap> tempList){
		DBContextHolder.setDataSource("dataSourceOne");
		List<String> list = new ArrayList<String>();
		for(OrderMap temp:tempList){
			list.add(temp.getType());
		}
		List<AlarmRange> rangeList =alarmRangeMapper.selectAlarmRangeByType(list);
		List<ColumnVo> reList=new ArrayList<ColumnVo>();
		Map<String,Long> map = new HashMap<String,Long>();
		for(AlarmRange range:rangeList){
			map.put(range.getType(), Long.valueOf(range.getValue()));
		}
		ColumnVo vo = null;
		for(OrderMap order:tempList){

			if(map.containsKey(order.getType())){
				if(order.getCount() > Long.valueOf(map.get(order.getType()))){
					vo = new ColumnVo("#FF5750",order.getCount());
				}else{
					vo = new ColumnVo("#672795",order.getCount());
				}
			}else{
				vo = new ColumnVo("#672795",order.getCount());
			}
			reList.add(vo);
		}
		
		return reList;
	}
}
