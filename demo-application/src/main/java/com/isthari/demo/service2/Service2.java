package com.isthari.demo.service2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableAutoConfiguration
@EnableEurekaClient
@RestController
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients
public class Service2 {
	
	@Autowired
	private ClientService3 clientService3;
	@Autowired
	private ClientService4 clientService4;
	
	@RequestMapping("/")
	public String home() {
		clientService3.test();
		clientService4.test();
		
		return "Hello World";
	}

	public static void main(String[] args) {
		SpringApplication.run(Service2.class, args);
	}

}

