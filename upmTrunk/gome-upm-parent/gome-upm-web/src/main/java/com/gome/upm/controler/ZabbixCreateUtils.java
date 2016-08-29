package com.gome.upm.controler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ZabbixCreateUtils {
	private static final Logger logger = LoggerFactory.getLogger(ZabbixCreateUtils.class);
	private static Date date;
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static String URL      = "http://10.58.44.57/zabbix/api_jsonrpc.php";
	private static String AUTH     = null;
	private static String USERNAME = "liuhaikun-ds";
	private static String PASSWORD = "haikun";

	/**
	 * 向Zabbix发送Post请求，并返回json格式字符串
	 * 
	 * @param param 请求参数
	 * @return
	 * @throws Exception
	 */
	private static String sendPost(String param) throws Exception {
		HttpURLConnection connection = null;
		DataOutputStream out = null;
		BufferedReader reader = null;
		StringBuffer sb = null;
		try {
			//创建连接
			URL url = new URL(URL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式

			connection.connect();

			//POST请求
			out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(param);
			out.flush();

			//读取响应
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String lines;
			sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
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


	/**
	 * 通过用户名和密码设置AUTH，获得权限
	 * 
	 * @throws Exception
	 */
	private static void setAuth() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user", USERNAME);
		params.put("password", PASSWORD);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "user.login");
		map.put("params", params);
		map.put("auth", null);
		map.put("id", 0);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONObject json = JSON.parseObject(response);
		AUTH = json.getString("result");
		System.out.println(AUTH);
	}


	/**
	 * 获得Template列表
	 * 
	 * @param host Zabbix中配置的主机hosts
	 * @return 返回hostid
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getTemplateList(String status) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("status", status);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("output", "extend");
		params.put("filter", filter);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "template.get");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 1);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONArray jsonArray = JSON.parseObject(response).getJSONArray("result");
		Object[] array = jsonArray.toArray();
		for (int i = 0; i < array.length; i++) {
			JSONObject jsonObject = new JSONObject((Map<String, Object>) array[i]);
			list.add(jsonObject);
		}
		return list;
	}
	

	private static String queryTemplate(String template) throws Exception {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("host", template);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("output", "templateid");
		params.put("filter", filter);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "template.get");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 1);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONObject object = JSON.parseObject(response).getJSONArray("result").getJSONObject(0);
		String templateid = (String) object.get("templateid");
		System.out.println("queryTemplate-----"+templateid);
		return templateid;
	}


	private static String getHostGroup(String hostgroup) throws Exception {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("name", hostgroup);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("output", "groupid");
		params.put("filter", filter);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "hostgroup.get");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 0);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONObject json = JSON.parseObject(response).getJSONArray("result").getJSONObject(0);
		String groupid = json.getString("groupid");
		System.out.println("getHostGroup-----"+groupid);
		return groupid;
	}


	private static String createHost(String host, String ip, String port, String groupid, String templateId,String templateCommonId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> interfaces = new HashMap<String, Object>();
		List<Map<String, Object>> listinterfaces = new ArrayList<Map<String, Object>>();
		interfaces.put("type", 1);
		interfaces.put("main", 1);
		interfaces.put("useip", 1);
		interfaces.put("ip", ip);
		interfaces.put("dns", "");
		interfaces.put("port", port);
		listinterfaces.add(interfaces);
		Map<String, Object> groups = new HashMap<String, Object>();
		List<Map<String, Object>> listgroups = new ArrayList<Map<String, Object>>();
		groups.put("groupid", groupid);
		listgroups.add(groups);
		Map<String, Object> templates = new HashMap<String, Object>();
		Map<String, Object> templatesCommon = new HashMap<String, Object>();
		List<Map<String, Object>> listtemplates = new ArrayList<Map<String, Object>>();
		templates.put("templateid", templateId);
		templatesCommon.put("templateid", templateCommonId);
		listtemplates.add(templates);
		listtemplates.add(templatesCommon);
		Map<String, Object> inventory = new HashMap<String, Object>();
		inventory.put("macaddress_a", "01234");
		inventory.put("macaddress_b", "56789");
		params.put("host", host);
		params.put("interfaces", listinterfaces);
		params.put("groups", listgroups);
		params.put("templates", listtemplates);
		params.put("inventory_mode", 0);
		params.put("inventory", inventory);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "host.create");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 1);
		String param = JSON.toJSONString(map);
		date = new Date();
		logger.info(df.format(date) +"---------创建host，JSON参数格式----------"+param);
		String response = sendPost(param);
		JSONObject object = JSON.parseObject(response).getJSONObject("result");
		String hostids = object.getString("hostids");
		return hostids;
	}

	private static String createHostGroup(String hostgroup) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", hostgroup);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "hostgroup.create");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 1);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONObject object = JSON.parseObject(response).getJSONObject("result");
		JSONArray jsonArray = (JSONArray) object.get("groupids");
		String groupid = (String) jsonArray.get(0);
		return groupid;
	}


	private static boolean isExistHostGroup(String hostgroup) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", hostgroup);
		params.put("nodeids", "[1]");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "hostgroup.exists");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 1);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONObject object = JSON.parseObject(response);
		boolean result = object.getBooleanValue("result");
		return result;
	}


	private static boolean isExistHost(String host) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("host", host);
		params.put("nodeids", "1");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "host.exists");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 1);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONObject object = JSON.parseObject(response);
		boolean result = object.getBooleanValue("result");
		return result;
	}
	

	private static String getProxy(String proxyName) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("output", "proxyid");
		Map<String, Object> hostMap = new HashMap<String, Object>();
		hostMap.put("host", proxyName);
		params.put("filter", hostMap);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "proxy.get");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 1);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONObject object = null;
		String result = "";
		if (JSON.parseObject(response).getJSONArray("result")!=null && JSON.parseObject(response).getJSONArray("result").toArray().length>0){
			object = JSON.parseObject(response).getJSONArray("result").getJSONObject(0);
			result = object.getString("proxyid");
		}
		return result;
	}


	private static String updateProxy(String hostId,String proxyid) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("proxyid", proxyid);
		List<String> listhostid = new ArrayList<String>();
		listhostid.add(hostId);
		params.put("hosts", listhostid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "proxy.update");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 1);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONObject object = JSON.parseObject(response).getJSONObject("result");
		String result = object.getJSONArray("proxyids").getString(0);
		return result;
	}


	private static String createProxy(String hostId,String proxyName) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("host", proxyName);
		params.put("status", "5");
		List<Map<String, Object>> listhostid = new ArrayList<Map<String, Object>>();
		Map<String, Object> hostMap = new HashMap<String, Object>();
		hostMap.put("hostid", hostId);
		listhostid.add(hostMap);
		params.put("hosts", listhostid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "proxy.create");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 1);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONObject object = JSON.parseObject(response);
		String result = object.getString("result");
		return result;
	}


	private static void updateHost(String hostid, String status) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hostid", hostid);
		params.put("status", status);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "host.update");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 1);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONObject object = JSON.parseObject(response);
		String result = object.getString("result");
	}
	private static void deleteHsot(String hostid) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		List<String> listparams = new ArrayList<String>();
		listparams.add(hostid);
		params.put("hostid", listparams);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "host.delete");
		map.put("params", listparams);
		map.put("auth", AUTH);
		map.put("id", 1);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONObject object = JSON.parseObject(response);
		String result = object.getString("result");
	}
	/**
	 * 获得主机host列表
	 * 
	 * @param host Zabbix中配置的主机hosts
	 * @return 返回hostid
	 * @throws Exception
	 */
	private static String getHostId(String host) throws Exception {
		Map<String, Object> filter = new HashMap<String, Object>();
		List<String> listparams = new ArrayList<String>();
		listparams.add(host);
		filter.put("host", listparams);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("output", "hostid");
		params.put("filter", filter);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "host.get");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 0);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONObject jsonObject = JSON.parseObject(response).getJSONArray("result").getJSONObject(0);
		String hostid = jsonObject.getString("hostid");
		return hostid;
	}
	
	/**
	 * 获得主机host列表
	 * 
	 * @param host Zabbix中配置的主机hosts
	 * @return 返回hostid
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getHostList(String status) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("status", status);
		Map<String, Object> output = new HashMap<String, Object>();
		output.put("host", "name");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("output", "extend");
		params.put("filter", filter);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "host.get");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 1);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONArray jsonArray = JSON.parseObject(response).getJSONArray("result");
		Object[] array = jsonArray.toArray();
		for (int i = 0; i < array.length; i++) {
			JSONObject jsonObject = new JSONObject((Map<String, Object>) array[i]);
			list.add(jsonObject);
		}
		return list;
	}
	
	/**
	 * 
	 * @param osType:操作系统类型0、Windows 1、Linux
	 * @param host:判断host是否存在一般是IP
	 * @param hostgroup:判断host group是否存在，存在关联，不存在创建新的group
	 * @param ip:服务器ip
	 * @param port：端口，默认为10050
	 * @return
	 * @throws Exception
	 */
	public static boolean createServerMonitor(String osType,String host,String hostgroup,String ip,String port) throws Exception{
		//1、用户验证
		date = new Date();
		logger.info(df.format(date) +"---------用户校验开始----------");
		setAuth();
		logger.info(df.format(date) +"---------用户校验通过----------");
		//操作系统类型0、Windows 1、Linux
		//2、判断host是否存在一般是IP
		boolean isExistHost = isExistHost(host);
		logger.info(df.format(date) +"---------判断host是否存在，结果为----------："+isExistHost);
		if(isExistHost){
			//host已存在，请重新命名
			//throw new RuntimeException("host已存在,请重新命名!");
		}
		//3、判断host group是否存在，存在关联，不存在创建新的group
		String groupid = "";
		boolean isExistHostGroup = isExistHostGroup(hostgroup);
		logger.info(df.format(date) +"---------判断host group是否存在，结果为----------："+isExistHostGroup);
		if(!isExistHostGroup){
			logger.info(df.format(date) +"---------创建hostGroup开始----------");
			groupid = createHostGroup(hostgroup);
			logger.info(df.format(date) +"---------创建hostGroup完成----------groupid为："+groupid);
		}else{
			groupid = getHostGroup(hostgroup);
			logger.info(df.format(date) +"---------判断host group已经存在，groupid为："+groupid);
		}
		/**
		 * 4、根据OS类型，选择对应的template,window和linux两种模板
		 * 当前也只有这两种模板
		 */
		String template="";
		if(osType=="0"){
			template = "gome windows";
		}else{
			template = "gome linux";
		}
		String templateId = queryTemplate(template);
		logger.info(df.format(date) +"--------获取到"+template+"的templateId为："+templateId);
		//TODO 此处为新加业务
		/**
		 * 不管是windows还是linux  都要关联一个Template ICMP Ping 这个模板监控ping的比较重要
		 */
		String templateCommonId = queryTemplate("Template ICMP Ping");
		logger.info(df.format(date) +"--------获取到【Template ICMP Ping】的templateCommonId为："+templateCommonId);
		//5、创建host
		String hostId="";
		if(!isExistHost){
			logger.info(df.format(date) +"---------创建host开始----------");
			hostId = createHost(host,ip,port,groupid,templateId,templateCommonId);
			logger.info(df.format(date) +"---------创建host完成----------hostId为："+hostId);
		}else{
			hostId = getHostId(host);
			logger.info(df.format(date) +"---------监控服务host已经存在----------hostId为："+hostId);
		}
		/**
		 * 6、根据代理选择策略，选择指定的代理proxy，并关联hsot
		 * 代理当前只有两个
		 * proxy1-10.58.60.40
		 * proxy2-10.58.44.92
		 * 随机绑定一个即可
		 */
		long time = new Date().getTime();
		String proxyName = "";
        if(time%2==0){
        	proxyName = "proxy1-10.58.60.40";
        }
        else{
        	proxyName = "proxy2-10.58.44.92";
        }
		String proxyid = getProxy(proxyName);
		logger.info(df.format(date) +"---------本次创建监控服务随机分配的代理为：【"+proxyName+"】获取到的proxyid为"+proxyid);
		if(proxyid!=null && proxyid!=""){
			//proxyid = updateProxy(hostId,proxyid);
		}else{
			logger.info(df.format(date) +"---------创建proxy开始----------");
			proxyid = createProxy(hostId,proxyName);
			logger.info(df.format(date) +"---------创建proxy完成----------proxyid为："+proxyid);
		}
		/**
		 * 更新和删除监控，暂时不需要就注释
		 */
		//String status = "0";
		//更新host
		logger.info(df.format(date) +"---------更新host开始----------");
		//updateHost(hostId,status);
		logger.info(df.format(date) +"---------更新host完成----------");
		//删除host
		logger.info(df.format(date) +"---------删除host开始----------hostId为："+hostId);
		//deleteHsot(hostId);
		logger.info(df.format(date) +"---------删除host完成----------hostId为："+hostId);
		return true;
	}
	public static void main(String[] args) throws Exception {
		 // 操作系统类型0、Windows 1、Linux
        String osType = "1";
        // host一般就是IP
        String host = "10.58.57.103";
        // 应用服务器+应用名称
        String hostgroup = "jiagou_jg-zjj";
        String ip = "10.58.57.103";
        // 端口默认为10050
        String port = "10050";
        boolean createServerMonitor = createServerMonitor(osType, host, hostgroup, ip, port);
        System.out.println(createServerMonitor);
	}
}
