package net.openurp.shcmusic.controller;

import jakarta.servlet.Filter;
import net.openurp.shcmusic.filter.BasicHttpAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

  @Autowired
  private BasicHttpAuthenticationFilter basicHttpAuthenticationFilter;

  @Bean
  public FilterRegistrationBean basicAuth() {
    FilterRegistrationBean auth = new FilterRegistrationBean();
    auth.setFilter((Filter) basicHttpAuthenticationFilter);
    auth.setName("basic auth");
    auth.addUrlPatterns(new String[]{"/*"});
    return auth;
  }
}
