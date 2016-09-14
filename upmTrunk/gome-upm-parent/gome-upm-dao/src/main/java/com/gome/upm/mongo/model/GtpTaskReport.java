package com.gome.upm.mongo.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 测试购物流程实体
 * 
 * @author zhangzhixiang-ds
 *
 */
@Document(collection = "TaskReport")
public class GtpTaskReport implements Serializable {

	private static final long serialVersionUID = 1053309015442156055L;

	@Id
	private String id;

	@Indexed
	private int taskId;

	private Long splitTime;

	private Long generateTime;

	private int taskType;

	private Long endTime;

	private int date;

	private int pass;

	private Long startTime;

	private int duration;

	private int totalCases;

	private List<GtpTaskReportDetail> details;

	private int fail;

	private String resultFilePath;

	private int aborted;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public Long getSplitTime() {
		return splitTime;
	}

	public void setSplitTime(Long splitTime) {
		this.splitTime = splitTime;
	}

	public Long getGenerateTime() {
		return generateTime;
	}

	public void setGenerateTime(Long generateTime) {
		this.generateTime = generateTime;
	}

	public int getTaskType() {
		return taskType;
	}

	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public int getPass() {
		return pass;
	}

	public void setPass(int pass) {
		this.pass = pass;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getTotalCases() {
		return totalCases;
	}

	public void setTotalCases(int totalCases) {
		this.totalCases = totalCases;
	}

	public int getFail() {
		return fail;
	}

	public void setFail(int fail) {
		this.fail = fail;
	}

	public String getResultFilePath() {
		return resultFilePath;
	}

	public void setResultFilePath(String resultFilePath) {
		this.resultFilePath = resultFilePath;
	}

	public int getAborted() {
		return aborted;
	}

	public void setAborted(int aborted) {
		this.aborted = aborted;
	}

	public List<GtpTaskReportDetail> getDetails() {
		return details;
	}

	public void setDetails(List<GtpTaskReportDetail> details) {
		this.details = details;
	}

}
