package com.gome.pricemonitor.domain;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * 商品实体类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月6日    caowei    新建
 * </pre>
 */
public class Goods {
	
	DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	
	//编号（导出时用到）
	private Integer num;
	
	//商品ID
	private Long id;
	
	//商品编号
	private String goodsCode;
	
	//商品名称
	private String goodsName;
	
	//参考价格
	private Double price;
	
	//价格长度>=8时，会自动转换成科学计数法，例如：9.99999999E8，因此需要进行还原方便显示
	private String priceStr;
	
	//图片地址
	private String picPath;
	
	//商品描述
	private String description;
	
	//跳转地址
	private String links;
	
	//状态(0:关闭   1:开启)
	private Integer status;
	
	private String statusStr;
	
	//商品分类ID
	private Long categoryId;
	
	//创建时间
	private Date createTime;
	
	//创建人
	private String createUser;
	
	//修改时间
	private Date updateTime;
	
	//修改人
	private String updateUser;
	
	//分类名称
	private String categoryName;
	
	//操作账号
	private String operateUser;
	
	private String createTimeStr;
	
	private String updateTimeStr;

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		if(price != null){
			BigDecimal bd = new BigDecimal(price.toString());
			this.priceStr = bd.toPlainString();
		}
		this.price = price;
	}

	public String getPriceStr() {
		return priceStr;
	}

	public void setPriceStr(String priceStr) {
		this.priceStr = priceStr;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLinks() {
		return links;
	}

	public void setLinks(String links) {
		this.links = links;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
		if(createTime != null){
			this.createTimeStr = df.format(createTime);
		}
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
		if(updateTime != null){
			this.updateTimeStr = df.format(updateTime);
		}
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}
	
}
