package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity;


import com.event_create_and_bokking_service.event_create_and_bokking_service.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class EventCreate extends BaseEntity {


    @ManyToOne
    private UserProfile eventCreator;

    @ManyToOne
    private UserProfile eventPerformers;

    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;

    private LocalDate dateTime;

    @ManyToOne
    private EventSetup eventSetup;





}
