package com.uexcel.regular.controller;

import com.uexcel.regular.dto.AvailableRoomsDto;
import com.uexcel.regular.dto.ErrorResponseDto;
import com.uexcel.regular.service.IRegularRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@Tag(name = "REST APIs For Regular Rooms  Reservation.",
        description = "The REST APIs For Regular Rooms  Reservation CRUD operations.")
@RestController
@RequestMapping("/api/free")
@AllArgsConstructor
public class ExperimentController {
    private final IRegularRoomService regularRoomService;

    @Operation(summary = "The API to Search For Regular Room Free Dates in Days.",
            description = "The API to Search For Regular Room Free Days Can Be Filtered by Desired number of days.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",description = "Ok",
                            content = @Content(schema=@Schema(implementation= AvailableRoomsDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",description = "Bad Request",
                            content = @Content(schema=@Schema(implementation= ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",description = "Not Found",
                            content = @Content(schema=@Schema(implementation=ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",description = "Internal Server Error",
                            content = @Content(schema=@Schema(implementation= ErrorResponseDto.class))
                    )
            }

    )
    @GetMapping("/room/num-of-days")
    public ResponseEntity<Map<String,List<AvailableRoomsDto>>> getFreeRoomsByDays(
            @RequestParam(required = false) Integer numberOfDays) {
        Map<String,List<AvailableRoomsDto>> sortedResult  =
                regularRoomService.getFreeRoomsByDays(numberOfDays);
        return new ResponseEntity<>(sortedResult, HttpStatus.OK);
    }

    @Operation(summary = "The API to Search For Regular Room Free Dates in Month.",
            description = "The API to Search For Regular Room Free Days Can Be Filtered by Desired Month.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",description = "Ok",
                            content = @Content(schema=@Schema(implementation= AvailableRoomsDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",description = "Bad Request",
                            content = @Content(schema=@Schema(implementation= ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",description = "Not Found",
                            content = @Content(schema=@Schema(implementation=ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",description = "Internal Server Error",
                            content = @Content(schema=@Schema(implementation= ErrorResponseDto.class))
                    )
            }

    )

    @GetMapping("/room/month")
    public ResponseEntity<Map<String,List<AvailableRoomsDto>>> getFreeRoomsByMonth(
            @RequestParam(required = false) String monthName) {
        Map<String,List<AvailableRoomsDto>> sortedResult =
                regularRoomService.getFreeRoomsByMonth(monthName);
        return new ResponseEntity<>(sortedResult, HttpStatus.OK);
    }

}
