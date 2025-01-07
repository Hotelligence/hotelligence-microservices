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
    private String roomName;
    private String roomNumber;
    private String roomType;
    private Integer numOfBeds;
    private String bedType;
    private Integer maxAdults;
    private Integer maxChildren;

    private List<String> images;
    private String description;
    private List<Amenities> amenities;

    private List<ExtraOptions> extraOptions;

    private Integer originPrice;
    private Double discountPercentage;
    private Integer discountedPrice;
    private Double taxPercentage;
    private Integer totalPrice;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Amenities {
        private String amenityType;
        private List<String> amenityName;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ExtraOptions {
        private String optionName;
        private Integer optionPrice;
    }

    //get from Booking
    private LocalDateTime checkinDate;
    private LocalDateTime checkoutDate;

}
