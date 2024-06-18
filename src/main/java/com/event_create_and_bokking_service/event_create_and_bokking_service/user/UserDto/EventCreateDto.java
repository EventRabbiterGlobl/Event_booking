package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserDto;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EventCreateDto {

    List<String> id;
    private String eventCreator;
    private LocalDate dateTime;

}
