package com.uexcel.apartment.openfeign.executive;

import com.uexcel.apartment.openfeign.ReservationDates;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ExecutiveRoom {
    private Long id;
    @Column(unique = true,nullable = false)
    private String roomNumber;
    private double price;
    private List<ReservationDates> reservationDates;
}
