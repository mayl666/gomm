package com.gome.upm.domain;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Properties;

public class MoOrderNotRechargeBO implements Serializable {
	private static final long serialVersionUID = 7319054013976673047L;
	/** 主键ID */
	private Long id;
	private Date startTime;
	private Date endTime;
	private Integer count;
	private Integer minute;
	
	private String oms;
	
	public MoOrderNotRechargeBO(){
		InputStream in =  MoOrderNotRechargeBO.class.getClassLoader().getResourceAsStream("jdbc.properties");
		Properties pro = new Properties();
		try {
			pro.load(in);
			this.oms = pro.getProperty("oms");
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getMinute() {
		return minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}


	public String getOms() {
		return oms;
	}


	public void setOms(String oms) {
		this.oms = oms;
	}
	
	
}
