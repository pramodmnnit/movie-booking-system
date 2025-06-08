package com.movie.booking.system.controller;

import com.movie.booking.system.dto.request.CreateBookingRequest;
import com.movie.booking.system.dto.response.ApiResponse;
import com.movie.booking.system.dto.response.BookingResponse;
import com.movie.booking.system.mapper.BookingMapper;
import com.movie.booking.system.model.Booking;
import com.movie.booking.system.pattern.facade.BookingFacade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingFacade bookingFacade;
    private final BookingMapper bookingMapper;

    @Autowired
    public BookingController(BookingFacade bookingFacade, BookingMapper bookingMapper) {
        this.bookingFacade = bookingFacade;
        this.bookingMapper = bookingMapper;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookingResponse>> createBooking(
            @Valid @RequestBody CreateBookingRequest request) {
        try {
            Booking booking = bookingFacade.createBooking(request);
            BookingResponse response = bookingMapper.toResponse(booking);
            return ResponseEntity.ok(ApiResponse.success("Booking created successfully", response));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<Void>> cancelBooking(@PathVariable String bookingId) {
        try {
            bookingFacade.cancelBooking(bookingId);
            return ResponseEntity.ok(ApiResponse.success("Booking cancelled successfully", null));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<BookingResponse>> getBookingDetails(@PathVariable String bookingId) {
        try {
            Booking booking = bookingFacade.getBookingDetails(bookingId);
            BookingResponse response = bookingMapper.toResponse(booking);
            return ResponseEntity.ok(ApiResponse.success("Booking details retrieved successfully", response));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getUserBookings(@PathVariable String userId) {
        try {
            List<Booking> bookings = bookingFacade.getUserBookings(userId);
            List<BookingResponse> responses = bookingMapper.toResponseList(bookings);
            return ResponseEntity.ok(ApiResponse.success("User bookings retrieved successfully", responses));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error(e.getMessage()));
        }
    }
} 