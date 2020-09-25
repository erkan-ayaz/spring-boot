package com.piateam.sai.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.piateam.sai.service.LongRunningService;

@RestController
public class APIController {
	@Autowired
	private LongRunningService service;

	@GetMapping("/blocking")
	public String blocking() {
		return service.execute();
	}

	@GetMapping("/deferred")
	public DeferredResult<String> deferred() {
		DeferredResult<String> deferred = new DeferredResult<>();
		/*
		ForkJoinPool.commonPool().submit(() -> {
			deferred.setResult(service.execute());
		});
		*/
		CompletableFuture.supplyAsync(service::execute).whenComplete((result, throwable) -> deferred.setResult(result));
		return deferred;
	}

	@GetMapping("/callable")
	public Callable<String> callable() {
		Callable<String> result = service::execute;
		return result;
	}
}
