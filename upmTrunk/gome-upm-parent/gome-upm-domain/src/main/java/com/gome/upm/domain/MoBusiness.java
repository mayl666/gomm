package com.gome.upm.domain;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Properties;

public class MoBusiness implements Serializable{
	private static final long serialVersionUID = 5373021679091005191L;
	private String name;
	private Long amount;
	private Date startTime;
	private Date endTime;
	
	private String oms;
	
	private String catb;
	
	public MoBusiness(){
		/*InputStream in =  MoBusiness.class.getClassLoader().getResourceAsStream("jdbc.properties");
		Properties pro = new Properties();
		try {
			pro.load(in);
			this.oms = pro.getProperty("oms");
			this.catb = pro.getProperty("catb");
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
		}*/

	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getOms() {
		return oms;
	}
	public void setOms(String oms) {
		this.oms = oms;
	}
	public String getCatb() {
		return catb;
	}
	public void setCatb(String catb) {
		this.catb = catb;
	}
	
	
	
}
