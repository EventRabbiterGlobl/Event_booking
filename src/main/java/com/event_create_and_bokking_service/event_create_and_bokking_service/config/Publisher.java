package com.event_create_and_bokking_service.event_create_and_bokking_service.config;


import com.event_create_and_bokking_service.event_create_and_bokking_service.user.UserDto.UserProfileDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
@Slf4j
public class Publisher {

    @Value("${stream.key:demo-key}")
    private String streamKey;

    private final RedisTemplate<String,Object> redisTemplate;
    private final ObjectMapper objectMapper;


    private final AtomicInteger atomicInteger= new AtomicInteger(0);

    public void streamPublisher(String message){
        try{
            this.redisTemplate.opsForStream().createGroup(streamKey,streamKey);
        } catch (RedisSystemException e) {
            String cause= String.valueOf(e.getCause());
            if (cause!=null){
                log.info("stream- redis group already exist , skipping redis group creation: {}",streamKey);
            }
        }
        ObjectRecord<String,String> record =StreamRecords.newRecord().ofObject(message)
                .withStreamKey(streamKey);
        this.redisTemplate.opsForStream().add(record);
        atomicInteger.incrementAndGet();
    }

    public  String getValue(String key){
        return (String) redisTemplate.opsForValue().get(key);
    }


    public void saveTestData(String key, String data) {
        redisTemplate.opsForValue().set(key, data,1, TimeUnit.MINUTES );
    }


    public void saveList(List<UserProfileDto> list){
        String key= UUID .randomUUID().toString();
        redisTemplate.opsForValue().set(key,list,1,TimeUnit.MINUTES);
    }

    public void saveUserProfiles(List<UserProfileDto> userProfileDtos) {
        String hashKey = "user_profiles";
        int expirationTimeMinutes = 10;
        for (UserProfileDto userProfileDto : userProfileDtos) {
            try {
                String fieldKey = String.valueOf(userProfileDto.getId());
                String json = objectMapper.writeValueAsString(userProfileDto);
                redisTemplate.opsForHash().put(hashKey, fieldKey, json);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        redisTemplate.expire(hashKey, expirationTimeMinutes, TimeUnit.MINUTES);
    }




}
