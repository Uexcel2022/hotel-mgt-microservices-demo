package com.uexcel.regular.mapper;

import com.uexcel.regular.dto.CheckinRequestDto;
import com.uexcel.regular.dto.CheckinResponseDto;
import com.uexcel.regular.model.Checkin;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.uexcel.regular.service.ICheckinService.getTime;

@Component
public class CheckinMapper {
    public Checkin toCheckin(Checkin checkin, CheckinRequestDto checkinRequestDto) {
        checkin.setName(checkinRequestDto.getName());
        checkin.setPhone(checkinRequestDto.getPhone());
        checkin.setNumberOfDays(checkinRequestDto.getNumberOfDays());
        checkin.setDateIn(getTime());
        return checkin;
    }

    public CheckinResponseDto toCheckinResponseDto(Checkin checkin, CheckinResponseDto checkinResponseDto) {
        checkinResponseDto.setId(checkin.getId());
        checkinResponseDto.setRoomNumber(checkin.getRegularRoom().getRoomNumber());
        checkinResponseDto.setDateIn(checkin.getDateIn());
        checkinResponseDto.setDateOut(checkin.getDateOut());
        checkinResponseDto.setName(checkin.getName());
        checkinResponseDto.setPhone(checkin.getPhone());
        checkinResponseDto.setAmount(checkin.getAmount());
        checkinResponseDto.setNumberOfDays(checkin.getNumberOfDays());
        return checkinResponseDto;
    }
}
