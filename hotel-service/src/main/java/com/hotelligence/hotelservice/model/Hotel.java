package com.hotelligence.hotelservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Comparator;
import java.util.List;

@Document(value = "hotels")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Hotel {
    @Id
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

    private List<RoomAmenities> roomAmenities;
    private List<ExtraOptions> extraOptions;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RoomAmenities {
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
