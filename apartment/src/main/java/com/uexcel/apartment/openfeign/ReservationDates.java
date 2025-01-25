package com.uexcel.apartment.openfeign;
import com.uexcel.apartment.model.Reservation;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter @Setter
public class ReservationDates {
    private String id;
    private LocalDate date;
    private Reservation reservation;
}
