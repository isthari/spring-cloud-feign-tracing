package com.isthari.demo.service2;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("Service3")
public interface ClientService3 {
	
	@RequestMapping(method = RequestMethod.GET, value="/" )
	public void test();
}
