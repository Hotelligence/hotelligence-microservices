package com.hotelligence.reviewservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(value = "reviews")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Review {
    @Id
    private String id;
    private String roomId;
    private String userId;
    private String userName;
    private Integer cleanPoint;
    private Integer servicePoint;
    private Integer staffPoint;
    private Integer facilityPoint;
    private Integer ecofriendlyPoint;
    private Double overallPoint;
    private String pointCategory;
    private String comment;
    private LocalDateTime reviewDate;
}
