package com.example.bookingservice.api;

import com.example.bookingservice.dto.BookingResponse;
import com.example.bookingservice.dto.CreateBookingRequest;
import com.example.bookingservice.dto.UpdateBookingStatusRequest;
import com.example.bookingservice.service.BookingServiceBean;
import jakarta.ejb.EJB;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Context;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Path("/bookings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookingResource {

    @EJB
    private BookingServiceBean bookingServiceBean;
    @Context
    private HttpHeaders httpHeaders;

    @POST
    public BookingResponse createBooking(CreateBookingRequest request) {
        return bookingServiceBean.createBooking(request);
    }

    @GET
    // Admin-only endpoint by assignment policy (kept open for now to avoid Jakarta security refactor)
    public List<BookingResponse> getAllBookings() {
        enforceAdminBasicAuth();
        return bookingServiceBean.getAllBookings();
    }

    @GET
    @Path("/{id}")
    public BookingResponse getBookingById(@PathParam("id") Long id) {
        return bookingServiceBean.getBookingById(id);
    }

    @GET
    @Path("/customer/{customerId}")
    public List<BookingResponse> getBookingsByCustomer(@PathParam("customerId") Long customerId) {
        return bookingServiceBean.getBookingsByCustomer(customerId);
    }

    @GET
    @Path("/provider/{providerId}")
    public List<BookingResponse> getBookingsByProvider(@PathParam("providerId") Long providerId) {
        return bookingServiceBean.getBookingsByProvider(providerId);
    }

    @GET
    @Path("/provider/{providerId}/completed")
    public List<BookingResponse> getCompletedBookingsByProvider(@PathParam("providerId") Long providerId) {
        return bookingServiceBean.getCompletedBookingsByProvider(providerId);
    }

    @PUT
    @Path("/{id}/status")
    public BookingResponse updateBookingStatus(@PathParam("id") Long id, UpdateBookingStatusRequest request) {
        return bookingServiceBean.updateBookingStatus(id, request);
    }

    @GET
    @Path("/health")
    public String health() {
        return "Booking Service is working";
    }

    private void enforceAdminBasicAuth() {
        String authorization = httpHeaders.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorization == null || !authorization.startsWith("Basic ")) {
            throw new WebApplicationException("Admin authentication required", Response.Status.UNAUTHORIZED);
        }

        try {
            String base64Credentials = authorization.substring("Basic ".length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
            String[] values = credentials.split(":", 2);

            if (values.length != 2 || !"admin".equals(values[0]) || !"admin123".equals(values[1])) {
                throw new WebApplicationException("Invalid admin credentials", Response.Status.FORBIDDEN);
            }
        } catch (IllegalArgumentException ex) {
            throw new WebApplicationException("Invalid authorization header", Response.Status.UNAUTHORIZED);
        }
    }
}
