package com.isthari.spring.cloud.feign.tracing;

import java.net.InetAddress;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@Scope(value="request", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class WebHandlerInterceptor  implements HandlerInterceptor  {
	private static Log logger = LogFactory.getLog(WebHandlerInterceptor.class);
	
	public static final String REQUEST_TRACE_UUID="REQUEST-TRACE-UUID";
	public static final String REQUEST_TRACE_HOP="REQUEST-TRACE-HOP";
	
	@Value("${spring.application.name}")	
	private String applicationName;
	
	@Autowired
	private TracingHandler tracingHandler;
	
	private UUID uuid;
	private Date date;
	private long nanoseconds;
	private String hop;
	private AtomicLong nextHop=new AtomicLong(0L);
		
//	public WebHandlerInterceptor(){
//		System.out.println("creado");
//	}
	
	public long getLatency(){
		long now = System.nanoTime();
		return now - this.nanoseconds;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		logger.debug("preHandle init");
		
		// OBTENER EL ID DE SOLICITUD
		// SI NO EXISTE ID DE SOLICITUD GENERAR UN NUEVO
		// GUARDARLO EN EL RequestHeaderHolder
		String uuid = request.getHeader(REQUEST_TRACE_UUID);
		String hop = request.getHeader(REQUEST_TRACE_HOP);
		
		if (uuid==null){
			this.uuid = UUIDGenerator.createTimeUUID();
		}else {
			this.uuid = UUID.fromString(uuid);
		}
		
		this.date = new Date();
		this.nanoseconds = System.nanoTime();
		if (hop==null){
			this.hop="1";
		}else {
			this.hop=hop;
		}													
		return true;
	}
	
	public UUID getUuid(){
		return this.uuid;
	}
	
	public String getNextHop(){
		return this.hop+"-"+this.nextHop.incrementAndGet();
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		logger.debug("post handle ");				
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		int latency = (int) this.getLatency();
		
		// colocar en una cola para insertarlo en cassandra
		logger.debug("after completion "+getUuid()+" hop: "+hop+" time: "+getLatency());				
		
		TracingRequest tracingRequest = new TracingRequest();
		tracingRequest.setEurekaClient(applicationName);
		tracingRequest.setHop(this.hop);
		tracingRequest.setLatency(latency);
		tracingRequest.setPath(request.getServletPath());
		tracingRequest.setRequest(this.uuid);
		tracingRequest.setServer(InetAddress.getLocalHost().getHostName());
		tracingRequest.setTimestamp(date);
		tracingRequest.setStatus(response.getStatus());
		
		tracingHandler.insertTrace(tracingRequest);
		
		logger.debug("fin");		
	}
	
	
}
