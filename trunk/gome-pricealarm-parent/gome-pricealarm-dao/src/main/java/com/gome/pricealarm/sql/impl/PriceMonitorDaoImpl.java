package com.gome.pricealarm.sql.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gome.presto.PrestoJdbcCli;
import com.gome.presto.entity.PriceLogVO;
import com.gome.pricealarm.common.Page;
import com.gome.pricealarm.domain.PriceLog;
import com.gome.pricealarm.sql.PriceMonitorDao;

/**
 * 价格监控接口实现类
 * @author caowei-ds1
 */
@Repository
public class PriceMonitorDaoImpl implements PriceMonitorDao{
	
	private static final Logger logger = LoggerFactory.getLogger(PriceMonitorDaoImpl.class);

	public List<PriceLog> selectPriceLogListFromSqlByConditionsPage(Page<PriceLog> page) {
		PriceLog conditions = page.getConditions();
		String sql = "select * from price_log_monitor where dt=" + "'" + conditions.getTimeStr() + "'";
		if(StringUtils.isNotEmpty(conditions.getId())){
			sql += " and id=" + "'" + conditions.getId() + "'";
		}
		if(StringUtils.isNotEmpty(conditions.getSkuNo())){    //商品编码支持模糊查询
			sql += " and skuno like " + "'%" + conditions.getSkuNo() + "%'";
		}
		if(StringUtils.isNotEmpty(conditions.getType())){
			sql += " and type=" + "'" + conditions.getType() + "'";
		}
		if(StringUtils.isNotEmpty(conditions.getAreaCode())){   //区域编码支持模糊查询
			sql += " and areacode like " + "'%" + conditions.getAreaCode() + "%'";
		}
		if(StringUtils.isNotEmpty(conditions.getNode())){
			sql += " and node=" + "'" + conditions.getNode() + "'";
		}
		if(StringUtils.isNotEmpty(conditions.getAction())){
			sql += " and action=" + conditions.getAction();
		}
		if(StringUtils.isNotEmpty(conditions.getResult())){
			sql += " and result=" + conditions.getResult();
		}
		if(StringUtils.isNotEmpty(conditions.getStatus())){
			sql += " and status=" + conditions.getStatus();
		}
		if(conditions.getStartTimeStamp() != 0 && conditions.getEndTimeStamp() != 0){
			sql += " and time between " + conditions.getStartTimeStamp() + " and " + conditions.getEndTimeStamp();
		}
		int resultNum = page.getPageNo() * page.getPageSize();
		sql += " order by time desc,result asc,status asc limit " + resultNum;
		List<PriceLogVO> result = PrestoJdbcCli.query(sql);
		List<PriceLog> priceList = new ArrayList<PriceLog>();
		List<PriceLogVO> subResult;
		if (result == null || result.isEmpty()) {
            return priceList;
        }
		if(page.getPageNo() > 1) {
			//分页
	        int startIndex = (page.getPageNo()-1)*page.getPageSize();
	        int endIndex = startIndex + page.getPageSize();
	        if (startIndex > endIndex || startIndex > result.size()) {
	            return priceList;
	        }
	        if (endIndex > result.size()) {
	            endIndex = result.size();
	        }
			subResult = result.subList(startIndex, endIndex);
		} else {
			subResult = result;
		}
		for (PriceLogVO vo : subResult) {
			PriceLog priceLog = new PriceLog();
			priceLog.setId(vo.getId());
			priceLog.setSkuNo(vo.getSkuno());
			priceLog.setType(vo.getType());
			priceLog.setAreaCode(vo.getAreacode());
			priceLog.setNode(vo.getNode());
			priceLog.setTimeStr(vo.getTime());
			priceLog.setAction(String.valueOf(vo.getAction()));
			priceLog.setResult(String.valueOf(vo.getResult()));
			priceLog.setStatus(String.valueOf(vo.getStatus()));
			priceList.add(priceLog);
		}
		return priceList;
	}

	public int selectTotalResultFromSqlByConditions(PriceLog conditions) {
		String sql = "select count(*) from price_log_monitor where dt=" + "'" + conditions.getTimeStr() + "'";
		if(StringUtils.isNotEmpty(conditions.getId())){
			sql += " and id=" + "'" + conditions.getId() + "'";
		}
		if(StringUtils.isNotEmpty(conditions.getSkuNo())){    //商品编码支持模糊查询
			sql += " and skuno like " + "'%" + conditions.getSkuNo() + "%'";
		}
		if(StringUtils.isNotEmpty(conditions.getType())){
			sql += " and type=" + "'" + conditions.getType() + "'";
		}
		if(StringUtils.isNotEmpty(conditions.getAreaCode())){   //区域编码支持模糊查询
			sql += " and areacode like " + "'%" + conditions.getAreaCode() + "%'";
		}
		if(StringUtils.isNotEmpty(conditions.getNode())){
			sql += " and node=" + "'" + conditions.getNode() + "'";
		}
		if(StringUtils.isNotEmpty(conditions.getAction())){
			sql += " and action=" + conditions.getAction();
		}
		if(StringUtils.isNotEmpty(conditions.getResult())){
			sql += " and result=" + conditions.getResult();
		}
		if(StringUtils.isNotEmpty(conditions.getStatus())){
			sql += " and status=" + conditions.getStatus();
		}
		if(conditions.getStartTimeStamp() != 0 && conditions.getEndTimeStamp() != 0){
			sql += " and time between " + conditions.getStartTimeStamp() + " and " + conditions.getEndTimeStamp();
		}
		long count = PrestoJdbcCli.count(sql);
		return (int) count;
	}

	public List<PriceLog> selectPriceLogListFromSqlByConditions(PriceLog conditions) {
		String sql = "select * from price_log_monitor where dt=" + "'" + conditions.getTimeStr() + "'";
		if(StringUtils.isNotEmpty(conditions.getId())){
			sql += " and id=" + "'" + conditions.getId() + "'";
		}
		if(StringUtils.isNotEmpty(conditions.getSkuNo())){    //商品编码支持模糊查询
			sql += " and skuno like " + "'%" + conditions.getSkuNo() + "%'";
		}
		if(StringUtils.isNotEmpty(conditions.getType())){
			sql += " and type=" + "'" + conditions.getType() + "'";
		}
		if(StringUtils.isNotEmpty(conditions.getAreaCode())){   //区域编码支持模糊查询
			sql += " and areacode like " + "'%" + conditions.getAreaCode() + "%'";
		}
		if(StringUtils.isNotEmpty(conditions.getNode())){
			sql += " and node=" + "'" + conditions.getNode() + "'";
		}
		if(StringUtils.isNotEmpty(conditions.getAction())){
			sql += " and action=" + conditions.getAction();
		}
		if(StringUtils.isNotEmpty(conditions.getResult())){
			sql += " and result=" + conditions.getResult();
		}
		if(StringUtils.isNotEmpty(conditions.getStatus())){
			sql += " and status=" + conditions.getStatus();
		}
		if(conditions.getStartTimeStamp() != 0 && conditions.getEndTimeStamp() != 0){
			sql += " and time between " + conditions.getStartTimeStamp() + " and " + conditions.getEndTimeStamp();
		}
		List<PriceLogVO> result = PrestoJdbcCli.query(sql);
		List<PriceLog> priceList = new ArrayList<PriceLog>();
		if (result == null || result.isEmpty()) {
            return priceList;
        }
		for (PriceLogVO vo : result) {
			PriceLog priceLog = new PriceLog();
			priceLog.setId(vo.getId());
			priceLog.setSkuNo(vo.getSkuno());
			priceLog.setType(vo.getType());
			priceLog.setAreaCode(vo.getAreacode());
			priceLog.setNode(vo.getNode());
			priceLog.setTime(vo.getTime());
			if(vo.getIsbatch() == 0){
				priceLog.setIsBatch("FALSE");
			} else {
				priceLog.setIsBatch("TRUE");
			}
			if(vo.getAction() == 0){
				priceLog.setAction("SEND");
			} else {
				priceLog.setAction("RECEIVE");
			}
			if(vo.getResult() == 0){
				priceLog.setResult("FALSE");
			} else {
				priceLog.setResult("TRUE");
			}
			if(vo.getStatus() == 1){
				priceLog.setStatus("START");
			} else if(vo.getStatus() == 2) {
				priceLog.setStatus("CONTINUE");
			} else {
				priceLog.setStatus("END");
			}
			priceList.add(priceLog);
		}
		return priceList;
	}
	
}
