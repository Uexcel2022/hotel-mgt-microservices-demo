package com.uexcel.apartment.openfeign.executive;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "executive", fallback = ExecutiveFeignClientFallBack.class)
public interface ExecutiveFeignClient {
    @GetMapping("/api/executive/rooms")
    ResponseEntity<List<ExecutiveRoom>> getExecutiveRoomDetails();
}
