package com.hotelligence.hotelservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponse {
    private String id;
    private String hotelName;
    private String address;
    private Integer star;
    private String description;
    private String image;
    private String city;
    private String province;
    private Float ratingScore;
    private Integer numOfReviews;
    private Float discount;
    private Double oldPrice;
    private Double newPrice;
    private Double totalPrice;
}
