package com.piateam.sai.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import com.piateam.sai.bean.Operation;
import com.piateam.sai.bean.Result;
import com.piateam.sai.common.SAIException;

@Service
public class MathCalculator {
	private static final Logger logger = LoggerFactory.getLogger(MathCalculator.class);

	@KafkaListener(topics = "${kafka.request.topic}", groupId = "${kafka.group.id}")
	@SendTo
	public Result calculate(Operation operation) {
		logger.debug("[calculate()] {}... [OK]", operation);
		char operand = operation.getOperand();
		List<Double> parameters = operation.getParameters();
		Result result;
		switch (operand) {
		case '+':
			result = sum(parameters);
			break;
		case '-':
			result = sub(parameters);
			break;
		default:
			throw new SAIException("Unsupported [Operand: " + operand + "]!");
		}
		return result;
	}

	private Result sum(List<Double> parameters) {
		double sum = 0;
		for (Double parameter : parameters) {
			sum += parameter.doubleValue();
		}
		return new Result(sum);
	}

	private Result sub(List<Double> parameters) {
		double sub = parameters.get(0);
		for (int i = 1; i < parameters.size(); i++) {
			sub -= parameters.get(i);
		}
		return new Result(sub);
	}
}
