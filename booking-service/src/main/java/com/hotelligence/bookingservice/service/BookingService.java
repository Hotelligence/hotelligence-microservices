package com.hotelligence.bookingservice.service;

import com.hotelligence.bookingservice.dto.BookingRequest;
import com.hotelligence.bookingservice.dto.BookingResponse;
import com.hotelligence.bookingservice.model.Booking;
import com.hotelligence.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;

    public void createBooking(BookingRequest bookingRequest) {
        Booking booking = Booking.builder()
                .roomId(bookingRequest.getRoomId())
                .bookingDate(bookingRequest.getBookingDate())
                .checkinDate(bookingRequest.getCheckinDate())
                .checkoutDate(bookingRequest.getCheckoutDate())
                .bookingStatus(bookingRequest.getBookingStatus())
                .cancelDue(bookingRequest.getCancelDue())
                .unCancelDue(bookingRequest.getUnCancelDue())
                .build();

        bookingRepository.save(booking);
        log.info("Booking {} is saved", booking.getId());
    }

    public List<BookingResponse> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();

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
