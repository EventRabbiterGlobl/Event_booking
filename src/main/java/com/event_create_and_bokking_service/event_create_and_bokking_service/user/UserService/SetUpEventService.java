package com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserService;

import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserDto.SetUpTheEventDto;
import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserEntity.BannerImage;
import org.springframework.web.multipart.MultipartFile;

public interface SetUpEventService {


    SetUpTheEventDto saveSetUpEvent(String creatorId,
                                    SetUpTheEventDto setUpTheEventDto,
                                    MultipartFile bannerImage,
                                    MultipartFile ticketImage,
                                    MultipartFile cardImage);




}
