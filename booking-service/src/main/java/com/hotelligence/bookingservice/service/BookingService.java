package com.hotelligence.bookingservice.service;

import com.hotelligence.bookingservice.dto.BookingRequest;
import com.hotelligence.bookingservice.dto.BookingResponse;
import com.hotelligence.bookingservice.model.Booking;
import com.hotelligence.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    private final WebClient.Builder webClient;


    public void placeBooking(String roomId, BookingRequest bookingRequest) {
        Booking booking = new Booking();
        booking.setRoomId(roomId);
        booking.setBookingDate(LocalDateTime.now());
        booking.setCheckinDate(bookingRequest.getCheckinDate());
        booking.setCheckoutDate(bookingRequest.getCheckoutDate());
        booking.setBookingStatus(bookingRequest.getBookingStatus());
        booking.setCancelDue(bookingRequest.getCancelDue());
        booking.setUnCancelDue(bookingRequest.getUnCancelDue());

        bookingRepository.save(booking);

        log.info("Place booking successfully");
    }

    public List<BookingResponse> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();

        return bookings.stream().map(this::mapToBookingResponse).toList();
    }

    public BookingResponse getBookingById(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking with id " + bookingId + " does not exist"));

        return mapToBookingResponse(booking);
    }

    public List<BookingResponse> getBookingsByRoomId(String roomId) {
        List<Booking> bookings = bookingRepository.findByRoomId(roomId);

        return bookings.stream().map(this::mapToBookingResponse).toList();
    }




    private BookingResponse mapToBookingResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .roomId(booking.getRoomId())
                .bookingDate(booking.getBookingDate())
                .checkinDate(booking.getCheckinDate())
                .checkoutDate(booking.getCheckoutDate())
                .bookingStatus(booking.getBookingStatus())
                .cancelDue(booking.getCancelDue())
                .unCancelDue(booking.getUnCancelDue())
                .build();
    }
}
