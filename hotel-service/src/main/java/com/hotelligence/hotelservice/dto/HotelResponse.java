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
    private String name;
    private String address;
    private Integer star;
    private String description;
}
