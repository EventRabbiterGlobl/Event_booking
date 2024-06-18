package com.event_create_and_bokking_service.event_create_and_bokking_service.admin.AdminService;


import com.event_create_and_bokking_service.event_create_and_bokking_service.admin.AdminDto.TicketBookingPaymentDto;
import com.event_create_and_bokking_service.event_create_and_bokking_service.admin.AdminEntity.TicketBookingPayment;
import com.razorpay.RazorpayException;

import java.util.List;

public interface TicketBookingPaymentService {


    TicketBookingPaymentDto savePaymentDetails(TicketBookingPaymentDto data);


    TicketBookingPaymentDto createTransaction(Double amount) throws RazorpayException;

    List< TicketBookingPayment> getTicketBookingData(String creatorId);





}
