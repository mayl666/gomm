package com.gome.upm.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 获取数据源配置信息
 * @author liuyuqiang
 *
 */
public class DataSourceGetUtils {
	
	public static String getDataSource(){
		InputStream in =  DataSourceGetUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
		Properties pro = new Properties();
		String driver_source ="dataSourceThree";
		try {
			pro.load(in);
			driver_source = pro.getProperty("driver_source");
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
		return driver_source;
	}
	
	
	public static String getDrgDataSource(){
		InputStream in =  DataSourceGetUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
		Properties pro = new Properties();
		String driver_source ="dataSourceEight";
		try {
			pro.load(in);
			driver_source = pro.getProperty("drg_source");
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
		return driver_source;
	}
	
}
