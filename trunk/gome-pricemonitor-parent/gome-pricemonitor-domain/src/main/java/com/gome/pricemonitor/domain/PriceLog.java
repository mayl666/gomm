package com.gome.pricemonitor.domain;

/**
 * 价格日志
 * @author caowei-ds1
 *
 */
public class PriceLog {
	/** 日志ID(唯一) */
	private String id;
	
	/** 商品编码 */
	private String skuNo;
	
	/** 价格类型 */
	private String type;
	
	/** 价格类型字符串 */
	private String typeStr;
	
	/** 区域编码 */
	private String areaCode;
	
	/** 当前节点 */
	private String node;
	
	/** 处理动作 */
	private String action;
	
	/** 处理动作字符串 */
	private String actionStr;
	
	/** 处理结果 */
	private String result;
	
	/** 处理结果字符串 */
	private String resultStr;
	
	/** 处理状态 */
	private String status;
	
	/** 处理状态字符串 */
	private String statusStr;
	
	/** 时间 */
	private String time;
	
	/** 时间字符串 */
	private String timeStr;
	
	/** 起始时间字符串 */
	private String startTime;
	
	/** 起始时间戳 */
	private long startTimeStamp;
	
	/** 结束时间 字符串*/
	private String endTime;
	
	/** 结束时间戳 */
	private long endTimeStamp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeStr() {
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getActionStr() {
		return actionStr;
	}

	public void setActionStr(String actionStr) {
		this.actionStr = actionStr;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResultStr() {
		return resultStr;
	}

	public void setResultStr(String resultStr) {
		this.resultStr = resultStr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTimeStr() {
		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public long getStartTimeStamp() {
		return startTimeStamp;
	}

	public void setStartTimeStamp(long startTimeStamp) {
		this.startTimeStamp = startTimeStamp;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public long getEndTimeStamp() {
		return endTimeStamp;
	}

	public void setEndTimeStamp(long endTimeStamp) {
		this.endTimeStamp = endTimeStamp;
	}

	
}
