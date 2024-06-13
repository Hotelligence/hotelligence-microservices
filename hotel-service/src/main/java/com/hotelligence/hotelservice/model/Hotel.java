package com.hotelligence.hotelservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

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
    private List<String> images;
    private String city;
    private String province;
//    private Double ratingScore;
//    private String ratingCategory;
//    private Integer numOfReviews;
//    private Integer originPrice;
//    private Double discount;
//    private Integer discountPrice;
//    private Double taxPercentage;
//    private Integer taxPrice;
//    private Integer extraFee;
//    private Integer totalPrice;

}
