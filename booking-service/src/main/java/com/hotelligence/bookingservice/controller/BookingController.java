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

    @PostMapping(path = "/placeBooking/{roomId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void placeBooking(@PathVariable("roomId") String roomId,@RequestBody BookingRequest bookingRequest){
        bookingService.placeBooking(roomId, bookingRequest);

    }

    @GetMapping(path = "/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingResponse> getAllBookings(){
        return bookingService.getAllBookings();
    }
}
