package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserDto;


import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageDto {
    private String imageUrl;
}
