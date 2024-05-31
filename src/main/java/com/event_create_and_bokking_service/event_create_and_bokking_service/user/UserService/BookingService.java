package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserService;

import com.event_create_and_bokking_service.event_create_and_bokking_service.admin.AdminDto.TicketBookingPaymentDto;
import com.event_create_and_bokking_service.event_create_and_bokking_service.admin.AdminEntity.TicketBookingPayment;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity.SetUpTheEvent;

import java.util.List;
import java.util.Optional;


public interface BookingService {


   List< SetUpTheEvent> getBooking();

   List<SetUpTheEvent> getTopNine();

   SetUpTheEvent getTheEvent(String id);


   TicketBookingPayment savePaymentDetails(TicketBookingPaymentDto ticketBookingPayment);

   TicketBookingPayment getEventTicket(String Id);

   List<TicketBookingPayment> getTicketList(String id);


}
