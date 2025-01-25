package com.uexcel.regular.dto;

import com.uexcel.regular.model.ReservationDates;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class RegularRoomDto {
    private Long id;
    @Column(unique = true,nullable = false)
    private String roomNumber;
    private double price;
    private List<ReservationDates> reservationDates;
}
