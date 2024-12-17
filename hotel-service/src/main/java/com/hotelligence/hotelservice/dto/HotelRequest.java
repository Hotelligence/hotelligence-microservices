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
public class HotelRequest {
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
}
