package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserRepository;

import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity.CardImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CardImageRepository extends JpaRepository<CardImage, UUID> {
}
