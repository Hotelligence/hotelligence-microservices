package com.hotelligence.bookingservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="t_bookings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String bookingNumber;
    @OneToMany(cascade = CascadeType.ALL)
    private List<BookingLineItems> bookingLineItemsList;
}
