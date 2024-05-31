package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserDto;


import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity.EventStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConformationDto {

    private String eventCreator;
    private String eventPerformers;
    private LocalDateTime localDateTime;

    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;
}
