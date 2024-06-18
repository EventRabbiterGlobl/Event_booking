package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserController;


import com.event_create_and_bokking_service.event_create_and_bokking_service.admin.AdminEntity.TicketBookingPayment;
import com.event_create_and_bokking_service.event_create_and_bokking_service.admin.AdminService.TicketBookingPaymentService;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity.SetUpTheEvent;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserService.BookingService;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserService.SetUpEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/event-create-booking")
public class ReportController {

    @Autowired
    private BookingService bookingService;


    @Autowired
    private TicketBookingPaymentService ticketBookingPaymentService;


    @Autowired
    private SetUpEventService setUpEventService;



    @GetMapping("/all-booking-data/{creatorId}")
    public ResponseEntity<List<TicketBookingPayment>> getAllBookingData(@PathVariable String creatorId) {
        List<TicketBookingPayment> bookingData = ticketBookingPaymentService.getTicketBookingData(creatorId);
        return ResponseEntity.ok().body(bookingData);
    }

    @GetMapping("/get-all-events-report/{creatorId}")
    public ResponseEntity<List<SetUpTheEvent>> getAllEventsReport(@PathVariable String creatorId){
        return ResponseEntity.ok().body(setUpEventService.getEventDetails(creatorId));
    }





}
