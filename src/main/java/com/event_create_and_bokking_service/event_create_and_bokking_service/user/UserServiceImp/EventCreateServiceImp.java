package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserServiceImp;

import com.event_create_and_bokking_service.event_create_and_bokking_service.configFeign.ChatNotificationServiceFeignClient;
import com.event_create_and_bokking_service.event_create_and_bokking_service.exception.EventNotFoundException;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserDto.*;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity.*;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserRepository.*;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserService.EventCreateService;
import com.event_create_and_bokking_service.event_create_and_bokking_service.configFeign.UserServiceFeignClient;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
public class EventCreateServiceImp implements EventCreateService {



    @Autowired
    private EventCreateRepository eventCreateRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private UserServiceFeignClient userServiceFeignClient;

    @Autowired
    private SetUpTheEventRepository setUpTheEventRepository;


    @Autowired
    private EventSetUpRepository eventSetUpRepository;



    @Autowired
    private ChatNotificationServiceFeignClient chatNotificationServiceFeignClient;
    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private ProfessionsDateAndTimeRepository professionsDateAndTimeRepository;



    public ResponseEntity<List<Object>> storeUserDataFromExternalService() {
        ResponseEntity<List<Object>> responseEntity = userServiceFeignClient.getAllDataForEventService();
        if (responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
            List<Object> userProfileDtoList = responseEntity.getBody();
            List<Object> userProfileList = new ArrayList<>();

            for (Object userProfileDto : userProfileDtoList) {
                UserProfile userProfile = modelMapper.map(userProfileDto, UserProfile.class);
                userProfileList.add(userProfile);
            }
            return ResponseEntity.ok(userProfileList);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }





    @Override
    public List<EventCreateDto> createEvent(EventCreateDto eventCreateDto) {
        EventSetup eventSetup=setupEvent(eventCreateDto.getEventCreator());
        UserProfile creator=creatorData(eventCreateDto.getEventCreator());



        List<UserProfile> eventPerformersId = performersData(eventCreateDto.getId());
        if (eventPerformersId == null) {
            return Collections.emptyList();
        }


        List<EventCreate> eventCreateData = eventPerformersId.stream()
                .map(data -> {
                    EventCreate eventCreate = new EventCreate();
                    eventCreate.setEventSetup(eventSetup);
                    eventCreate.setEventPerformers(data);
                    eventCreate.setEventCreator(creator);
                    eventCreate.setDateTime(eventCreateDto.getDateTime());
                    eventCreate.setEventStatus(EventStatus.REQUEST_SEND);
                    return eventCreate;
                })
                .collect(Collectors.toList());

        List<EventCreate> savedEventCreates = eventCreateRepository.saveAll(eventCreateData);





        List<EventCreateDto> savedEventCreateDtos = savedEventCreates.stream()
                .map(eventCreate -> modelMapper.map(eventCreate, EventCreateDto.class))
                .collect(Collectors.toList());

        savedEventCreates.forEach(eventCreate -> {
            saveDate(eventCreate.getEventPerformers(), eventCreateDto.getDateTime(), eventSetup, eventCreate);
        });



        savedEventCreates.forEach(eventCreateDtos -> {
            EventNotificationDto eventNotificationDto = EventNotificationDto.builder()
                    .eventPerformers(String.valueOf(eventCreateDtos.getEventPerformers().getId()))
                    .creatorId(String.valueOf(eventCreateDtos.getEventCreator().getId()))
                    .eventSetup(String.valueOf(eventCreateDtos.getEventSetup().getId()))
                    .build();

            ResponseEntity<EventNotificationDto> response = chatNotificationServiceFeignClient
                    .setEventNotification
                    (eventNotificationDto);

        });



        return savedEventCreateDtos;
    }

    private EventSetup setupEvent(String eventCreatorId){
        EventSetup eventSetup=EventSetup.builder()
                .eventCreatorID(UUID.fromString(eventCreatorId))
                .build();
        return eventSetUpRepository.save(eventSetup);
    }

    public UserProfile creatorData(String creatorData) {
        return userProfileRepository.findById(UUID.fromString(creatorData)).orElse(null);
    }
    private List<UserProfile> performersData(List<String> performersId) {
        return performersId.stream()
                .map(data -> userProfileRepository.findById(UUID.fromString(data)).orElse(null))
                .collect(Collectors.toList());
    }


    private void saveDate(UserProfile profession, LocalDate date, EventSetup eventSetup, EventCreate event) {
        ProfessionsDateAndTime professionsDateAndTime = ProfessionsDateAndTime.builder()
                .dateTime(date)
                .userProfile(profession)
                .eventCreate(event)
                .eventSetup(eventSetup)
                .build();
        professionsDateAndTimeRepository.save(professionsDateAndTime);
    }







    @Override
    public List<EventCreate> lisOFEventSpecificPerson(String id) {
        return eventCreateRepository.findByEventPerformers_Id(UUID.fromString(id));
    }

    @Override
    public List<EventCreate> lisOfEventSpecificCreator(String id) {
        Optional<List<EventCreate>> listOfEventForCreator=Optional
                .ofNullable(eventCreateRepository.findByEventCreator_Id(UUID.fromString(id)));
        return listOfEventForCreator.orElseGet(Collections::emptyList)
                .stream()
                .filter(data->!data.isDeleted())
                .collect(Collectors.toList());
    }

    @Override
    public List<EventCreate> listOfEventSetup(String setupId) {
        Optional<List<EventCreate>> optionalEventSetups = Optional.ofNullable(eventCreateRepository.findByEventSetupId
                (UUID.fromString(setupId)));
        return optionalEventSetups.orElseGet(Collections::emptyList)
                .stream()
                .filter(data -> !data.isDeleted())
                .collect(Collectors.toList());
    }

    @Override
    public Map<Integer, List<EventCreate>> findAllAndGroupByEventSetup(String creatorId) {

        List<EventCreate> eventCreates = eventCreateRepository.findByEventCreator_Id(UUID.fromString(creatorId));


        eventCreates.sort(Comparator.comparing(eventCreate -> eventCreate.getEventSetup().getId()));


        Map<EventSetup, List<EventCreate>> groupedByEventSetup = eventCreates.stream()
                .collect(Collectors.groupingBy(EventCreate::getEventSetup, LinkedHashMap::new, Collectors.toList()));


        Map<Integer, List<EventCreate>> result = new LinkedHashMap<>();
        AtomicInteger counter = new AtomicInteger(1);


        groupedByEventSetup.forEach((eventSetup, eventCreatesList) -> {
            result.put(counter.getAndIncrement(), eventCreatesList);
        });

        return result;
    }



    @Override
    public EventCreate  saveEventConformationDto(String eventId,ConformationDto conformationDto ){
        Optional<EventCreate> getEvent=eventCreateRepository.findById(UUID.fromString(eventId));
        if (getEvent.isPresent()){
            EventCreate create=getEvent.get();
            create.setEventStatus(conformationDto.getEventStatus());
            return  eventCreateRepository.save(create);
        }else {
            throw new EventNotFoundException("Event with ID " + eventId + " not found");
        }
    }

    @Override
    public UserProfileDto saveUserData(UserProfileDto userProfileDto) {
        UserProfile userProfile=modelMapper.map(userProfileDto,UserProfile.class);
        userProfile.setId(userProfile.getId());
        userProfile=userProfileRepository.save(userProfile);
        return modelMapper.map(userProfile,UserProfileDto.class);
    }

    @Override
    public SetUpTheEventDto setUpEventDto(String creatorId, SetUpTheEventDto setUpTheEventDto) {
        SetUpTheEvent setUpTheEvent = modelMapper.map(setUpTheEventDto, SetUpTheEvent.class);
        if (creatorId != null) {
            UserProfile creator = userProfileRepository.findById(UUID.fromString(creatorId))
                    .orElseThrow(() -> new EntityNotFoundException("UserProfile with id " + creatorId + " not found"));
            setUpTheEvent.setCreatorId(creator);
        }
        return modelMapper.map(setUpTheEvent, SetUpTheEventDto.class);
    }

    @Override
    public List<String> getUserProfile(DataDto localDateTime) {
        List<ProfessionsDateAndTime> listOfUsers = professionsDateAndTimeRepository.findByDateTime(localDateTime.getLocalDate());
        System.out.println(localDateTime+"----------");

        return listOfUsers.stream()
                .filter(user -> user != null && localDateTime.getLocalDate().equals(user.getDateTime()))
                .peek(user -> System.out.println("Filtered user: " + user))
                .map(ProfessionsDateAndTime::getUserProfile)
                .filter(Objects::nonNull)
                .peek(userProfile -> System.out.println("User profile: " + userProfile))
                .map(userProfile -> userProfile.getId().toString())
                .distinct()
                .peek(id -> System.out.println("User profile ID: " + id))
                .collect(Collectors.toList());
    }




    @Override
    public  Boolean isEventCreate (String setupId) {
        Optional<SetUpTheEvent> eventCreate=setUpTheEventRepository.findByEventSetup_id(UUID.fromString(setupId));
        if (eventCreate.isPresent()){
            System.out.println(false);
            return false;
        }else {
            System.out.println(true);
            return true;
        }


    }


}
