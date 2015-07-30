package com.isthari.spring.cloud.feign.tracing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
@ComponentScan(basePackages="com.isthari.spring.cloud.feign.tracing")
public class WebHandlerInterceptorFactory extends WebMvcConfigurerAdapter {
	
	@Autowired 
	private WebHandlerInterceptor interceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {		
		registry.addInterceptor(interceptor);
	}		
}
