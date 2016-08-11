package com.gome.monitoringplatform.gjob;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.gome.job.util.AbstractJobRunnable;


public abstract class AbsJobImpl extends AbstractJobRunnable{
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static String myUserName = "pw_orderhis_uat";
	static String myPassword = "zy09rmFCrAiNvvU6";
	static String databaseName = "ec_order_history_uat";
	static String ip = "10.126.44.51";
	static int port = 27027;
	
	/**
	 * 订单
	 */
	static String order="orderstatushistory";
	/**
	 * 配送单
	 */
	static String shippinggroup="shippinggroupstatushistory";
	static SimpleDateFormat sdfyyyyMM=new SimpleDateFormat("yyyyMM");
	class ReObj {
		private Date endTime;
		private Date startTime;
		public Date getEndTime() {
			return endTime;
		}
		public void setEndTime(Date endTime) {
			this.endTime = endTime;
		}
		public Date getStartTime() {
			return startTime;
		}
		public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}
	}
	
	ReObj getTime() throws ParseException{
		ReObj reObj=new ReObj();
		String ww=formatter.format(new Date());
		String[]  ll=ww.split(" ");
		String[]  ll1=ll[1].split(":");
		String ddd=ll[0]+" "+ll1[0]+":"+Integer.parseInt(ll1[1])/5*5+":00";
		Date endTime=formatter.parse(ddd);
		reObj.setEndTime(endTime);
		Date startTime=new Date(endTime.getTime()-1000*60*5);
		reObj.setStartTime(startTime);
		return reObj;
	}
}
