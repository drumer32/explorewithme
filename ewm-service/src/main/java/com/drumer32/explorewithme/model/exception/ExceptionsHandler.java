package com.drumer32.explorewithme.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
@RestControllerAdvice
public class ExceptionsHandler {

    /*
    400 BAD_REQUEST ("Запрос составлен с ошибкой")
    403 FORBIDDEN ("Не выполнены условия для совершения операции")
    404 NOT_FOUND
    409 CONFLICT ("Запрос приводит к нарушению целостности данных")
    500 INTERNAL_SERVER_ERROR ("Внутренняя ошибка сервера")
     */

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(final ValidationException e) {
        return ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .reason("Some errors with validation was handled")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleBadConditionException(final BadConditionException e) {
        return ApiError.builder()
                .status(HttpStatus.FORBIDDEN)
                .reason("The conditions for the operation are not met")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleAccessException(final AccessException e) {
        return ApiError.builder()
                .status(HttpStatus.FORBIDDEN)
                .reason("You have no rights for this")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictException(final ConflictException e) {
        return ApiError.builder()
                .status(HttpStatus.CONFLICT)
                .reason("Integrity constraint violation error")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleObjectNotFoundException(final ObjectNotFoundException e) {
        return ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .reason("Object not found")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleObjectAlreadyExistException(final ObjectAlreadyExistException e) {
        return ApiError.builder()
                .status(HttpStatus.FORBIDDEN)
                .reason("This object is already exist")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
