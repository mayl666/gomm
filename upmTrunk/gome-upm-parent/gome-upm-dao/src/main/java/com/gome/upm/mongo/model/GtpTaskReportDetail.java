package com.gome.upm.mongo.model;

import java.io.Serializable;

/**
 * 自动化购物流程详情
 * @author zhangzhixiang-ds
 *
 */
public class GtpTaskReportDetail implements Serializable {

	private static final long serialVersionUID = 410414343979561562L;
	
	public final static int NORMAL_STATE = 0;  //通过
	public final static int FAIL_STATE = 1;    //失败

	private Long startTime;

	private boolean rerun;

	private String caseDesc;

	private int duration;

	private String testResult;

	private String owner;

	//private List<GtpTaskReportDetail> children;

	private int rerunCount;

	private Long endTime;

	private String testCaseName;

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public boolean isRerun() {
		return rerun;
	}

	public void setRerun(boolean rerun) {
		this.rerun = rerun;
	}

	public String getCaseDesc() {
		return caseDesc;
	}

	public void setCaseDesc(String caseDesc) {
		this.caseDesc = caseDesc;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getTestResult() {
		return testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

//	public List<GtpTaskReportDetail> getChildren() {
//		return children;
//	}
//
//	public void setChildren(List<GtpTaskReportDetail> children) {
//		this.children = children;
//	}

	public int getRerunCount() {
		return rerunCount;
	}

	public void setRerunCount(int rerunCount) {
		this.rerunCount = rerunCount;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getTestCaseName() {
		return testCaseName;
	}

	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}

}
