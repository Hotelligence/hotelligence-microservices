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

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    private final WebClient.Builder webClient;


    public void placeBooking(String roomId, BookingRequest bookingRequest) {
        Booking booking = Booking.builder()
                .userId(bookingRequest.getUserId())
                .hotelId(bookingRequest.getHotelId())
                .roomId(roomId)
                .roomName(bookingRequest.getRoomName())
                .fullName(bookingRequest.getFullName())
                .email(bookingRequest.getEmail())
                .phoneNumber(bookingRequest.getPhoneNumber())
                .paymentMethod(bookingRequest.getPaymentMethod())
                .bookingDate(LocalDateTime.now())
                .checkinDate(bookingRequest.getCheckinDate())
                .checkoutDate(bookingRequest.getCheckoutDate())
                .bookingStatus("Đang chờ thanh toán")
                .isCheckedOut(false)
                .build();

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
        LocalDateTime cancelDue = booking.getBookingDate().plusDays(3);
        LocalDateTime unCancelDue = booking.getBookingDate().plusDays(7);
        Integer numOfNights = booking.getCheckoutDate().getDayOfYear() - booking.getCheckinDate().getDayOfYear();

        return BookingResponse.builder()
                .id(booking.getId())
                .userId(booking.getUserId())
                .hotelId(booking.getHotelId())
                .roomId(booking.getRoomId())
                .roomName(booking.getRoomName())
                .fullName(booking.getFullName())
                .email(booking.getEmail())
                .phoneNumber(booking.getPhoneNumber())
                .paymentMethod(booking.getPaymentMethod())
                .bookingDate(booking.getBookingDate())
                .checkinDate(booking.getCheckinDate())
                .checkoutDate(booking.getCheckoutDate())
                .numOfNights(numOfNights)
                .bookingStatus(booking.getBookingStatus())
                .isCheckedOut(booking.isCheckedOut())
                .cancelDue(cancelDue)
                .unCancelDue(unCancelDue)
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

    public BookingResponse getActiveBookingByRoomId(String roomId) {
        List<BookingResponse> bookings = getBookingsByRoomId(roomId);

        return bookings.stream()
                .filter(booking -> !booking.isCheckedOut())
                .findFirst()
                .orElse(null);
    }

    public List<BookingResponse> getBookingsByHotelId(String hotelId) {
        List<Booking> bookings = bookingRepository.findByHotelId(hotelId);

        return bookings.stream().map(this::mapToBookingResponse).toList();
    }

    public BookingResponse updateCheckoutStatus(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking with id " + bookingId + " does not exist"));

        booking.setCheckedOut(true);
        booking.setBookingStatus("Hoàn tất");
        bookingRepository.save(booking);
        return mapToBookingResponse(booking);
    }
}
