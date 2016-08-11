package com.gome.alarmplatform.service;

/**
 * 邮件service接口.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年06月14日    caowei    新建
 * </pre>
 */
public interface MailService {

	/**
	 * 
	 * 发送邮件.
	 *
	 * @param subject
	 * 			主题
	 * @param content
	 * 			内容
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年06月14日    caowei    新建
	 * </pre>
	 */
	void sendMail(String mail, String subject, String content);

}
