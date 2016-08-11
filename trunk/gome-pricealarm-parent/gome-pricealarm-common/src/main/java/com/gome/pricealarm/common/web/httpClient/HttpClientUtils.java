/**
 * Copyright(c) 2002-2013, 360buy.com  All Rights Reserved
 */

package com.gome.pricealarm.common.web.httpClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.NameValuePair;

import com.gome.pricealarm.common.util.StringUtils;
import com.gome.pricealarm.constants.Constants;


/**
 * @author liyujian
 * @date 2013-12-13
 */
public class HttpClientUtils {

	/**
	 * 建立请求，以模拟远程HTTP的POST请求方式构造并获取支付宝的处理结果
	 * 如果接口中没有上传文件参数，那么strParaFileName与strFilePath设置为空值 如：buildRequest("",
	 * "",sParaTemp)
	 * 
	 * @param strParaFileName
	 *            文件类型的参数名
	 * @param strFilePath
	 *            文件路径
	 * @param sParaTemp
	 *            请求参数数组
	 * @return 支付宝处理结果
	 * @throws Exception
	 */
	public static String buildRequest(String url, Map<String, String> sParaTemp) throws Exception {

		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		request.setUrl(url);
		// 设置编码集
		request.setCharset(Constants.UTF8);
		request.setMethod(HttpRequest.METHOD_GET);
		request.setQueryString(StringUtils.map2String(sParaTemp));

		HttpResponse response = httpProtocolHandler.execute(request, "", "");
		if (response == null) {
			return null;
		}

		String strResult = response.getStringResult();

		return strResult;
	}

	public static String post(String url, Map<String, String> sParaTemp) throws Exception {
		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		request.setUrl(url);
		// 设置编码集
		request.setCharset(Constants.UTF8);
		request.setMethod(HttpRequest.METHOD_POST);
		request.setParameters(generatNameValuePair(sParaTemp));

		HttpResponse response = httpProtocolHandler.execute(request, "", "");
		if (response == null) {
			return null;
		}

		String strResult = response.getStringResult();

		return strResult;
	}

	public static String get(String url, Map<String, String> sParaTemp) throws Exception {

		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		request.setUrl(url);
		// 设置编码集
		request.setCharset(Constants.UTF8);
		request.setMethod(HttpRequest.METHOD_GET);
		request.setQueryString(map2String(sParaTemp));

		HttpResponse response = httpProtocolHandler.execute(request, "", "");
		if (response == null) {
			return null;
		}

		String strResult = response.getStringResult();

		return strResult;
	}

	/**
	 * MAP类型数组转换成NameValuePair类型
	 * 
	 * @param properties
	 *            MAP类型数组
	 * @return NameValuePair类型数组
	 */
	private static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
		NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
		int i = 0;
		for (Map.Entry<String, String> entry : properties.entrySet()) {
			nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
		}

		return nameValuePair;
	}

	private static String map2String(Map<String, String> params) {
		int count = 0;
		StringBuilder builder = new StringBuilder();
		for (Entry<String, String> entry : params.entrySet()) {
			if (count > 0) {
				builder.append("&");
			}
			builder.append(entry.getKey());
			builder.append("=");
			try {
				if (null == entry.getValue()) {
					builder.append(entry.getValue());
				} else {
					builder.append(URLEncoder.encode(entry.getValue(), "utf-8"));
				}
			} catch (UnsupportedEncodingException e) {
				builder.append(entry.getValue());
			}
			count++;
		}
		return builder.toString();
	}
}
