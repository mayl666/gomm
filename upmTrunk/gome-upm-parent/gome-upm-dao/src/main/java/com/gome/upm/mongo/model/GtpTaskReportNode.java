package com.gome.upm.mongo.model;

import java.io.Serializable;

public class GtpTaskReportNode implements Serializable {

	private static final long serialVersionUID = -4671408994533261626L;
	
	/**
	 * 節點名稱
	 */
	private String name;
	
	/**
	 * 执行耗时
	 */
	private int usedTime;
	
	/**
	 * 测试结果
	 */
	private String result;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(int usedTime) {
		this.usedTime = usedTime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "GtpTaskReportNode [name=" + name + ", usedTime=" + usedTime + ", result=" + result + "]";
	}
	
	

}
