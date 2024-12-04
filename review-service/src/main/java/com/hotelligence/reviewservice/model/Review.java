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
    private String hotelId;
    private String roomId;
    private String userId;
    private String userName;
    private Double cleanPoint;
    private Double servicePoint;
    private Double staffPoint;
    private Double facilityPoint;
    private Double environmentPoint;
    private Double overallPoint;
    private String pointCategory;
    private String comment;
    private LocalDateTime reviewDate;
}
