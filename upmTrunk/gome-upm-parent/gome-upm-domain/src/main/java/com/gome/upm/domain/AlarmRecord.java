package com.gome.upm.domain;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * 报警记录实体类
 * @author caowei-ds1
 * @date 2016年6月22日
 */
public class AlarmRecord {
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static Properties props = new Properties();
	
	static {
		try {
//			System.out.println("path:" + AlarmRecord.class.getClassLoader().getResource("alarm.properties").getPath());
			InputStream in = AlarmRecord.class.getClassLoader().getResourceAsStream("alarm.properties");
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**编号（导出时用到） */
	private Integer num;
	
	/** 主键ID */
	private Long id;
	
	/** 类型 */
	private String type;
	
	/** 类型描述 */
	private String typeStr;
	
	/** 报警内容 */
	private String content;
	
	/** 关联ID(url、port表主键id) */
	private Long pid;
	
	/** 状态    0：待处理；1：处理中；2：已处理；3：忽略*/
	private Integer status;
	
	/** 状态描述 */
	private String statusStr;
	
	/** 报警级别   1：一级；2：二级；3：三级*/
	private Integer level;
	
	/** 报警级别描述 */
	private String levelStr;
	
	/** 报警时间 */
	private Date sendTime;
	
	/** 报警时间字符串 */
	private String sendTimeStr;
	
	/** 开始时间 */
	private String startTime;
	
	/** 结束时间 */
	private String endTime;
	
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
		this.typeStr = props.getProperty(type);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
		if(sendTime != null){
			this.sendTimeStr = df.format(sendTime);
		}
	}

	public String getSendTimeStr() {
		return sendTimeStr;
	}

	public void setSendTimeStr(String sendTimeStr) {
		this.sendTimeStr = sendTimeStr;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTypeStr() {
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
		if(status == 0){
			this.statusStr = "待处理";
		}else if(status == 1){
			this.statusStr = "处理中";
		}else if(status == 2){
			this.statusStr = "已处理";
		}else if(status == 3){
			this.statusStr = "忽略";
		}else {
			this.statusStr = "未知";
		}
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
		if(level == 1){
			this.levelStr = "一级";
		}else if(level == 2){
			this.levelStr = "二级";
		}else if(level == 3){
			this.levelStr = "三级";
		}else {
			this.levelStr = "未知";
		}
	}

	public String getLevelStr() {
		return levelStr;
	}

	public void setLevelStr(String levelStr) {
		this.levelStr = levelStr;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

}
