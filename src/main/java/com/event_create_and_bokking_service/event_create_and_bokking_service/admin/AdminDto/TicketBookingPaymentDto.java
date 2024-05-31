package com.event_create_and_bokking_service.event_create_and_bokking_service.admin.AdminDto;


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
@Builder
public class TicketBookingPaymentDto{


    private String numberOfTickets;
    private Integer amountOfTicket;
    private String paymentId;
    private String currency;

    private String key;
    private String setUpTheEvent;
    private String eventSetup;
    private String ticketBookingUser;






}
