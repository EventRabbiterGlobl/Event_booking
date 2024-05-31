package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserDto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventNotificationDto {

    private String eventPerformers;
    private String creatorId;
    private String eventSetup;

}