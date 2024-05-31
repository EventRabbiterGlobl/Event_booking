package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserRepository;

import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity.EventCreate;
import com.netflix.eventbus.spi.EventCreator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EventCreateRepository extends JpaRepository<EventCreate, UUID> {


    List<EventCreate> findByEventPerformers_Id(UUID eventPerformersId);


    List<EventCreate> findByEventSetupId(UUID eventSetupId);


    List<EventCreate> findByEventCreator_Id(UUID eventCreatorId);


}
