package com.piateam.sai.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LongRunningService {
	private static final Logger logger = LoggerFactory.getLogger(LongRunningService.class);

	public String execute() {
		try {
			Thread.sleep(5000);
			logger.debug("[execute()] done... [OK]");
			return "Completed";
		} catch (Exception e) {
			logger.error("[execute()] Something went wrong!!! [NOK]", e);
			throw new RuntimeException("Something went wrong!", e);
		}
	}
}
