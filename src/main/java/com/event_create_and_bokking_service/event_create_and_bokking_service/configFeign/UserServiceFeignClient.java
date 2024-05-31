package com.event_create_and_bokking_service.event_create_and_bokking_service.configFeign;


import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserDto.UserProfileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@FeignClient("USER-SERVICE")
public interface UserServiceFeignClient {

    @GetMapping(value ="/api/v1/user/getAllUserDataForEventService",consumes = "application/json")
    ResponseEntity<List<Object>> getAllDataForEventService();

    @GetMapping(value = "/api/v1/user/hi",consumes = "application/json")
    public ResponseEntity<String>  hi();



}

