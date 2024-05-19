package com.hotelligence.bookingservice.controller;

import com.hotelligence.bookingservice.dto.BookingRequest;
import com.hotelligence.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeBooking(@RequestBody BookingRequest bookingRequest) {
        bookingService.placeBooking(bookingRequest);
        return "Booking successfully";
    }

}
