package com.uexcel.apartment.dto;

import com.uexcel.apartment.openfeign.executive.ExecutiveRoom;
import com.uexcel.apartment.openfeign.regular.RegularRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class ConsolidatedInfoDto {
    private List<ApartmentDto> apartment;
    private List<ExecutiveRoom> executiveRoom;
    private List<RegularRoom> regularRoom;
}
