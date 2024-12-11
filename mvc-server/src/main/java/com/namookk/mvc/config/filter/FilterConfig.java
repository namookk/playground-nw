package com.namookk.mvc.config.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

  @Bean
  public FilterRegistrationBean<AuthenticationFilter> authenticationFilter(){
    FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new AuthenticationFilter());
    registrationBean.addUrlPatterns("/users/*"); // 특정 URL 패턴에만 적용
    return registrationBean;
  }
}
