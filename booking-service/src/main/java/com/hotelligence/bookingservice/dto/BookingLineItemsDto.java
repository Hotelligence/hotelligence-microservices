package com.hotelligence.bookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingLineItemsDto {
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
