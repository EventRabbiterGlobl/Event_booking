package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserConfig;

import lombok.*;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserServiceDto {



    private UUID id;
    private String firstName;
    private String secondName;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String otp;
    private String bio;
    private String description;
    private boolean profileVerification;
    private String listOfCategoryId;


    private double location;
    private double locationLongitude;
    private String nameOfPlace;
    private Long seatNumber;



    private List<String> names;

}
