package com.uexcel.executive.dto;

import com.uexcel.executive.model.ReservationDates;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Builder
@Getter
@Setter
public class ExecutiveRoomDto {
    private Long id;
    @Column(unique = true,nullable = false)
    private String roomNumber;
    private double price;
    private List<ReservationDates> reservationDates;
}
