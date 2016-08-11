package com.gome.alarmplatform.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gome.alarmplatform.common.util.SendMail;
import com.gome.alarmplatform.service.MailService;

/**
 * 
 * 邮件service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年06月14日    caowei    新建
 * </pre>
 */
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service
public class MailServiceImpl implements MailService {

	public void sendMail(String mail, String subject, String content) {
		String[] str_to = mail.split(";");
		SendMail.sendMails(str_to, subject, content);
		
	}

}
