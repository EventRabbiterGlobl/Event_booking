package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserService;

import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserDto.*;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity.EventCreate;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity.SetUpTheEvent;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


public interface EventCreateService {

    ResponseEntity<List<Object>> storeUserDataFromExternalService();

    List<EventCreateDto> createEvent(EventCreateDto eventCreateDto);

    List<EventCreate> lisOFEventSpecificPerson(String id);
    List<EventCreate> lisOfEventSpecificCreator(String id);
    List<EventCreate> listOfEventSetup(String setupId);

    Map<Integer, List<EventCreate>> findAllAndGroupByEventSetup(String creatorId);

    EventCreate saveEventConformationDto( String eventId,ConformationDto conformationDto);

    UserProfileDto saveUserData(UserProfileDto userProfileDto);


    SetUpTheEventDto setUpEventDto(String creatorId,SetUpTheEventDto eventCreateDto);

    List<String> getUserProfile( DataDto localDateTime);


    Boolean isEventCreate(String setupId);







}
