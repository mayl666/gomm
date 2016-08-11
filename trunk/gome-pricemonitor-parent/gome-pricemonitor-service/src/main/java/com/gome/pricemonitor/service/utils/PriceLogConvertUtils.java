package com.gome.pricemonitor.service.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.gome.pricemonitor.domain.PriceLog;

public class PriceLogConvertUtils {

	/**
	 * 属性转换
	 * @param priceLogList
	 * @return
	 */
	public static List<PriceLog> propertyConvert(List<PriceLog> priceLogList){
		if(priceLogList != null && priceLogList.size() > 0){
			for (PriceLog priceLog : priceLogList) {
				if(StringUtils.isNotEmpty(priceLog.getType())){
					Map<String,String> typeMap = new HashMap<String,String>();
					typeMap.put("01", "联营售价限价保护价");
					typeMap.put("02", "联营直降价");
					typeMap.put("03", "联营区域售价");
					typeMap.put("04", "自营售价限价保护价");
					typeMap.put("05", "自营直降价");
					typeMap.put("06", "自营区域售价");
					typeMap.put("07", "自营区域直降价");
					typeMap.put("08", "团抢价");
					typeMap.put("09", "联营掌上专享价");
					typeMap.put("10", "自营掌上专享价");
					typeMap.put("11", "会员价");
					typeMap.put("12", "唯品惠");
					typeMap.put("13", "联营预售价");
					typeMap.put("14", "自营预售价");
					typeMap.put("15", "预约价");
					typeMap.put("16", "残次品");
					typeMap.put("17", "海外购");
					priceLog.setTypeStr(typeMap.get(priceLog.getType()));
				}
				if(StringUtils.isNotEmpty(priceLog.getAction())){
					if("0".equals(priceLog.getAction())){
						priceLog.setActionStr("发送");
					} else if("1".equals(priceLog.getAction())){
						priceLog.setActionStr("接收");
					}
				}
				if(StringUtils.isNotEmpty(priceLog.getResult())){
					if("1".equals(priceLog.getResult())){
						priceLog.setResultStr("成功");
					} else if("0".equals(priceLog.getResult())){
						priceLog.setResultStr("失败");
					}
				}
				if(StringUtils.isNotEmpty(priceLog.getStatus())){
					if("1".equals(priceLog.getStatus())){
						priceLog.setStatusStr("开始");
					} else if("2".equals(priceLog.getStatus())){
						priceLog.setStatusStr("过程中");
					}  else if("3".equals(priceLog.getStatus())){
						priceLog.setStatusStr("结束");
					} 
				}
			}
		}
		return priceLogList;
	}
	
}
