package com.example.bookingservice.service;

import com.example.bookingservice.client.OfferClient;
import com.example.bookingservice.client.UserClient;
import com.example.bookingservice.dto.BookingResponse;
import com.example.bookingservice.dto.CreateBookingRequest;
import com.example.bookingservice.dto.OfferDetailsResponse;
import com.example.bookingservice.dto.UpdateBookingStatusRequest;
import com.example.bookingservice.dto.UserSummaryResponse;
import com.example.bookingservice.entity.BookingEntity;
import com.example.bookingservice.exception.BookingNotFoundException;
import com.example.bookingservice.exception.InsufficientBalanceException;
import com.example.bookingservice.repository.BookingRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class BookingServiceBean {

    @EJB
    private BookingRepository bookingRepository;

    @EJB
    private OfferClient offerClient;

    @EJB
    private UserClient userClient;

    @EJB
    private BookingValidationService bookingValidationService;

    public BookingResponse createBooking(CreateBookingRequest request) {
        bookingValidationService.validateCreateRequest(request);

        OfferDetailsResponse offer = offerClient.getOfferById(request.getOfferId());
        if (!offer.isActive()) {
            throw new RuntimeException("Selected offer is not active");
        }

        boolean deducted = userClient.deductBalance(request.getCustomerId(), offer.getPrice());
        if (!deducted) {
            throw new InsufficientBalanceException("Insufficient balance to complete booking");
        }

        BookingEntity booking = new BookingEntity();
        booking.setCustomerId(request.getCustomerId());
        booking.setOfferId(offer.getId());
        booking.setProviderId(offer.getProviderId());
        booking.setCategory(offer.getCategory());
        booking.setPrice(offer.getPrice());
        booking.setStatus("CONFIRMED");
        booking.setCreatedAt(LocalDateTime.now());

        BookingEntity saved = bookingRepository.save(booking);
        return toResponse(saved);
    }

    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public BookingResponse getBookingById(Long id) {
        BookingEntity booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + id));
        return toResponse(booking);
    }

    public List<BookingResponse> getBookingsByCustomer(Long customerId) {
        return bookingRepository.findByCustomerId(customerId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<BookingResponse> getBookingsByProvider(Long providerId) {
        return bookingRepository.findByProviderId(providerId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<BookingResponse> getCompletedBookingsByProvider(Long providerId) {
        return bookingRepository.findByProviderIdAndStatus(providerId, "COMPLETED")
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public BookingResponse updateBookingStatus(Long bookingId, UpdateBookingStatusRequest request) {
        if (request == null || request.getStatus() == null || request.getStatus().isBlank()) {
            throw new RuntimeException("status is required");
        }

        BookingEntity booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + bookingId));
        booking.setStatus(request.getStatus().trim().toUpperCase());
        BookingEntity updated = bookingRepository.save(booking);
        return toResponse(updated);
    }

    private BookingResponse toResponse(BookingEntity booking) {
        UserSummaryResponse customer = userClient.getUserById(booking.getCustomerId());
        UserSummaryResponse provider = userClient.getUserById(booking.getProviderId());
        return new BookingResponse(
                booking.getId(),
                booking.getCustomerId(),
                booking.getProviderId(),
                booking.getOfferId(),
                booking.getCategory(),
                booking.getPrice(),
                booking.getStatus(),
                booking.getCreatedAt(),
                customer != null ? customer.getUsername() : null,
                provider != null ? provider.getUsername() : null
        );
    }
}
