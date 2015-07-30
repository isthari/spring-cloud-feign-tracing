package com.isthari.spring.cloud.feign.tracing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class RestInterceptor implements RequestInterceptor {		
	@Autowired
	private WebHandlerInterceptor interceptor;
	
	@Override
	public void apply(RequestTemplate template) {
		template.header(WebHandlerInterceptor.REQUEST_TRACE_UUID, interceptor.getUuid().toString());
		template.header(WebHandlerInterceptor.REQUEST_TRACE_HOP, interceptor.getNextHop());
	}

}
