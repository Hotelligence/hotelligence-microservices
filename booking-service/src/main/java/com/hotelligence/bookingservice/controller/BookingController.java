package com.hotelligence.bookingservice.controller;


import com.hotelligence.bookingservice.dto.BookingRequest;
import com.hotelligence.bookingservice.dto.BookingResponse;
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
    public void createBooking(@RequestBody BookingRequest bookingRequest){
        bookingService.createBooking(bookingRequest);
    }

    @GetMapping(path = "/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingResponse> getAllBookings(){
        return bookingService.getAllBookings();
    }
}
