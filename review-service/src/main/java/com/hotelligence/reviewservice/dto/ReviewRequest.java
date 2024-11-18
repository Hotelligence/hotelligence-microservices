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

    public Double getOverallPoint() {
        int totalPoints = 0;
        int count = 0;

        if (cleanPoint != null) {
            totalPoints += cleanPoint;
            count++;
        }
        if (servicePoint != null) {
            totalPoints += servicePoint;
            count++;
        }
        if (staffPoint != null) {
            totalPoints += staffPoint;
            count++;
        }
        if (facilityPoint != null) {
            totalPoints += facilityPoint;
            count++;
        }
        if (ecofriendlyPoint != null) {
            totalPoints += ecofriendlyPoint;
            count++;
        }

        return count > 0 ? (double) totalPoints / count : 0;
    }
    
    public String getPointCategory() {
        if (getOverallPoint() >= 9)
            return "Tuyệt vời";
        else if (getOverallPoint() >= 8)
            return "Xuất sắc";
        else if (getOverallPoint() >= 7)
            return "Tốt";
        else
            return "Trung bình";
    }
}
