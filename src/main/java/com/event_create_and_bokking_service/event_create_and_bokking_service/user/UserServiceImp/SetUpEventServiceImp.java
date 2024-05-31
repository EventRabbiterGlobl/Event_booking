package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserServiceImp;

import com.cloudinary.Cloudinary;
import com.event_create_and_bokking_service.event_create_and_bokking_service.cloudinaryservice.CloudinaryService;
import com.event_create_and_bokking_service.event_create_and_bokking_service.configFeign.ChatNotificationServiceFeignClient;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserDto.SetUpTheEventDto;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity.*;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserRepository.*;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserService.SetUpEventService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.hc.client5.http.auth.AuthStateCacheable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;


@Service
public class SetUpEventServiceImp implements SetUpEventService {





    @Autowired
    private SetUpTheEventRepository setUpTheEventRepository;

   @Autowired
   private UserProfileRepository userProfileRepository;


   @Autowired
   private EventSetUpRepository eventSetUpRepository;


    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private ChatNotificationServiceFeignClient chatNotificationServiceFeignClient;

    @Autowired
    private CloudinaryService cloudinaryService;


    @Autowired
    private BannerImageRepository bannerImageRepository;

    @Autowired
    private PublicEventImageRepository publicEventImageRepository;

    @Autowired
    private CardImageRepository cardImageRepository;


    @Override
    public SetUpTheEventDto saveSetUpEvent(String creatorId,
                                           SetUpTheEventDto setUpTheEventDto,
                                           MultipartFile bannerImage,
                                           MultipartFile ticketImage,
                                           MultipartFile cardImage) {

        UserProfile creator = userProfileRepository.findById(UUID.fromString(creatorId))
                .orElseThrow(() -> new EntityNotFoundException("User with id " + creatorId + " not found"));

        EventSetup eventSetup=eventSetUpRepository.findById(UUID.fromString(setUpTheEventDto.getEventSetup()))
                .orElseThrow(()-> new EntityNotFoundException(("Event SetUp not fount")));
        String eventName= setUpTheEventDto.getEventName();


        BannerImage banner=bannerImage(eventSetup,bannerImage,eventName);

        PublicEventImage publicEventTicketImage=publicEventImage(eventSetup,ticketImage,eventName);


        CardImage publicEventCardImage=cardImage(eventSetup,cardImage,eventName);

            SetUpTheEvent setUpTheEvent = modelMapper.map(setUpTheEventDto, SetUpTheEvent.class);
            setUpTheEvent.setCreatorId(creator);
            setUpTheEvent.setEventSetup(eventSetup);
            setUpTheEvent.setActivated(true);
            setUpTheEvent.setBannerImage(banner);
            setUpTheEvent.setPublicEventImage(publicEventTicketImage);
            setUpTheEvent.setCardImage(publicEventCardImage);
            SetUpTheEvent setUpTheEventDto1 = setUpTheEventRepository.save(setUpTheEvent);
            return modelMapper.map(setUpTheEventDto1, SetUpTheEventDto.class);



    }



    private BannerImage bannerImage(EventSetup eventSetup,
                                    MultipartFile bannerImage,
                                    String bannerName

    ){
        Map data = cloudinaryService.upload(bannerImage,bannerName);
        String imageUrl = (String) data.get("secure_url");
        String publicId = (String) data.get("public_id");

        BannerImage bannerImageData=BannerImage
                .builder()
                .bannerImage(imageUrl)
                .bannerImagePublicId(publicId)
                .build();

        return bannerImageRepository.save(bannerImageData);
    }


    private PublicEventImage publicEventImage(EventSetup eventSetup,
                                              MultipartFile bannerImage,
                                              String publicImageName){

        Map data = cloudinaryService.upload(bannerImage,publicImageName);
        String imageUrl = (String) data.get("secure_url");
        String publicId = (String) data.get("public_id");

        PublicEventImage publicEventImage=PublicEventImage
                .builder()
                .imagePublicId(publicId)
                .imageName(publicImageName)
                .imageUrl(imageUrl)
                .build();
        return publicEventImageRepository.save(publicEventImage);
    }

    private CardImage cardImage(EventSetup eventSetup,
                                              MultipartFile bannerImage,
                                              String publicImageName){

        Map data = cloudinaryService.upload(bannerImage,publicImageName);
        String imageUrl = (String) data.get("secure_url");
        String publicId = (String) data.get("public_id");

        CardImage publicEventImage=CardImage
                .builder()
                .imagePublicId(publicId)
                .imageName(publicImageName)
                .imageUrl(imageUrl)
                .build();
        return cardImageRepository.save(publicEventImage);
    }




}
