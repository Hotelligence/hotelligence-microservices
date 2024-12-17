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

    //get from Room
    private Integer roomCount;
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
