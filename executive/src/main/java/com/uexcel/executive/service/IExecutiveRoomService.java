package com.uexcel.executive.service;

import com.uexcel.executive.dto.AvailableRoomsDto;
import com.uexcel.executive.dto.ExecutiveRoomDto;
import com.uexcel.executive.dto.ReservedRoomInFoDto;

import java.util.List;
import java.util.Map;

public interface IExecutiveRoomService {
    ReservedRoomInFoDto getExecutiveRoomByRoomNumber(String roomNumber);
    Map<String, List<AvailableRoomsDto>> getFreeRoomsByDays(Integer numberOfDays);
    Map<String,List<AvailableRoomsDto>> getFreeRoomsByMonth(String monthName);
    List<ExecutiveRoomDto> getExecutiveRooms();
}
