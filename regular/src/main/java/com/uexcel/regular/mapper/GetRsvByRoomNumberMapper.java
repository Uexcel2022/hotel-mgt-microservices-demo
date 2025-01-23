package com.uexcel.regular.mapper;

import com.uexcel.regular.dto.ReservationDatesDto;
import com.uexcel.regular.dto.ReservationDetailsDto;
import com.uexcel.regular.dto.ReservationInfoDto;
import com.uexcel.regular.dto.ReservedRoomInFoDto;
import com.uexcel.regular.model.RegularRoom;
import com.uexcel.regular.model.Reservation;
import com.uexcel.regular.model.ReservationDates;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetRsvByRoomNumberMapper {
    /*
         mapping reservation dates fetched by room number to ReservedRoomInFoDto
    */
    public ReservedRoomInFoDto toReservedRoomInFoDto(
            List<ReservationDates> reservationDates, ReservedRoomInFoDto reservedRoomInFoDto) {
            reservationDates.forEach(reservationDate -> {
                Reservation rsv = reservationDate.getReservation();
                RegularRoom room = reservationDate.getRegularRoom();
                reservedRoomInFoDto.setRoomNumber(room.getRoomNumber());
                reservedRoomInFoDto.setPrice(room.getPrice());
                reservedRoomInFoDto.setId(room.getId());
                ReservationInfoDto rsvInfo = new
                        ReservationInfoDto(rsv.getId(),rsv.getName(),rsv.getPhone(),rsv.getDescription());
                ReservationDatesDto rsvDates =
                        new ReservationDatesDto(reservationDate.getId(),reservationDate.getDate());
                reservedRoomInFoDto.getReservationDetails()
                        .add(new ReservationDetailsDto(rsvDates,rsvInfo));
            });
            return reservedRoomInFoDto;
    }
}
