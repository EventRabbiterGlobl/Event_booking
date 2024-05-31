package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity;


import com.event_create_and_bokking_service.event_create_and_bokking_service.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CardImage  extends BaseEntity {

    private String imageName;
    private String imageUrl;
    private String imagePublicId;
}
