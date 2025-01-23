package com.uexcel.regular.controller;

import com.uexcel.regular.dto.ErrorResponseDto;
import com.uexcel.regular.dto.FreeRoomsDto;
import com.uexcel.regular.dto.ReservedRoomInFoDto;
import com.uexcel.regular.exception.AppExceptions;
import com.uexcel.regular.service.IRegularRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "REST APIs to Fetch Regular Rooms  Information.",
        description = "REST APIs to Fetch Regular Rooms Consolidated  Information.")
@RestController
@AllArgsConstructor
@RequestMapping("/api/regular")
public class RegularRoomController {
    private final IRegularRoomService regularRoomService;

    @Operation(summary = "REST APIs to Fetch Regular Rooms  Information.",
            description = "REST APIs to Fetch Regular Rooms  Information.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",description = "Ok",
                            content = @Content(schema=@Schema(implementation= ReservedRoomInFoDto.class))
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
    @GetMapping("reservation/room")
    public ResponseEntity<ReservedRoomInFoDto> getRegularRoomById(
            @RequestParam String roomNumber) {
        ReservedRoomInFoDto regularRoom =
                regularRoomService.getRegularRoomByRoomNumber(roomNumber);
        if (regularRoom.getId()==null) {
            throw new AppExceptions(HttpStatus.NOT_FOUND.value(),
                    "Not Found","No reservation found for room number: " + roomNumber
            );
        }
        return new ResponseEntity<>(regularRoom, HttpStatus.OK);
    }
}
