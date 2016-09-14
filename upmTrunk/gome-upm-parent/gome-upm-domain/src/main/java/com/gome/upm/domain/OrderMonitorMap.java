package com.gome.upm.domain;

public class OrderMonitorMap {

	public OrderMonitorMap() {

	}

	public OrderMonitorMap(String name, Long value, Long precent, boolean isWarning) {
		super();
		this.name = name;
		this.value = value;
		this.precent = precent;
		this.isWarning = isWarning;
	}

	private String name;

	private Long value;

	private Long precent;

	private boolean isWarning;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public Long getPrecent() {
		return precent;
	}

	public void setPrecent(Long precent) {
		this.precent = precent;
	}

	public boolean isWarning() {
		return isWarning;
	}

	public void setWarning(boolean isWarning) {
		this.isWarning = isWarning;
	}

}
