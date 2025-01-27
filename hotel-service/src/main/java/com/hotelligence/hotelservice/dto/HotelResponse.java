package com.hotelligence.hotelservice.dto;

import com.hotelligence.hotelservice.model.Hotel;
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

    private String optionalFees;
    private String amenities;
    private String policies;
    private String otherNames;

    private List<Hotel.RoomAmenities> roomAmenities;
    private List<Hotel.ExtraOptions> extraOptions;

    //get from Room
    private Integer roomCount;
    private Integer roomLowestOriginPrice;
    private Double roomLowestDiscountPercentage;
    private Integer roomLowestDiscountedPrice;
    private Double roomLowestTaxPercentage;
    private Integer roomLowestTotalPrice;

    //get from Review
    private Integer reviewCount;
    private Double reviewAverageCleanPoint;
    private Double reviewAverageServicePoint;
    private Double reviewAverageStaffPoint;
    private Double reviewAverageFacilityPoint;
    private Double reviewAverageEnvironmentPoint;
    private Double reviewAverageOverallPoint;
    private String reviewAveragePointCategory;
}
