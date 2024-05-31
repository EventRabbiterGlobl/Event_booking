package com.event_create_and_bokking_service.event_create_and_bokking_service.admin.AdminEntity;

import com.event_create_and_bokking_service.event_create_and_bokking_service.baseEntity.BaseEntity;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity.EventSetup;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity.SetUpTheEvent;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity.UserProfile;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class TicketBookingPayment extends BaseEntity {


    @ManyToOne
    private UserProfile ticketBookingUser;

    private String numberOfTickets;
    private String amountOfTicket;
    private String paymentId;
    private String currency;
    private String paymentSignature;



    @ManyToOne
    private SetUpTheEvent setUpTheEvent;

    @ManyToOne
    private EventSetup eventSetup;









}
