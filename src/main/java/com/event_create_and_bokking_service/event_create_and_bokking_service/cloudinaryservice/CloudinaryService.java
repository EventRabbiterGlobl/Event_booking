package com.event_create_and_bokking_service.event_create_and_bokking_service.cloudinaryservice;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CloudinaryService {
    Map upload(MultipartFile file, String folder);

    Map<String, Object> uploadVideo(MultipartFile file, String folder);
}
