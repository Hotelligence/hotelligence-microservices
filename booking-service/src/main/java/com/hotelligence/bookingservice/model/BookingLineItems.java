package com.hotelligence.bookingservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name = "t_booking_line_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingLineItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date checkinDate;
    private Date checkoutDate;
    private Double nightlyPrice;
    private Integer numOfNights;
    private Double tax;
    private Double extraFee;
    private Double totalPrice;
    private String bookingStatus;
}
