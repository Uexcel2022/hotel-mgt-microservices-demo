package com.uexcel.regular.service;

import com.uexcel.regular.dto.FreeRoomsDto;
import com.uexcel.regular.dto.ReservationDto;
import com.uexcel.regular.dto.ReservationResponseDto;
import com.uexcel.regular.dto.ResponseDto;

import java.util.List;

public interface IReservationService {
    List<FreeRoomsDto> getFreeRoomsByMonth(String monthNAme);
    List<FreeRoomsDto> getFreeRoomsByDays(Integer days);
    ReservationResponseDto saveReservation(ReservationDto reservationDto);
    ResponseDto deletePastReservations();
}
