package com.isthari.demo.application;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("Service1")
public interface ClientService1 {
	
	@RequestMapping(method = RequestMethod.GET, value="/" )
	public void test();
}
