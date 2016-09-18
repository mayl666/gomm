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
 * 服务器监控分析定时任务类---zabbix测试服务器
 */
public class CpuAndMemoryTimer {
	private static final Logger logger = LoggerFactory.getLogger(CpuAndMemoryTimer.class);
	@Resource(name = "serverAnalysisService")
	ServerAnalysisService serverAnalysisService;
	public void work() {
		try {
			Date date = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ServerItemDetail serverItemDetail = new ServerItemDetail();
			//cpu使用率高于80%
			String analyzeCpuValue = AppConfigUtil.getStringValue("server.analyze.cpu");
			serverItemDetail.setValue(Double.parseDouble(analyzeCpuValue));
			serverItemDetail.setClock((df1.parse(df.format(date)).getTime()/1000)-3600);
			logger.info(df.format(date)+"CpuAndMemoryTimer--cpu--执行开始啦！！！！！！"+date+"--------"+((df1.parse(df.format(date)).getTime()/1000)-3600));
			//查询当前时间点CPU的高于80%
			serverItemDetail.setKey_("cpu_use_all");
			//查询当前时间点Memory的值高于90%
			String analyzeMemoryValue = AppConfigUtil.getStringValue("server.analyze.memory");
			List<ServerItemDetail> cpuList  = serverAnalysisService.queryCpuValueList(serverItemDetail);
			if(cpuList!=null && cpuList.size()>0){
				for (int i = 0; i < cpuList.size(); i++) {
					serverItemDetail.setHostid(cpuList.get(i).getHostid());
					//查询当前时间点Memory的值
					serverItemDetail.setKey_("used_memory");
					serverItemDetail.setValue(Double.parseDouble(analyzeMemoryValue));
					List<ServerItemDetail> memryListCom  = serverAnalysisService.queryCpuValueList(serverItemDetail);
					if(memryListCom!=null && memryListCom.size()>0){
						logger.info("cpu使用率高于80%和当前时间点Memory的值高于90%--不进行Memory数据保存！！");
					}else{
						//新增memory的监控值
						serverItemDetail.setValue(0.0000);
						List<ServerItemDetail> memryList  = serverAnalysisService.queryCpuValueList(serverItemDetail);
						if(memryList!=null && memryList.size()>0){
							memryList.get(0).setvType("2");
							ServerItemDetail memory = serverAnalysisService.queryItems(memryList.get(0));
							if(memory==null){
								serverAnalysisService.addItem(memryList.get(0));
							}
							serverAnalysisService.addItemValueNew(memryList.get(0));
						}
					}
					//新增cpu的监控值
					cpuList.get(i).setvType("2");
					ServerItemDetail cpu = serverAnalysisService.queryItems(cpuList.get(i));
					if(cpu==null){
						serverAnalysisService.addItem(cpuList.get(i));
					}
					serverAnalysisService.addItemValueNew(cpuList.get(i));
					//查询服务器基本信息
					HostsInfo hostsInfo = serverAnalysisService.queryHost(cpuList.get(i).getHostid());
					hostsInfo.setvType("2");
					HostsInfo hostsInfoOld = serverAnalysisService.queryHostsInfo(hostsInfo);
					if(hostsInfoOld!=null){
						serverAnalysisService.updateHostsInfo(hostsInfo);
					}else{
						serverAnalysisService.addHostsInfo(hostsInfo);
					}
				}
				System.out.println("CpuAndMemoryTimer--cpu--执行完毕啦！！！！！！"+date);
			}
			logger.info("CpuAndMemoryTimer--内存--执行开始啦！！！！！！"+date+"--------"+new Date(((df1.parse(df.format(date)).getTime()/1000)-3600)*1000l));
			ServerItemDetail itemDetail = new ServerItemDetail();
			itemDetail.setKey_("used_memory");
			itemDetail.setValue(Double.parseDouble(analyzeMemoryValue));
			itemDetail.setClock((df1.parse(df.format(date)).getTime()/1000)-3600);
			List<ServerItemDetail> memryList  = serverAnalysisService.queryCpuValueList(itemDetail);
			if(memryList!=null && memryList.size()>0){
				for (int i = 0; i < memryList.size(); i++) {
					itemDetail.setHostid(memryList.get(i).getHostid());
					//查询当前时间点cpu的值
					itemDetail.setKey_("cpu_use_all");
					itemDetail.setValue(Double.parseDouble(analyzeCpuValue));
					List<ServerItemDetail> cpuListnewCom  = serverAnalysisService.queryCpuValueList(itemDetail);
					if(cpuListnewCom!=null && cpuListnewCom.size()>0){
						logger.info("cpu使用率高于80%和当前时间点Memory的值高于90%--不进行cpu数据保存！！");
					}else{
						//新增cpu的监控值
						itemDetail.setValue(0.0000);
						List<ServerItemDetail> cpuListnew  = serverAnalysisService.queryCpuValueList(itemDetail);
						if(cpuListnew!=null && cpuListnew.size()>0){
							cpuListnew.get(0).setvType("2");
							ServerItemDetail cpu = serverAnalysisService.queryItems(cpuListnew.get(0));
							if(cpu==null){
								serverAnalysisService.addItem(cpuListnew.get(0));
							}
							serverAnalysisService.addItemValueNew(cpuListnew.get(0));
						}
					}
					//新增Memory的监控值
					memryList.get(i).setvType("2");
					ServerItemDetail memory = serverAnalysisService.queryItems(memryList.get(i));
					if(memory==null){
						serverAnalysisService.addItem(memryList.get(i));
					}
					serverAnalysisService.addItemValueNew(memryList.get(i));
					
					//查询服务器基本信息
					HostsInfo hostsInfo = serverAnalysisService.queryHost(memryList.get(i).getHostid());
					hostsInfo.setvType("2");
					HostsInfo hostsInfoOld = serverAnalysisService.queryHostsInfo(hostsInfo);
					if(hostsInfoOld!=null){
						serverAnalysisService.updateHostsInfo(hostsInfo);
					}else{
						serverAnalysisService.addHostsInfo(hostsInfo);
					}
				}
			}
			System.out.println("CpuAndMemoryTimer--内存--执行完毕啦！！！！！！"+date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/**
		 * 定时更新服务器的基本属性信息
		 */
		List<HostsInfo> hostsList = serverAnalysisService.queryHostsInfoList("2");
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
