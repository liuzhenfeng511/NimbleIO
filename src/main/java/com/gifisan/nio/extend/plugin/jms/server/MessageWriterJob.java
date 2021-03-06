package com.gifisan.nio.extend.plugin.jms.server;


import com.gifisan.nio.common.Logger;
import com.gifisan.nio.common.LoggerFactory;
import com.gifisan.nio.extend.plugin.jms.Message;

public class MessageWriterJob implements Runnable {

	private static Logger		logger	= LoggerFactory.getLogger(MessageWriterJob.class);
	private Consumer			consumer	;
	private Message			message	;
	private MQContext			context	;

	public MessageWriterJob(MQContext context, Consumer consumer,  Message message) {
		this.context = context;
		this.consumer = consumer;
		this.message = message;
	}

	public void run() {
		try {
			
			consumer.push(message);
			
			context.consumerMessage(message);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// 回炉
			context.offerMessage(message);

		}

	}

}
