package com.hotelligence.hotelservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<String> images;
    private String city;
    private String province;
    private Double ratingScore;
    private String ratingCategory;
    private Integer numOfReviews;
    private Integer originPrice;
    private Double discount;
    private Integer discountPrice;
    private Double taxPercentage;
    private Integer taxPrice;
    private Integer extraFee;
    private Integer totalPrice;
}
