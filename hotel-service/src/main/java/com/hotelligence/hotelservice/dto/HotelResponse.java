package com.hotelligence.hotelservice.dto;

import com.hotelligence.hotelservice.model.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponse {
    private String id;
    private String createdBy;
    private String hotelName;
    private String country;
    private String province;
    private String city;
    private String district;
    private String address;
    private String postalCode;
    private String businessType;
    private String phoneNumber;
    private String emailAddress;
    private Integer star;
    private String description;
    private List<String> images;

    //get from Room
    private Double roomLowestDiscount;
    private Integer roomLowestOriginPrice;
    private Double roomLowestTaxPercentage;
    private Integer roomLowestTax;
    private Integer roomLowestDiscountPrice;
    private Integer roomLowestTotalPrice;

    //get from Review
    private Integer reviewCount;
    private Integer reviewAverageCleanPoint;
    private Integer reviewAverageServicePoint;
    private Integer reviewAverageStaffPoint;
    private Integer reviewAverageFacilityPoint;
    private Integer reviewAverageEcofriendlyPoint;
    private Double reviewAverageOverallPoint;
}
