package com.isthari.demo.application;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("Service2")
public interface ClientService2 {
	
	@RequestMapping(method = RequestMethod.GET, value="/" )
	public void test();
}
