package com.gome.pricemonitor.domain;

import java.util.Date;

public class ManagerRole {

	public static final int ROLE_DELETE = -1;// 删除状态
	public static final int ROLE_NORMAL = 0;// 正常状态
	public static final int ROLE_LOCK = 1;// 锁定状态

	private int num;
	private Long roleId;

	private String roleName;

	private Date createTime;

	private Date updateTime;

	private Integer state;

	private String description;

	private String operatorName;

	private String createTimeView;

	private String stateVal;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getStateVal() {
		return stateVal;
	}

	public void setStateVal(String stateVal) {
		this.stateVal = stateVal;
	}

	public String getCreateTimeView() {
		return createTimeView;
	}

	public void setCreateTimeView(String createTimeView) {
		this.createTimeView = createTimeView;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName == null ? null : roleName.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}