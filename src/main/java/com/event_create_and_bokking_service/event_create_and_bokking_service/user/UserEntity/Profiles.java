package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Profiles")
public class Profiles {

    private UUID id;
    private String username;

}
