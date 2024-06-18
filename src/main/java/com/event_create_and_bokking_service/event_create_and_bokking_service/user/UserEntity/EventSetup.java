package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity;

import com.event_create_and_bokking_service.event_create_and_bokking_service.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString

public class EventSetup extends BaseEntity {
    private UUID eventCreatorID;




}
