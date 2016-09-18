package com.gome.upm.service.quartz;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gome.upm.common.util.AppConfigUtil;
import com.gome.upm.domain.HostsInfo;
import com.gome.upm.domain.ServerItemDetail;
import com.gome.upm.service.ServerAnalysisService;
/**
 * 服务器监控分析定时任务类
 */
public class TCPTimer {
	private static final Logger logger = LoggerFactory.getLogger(TCPTimer.class);
	@Resource(name = "serverAnalysisService")
	ServerAnalysisService serverAnalysisService;
	public void work() {
		try {
			Date date = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//使用率低于15%
			ServerItemDetail serverItemDetail = new ServerItemDetail();
			String analyzeValue = AppConfigUtil.getStringValue("server.analyze");
			serverItemDetail.setValue(Double.parseDouble(analyzeValue));
			serverItemDetail.setClock((df1.parse(df.format(date)).getTime()/1000)-3600);
			logger.info("TCPTimer执行开始啦！！！！！！"+date+"--------"+new Date(((df1.parse(df.format(date)).getTime()/1000)-3600)*1000l));
			//查询当前时间点CPU的值低于15%
			serverItemDetail.setKey_("cpu_use_all");
			List<ServerItemDetail> cpuList  = serverAnalysisService.queryValueList(serverItemDetail);
			String[] serverIp = new String[cpuList.size()];
			if(cpuList!=null && cpuList.size()>0){
				for (int i = 0; i < cpuList.size(); i++) {
					serverItemDetail.setHostid(cpuList.get(i).getHostid());
					//查询当前时间点Memory的值低于15%
					serverItemDetail.setKey_("used_memory");
					List<ServerItemDetail> memryList  = serverAnalysisService.queryValueList(serverItemDetail);
					/**
					 * cpu和memory同时低于15%
					 */
					if (memryList!=null && memryList.size()>0) {
						//新增cpu的监控值
						cpuList.get(i).setvType("1");
						ServerItemDetail cpu = serverAnalysisService.queryItems(cpuList.get(i));
						if(cpu==null){
							serverAnalysisService.addItem(cpuList.get(i));
						}
						serverAnalysisService.addItemValue(cpuList.get(i));
						//新增memory的监控值
						memryList.get(0).setvType("1");
						ServerItemDetail memory = serverAnalysisService.queryItems(memryList.get(0));
						if(memory==null){
							serverAnalysisService.addItem(memryList.get(0));
						}
						serverAnalysisService.addItemValue(memryList.get(0));
						serverItemDetail.setKey_("iptstate.tcp.syn");
						//查询当前时间点tco_syn的值
						serverItemDetail.setvType("1");
						ServerItemDetail tco_syn  = serverAnalysisService.queryValue(serverItemDetail);
						if(tco_syn!=null){
							//新增tco_syn的监控值
							tco_syn.setvType("1");
							ServerItemDetail tco_syn1 = serverAnalysisService.queryItems(tco_syn);
							if(tco_syn1==null){
								serverAnalysisService.addItem(tco_syn);
							}
							serverAnalysisService.addItemValue(tco_syn);
						}
						serverItemDetail.setKey_("iptstate.tcp.established");
						//查询当前时间点tcp_established的值
						ServerItemDetail tcp_established  = serverAnalysisService.queryValue(serverItemDetail);
						if(tcp_established!=null){
							//新增tcp_established的监控值
							tcp_established.setvType("1");
							ServerItemDetail tcp_established1 = serverAnalysisService.queryItems(tcp_established);
							if(tcp_established1==null){
								serverAnalysisService.addItem(tcp_established);
							}
							serverAnalysisService.addItemValue(tcp_established);
						}
						//查询服务器基本信息
						HostsInfo hostsInfo = serverAnalysisService.queryHost(cpuList.get(i).getHostid());
						serverIp[i]=hostsInfo.getIp();
						hostsInfo.setvType("1");
						HostsInfo hostsInfoOld = serverAnalysisService.queryHostsInfo(hostsInfo);
						if(hostsInfoOld!=null){
							serverAnalysisService.updateHostsInfo(hostsInfo);
						}else{
							hostsInfo.setvType("1");
							serverAnalysisService.addHostsInfo(hostsInfo);
						}
					}
				}
				System.out.println("TCPTimer执行完毕啦！！！！！！"+date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * 定时更新服务器的基本属性信息
		 */
		List<HostsInfo> hostsList = serverAnalysisService.queryHostsInfoList("1");
		String[] ipArr = new String[hostsList.size()];
		if(hostsList!=null && hostsList.size()>0){
			for (int j = 0; j < hostsList.size(); j++) {
				ipArr[j] = hostsList.get(j).getIp();
			}
		}
		try {
			String response = integratedQuery(ipArr);
			if(response!=null && !"".endsWith(response)){
				JSONArray jsonArray = JSON.parseArray(response);
				if(jsonArray!=null && jsonArray.toArray().length>0){
					for (int m = 0; m < hostsList.size(); m++) {
						for (int j = 0; j < jsonArray.toArray().length; j++) {
							String status = jsonArray.getJSONObject(j).getString("status");
							String ip = jsonArray.getJSONObject(j).getString("ip");
							if(status!=null && status.equals("success")){
								JSONObject jsonObject = jsonArray.getJSONObject(j).getJSONObject("searchResult");
								HostsInfo queryHostDetail = hostsList.get(m);
								if(queryHostDetail.getIp().equals(ip)){
									if(jsonObject.getString("envType")!=null){
										queryHostDetail.setOsType(jsonObject.getString("envType"));
									}else{
										queryHostDetail.setOsType("无");
									}
									if(jsonObject.getString("prjName")!=null){
										queryHostDetail.setProjectName(jsonObject.getString("prjName"));
									}else{
										queryHostDetail.setProjectName("无");
									}
									if(jsonObject.getString("prjAdmin")!=null){
										queryHostDetail.setProjectLeader(jsonObject.getString("prjAdmin"));
									}else{
										queryHostDetail.setProjectLeader("无");
									}
									if(jsonObject.getString("appName")!=null){
										queryHostDetail.setApplicationName(jsonObject.getString("appName"));
									}else{
										queryHostDetail.setApplicationName("无");
									}
									if(jsonObject.getString("appAdmin")!=null){
										queryHostDetail.setApplicationLeader(jsonObject.getString("appAdmin"));
									}else{
										queryHostDetail.setApplicationLeader("无");
									}
									serverAnalysisService.updateHostsInfo(queryHostDetail);
									continue;
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 向Zabbix发送Post请求，并返回json格式字符串
	 * 
	 * @param param 请求参数
	 * @return
	 * @throws Exception
	 */
	public String integratedQuery(String[] ipArr) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ips",ipArr);
		String param = JSON.toJSONString(map);
		HttpURLConnection connection = null;
		DataOutputStream out = null;
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		try {
			//创建连接
			String analyzeUrl = AppConfigUtil.getStringValue("dcms.analyze.url");
			URL url = new URL(analyzeUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
			connection.setRequestProperty("Charset", "utf-8");
			connection.connect();
			//POST请求
			out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(param);
			out.flush();
			//读取响应
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
			String lines;
			sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes());
				sb.append(lines);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
			if (reader != null) {
				reader.close();
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
		return sb.toString();
	}
}
