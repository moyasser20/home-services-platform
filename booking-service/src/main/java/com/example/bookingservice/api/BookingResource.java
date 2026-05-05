package com.example.bookingservice.api;

import com.example.bookingservice.dto.BookingResponse;
import com.example.bookingservice.dto.CreateBookingRequest;
import com.example.bookingservice.service.BookingServiceBean;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/bookings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookingResource {

    @EJB
    private BookingServiceBean bookingServiceBean;

    @POST
    public BookingResponse createBooking(CreateBookingRequest request) {
        return bookingServiceBean.createBooking(request);
    }

    @GET
    public List<BookingResponse> getAllBookings() {
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
    @Path("/health")
    public String health() {
        return "Booking Service is working";
    }
}
