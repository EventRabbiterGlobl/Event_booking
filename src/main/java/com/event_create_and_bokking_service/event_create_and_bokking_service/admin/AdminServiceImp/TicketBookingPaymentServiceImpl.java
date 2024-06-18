package com.event_create_and_bokking_service.event_create_and_bokking_service.admin.AdminServiceImp;

import com.event_create_and_bokking_service.event_create_and_bokking_service.admin.AdminDto.TicketBookingPaymentDto;
import com.event_create_and_bokking_service.event_create_and_bokking_service.admin.AdminEntity.TicketBookingPayment;
import com.event_create_and_bokking_service.event_create_and_bokking_service.admin.AdminRepository.TicketBookingPaymentRepository;
import com.event_create_and_bokking_service.event_create_and_bokking_service.admin.AdminService.TicketBookingPaymentService;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity.EventCreate;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity.UserProfile;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserRepository.EventCreateRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.dataloader.Try;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class TicketBookingPaymentServiceImpl implements TicketBookingPaymentService {


    private static final String KEY="rzp_test_Vv4K0xZB1OflgS";
    private static final String KEY_SECURITY="t8dvwBA9ChCi6fjQRTs8cpCJ";
    private static final String CURRENCY="INR";


    @Autowired
    private EventCreateRepository eventCreateRepository;


    @Autowired
    private TicketBookingPaymentRepository ticketBookingPaymentRepository;




    @Override
    public TicketBookingPaymentDto savePaymentDetails(TicketBookingPaymentDto data) {
        return null;
    }

    @Override
    public TicketBookingPaymentDto createTransaction(Double amount) throws RazorpayException {

        try{

            JSONObject jsonObject=new JSONObject();
            jsonObject.put("amount",(amount*100));
            jsonObject.put("currency",CURRENCY);

            RazorpayClient razorpayClient=new RazorpayClient(KEY,KEY_SECURITY);
            Order order= razorpayClient.orders.create(jsonObject);


           return transactionDetails(order);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }


        return null;

    }

    @Override
    public List <TicketBookingPayment> getTicketBookingData(String creatorId) {
        return ticketBookingPaymentRepository.findBySetUpTheEvent_CreatorId_Id
                (UUID.fromString(creatorId));
    }



    private TicketBookingPaymentDto transactionDetails(Order order){
        String orderId=order.get("id").toString();
        String currency=order.get("currency").toString();
        Integer amount=order.get("amount");
        TicketBookingPaymentDto ticketBookingPaymentDto=new TicketBookingPaymentDto();
        ticketBookingPaymentDto.setPaymentId(orderId);
        ticketBookingPaymentDto.setCurrency(currency);
        ticketBookingPaymentDto.setAmountOfTicket(amount);
        ticketBookingPaymentDto.setKey(KEY);
        return ticketBookingPaymentDto;
    }






}
