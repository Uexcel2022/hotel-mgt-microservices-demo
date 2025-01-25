package com.uexcel.apartment.openfeign.executive;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ExecutiveFeignClientFallBack implements ExecutiveFeignClient  {
    @Override
    public ResponseEntity<List<ExecutiveRoom>> getExecutiveRoomDetails() {
        return null;
    }
}
