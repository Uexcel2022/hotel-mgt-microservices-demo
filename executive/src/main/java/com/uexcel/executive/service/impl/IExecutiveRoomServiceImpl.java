package com.uexcel.executive.service.impl;

import com.uexcel.executive.constants.Month;
import com.uexcel.executive.dto.AvailableRoomsDto;
import com.uexcel.executive.dto.ExecutiveRoomDto;
import com.uexcel.executive.dto.ReservedRoomInFoDto;
import com.uexcel.executive.exception.AppExceptions;
import com.uexcel.executive.mapper.GetRsvByRoomNumberMapper;
import com.uexcel.executive.model.ExecutiveRoom;
import com.uexcel.executive.model.ReservationDates;
import com.uexcel.executive.persistence.ExecutiveRoomRepository;
import com.uexcel.executive.service.IExecutiveRoomService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class IExecutiveRoomServiceImpl implements IExecutiveRoomService {
    private final ExecutiveRoomRepository executiveRoomRepository;
    private final GetRsvByRoomNumberMapper rsvMapper;
    private final Month month;
    @Override
    public ReservedRoomInFoDto getExecutiveRoomByRoomNumber(String roomNumber) {
        List<ReservationDates> regularRoom =
                executiveRoomRepository.findByRoomNumberJpql(roomNumber);
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
        List<ExecutiveRoom> executiveRooms = executiveRoomRepository.findAll();
        executiveRooms.forEach(regularRoom -> {
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
        List<ExecutiveRoom> executiveRooms = executiveRoomRepository.findAll();
        executiveRooms.forEach(regularRoom -> {
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

    @Override
    public List<ExecutiveRoomDto> getExecutiveRooms() {
        List<ExecutiveRoom> executiveRooms = executiveRoomRepository.findAll();
        List<ExecutiveRoomDto> executiveRoomDtos = new ArrayList<>();
        executiveRooms.forEach(em -> {
            if(!em.getReservationDates().isEmpty()) {
                List<ReservationDates> reservationDates = em.getReservationDates();
                reservationDates.sort(Comparator.comparing(ReservationDates::getDate));
                ExecutiveRoomDto executiveRoomDto = ExecutiveRoomDto.builder()
                        .id(em.getId()).roomNumber(em.getRoomNumber())
                        .price(em.getPrice()).reservationDates(reservationDates)
                        .build();

                executiveRoomDtos.add(executiveRoomDto);
            }
        });

        return executiveRoomDtos;
    }


}
