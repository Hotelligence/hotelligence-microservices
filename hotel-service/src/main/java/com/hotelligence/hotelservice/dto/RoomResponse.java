package com.hotelligence.hotelservice.dto;

import com.hotelligence.hotelservice.model.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
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
    private List<Room.Amenities> amenities;

    private List<Room.ExtraOptions> extraOptions;

    private Integer originPrice;
    private Double discountPercentage;
    private Integer discountedPrice;
    private Double taxPercentage;
    private Integer totalPrice;

    //get from Booking
    private LocalDateTime checkinDate;
    private LocalDateTime checkoutDate;

}
