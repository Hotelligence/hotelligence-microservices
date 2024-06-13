package com.hotelligence.reviewservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReviewResponse {
    private String id;
    private String roomId;
    private String userId;
    private String userName;
    private Integer cleanPoint;
    private Integer servicePoint;
    private Integer staffPoint;
    private Integer facilityPoint;
    private Integer ecoPoint;
    private Double overallPoint;
    private String pointCategory;
    private String comment;
    private LocalDateTime reviewDate;
}
