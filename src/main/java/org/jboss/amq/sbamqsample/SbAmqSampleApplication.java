package org.jboss.amq.sbamqsample;

import java.util.concurrent.Executor;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@SpringBootApplication
@EnableJms
@EnableScheduling
public class SbAmqSampleApplication {

	@Value("${amq.broker.url}")
	private String amqBrokerUrl;

	@Value("${amq.security.user}")
	private String username;

	@Value("${amq.security.pass}")
	private String password;

	/* Beans to use ActiveMQ */

	@Bean
	public ConnectionFactory connectionFactory() {
		// Create a ConnectionFactory
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(amqBrokerUrl);

		connectionFactory.setUserName(username);
		connectionFactory.setPassword(password);

		return connectionFactory;
	}

	@Bean
	public JmsListenerContainerFactory<?> amqBrokerFactory(ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		// This provides all boot's default to this factory, including the message
		configurer.configure(factory, connectionFactory);
		// You could still override some of Boot's default if necessary.
		return factory;
	}

	/* Beans to use Scheduling */
	@Bean
	public TaskScheduler taskScheduler() {
		return new ConcurrentTaskScheduler();
	}

	@Bean
	public Executor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}
	
	/* SpringBoot Application */

	public static void main(String[] args) {
		SpringApplication.run(SbAmqSampleApplication.class, args);
	}

}
