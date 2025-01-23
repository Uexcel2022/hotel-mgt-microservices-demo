package com.uexcel.regular.service.impl;

import com.uexcel.regular.constants.Month;
import com.uexcel.regular.dto.AvailableRoomsDto;
import com.uexcel.regular.dto.ReservedRoomInFoDto;
import com.uexcel.regular.exception.AppExceptions;
import com.uexcel.regular.mapper.GetRsvByRoomNumberMapper;
import com.uexcel.regular.model.RegularRoom;
import com.uexcel.regular.model.ReservationDates;
import com.uexcel.regular.persistence.RegularRoomRepository;
import com.uexcel.regular.service.IRegularRoomService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class IRegularRoomServiceImpl implements IRegularRoomService {
    private final RegularRoomRepository regularRoomRepository;
    private final GetRsvByRoomNumberMapper rsvMapper;
    private final Month month;
    @Override
    public ReservedRoomInFoDto getRegularRoomByRoomNumber(String roomNumber) {
        List<ReservationDates> regularRoom =
                regularRoomRepository.findByRoomNumberJpql(roomNumber);
           return   rsvMapper.toReservedRoomInFoDto(regularRoom,new ReservedRoomInFoDto());
    }

    @Override
    public Map<String, List<AvailableRoomsDto>> getFreeRoomsByDays(Integer numberOfDays) {
        List<AvailableRoomsDto> availableRoomsDto = new ArrayList<>();
        List<LocalDate> dates = new ArrayList<>();
        if (numberOfDays == null) {
            numberOfDays = 7;
        }
       /*
            Creating list of dates based on the desired number of days
        */
        for (int i = 0; i < numberOfDays; i++) {
            dates.add(LocalDate.now().plusDays(i));
        }
        dates.sort(Comparator.naturalOrder());
        List<RegularRoom> regularRooms = regularRoomRepository.findAll();
        regularRooms.forEach(regularRoom -> {
            List<ReservationDates> reservationDates = regularRoom.getReservationDates();
            if (reservationDates.isEmpty()) {
                for (LocalDate localDate : dates) {
                    availableRoomsDto.add(new AvailableRoomsDto(localDate,
                            regularRoom.getRoomNumber()));
                }
            }
            if(!regularRoom.getReservationDates().isEmpty()) {
                for (LocalDate localDate : dates) {
                    if (regularRoom.getReservationDates().stream().noneMatch(v -> v.getDate().equals(localDate)) ) {
                        availableRoomsDto.add(new AvailableRoomsDto(localDate,
                                regularRoom.getRoomNumber()));
                    }

                }
            }
        });

        availableRoomsDto.sort(Comparator.comparing(AvailableRoomsDto::getDate));

             return   availableRoomsDto.stream().
                     collect(groupingBy(AvailableRoomsDto::getRoom, LinkedHashMap::new,toList()));
    }

    @Override
    public Map<String, List<AvailableRoomsDto>> getFreeRoomsByMonth(String monthName) {
        List<LocalDate> dates = new ArrayList<>();
        List<AvailableRoomsDto> availableRoomsDto = new ArrayList<>();
        LocalDate monthDate;
        if (monthName == null) {
            monthDate = month.getStartDate(String.valueOf(LocalDate.now().getMonth()));
        }else {
            monthDate = month.getStartDate(monthName.toUpperCase());
        }
        LocalDate now = LocalDate.now();
        if(monthDate.getMonth().equals("DECEMBER") && monthName != null
                && monthName.equalsIgnoreCase("JANUARY")){
            monthDate =  LocalDate.of(now.getYear()+1,1,1);
        }
        if(monthDate.getDayOfYear() < now.getDayOfYear() && monthDate.getYear()==now.getYear()
                && ! monthDate.getMonth().equals(now.getMonth())){
            throw new AppExceptions(HttpStatus.BAD_REQUEST.value(),
                    "Bad Request","The desired month is not available."
            );
        }
       /*
            Creating list of dates based on the desired  month
        */
        if(monthDate.getMonth().equals(LocalDate.now().getMonth()) && monthDate.getYear()==LocalDate.now().getYear()){
            for (int i = 0; i < LocalDate.now().lengthOfMonth(); i++) {
                if(monthDate.plusDays(i).isAfter(LocalDate.now())||monthDate.plusDays(i).equals(LocalDate.now())) {
                    dates.add(monthDate.plusDays(i));
                }
            }
        } else {
            for (int i = 0; i < monthDate.lengthOfMonth(); i++) {
                dates.add(monthDate.plusDays(i));
            }
        }
        dates.sort(Comparator.naturalOrder());
        List<RegularRoom> regularRooms = regularRoomRepository.findAll();
        regularRooms.forEach(regularRoom -> {
            List<ReservationDates> reservationDates = regularRoom.getReservationDates();
            if (reservationDates.isEmpty() ) {
                for (LocalDate localDate : dates) {
                    availableRoomsDto.add(new AvailableRoomsDto(localDate,
                            regularRoom.getRoomNumber()));
                }
            }
            if(!regularRoom.getReservationDates().isEmpty()) {
                for (LocalDate localDate : dates) {
                    if (regularRoom.getReservationDates().stream().noneMatch(v -> v.getDate().equals(localDate)) ) {
                        availableRoomsDto.add(new AvailableRoomsDto(localDate,
                                regularRoom.getRoomNumber()));
                    }
                }
            }
        });

        availableRoomsDto.sort(Comparator.comparing(AvailableRoomsDto::getDate));

           return   availableRoomsDto.stream()
                   .collect(groupingBy(AvailableRoomsDto::getRoom, LinkedHashMap::new,toList()));

    }



}
