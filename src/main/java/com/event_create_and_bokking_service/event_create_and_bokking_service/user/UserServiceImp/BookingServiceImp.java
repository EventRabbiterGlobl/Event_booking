package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserServiceImp;

import com.event_create_and_bokking_service.event_create_and_bokking_service.admin.AdminDto.TicketBookingPaymentDto;
import com.event_create_and_bokking_service.event_create_and_bokking_service.admin.AdminEntity.TicketBookingPayment;
import com.event_create_and_bokking_service.event_create_and_bokking_service.admin.AdminRepository.TicketBookingPaymentRepository;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserConfig.UserServiceDto;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity.EventSetup;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity.SetUpTheEvent;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity.UserProfile;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserRepository.EventSetUpRepository;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserRepository.SetUpTheEventRepository;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserRepository.UserProfileRepository;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserService.BookingService;
import org.apache.hc.client5.http.auth.AuthStateCacheable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class BookingServiceImp implements BookingService {



    @Autowired
    private SetUpTheEventRepository setUpTheEventRepository;



    @Autowired
    private EventSetUpRepository eventSetUpRepository;


    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TicketBookingPaymentRepository ticketBookingPaymentRepository;

    @Override
    public List<SetUpTheEvent> getBooking() {
        return setUpTheEventRepository.findAll().stream()
                .filter(data -> !data.isDeleted())
                .filter(data -> Integer.parseInt(data.getPeopleCapacityOfEvent()) != 5)
                .collect(Collectors.toList());
    }

    @Override
    public List<SetUpTheEvent> getTopNine() {
        return setUpTheEventRepository.findAll().stream()
                .filter(data -> !data.isDeleted())
                .sorted(Comparator.comparingInt(data -> Integer.parseInt(data.getPeopleCapacityOfEvent())))
                .limit(9)
                .collect(Collectors.toList());
    }


    @Override
    public SetUpTheEvent getTheEvent(String id) {
        return  setUpTheEventRepository.findById(UUID.fromString(id)).orElse(null);
    }

    @Override
    public TicketBookingPayment savePaymentDetails(TicketBookingPaymentDto ticketBookingPayment) {

        UserProfile userProfile = userProfileRepository.findById(UUID.fromString(ticketBookingPayment.getTicketBookingUser()))
                .orElse(null);
        EventSetup eventSetup=eventSetUpRepository.findById(UUID.fromString(ticketBookingPayment.getEventSetup()))
                .orElse(null);
        SetUpTheEvent setUpTheEvent=setUpTheEventRepository.findById(UUID.fromString(ticketBookingPayment.getSetUpTheEvent()))
                .orElse(null);

        TicketBookingPayment ticketBookingPayment1=new TicketBookingPayment();
        ticketBookingPayment1.setTicketBookingUser(userProfile);
        ticketBookingPayment1.setEventSetup(eventSetup);
        ticketBookingPayment1.setSetUpTheEvent(setUpTheEvent);
        ticketBookingPayment1.setNumberOfTickets(ticketBookingPayment.getNumberOfTickets());
        ticketBookingPayment1.setAmountOfTicket(String.valueOf(ticketBookingPayment.getAmountOfTicket()));
        ticketBookingPayment1.setPaymentSignature(ticketBookingPayment.getKey());
        ticketBookingPayment1.setPaymentId(ticketBookingPayment.getPaymentId());
        ticketBookingPayment1.setCurrency("INR");
        TicketBookingPayment data=ticketBookingPaymentRepository.save(ticketBookingPayment1);
        reduceTicket(data);

        return data;
    }

    @Override
    public TicketBookingPayment getEventTicket(String id) {

        TicketBookingPayment ticketBookingPayment=ticketBookingPaymentRepository.
                findById(UUID.fromString(id)).orElse(null);

        System.out.println(ticketBookingPayment);
        return ticketBookingPayment;
    }

    public List<TicketBookingPayment> getTicketList(String id) {
        return ticketBookingPaymentRepository.findByTicketBookingUser_Id(UUID.fromString(id));
    }


    private void reduceTicket(TicketBookingPayment ticketBookingPayment) {

        SetUpTheEvent setUpTheEvent = setUpTheEventRepository.findById(ticketBookingPayment.getSetUpTheEvent().getId())
                .orElse(null);

        if (setUpTheEvent != null) {
            int currentCapacity = Integer.parseInt(setUpTheEvent.getPeopleCapacityOfEvent());
            int numberOfTickets = Integer.parseInt(ticketBookingPayment.getNumberOfTickets());
            int newCapacity = currentCapacity - numberOfTickets;
            setUpTheEvent.setPeopleCapacityOfEvent(String.valueOf(newCapacity));
            setUpTheEventRepository.save(setUpTheEvent);
        }
    }



}
