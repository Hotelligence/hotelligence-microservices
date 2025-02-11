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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    private final WebClient webClient;
    private final Map<String, String> otpStorage = new HashMap<>();

    @Transactional
    public void initiateBooking(BookingRequest bookingRequest) {
        String otp = generateOTP();
        otpStorage.put(bookingRequest.getEmail(), otp);
        sendOtpEmail(bookingRequest.getEmail(), otp);
        log.info("OTP sent to email: {}", bookingRequest.getEmail());
    }

    @Transactional
    public void placeBooking(String roomId, BookingRequest bookingRequest, String otp) {
        if (!verifyOtp(bookingRequest.getEmail(), otp)) {
            throw new IllegalArgumentException("Invalid OTP");
        }

        Booking booking = Booking.builder()
                .userId(bookingRequest.getUserId())
                .hotelId(bookingRequest.getHotelId())
                .roomId(roomId)
                .roomName(bookingRequest.getRoomName())
                .fullName(bookingRequest.getFullName())
                .email(bookingRequest.getEmail())
                .phoneNumber(bookingRequest.getPhoneNumber())
                .paymentMethod(bookingRequest.getPaymentMethod())
                .paymentAmount(bookingRequest.getPaymentAmount())
                .bookingDate(LocalDateTime.now())
                .checkinDate(bookingRequest.getCheckinDate())
                .checkoutDate(bookingRequest.getCheckoutDate())
                .bookingStatus("Đang chờ thanh toán")
                .isCheckedOut(false)
                .build();

        bookingRepository.save(booking);

        sendCongratulationsEmail(booking.getEmail(), booking.getFullName(), booking.getId());

        log.info("Place booking successfully");
    }

    private boolean verifyOtp(String email, String otp) {
        return otp.equals(otpStorage.get(email));
    }

    private void sendOtpEmail(String toEmail, String otp) {
        webClient.post()
                .uri("http://localhost:8080/api/emails/sendOtpEmail/{toEmail}/{otp}", toEmail, otp)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    private void sendCongratulationsEmail(String toEmail, String fullName, String bookingId) {
        webClient.post()
                .uri("http://localhost:8080/api/emails/sendCongratulationsEmail/{toEmail}/{fullName}/{bookingId}", toEmail, fullName, bookingId)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    private String generateOTP() {
        int otp = (int) (Math.random() * 900000) + 100000;
        return String.valueOf(otp);
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
                .paymentAmount(booking.getPaymentAmount())
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
