package com.piateam.sai.controller;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.DeferredResult;

import com.piateam.sai.bean.Operation;
import com.piateam.sai.bean.Result;
import com.piateam.sai.common.SAIException;

@RestController
public class BackendGate {
	//	@Autowired
	//	private TransmitService										transmitService;
	@Value("${kafka.request.topic}")
	private String												requestTopic;
	@Autowired
	private ReplyingKafkaTemplate<String, Operation, Result>	replyingKafkaTemplate;

	@PostMapping("/math")
	public DeferredResult<Result> math(@RequestBody Operation operation) {
		DeferredResult<Result> deferredResult = new DeferredResult<>();
		ProducerRecord<String, Operation> record = new ProducerRecord<String, Operation>(requestTopic, null, String.valueOf(System.currentTimeMillis()), operation);
		RequestReplyFuture<String, Operation, Result> future = replyingKafkaTemplate.sendAndReceive(record);
		future.completable().//
				thenAccept(result -> deferredResult.setResult(result.value())).//
				exceptionally(exception -> {
					deferredResult.setErrorResult(new SAIException("Failed!", exception));
					return null;
				});
		return deferredResult;
	}
	//	@GetMapping(value = "/square/{value}")
	//	public DeferredResult<Double> square(@PathParam("value") Double value) {
	//		DeferredResult<Double> deferredResult = new DeferredResult<>();
	//		CompletableFuture<Double> squareCompletableFuture = transmitService.square(value);
	//		squareCompletableFuture.//
	//				thenAccept(result -> deferredResult.setResult(result)).//
	//				exceptionally(exception -> {
	//					deferredResult.setErrorResult(new SAIException("Failed!", exception));
	//					return null;
	//				});
	//		return deferredResult;
	//	}

	@ExceptionHandler(SAIException.class)
	public final ResponseEntity<String> handle(SAIException exception, WebRequest request) {
		ResponseEntity<String> result = new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		return result;
	}
}
