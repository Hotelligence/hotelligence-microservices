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
public class ReviewRequest {
    private String hotelId;
    private String roomId;
    private String userId;
    private String userName;
    private Double cleanPoint;
    private Double servicePoint;
    private Double staffPoint;
    private Double facilityPoint;
    private Double environmentPoint;
    private String pointCategory;
    private String comment;
    private LocalDateTime reviewDate;

}
