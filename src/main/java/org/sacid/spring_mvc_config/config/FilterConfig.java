package org.sacid.spring_mvc_config.config;

import javax.servlet.Filter;
import org.sacid.spring_mvc_config.filter.ContentCachingRequestWrapperFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<Filter> getReusableRequestFilterRegistration() {
      var registrationBean = new FilterRegistrationBean<Filter>(new ContentCachingRequestWrapperFilter());
      registrationBean.addUrlPatterns("*");
      registrationBean.setName("contentCachingRequestWrapperFilter");
      return registrationBean;
    }
}
