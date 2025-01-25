package com.uexcel.apartment.dto;



import com.uexcel.apartment.openfeign.ReservationDates;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Builder
@Getter
@Setter
public class ApartmentDto {
    private Long id;
    private String apartmentCode;
    private double price;
    private List<ReservationDates> reservationDates;
}
