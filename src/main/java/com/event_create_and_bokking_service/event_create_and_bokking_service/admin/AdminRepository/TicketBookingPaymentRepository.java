package com.event_create_and_bokking_service.event_create_and_bokking_service.admin.AdminRepository;

import com.event_create_and_bokking_service.event_create_and_bokking_service.admin.AdminEntity.TicketBookingPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TicketBookingPaymentRepository extends JpaRepository<TicketBookingPayment, UUID> {

    List<TicketBookingPayment> findByTicketBookingUser_Id(UUID id);


}
