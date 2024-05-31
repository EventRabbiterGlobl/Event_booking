package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserRepository;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity.EventSetup;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity.SetUpTheEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SetUpTheEventRepository extends JpaRepository<SetUpTheEvent, UUID> {

    Optional<SetUpTheEvent> findByEventSetup_id(UUID id);


}
