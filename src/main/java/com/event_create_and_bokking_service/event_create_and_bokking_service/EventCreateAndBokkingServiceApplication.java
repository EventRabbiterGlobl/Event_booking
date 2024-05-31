package com.event_create_and_bokking_service.event_create_and_bokking_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableCaching
@EnableFeignClients
public class EventCreateAndBokkingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventCreateAndBokkingServiceApplication.class, args);
	}

}
