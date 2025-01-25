package com.uexcel.apartment.controller;

import com.uexcel.apartment.dto.ApartmentDto;
import com.uexcel.apartment.dto.ConsolidatedInfoDto;
import com.uexcel.apartment.dto.ErrorResponseDto;
import com.uexcel.apartment.openfeign.executive.ExecutiveFeignClient;
import com.uexcel.apartment.openfeign.executive.ExecutiveRoom;
import com.uexcel.apartment.openfeign.regular.RegularRoom;
import com.uexcel.apartment.openfeign.regular.RegularRoomFeignClient;
import com.uexcel.apartment.service.IApartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Tag(name = "REST API to Fetch Consolidated Reservation Details.",
        description = "REST API to Fetch Apartment,Executive and Regular Room Reservation  Details.")
@AllArgsConstructor
@RestController
@RequestMapping("api/consolidated/info")
public class FeignClientController {
    private final RegularRoomFeignClient regularRoomFeignClient;
    private  final ExecutiveFeignClient executiveFeignClient;
    private  final IApartmentService iapartmentService;

    @Operation(summary = "REST API to Fetch Consolidated Reservation Details.",
            description = "REST API to Fetch Apartment,Executive and Regular Room Reservation  Details.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",description = "Ok",
                            content = @Content(schema=@Schema(implementation= ConsolidatedInfoDto.class))
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
    @GetMapping("/regular-room")
    ResponseEntity<ConsolidatedInfoDto> getRegularRooms() {
        List<ApartmentDto> apartmentDtos = iapartmentService.getApartments();
        List<RegularRoom> regularRoom =  regularRoomFeignClient.getRegularRoomDetails().getBody();
        List<ExecutiveRoom> executiveRooms = executiveFeignClient.getExecutiveRoomDetails().getBody();
        ConsolidatedInfoDto consolidatedInfoDto = ConsolidatedInfoDto.builder()
                .apartment(apartmentDtos)
                .regularRoom(regularRoom)
                .executiveRoom(executiveRooms)
                .build();
        return ResponseEntity.ok(consolidatedInfoDto);
    }
}
