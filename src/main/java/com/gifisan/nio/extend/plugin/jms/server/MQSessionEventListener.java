package com.gifisan.nio.extend.plugin.jms.server;

import com.gifisan.nio.common.Logger;
import com.gifisan.nio.common.LoggerFactory;
import com.gifisan.nio.component.SEListenerAdapter;
import com.gifisan.nio.component.Session;

public class MQSessionEventListener extends SEListenerAdapter {

	private static final Logger	LOGGER	= LoggerFactory.getLogger(MQSessionEventListener.class);

	public void sessionOpened(Session session) {
		
		MQContext context = MQContext.getInstance();

		MQSessionAttachment attachment = context.getSessionAttachment(session);

		if (attachment == null) {

			attachment = new MQSessionAttachment(context);

			session.setAttachment(context, attachment);
		}
	}

	// FIXME 移除该EndPoint上的consumer
	public void sessionClosed(Session session) {
		
		MQContext context = MQContext.getInstance();

		MQSessionAttachment attachment = context.getSessionAttachment(session);
		
		if (attachment == null) {
			return;
		}

		TransactionSection section = attachment.getTransactionSection();

		if (section != null) {

			section.rollback();
		}

		Consumer consumer = attachment.getConsumer();

		if (consumer != null) {

			consumer.getConsumerQueue().remove(consumer);

			consumer.getConsumerQueue().getSnapshot();

			context.removeReceiver(consumer.getQueueName());
		}

		LOGGER.debug(">>>> TransactionProtectListener execute");

	}

}
