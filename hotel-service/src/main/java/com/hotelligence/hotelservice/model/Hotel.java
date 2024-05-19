package com.hotelligence.hotelservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "hotels")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Hotel {
    @Id
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
