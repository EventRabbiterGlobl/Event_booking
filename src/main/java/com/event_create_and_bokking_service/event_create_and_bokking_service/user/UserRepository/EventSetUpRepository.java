package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserRepository;

import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity.EventCreate;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity.EventSetup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventSetUpRepository extends JpaRepository<EventSetup, UUID> {
    List<EventSetup> findByEventCreatorID(UUID evenCreatorId);


}
