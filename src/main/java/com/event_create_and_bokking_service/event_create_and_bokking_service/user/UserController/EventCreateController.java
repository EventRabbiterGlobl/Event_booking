package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserController;


import com.event_create_and_bokking_service.event_create_and_bokking_service.config.Publisher;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserDto.*;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity.EventCreate;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserService.EventCreateService;
import com.event_create_and_bokking_service.event_create_and_bokking_service.configFeign.UserServiceFeignClient;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserService.SetUpEventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

    @RestController
    @RequestMapping("api/v1/event-create-booking")
public class EventCreateController {

    @Autowired
    private EventCreateService eventCreateService;


    @Autowired
    private SetUpEventService setUpEventService;


    @Autowired
    private Publisher publisher;



    @Autowired
    private UserServiceFeignClient userServiceFeignClient;


    @GetMapping("/test")
    public ResponseEntity<String> testHi(){
        return userServiceFeignClient.hi();
    }

    @PostMapping("/getUserData")
    public ResponseEntity<List<Object>> getDataInUser() {
        return eventCreateService.storeUserDataFromExternalService();
    }
    @PostMapping("/getUserSpecificDate")
    public ResponseEntity<List<String>> getUserInSpecificData(
                @RequestBody DataDto dataDto
    ) {
        return ResponseEntity.ok().body(
                    Collections.singletonList(eventCreateService.getUserProfile(dataDto).toString())
        );
    }

    @PostMapping("/getUserDataInAuthService")
    public ResponseEntity<UserProfileDto> saveUserData(@RequestBody UserProfileDto userProfileDto){
        return ResponseEntity.ok().body(eventCreateService.saveUserData(userProfileDto));
    }


    @PostMapping("/select/event-create")
    public ResponseEntity<List<EventCreateDto>> createEvent(@RequestBody EventCreateDto eventProfileData) {
        List<EventCreateDto> createdEvents = eventCreateService.createEvent(eventProfileData);
        return ResponseEntity.ok().body(createdEvents);
    }







    @GetMapping("/get/event/list/{id}")
    public ResponseEntity<List<EventCreate>> eventList(@PathVariable String id){
        return  ResponseEntity.ok().body(eventCreateService.lisOFEventSpecificPerson(id));
    }


    @GetMapping("/get/event/create/details/{id}")
    public ResponseEntity<List<EventCreate>> eventCreate(@PathVariable String id){
        return ResponseEntity.ok().body(eventCreateService.listOfEventSetup(id));
    }


    @PostMapping("/set/event/status/{eventId}")
    public ResponseEntity<EventCreate> setTheStatus(@PathVariable String eventId,@RequestBody ConformationDto status){
        return ResponseEntity.ok().body(eventCreateService.saveEventConformationDto(eventId,status));
    }


    @PostMapping("/setup/event")
    public ResponseEntity<SetUpTheEventDto> setEvent(@RequestParam("creatorId") String creatorId,
                                                     @RequestParam("setUpTheEventDto") String setUpTheEventDto,
                                                     @RequestParam("bannerImage") MultipartFile bannerImage,
                                                     @RequestParam("ticketImage") MultipartFile ticketImage,
                                                     @RequestParam("cardImage") MultipartFile cardImage
    ) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
        SetUpTheEventDto setUpTheEventDto1=objectMapper.readValue(setUpTheEventDto, SetUpTheEventDto.class);
            return ResponseEntity.ok().body(setUpEventService.saveSetUpEvent(
                    creatorId,
                    setUpTheEventDto1,
                    bannerImage,
                    ticketImage,
                    cardImage));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    @GetMapping("/get/event/byGroup/{creatorId}")
    public ResponseEntity<Map<Integer, List<EventCreate>>> getLikGroup(@PathVariable String creatorId) {
        Map<Integer, List<EventCreate>> groupedEvents = eventCreateService.findAllAndGroupByEventSetup(creatorId);
        System.out.println(groupedEvents);
        return ResponseEntity.ok().body(groupedEvents);
    }


    @GetMapping("/isCreated/event/{id}")
    public ResponseEntity<Boolean> isCreateEvent(@PathVariable String id){
        return ResponseEntity.ok().body(eventCreateService.isEventCreate(id));

    }

}
