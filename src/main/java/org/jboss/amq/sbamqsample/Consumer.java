package org.jboss.amq.sbamqsample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
	
	private final Logger logger = LoggerFactory.getLogger(Consumer.class);
	
	@JmsListener(destination = "sampleQueue")
    public void receiveMessage(String msg) {
        logger.info("Received <" + msg + ">");
    }	

}
