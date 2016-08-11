package com.gome.pricemonitor.domain;

import java.util.Date;
import java.util.List;

public class ManagerFunc {

	public static final Integer FRONT_CHECKED = 0;// 选中
	public static final Integer FRONT_NO_CHECKED = 0;// 不选中
	private Long funcId;

	private String funcName;

	private String funcUrl;

	private Date createTime;

	private Date updateTime;

	private Integer state;

	private Long parentId;

	private boolean checked;

	private List<ManagerFunc> childNodes;

	public List<ManagerFunc> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(List<ManagerFunc> childNodes) {
		this.childNodes = childNodes;
	}

	

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Long getFuncId() {
		return funcId;
	}

	public void setFuncId(Long funcId) {
		this.funcId = funcId;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName == null ? null : funcName.trim();
	}

	public String getFuncUrl() {
		return funcUrl;
	}

	public void setFuncUrl(String funcUrl) {
		this.funcUrl = funcUrl == null ? null : funcUrl.trim();
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (checked ? 1231 : 1237);
		result = prime * result
				+ ((childNodes == null) ? 0 : childNodes.hashCode());
		result = prime * result
				+ ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((funcId == null) ? 0 : funcId.hashCode());
		result = prime * result
				+ ((funcName == null) ? 0 : funcName.hashCode());
		result = prime * result + ((funcUrl == null) ? 0 : funcUrl.hashCode());
		result = prime * result
				+ ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result
				+ ((updateTime == null) ? 0 : updateTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		   
	        if (obj instanceof ManagerFunc) {   
	        	ManagerFunc func = (ManagerFunc) obj; 
	        	return this.funcId.equals(func.getFuncId());
	           
	        }   
	        return super.equals(obj);  
    }
}