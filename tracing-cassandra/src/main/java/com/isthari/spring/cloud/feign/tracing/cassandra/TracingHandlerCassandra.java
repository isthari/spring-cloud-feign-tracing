package com.isthari.spring.cloud.feign.tracing.cassandra;

import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.isthari.spring.cloud.feign.tracing.TracingHandler;
import com.isthari.spring.cloud.feign.tracing.TracingRequest;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;

@Component
public class TracingHandlerCassandra implements TracingHandler, InitializingBean {
	private static Log logger = LogFactory.getLog(TracingHandlerCassandra.class);
	
	private Session session;
	private PreparedStatement statement;
	
	@Value("${isthari.spring.cloud.feign.tracing.cassandra.createKeyspace:true}")
	private boolean createKeyspace;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
								
		if (createKeyspace){
			logger.info("Creating cassandra keyspace");
			try (Session tempSession = cluster.connect();){
				tempSession.execute("CREATE KEYSPACE if NOT EXISTS isthari_tracing WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1}");
				tempSession.execute("use isthari_tracing");
				tempSession.execute("CREATE TABLE if NOT EXISTS tracing_request (request timeuuid , hop text , eureka_client text , path text , latency int, timestamp timestamp, server text, status int, primary key (request, hop))");
			}			
		}
		
		session = cluster.connect("isthari_tracing");
		statement = session.prepare("insert into tracing_request (request,hop,eureka_client,path,latency,timestamp,server,status) values (?,?,?,?,?,?,?,?)");
	}
	
	@Override	
	@HystrixCommand(fallbackMethod="fallback")
	public Future<Void> insertTrace(final TracingRequest tracingRequest) {
		return new AsyncResult<Void>() {
			@Override
			public Void invoke() {
				try {
					session.executeAsync(statement.bind(
							tracingRequest.getRequest(),
							tracingRequest.getHop(),
							tracingRequest.getEurekaClient(), 
							tracingRequest.getPath(),
							tracingRequest.getLatency(),
							tracingRequest.getTimestamp(),
							tracingRequest.getServer(),
							tracingRequest.getStatus()));
				}catch(Throwable e){
					logger.error("error cassandra", e);
				}
				return null;
			}
		};	
	}
	
	public Future<Void> fallback(final TracingRequest tracingRequest){
		return null;
	}

	
	
}
