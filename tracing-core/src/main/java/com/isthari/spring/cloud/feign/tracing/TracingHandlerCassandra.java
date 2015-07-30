package com.isthari.spring.cloud.feign.tracing;

import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;

@Component
public class TracingHandlerCassandra implements TracingHandler {
	private static Log logger = LogFactory.getLog(TracingHandlerCassandra.class);
	
	@Override
	@HystrixCommand(fallbackMethod="fallback")
	public Future<Void> insertTrace() {
		return new AsyncResult<Void>() {
			@Override
			public Void invoke() {
				try {
					logger.info("durmiendo");
					Thread.sleep(100000);
					logger.info("despierto");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
				return null;
			}
		};	
	}
	
	public Future<Void> fallback(){
		return null;
	}
	
}
