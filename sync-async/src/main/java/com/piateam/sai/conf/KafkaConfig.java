package com.piateam.sai.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

import com.piateam.sai.bean.Operation;
import com.piateam.sai.bean.Result;

@Configuration
public class KafkaConfig {
	@Value("${kafka.group.id}")
	private String	groupId;
	@Value("${kafka.reply.topic}")
	private String	replyTopic;

	@Bean
	public ReplyingKafkaTemplate<String, Operation, Result> replyingKafkaTemplate(ProducerFactory<String, Operation> producerFactory, ConcurrentKafkaListenerContainerFactory<String, Result> containerFactory) {
		ConcurrentMessageListenerContainer<String, Result> container = containerFactory.createContainer(replyTopic);
		ContainerProperties containerProperties = container.getContainerProperties();
		containerProperties.setMissingTopicsFatal(false);
		containerProperties.setGroupId(groupId);
		return new ReplyingKafkaTemplate<>(producerFactory, container);
	}

	@Bean
	public KafkaTemplate<String, Result> kafkaTemplate(ProducerFactory<String, Result> producerFactory, ConcurrentKafkaListenerContainerFactory<String, Result> containerFactory) {
		KafkaTemplate<String, Result> kafkaTemplate = new KafkaTemplate<>(producerFactory);
		containerFactory.getContainerProperties().setMissingTopicsFatal(false);
		containerFactory.setReplyTemplate(kafkaTemplate);
		return kafkaTemplate;
	}
}
