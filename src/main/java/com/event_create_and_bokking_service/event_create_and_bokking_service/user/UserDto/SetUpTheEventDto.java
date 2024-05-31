package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserDto;


import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity.EventSetup;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetUpTheEventDto {

    private String eventName;
    private String placeName;
    private String peopleCapacityOfEvent;
    private String priceOfBasieTicket;
    private String priceOfDeluxeTicket;
    private String priceOfPremiumTicket;
    private Double locationLatitude;
    private Double locationLongitude;
    private String creatorId;

    private String eventSetup;




}
