package com.uexcel.regular.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Schema(name ="AvailableRoom",description = "This Schema will hold available room on a given date.")
@Getter @Setter
@AllArgsConstructor
public class AvailableRoomsDto {
    private LocalDate date;
    private String room;
}
