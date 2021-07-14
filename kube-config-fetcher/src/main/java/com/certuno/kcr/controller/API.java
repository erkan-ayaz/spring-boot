package com.certuno.kcr.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.certuno.kcr.conf.ApplicationConfiguration;

@RestController
public class API {
  private static final Logger logger = LoggerFactory.getLogger(API.class);
  @Autowired
  private ApplicationConfiguration configuration;

  @GetMapping("/one")
  public String one() {
    logger.debug("[one()] called... [OK]");
    return configuration.getOne();
  }

  @GetMapping("/two")
  public String two() {
    logger.debug("[two()] called... [OK]");
    return configuration.getTwo();
  }

  @GetMapping("/module")
  public String module() {
    logger.info("[module()] called... [OK]");
    return configuration.getModule();
  }

}
