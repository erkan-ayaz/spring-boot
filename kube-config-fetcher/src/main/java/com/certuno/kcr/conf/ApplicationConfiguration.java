package com.certuno.kcr.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
  @Value("${spring.application.name}")
  private String module;
  @Value("${application.dynamic.one}")
  private String one;
  @Value("${application.dynamic.two}")
  private String two;

  public String getModule() {
    return module;
  }

  public String getOne() {
    return one;
  }

  public String getTwo() {
    return two;
  }


}
