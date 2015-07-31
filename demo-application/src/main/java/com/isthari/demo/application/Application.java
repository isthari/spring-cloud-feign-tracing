package com.isthari.demo.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableAutoConfiguration
@EnableEurekaClient
@RestController
@EnableDiscoveryClient
//@EnableHystrix
@EnableFeignClients
@EnableCircuitBreaker
public class Application {
	
	@Autowired
	private ClientService1 clientService1;
	
	@Autowired
	private ClientService2 clientService2;
	
	@RequestMapping("/")
	public String home() {
		clientService1.test();
		clientService2.test();
		
		return "Hello World";
//		throw new RuntimeException("error");
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

