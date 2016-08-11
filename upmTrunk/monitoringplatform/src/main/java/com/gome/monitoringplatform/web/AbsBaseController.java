package com.gome.monitoringplatform.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
/**
 * 
 *
 * @author fangjinwei
 */
public abstract class AbsBaseController {
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 通过PrintWriter将响应数据写入response，ajax可以接受到这个数据
	 * 
	 * @param response
	 * @param data
	 */
	public void renderData(HttpServletResponse response, String data) {
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			printWriter.print(data);
		} catch (IOException ex) {
			// Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE,
			// null, ex);
		} finally {
			if (null != printWriter) {
				printWriter.flush();
				printWriter.close();
			}
		}
	}

	/**
	 * 根据当前时间获取到5分钟后的时间
	 * 
	 * @param startTime
	 * @return
	 * @throws ParseException
	 */
	public static String getFiveAfterTime(String startTime) throws ParseException {
		if (startTime == null) {
			return null;
		}
		Date afterDate = new Date(formatter.parse(startTime).getTime() + 300000);
		return formatter.format(afterDate);
	}
}
