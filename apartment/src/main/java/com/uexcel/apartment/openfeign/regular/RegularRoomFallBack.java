package com.uexcel.apartment.openfeign.regular;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class RegularRoomFallBack implements RegularRoomFeignClient {
    @Override
    public ResponseEntity<List<RegularRoom>> getRegularRoomDetails() {
        return null;
    }
}
