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
    private String hotelName;
    private String address;
    private Integer star;
    private String description;
    private List<String> images;
    private String city;
    private String province;
}
