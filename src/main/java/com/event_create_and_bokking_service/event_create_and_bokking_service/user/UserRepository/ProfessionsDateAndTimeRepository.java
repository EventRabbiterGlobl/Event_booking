package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserRepository;

import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity.ProfessionsDateAndTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ProfessionsDateAndTimeRepository extends JpaRepository<ProfessionsDateAndTime,UUID> {

    List<ProfessionsDateAndTime> findByDateTime(LocalDate localDateTime);




}
