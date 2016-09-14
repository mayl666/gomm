package com.gome.upm.service.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class KafkaService extends Thread {

private static final Logger LOG = LoggerFactory.getLogger(KafkaService.class);
	
	private ThreadPoolExecutor consumeExecutor;
	private int minConsumerThread = 20;
	private int maxConsumerThread = 64;
	private ConsumerConnector consumer;
	private Properties props = new Properties();
	/** kafka zk地址 */
	private String connectstr;
	/** 主题 */
	private String topic;
	
	private String groupName;
	
	@Resource
	private KafkaConsumerService kafkaConsumerService;
	
	@Override
	public void run() {
		if(StringUtils.isBlank(connectstr)){
			throw new IllegalArgumentException("kafka zk地址不能为空");
		}
		if(StringUtils.isBlank(topic)){
			throw new IllegalArgumentException("topic不能为空");
		}
		if(StringUtils.isBlank(groupName)){
			throw new IllegalArgumentException("groupName不能为空");
		}
		/*if(messageListener == null){
			throw new IllegalArgumentException("messageListener不能为null");
		}*/
		consumeExecutor = new ThreadPoolExecutor(minConsumerThread, maxConsumerThread, 1000 * 60, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(200), new ThreadFactory() {
					private final AtomicInteger threadIndex = new AtomicInteger(0);

					public Thread newThread(Runnable r) {
						return new Thread(r, "Consumer Thread_" + this.threadIndex.incrementAndGet());
					}
				}, new ThreadPoolExecutor.CallerRunsPolicy());
		props.put("zookeeper.connect", connectstr);
		props.put("group.id", groupName);
		props.put("auto.commit.interval.ms", "5000");
		props.put("zookeeper.session.timeout.ms", "7000");
		props.put("auto.offset.reset", "smallest");
		props.put("rebalance.max.retries", "20");
		props.put("rebalance.backoff.ms", "12000");
		props.put("zookeeper.sync.time.ms", String.valueOf(30 * 1000));
		consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, new Integer(1));
		Map<String, List<KafkaStream<byte[],byte[]>>>  streamMap=consumer.createMessageStreams(topicCountMap);
		KafkaStream<byte[], byte[]> stream = streamMap.get(topic).get(0);
		ConsumerIterator<byte[], byte[]> it = stream.iterator();
		//block here
		System.out.println("接收数据.......");
		while (it.hasNext()){
			byte[] bytes = it.next().message();
			String message = new String(bytes);
			LOG.info("KafkaConsumer get data:"+message);
			kafkaConsumerService.consumeMessage(message);
		}
	}
	
	public void shutdown(){
		if(this.consumer != null){
			this.consumer.shutdown();
		}
		if(this.consumeExecutor != null && !this.consumeExecutor.isShutdown()){
			this.consumeExecutor.shutdown();
		}
	}

	public int getMinConsumerThread() {
		return minConsumerThread;
	}

	public void setMinConsumerThread(int minConsumerThread) {
		this.minConsumerThread = minConsumerThread;
	}

	public int getMaxConsumerThread() {
		return maxConsumerThread;
	}

	public void setMaxConsumerThread(int maxConsumerThread) {
		this.maxConsumerThread = maxConsumerThread;
	}

	public String getConnectstr() {
		return connectstr;
	}

	public void setConnectstr(String connectstr) {
		this.connectstr = connectstr;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/*public MessageListener getMessageListener() {
		return messageListener;
	}

	public void setMessageListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}*/
	
}
