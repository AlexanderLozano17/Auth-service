package com.authservice.controller.advice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.authservice.response.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
	        Map<String, String> errors = new HashMap<>();
	        ex.getBindingResult().getAllErrors().forEach(error -> {
	            String fieldName = ((FieldError) error).getField();
	            String errorMessage = error.getDefaultMessage();
	            errors.put(fieldName, errorMessage);
	        });

	        ErrorResponse errorResponse = ErrorResponse.builder()
	            .timestamp(LocalDateTime.now())
	            .status(HttpStatus.BAD_REQUEST.value())
	            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
	            .message("Error de validación en la solicitud.")
	            .path(request.getDescription(false).replace("uri=", "")) // Obtiene la URI de la solicitud
	            .details(errors)
	            .build();

	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	    }

	    @ExceptionHandler(Exception.class)
	    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	    public ResponseEntity<ErrorResponse> handleAllUncaughtException(Exception ex, WebRequest request) {
	        // Loguea la excepción completa para depuración (usa @Slf4j en esta clase)
	        // log.error("Uncaught exception: " + ex.getMessage(), ex);

	        ErrorResponse errorResponse = ErrorResponse.builder()
	            .timestamp(LocalDateTime.now())
	            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
	            .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
	            .message("Ha ocurrido un error interno en el servidor. Por favor, inténtelo de nuevo más tarde.")
	            .path(request.getDescription(false).replace("uri=", ""))
	            .build();

	        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
}
