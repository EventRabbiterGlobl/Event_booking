package com.event_create_and_bokking_service.event_create_and_bokking_service.admin.AdminController;


import com.event_create_and_bokking_service.event_create_and_bokking_service.admin.AdminDto.TicketBookingPaymentDto;
import com.event_create_and_bokking_service.event_create_and_bokking_service.admin.AdminService.TicketBookingPaymentService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/event-create-booking")
public class TicketBookingPaymentController {


    @Autowired
    private TicketBookingPaymentService ticketBookingPaymentService;




    @PostMapping("/createTransaction/{amount}")
    public TicketBookingPaymentDto createTransaction(@PathVariable("amount") Double amount) throws RazorpayException {
       return ticketBookingPaymentService.createTransaction(amount);
    }


}
