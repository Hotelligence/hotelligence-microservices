package com.hotelligence.bookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private String userId;
    private String roomId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String paymentMethod;
    private LocalDateTime bookingDate;
    private LocalDateTime checkinDate;
    private LocalDateTime checkoutDate;
    private String bookingStatus;
    private LocalDateTime cancelDue;
    private LocalDateTime unCancelDue;
}
