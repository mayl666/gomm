package com.gome.upm.mongo.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * 测试购物流程前段展示实体
 * 
 * @author zhangzhixiang-ds
 *
 */

public class GtpTaskReportViewIndex implements Serializable {

	private static final long serialVersionUID = 1053309015442156055L;
	
	/**
	 * 主下单流程失败次数
	 */
	private int totalFailCount1;

	/**
	 * 搜索下单流程失败次数
	 */
	private int totalFailCount2;
	
	/**
	 * 测试下单流程总数(主流程==搜索流程)
	 */
	private int totalCount;
	
	/**
	 * 主流程各节点失败次数
	 */
	private int[] nodeFailCount1;
	
	/**
	 * 搜索流程各节点失败次数
	 */
	private int[] nodeFailCount2;
	
	/**
	 * 创建时间
	 */
	private String[] createTime;
	
	/**
	 * 主流程耗时
	 */
	private int[] proTime1;
	
	/**
	 * 搜索流程耗时
	 */
	private int[] proTime2;

	public int getTotalFailCount1() {
		return totalFailCount1;
	}

	public void setTotalFailCount1(int totalFailCount1) {
		this.totalFailCount1 = totalFailCount1;
	}

	public int getTotalFailCount2() {
		return totalFailCount2;
	}

	public void setTotalFailCount2(int totalFailCount2) {
		this.totalFailCount2 = totalFailCount2;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int[] getNodeFailCount1() {
		return nodeFailCount1;
	}

	public void setNodeFailCount1(int[] nodeFailCount1) {
		this.nodeFailCount1 = nodeFailCount1;
	}

	public int[] getNodeFailCount2() {
		return nodeFailCount2;
	}

	public void setNodeFailCount2(int[] nodeFailCount2) {
		this.nodeFailCount2 = nodeFailCount2;
	}

	public String[] getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String[] createTime) {
		this.createTime = createTime;
	}

	public int[] getProTime1() {
		return proTime1;
	}

	public void setProTime1(int[] proTime1) {
		this.proTime1 = proTime1;
	}

	public int[] getProTime2() {
		return proTime2;
	}

	public void setProTime2(int[] proTime2) {
		this.proTime2 = proTime2;
	}
	
	

}
