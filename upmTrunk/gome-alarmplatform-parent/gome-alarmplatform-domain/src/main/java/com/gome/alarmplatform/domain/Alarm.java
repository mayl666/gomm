package com.gome.alarmplatform.domain;

import java.util.Date;

/**
 * 报警实体类
 * @author caowei-ds1
 * 2016年06月16日
 *
 */
public class Alarm {

	//报警记录id
	private Long id;

	//类型
	private String type;
	
	//内容
	private String content;
	
	//关联id（对应url、port表中主键id）
	private Long pid;
	
	//报警级别
	private Integer level;
	
	//报警时间
	private Date sendTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

}
