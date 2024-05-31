package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity;


import com.event_create_and_bokking_service.event_create_and_bokking_service.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SetUpTheEvent extends BaseEntity {

    private String eventName;
    private String placeName;
    private String peopleCapacityOfEvent;
    private String priceOfBasieTicket;
    private String priceOfDeluxeTicket;
    private String priceOfPremiumTicket;
    private Double locationLatitude;
    private Double locationLongitude;

    @ManyToOne
    private UserProfile creatorId;

    @OneToOne
    private EventSetup eventSetup;


    @ManyToOne
    private BannerImage bannerImage;

    @ManyToOne
    private PublicEventImage publicEventImage;


    @ManyToOne
    private CardImage cardImage;




}
