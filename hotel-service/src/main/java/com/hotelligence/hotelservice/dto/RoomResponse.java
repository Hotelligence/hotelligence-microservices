package com.hotelligence.hotelservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
    private String id;
    private String hotelId;
    private String image;
    private String roomName;
    private String roomType;
    private Double nightlyPrice;
    private Integer numOfBeds;
    private Integer numOfGuests;
    private String description;
    private Array amenities;
    private Double breakfastPrice;
    private Double breakfastFor2Price;
    private Double discount;
    private Double oldPrice;
    private Double newPrice;
    private Double totalPrice;
    private String status;

}
