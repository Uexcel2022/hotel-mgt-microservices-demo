package com.uexcel.regular.exception;

import com.uexcel.regular.dto.ErrorResponseDto;
import com.uexcel.regular.dto.ReservedErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.uexcel.regular.service.ICheckinService.getTime;
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> errors = new LinkedHashMap<>();
        List<String> errorsList =   ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        errors.put("errors", errorsList);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AppExceptions.class)
    public ResponseEntity<ErrorResponseDto> handleAppException(AppExceptions ex, WebRequest request) {
        return ResponseEntity.status(ex.getStatus()).body(new ErrorResponseDto(getTime(),ex.getStatus(),
                     ex.getDescription(), ex.getMessage(),request.getDescription(false))
        );
    }

    @ExceptionHandler(ReservedRoomException.class)
    public ResponseEntity<ReservedErrorResponseDto>
    handleReservedRoomException(ReservedRoomException ex, WebRequest request) {
        return ResponseEntity.status(400).body(new ReservedErrorResponseDto(getTime(),400,
                "Bad Request", ex.getMessage(),ex.getDateRooms(),request.getDescription(false))
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleExceptions(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(new ErrorResponseDto(getTime(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error", ex.getMessage(),request.getDescription(false))
        );
    }
}
