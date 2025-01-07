package com.hotelligence.bookingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(value = "bookings")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Booking {
    @Id
    private String id;
    private String userId;
    private String hotelId;
    private String roomId;

    private String fullName;
    private String email;
    private String phoneNumber;
    private String paymentMethod;

    private LocalDateTime bookingDate;
    private LocalDateTime checkinDate;
    private LocalDateTime checkoutDate;
    private Integer numOfNights;

    private String bookingStatus;
    private boolean isCheckedOut;
    private LocalDateTime cancelDue;
    private LocalDateTime unCancelDue;
}
