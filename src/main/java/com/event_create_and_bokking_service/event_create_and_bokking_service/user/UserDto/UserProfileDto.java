package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserDto;


import lombok.*;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDto {

    private UUID id;
    private String firstName;
    private String secondName;
    private String username;
    private String phoneNumber;



}
