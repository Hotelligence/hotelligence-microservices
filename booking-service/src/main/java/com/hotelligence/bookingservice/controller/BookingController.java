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
    public void placeBooking(@PathVariable("roomId") String roomId, @RequestBody BookingRequest bookingRequest){
        bookingService.placeBooking(roomId, bookingRequest);
    }

    @GetMapping(path = "/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingResponse> getAllBookings(){
        return bookingService.getAllBookings();
    }

    @GetMapping(path = "/getBookingById/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public BookingResponse getBookingById(@PathVariable("bookingId") String bookingId){
        return bookingService.getBookingById(bookingId);
    }

    @GetMapping(path = "/getBookingsByRoomId/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingResponse> getBookingsByRoomId(@PathVariable("roomId") String roomId){
        return bookingService.getBookingsByRoomId(roomId);
    }

    @GetMapping(path = "/getBookingsByUserId/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingResponse> getBookingsByUserId(@PathVariable("userId") String userId){
        return bookingService.getBookingsByUserId(userId);
    }

    @PatchMapping(path = "/updateBookingStatus/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public BookingResponse updateBookingStatus(@PathVariable("bookingId") String bookingId, @RequestBody BookingRequest bookingRequest) {
        return bookingService.updateBookingStatus(bookingId, bookingRequest);
    }

    @PatchMapping(path = "/cancelBooking/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public BookingResponse cancelBooking(@PathVariable("bookingId") String bookingId){
        return bookingService.cancelBooking(bookingId);
    }

}
