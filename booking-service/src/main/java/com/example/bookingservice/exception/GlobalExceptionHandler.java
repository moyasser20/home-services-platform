package com.example.bookingservice.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<RuntimeException> {
    @Override
    public Response toResponse(RuntimeException exception) {
        if (exception instanceof BookingNotFoundException) {
            return build(Response.Status.NOT_FOUND, exception.getMessage());
        }
        if (exception instanceof InsufficientBalanceException) {
            return build(Response.Status.BAD_REQUEST, exception.getMessage());
        }
        if (exception instanceof OfferUnavailableException) {
            return build(Response.Status.BAD_REQUEST, exception.getMessage());
        }
        return build(Response.Status.BAD_REQUEST, exception.getMessage());
    }

    private Response build(Response.Status status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("message", message);
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }
}
