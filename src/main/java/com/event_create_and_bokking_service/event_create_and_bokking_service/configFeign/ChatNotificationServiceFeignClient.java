package com.event_create_and_bokking_service.event_create_and_bokking_service.configFeign;


import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserDto.EventNotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("CHAT-NOTIFICATION-SERVICE")
public interface ChatNotificationServiceFeignClient {
    @PostMapping(value = "api/v1/notification-chat/set/event/notification" ,consumes = "application/json")
    ResponseEntity<EventNotificationDto> setEventNotification(EventNotificationDto eventNotificationDto);


    @GetMapping(value = "api/v1/notification-chat/notification/status" ,consumes = "application/json")
    ResponseEntity<Boolean> conformation(@PathVariable("userId") String userID);




}
