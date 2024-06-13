package com.hotelligence.bookingservice.service;

import com.hotelligence.bookingservice.dto.BookingRequest;
import com.hotelligence.bookingservice.dto.BookingResponse;
import com.hotelligence.bookingservice.model.Booking;
import com.hotelligence.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    private final WebClient.Builder webClient;


    public void placeBooking(String roomId, BookingRequest bookingRequest) {
        Booking booking = new Booking();
        booking.setUserId(bookingRequest.getUserId());
        booking.setRoomId(roomId);
        booking.setFullName(bookingRequest.getFullName());
        booking.setEmail(bookingRequest.getEmail());
        booking.setPhoneNumber(bookingRequest.getPhoneNumber());
        booking.setPaymentMethod(bookingRequest.getPaymentMethod());
        booking.setBookingDate(bookingRequest.getBookingDate());
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
                .userId(booking.getUserId())
                .roomId(booking.getRoomId())
                .fullName(booking.getFullName())
                .email(booking.getEmail())
                .phoneNumber(booking.getPhoneNumber())
                .paymentMethod(booking.getPaymentMethod())
                .bookingDate(booking.getBookingDate())
                .checkinDate(booking.getCheckinDate())
                .checkoutDate(booking.getCheckoutDate())
                .bookingStatus(booking.getBookingStatus())
                .cancelDue(booking.getCancelDue())
                .unCancelDue(booking.getUnCancelDue())
                .build();
    }

    public List<BookingResponse> getBookingsByUserId(String userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);

        return bookings.stream().map(this::mapToBookingResponse).toList();
    }

    @Transactional
    public BookingResponse updateBookingStatus(String bookingId, BookingRequest bookingRequest) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking with id " + bookingId + " does not exist"));

        booking.setBookingStatus(bookingRequest.getBookingStatus());
        bookingRepository.save(booking);
        return mapToBookingResponse(booking);
    }

    @Transactional
    public BookingResponse cancelBooking(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking with id " + bookingId + " does not exist"));


        booking.setBookingStatus("Đã hủy");
        bookingRepository.save(booking);
        return mapToBookingResponse(booking);
    }
}
