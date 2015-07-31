package com.isthari.spring.cloud.feign.tracing;

import java.util.concurrent.Future;



public interface TracingHandler {
	
	public Future<Void> insertTrace(TracingRequest tracingRequest);
}
