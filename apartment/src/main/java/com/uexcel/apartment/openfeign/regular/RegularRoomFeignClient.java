package com.uexcel.apartment.openfeign.regular;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name= "regular" , fallback = RegularRoomFallBack.class)
public interface RegularRoomFeignClient {
    @GetMapping("/api/regular/rooms")
    ResponseEntity<List<RegularRoom>> getRegularRoomDetails();
}
