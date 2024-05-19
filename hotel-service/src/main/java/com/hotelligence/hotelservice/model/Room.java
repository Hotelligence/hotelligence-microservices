package com.hotelligence.hotelservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Array;

@Document(value = "rooms")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Room {
    @Id
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
