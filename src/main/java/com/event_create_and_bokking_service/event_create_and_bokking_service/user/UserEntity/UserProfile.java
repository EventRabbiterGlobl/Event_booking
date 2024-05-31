package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserProfile {

    @Id
    private UUID id;
    private String firstName;
    private String secondName;
    private String username;
    private String phoneNumber;
    private String imageUrl;


}
