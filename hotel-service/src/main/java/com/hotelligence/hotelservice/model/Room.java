package com.hotelligence.hotelservice.model;

import com.hotelligence.hotelservice.dto.RoomResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private Integer nightlyPrice;
    private Integer numOfBeds;
    private Integer numOfGuests;
    private String description;
    private ArrayList<String> amenities;
    private Integer breakfastPrice;
    private Integer breakfastFor2Price;
    private Double discount;
    private Integer numOfRooms;
    private Integer originPrice;
    private Double taxPercentage;
    private Integer tax;
    private Integer extraFee;
    private Integer discountPrice;
    private Integer totalPrice;
    private ArrayList<AvailableDate> availableDates;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AvailableDate {
        private LocalDateTime from;
        private LocalDateTime to;
    }
}
