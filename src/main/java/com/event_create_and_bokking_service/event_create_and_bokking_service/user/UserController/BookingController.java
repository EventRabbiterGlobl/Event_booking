package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserController;


import com.event_create_and_bokking_service.event_create_and_bokking_service.admin.AdminDto.TicketBookingPaymentDto;
import com.event_create_and_bokking_service.event_create_and_bokking_service.admin.AdminEntity.TicketBookingPayment;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity.SetUpTheEvent;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserService.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/event-create-booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;


    @GetMapping("/get/event/card")
    public ResponseEntity<List<SetUpTheEvent>> getEventCards(){
        return ResponseEntity.ok().body(bookingService.getBooking());
    }

    @GetMapping("/get-top-nine")
    public ResponseEntity<List<SetUpTheEvent>> getTopNine(){
        return ResponseEntity.ok().body(bookingService.getTopNine());
    }


    @GetMapping("/get/event/for/booking/{id}")
    public ResponseEntity<SetUpTheEvent>getEvent(@PathVariable String id){
        return ResponseEntity.ok().body(bookingService.getTheEvent(id));
    }

    @GetMapping("/get/ticket/{id}")
    public ResponseEntity<TicketBookingPayment> getTicket(@PathVariable String id){
        return ResponseEntity.ok().body(bookingService.getEventTicket(id));
    }

    @PostMapping("/payment/details")
    public ResponseEntity<TicketBookingPayment> paymentDetails(@RequestBody TicketBookingPaymentDto ticketBookingPayment){
        return ResponseEntity.ok().body(bookingService.savePaymentDetails(ticketBookingPayment));
    }


    @GetMapping("get-list-of/ticket/{id}")
    public ResponseEntity<List<TicketBookingPayment>> lisOfTicket(@PathVariable String id){
        return  ResponseEntity.ok().body(bookingService.getTicketList(id));
    }



}
