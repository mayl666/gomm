package org.gome.pricelogsync.domain;

import java.util.Date;

/**
 * 价格日志
 * @author caowei-ds1
 *
 */
public class PriceLog {

	/** 文档ID(唯一) */
	private String uuid;
	
	/** 索引   */
	private String index;
	
	/** ID(唯一) */
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
	
	/** 是否批处理    TRUE:1    FALSE:0 */
	private byte isBatch;
	
	/** 处理动作       SEND:0    RECIVE:1    */
	private byte action;
	
	/** 处理动作字符串 */
	private String actionStr;
	
	/** 处理结果    TRUE:1    FALSE:0   */
	private byte result;
	
	/** 处理结果字符串 */
	private String resultStr;
	
	/** 处理状态    START:1    CONTINUE:2    END:3 */
	private byte status;
	
	/** 处理状态字符串 */
	private String statusStr;
	
	/** UTC时间 */
	private String time;
	
	/** 时间戳 */
	private Date timestamp;
	
	/** 时间字符串 */
	private String timeStr;
	
	/** 起始时间字符串 */
	private String startTime;
	
	/** 结束时间 字符串*/
	private String endTime;
	
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

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

	public byte getIsBatch() {
		return isBatch;
	}

	public void setIsBatch(byte isBatch) {
		this.isBatch = isBatch;
	}

	public byte getAction() {
		return action;
	}

	public void setAction(byte action) {
		this.action = action;
	}

	public String getActionStr() {
		return actionStr;
	}

	public void setActionStr(String actionStr) {
		this.actionStr = actionStr;
	}

	public byte getResult() {
		return result;
	}

	public void setResult(byte result) {
		this.result = result;
	}

	public String getResultStr() {
		return resultStr;
	}

	public void setResultStr(String resultStr) {
		this.resultStr = resultStr;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
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

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
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

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
