package com.isthari.spring.cloud.feign.tracing;

import java.util.Date;
import java.util.UUID;

public class TracingRequest {
	private UUID request;
	private String hop;
	private String eurekaClient;
	private int latency;
	private String path;
	private String server;
	private Date timestamp;
	private int status;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public UUID getRequest() {
		return request;
	}
	public void setRequest(UUID request) {
		this.request = request;
	}
	public String getHop() {
		return hop;
	}
	public void setHop(String hop) {
		this.hop = hop;
	}
	public String getEurekaClient() {
		return eurekaClient;
	}
	public void setEurekaClient(String eurekaClient) {
		this.eurekaClient = eurekaClient;
	}
	public int getLatency() {
		return latency;
	}
	public void setLatency(int latency) {
		this.latency = latency;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
}
