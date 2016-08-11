package com.gome.pricemonitor.domain;

import java.util.Date;

public class ManagerUser {

	public static final int USER_DELETE = -1;// 删除状态
	public static final int USER_NORMAL = 0;// 正常状态
	public static final int USER_LOCK = 1;// 锁定状态

	private int num;
	private Long userId;

	private String userName;

	private String passwd;

	private String realName;

	private String contactWay;

	private Integer state;

	private String headPath;

	private Date createTime;

	private Date updateTime;

	private String operatorName;

	private Long roleId;

	private String roleName;

	private String createTimeView;

	private String stateVal;

	public String getStateVal() {
		return stateVal;
	}

	public void setStateVal(String stateVal) {
		this.stateVal = stateVal;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getCreateTimeView() {
		return createTimeView;
	}

	public void setCreateTimeView(String createTimeView) {
		this.createTimeView = createTimeView;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd == null ? null : passwd.trim();
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName == null ? null : realName.trim();
	}

	public String getContactWay() {
		return contactWay;
	}

	public void setContactWay(String contactWay) {
		this.contactWay = contactWay == null ? null : contactWay.trim();
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getHeadPath() {
		return headPath;
	}

	public void setHeadPath(String headPath) {
		this.headPath = headPath == null ? null : headPath.trim();
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

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName == null ? null : operatorName.trim();
	}

	@Override
	public String toString() {
		return "ManagerUser [userId=" + userId + ", userName=" + userName
				+ ", passwd=" + passwd + ", realName=" + realName
				+ ", contactWay=" + contactWay + ", state=" + state
				+ ", headPath=" + headPath + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", operatorName="
				+ operatorName + "]";
	}

}