package com.hotelligence.hotelservice.dto;

import com.hotelligence.hotelservice.model.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequest {
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
    private ArrayList<Room.AvailableDate> availableDates;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AvailableDate {
        private LocalDateTime from;
        private LocalDateTime to;
    }
}
