package org.jboss.amq.sbamqsample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Producer {

	private final Logger logger = LoggerFactory.getLogger(Producer.class);

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Scheduled(fixedRate = 15000)
	public void sendMessage() throws Exception {
		String msg = "Hello World!";
		
		this.jmsTemplate.convertAndSend("sampleQueue", msg);
		
		logger.info("Messages were sent to the Queue");		
	}

}
