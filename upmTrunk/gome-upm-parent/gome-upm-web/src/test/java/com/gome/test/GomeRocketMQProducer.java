package com.gome.test;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

/**
 * RocketMQ消息的生产者，结合策略模式和懒加载的单例设计，新的生产者需要新建一个枚举对象
 * 
 */
public enum GomeRocketMQProducer {
	
	/** 订单未流转/状态不一致	*/
	SENDER_ORDER_INC("upm_syn_topic", ""),
	
	/** 商品评价增量 */
	SENDER_APPRAISE_INC("appraise", "appraise_increment"),
	/** 商品咨询增量 */
	SENDER_QUESTION_INC("question", "question_increment"),
	/** 前台组清缓存 */
	SENDER_HOMESTOCK_INC("homestock", "homestock_increment"),
	/** 前台组 团，抢，无线专享价 */
	SENDER_INDEXSERACH_INC("index_external", "group_rush_mobile"),
	/** BCC清缓存 */
	SENDER_LETV_INC("bcc_cache", "bcc_invalidateCache" + ConfigUtil.getInstance("rocketmq.properties").getValue("appraise.env")),
	/** 所有相关价格增量消息 */
	SENDER_GOMEPRICE_INC("bcc_price", "bcc_price"+ConfigUtil.getInstance("rocketmq.properties").getValue("appraise.env"));
	
	protected Logger logger = LoggerFactory.getLogger(GomeRocketMQProducer.class);

	/** 发送消息失败时，重新尝试的最大次数 */
	private final int RTRY_TIMES = 3;

	/** 必需配置： 如, order */
	private String topic;
	/** 必需配置：tags,如：TagA */
	private String tags;

	/** 必需配置： 名称服务器地址 */
	private String namesrvAddr;
	/** 必需配置：标识当前环境的后缀如_dev、_sit */
	private String env;

	/**
	 * 消息延时投递时间级别，0表示不延时，大于0表示特定延时级别（具体级别在服务器端定义） 
	 * messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 40m 50m 1h 2h 6h
	 */
	private int level = 5;

	private DefaultMQProducer producer;

	private GomeRocketMQProducer(String topic, String tags) {
		this.topic = topic;
		this.tags = tags;

//		this.namesrvAddr = ConfigUtil.getInstance("rocketmq.properties").getValue("appraise.namesrvAddr");
//		this.env = ConfigUtil.getInstance("rocketmq.properties").getValue("appraise.env");
		
		this.namesrvAddr = "10.58.57.47:9876;10.58.57.51:9876";
		this.env = "_uat";
		init();
	}

	public static void main(String[] args) {
		try {
			Map<String,Integer> info = new HashMap<String,Integer>(10);
			info.put("DRAGON-z-OD",1);
			info.put("DRAGON-n-OD",1);
			info.put("OMS-DRG-z",1);
			info.put("OMS-DRG-n",1);
			info.put("OMS-POP-z",1);
			info.put("OMS-POP-n",1);
			info.put("Z-CO-G3PP",1);
			info.put("Z-CO-SO-R-DRG",1);
			info.put("Z-CO-SO-R-POP",1);
			info.put("Z-CO-SO-kf",1);
			info.put("Z-CO-SO-c",1);
			info.put("Z-CO-SO-N-DRG",1);
			info.put("Z-CO-SO-N-POP",1);
			String str = JSON.toJSONString(info);
			System.out.println(GomeRocketMQProducer.SENDER_ORDER_INC.send("upm_syn_group", str.getBytes()));
			
		} catch (MQClientException e) {
			e.printStackTrace();
		} catch (RemotingException e) {
			e.printStackTrace();
		} catch (MQBrokerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void init() {
		logger.info("初始化RocketMQ消息发送者，namesrvAddr={" + this.namesrvAddr + "}，topic={" + getEnvTopic() + "}，tags={" + this.tags + "}");
		DefaultMQProducer producerNew = null;
		try {
			producerNew = new DefaultMQProducer(getEnvTopic());
			producerNew.setNamesrvAddr(namesrvAddr);
			producerNew.setInstanceName(RunTimeUtil.getRocketMqUniqeInstanceName());
			producerNew.start();
		} catch (MQClientException e) {
			logger.error("rocketmq restartProducer error ", e);
		}
		this.producer = producerNew;
	}

	/**
	 * 发送消息到RocketMQ，失败则会重试2次 外层重试发送，内层的循环发送暂时有bug,在主当机的时候有问题
	 */
	public SendResult sendWithRetry(Message msg) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
		SendResult result = null;
		for (int j = 0; j < RTRY_TIMES; j++) {
			try {
				result = send(msg);
				return result;
			} catch (Exception e) {
				logger.debug("发送消息到RocketMQ失败，会进行重试" + (RTRY_TIMES - 1 - j) + "次，失败原因：" + e.getMessage(), e);
				continue;
			}
		}
		return result;
	}

	/**
	 * 发送消息到RocketMQ，失败则会重试2次 外层重试发送，内层的循环发送暂时有bug,在主当机的时候有问题
	 */
	public SendResult sendWithRetry(String newTags, String keys, byte[] body) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
		Message msg = new Message(getEnvTopic(), newTags, keys, body);
		return sendWithRetry(msg);
	}

	/**
	 * 发送消息到RocketMQ，失败则会重试2次 外层重试发送，内层的循环发送暂时有bug,在主当机的时候有问题
	 */
	public SendResult sendWithRetry(String keys, byte[] body) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
		Message msg = new Message(getEnvTopic(), tags, keys, body);
		return sendWithRetry(msg);
	}

	/**
	 * 发送消息到RocketMQ，失败不会重试
	 */
	public SendResult send(Message msg) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
		msg.setDelayTimeLevel(level);
		SendResult result = producer.send(msg);
		logger.info("发送RocketMQ消息：" + msg + "，结果：" + result);
		return result;
	}

	/**
	 * 发送消息到RocketMQ，失败不会重试
	 * 
	 * @param newTags
	 * @param keys
	 * @param body
	 * @return
	 */
	public SendResult send(String newTags, String keys, byte[] body) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
		Message msg = new Message(getEnvTopic(), newTags, keys, body);
		return send(msg);
	}

	/**
	 * 发送消息到RocketMQ，失败不会重试
	 * 
	 * @param keys
	 * @param body
	 * @return
	 */
	public SendResult send(String keys, byte[] body) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
		Message msg = new Message(getEnvTopic(), tags, keys, body);
		System.out.println("MSG:"+JSON.toJSON(msg));
		return send(msg);
	}

	/**
	 * 发送消息到RocketMQ，失败不会重试
	 * 
	 * @param keys
	 * @param body
	 * @return
	 */
	public SendResult sendWithDelay(String keys, byte[] body, int level) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
		Message msg = new Message(getEnvTopic(), tags, keys, body);
		msg.setDelayTimeLevel(level);
		SendResult result = producer.send(msg);
		logger.info("发送RocketMQ消息：" + msg + "，结果：" + result);
		return result;
	}
	
	/**
	 * 将对象转换成字节数组
	 * 
	 * @param obj
	 * @return
	 */
	public byte[] toByte(Object obj) {
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		byte[] bytes = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			bytes = baos.toByteArray();
		} catch (Exception e) {
			logger.error("将对象转换成字节数组失败：" + e.getMessage(), e);
			throw new RuntimeException("将对象转换成字节数组失败：" + e.getMessage(), e);
		} finally {
			try {
				if (oos != null) {
					oos.flush();
					oos.close();
				}
				if (baos != null) {
					baos.flush();
					baos.close();
				}
			} catch (Exception e) {
				logger.error("将对象转换成字节数组，关闭字节流失败：" + e.getMessage(), e);
			}
		}
		return bytes;
	}

	public String testSend() {
		try {
			SendResult result = send("test2014", "nice to meet you".getBytes());
			return result.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "send message to RocketMQ fail !";
	}

	/**
	 * 环境隔离的topic
	 */
	public String getEnvTopic() {
		return topic + env;
	}

	public String getTopic() {
		return topic;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public DefaultMQProducer getProducer() {
		return producer;
	}

	public void setProducer(DefaultMQProducer producer) {
		this.producer = producer;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
}
