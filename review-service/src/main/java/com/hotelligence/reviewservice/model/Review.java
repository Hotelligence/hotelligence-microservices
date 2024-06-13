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
    private Integer ecoPoint;
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
        if (ecoPoint != null) {
            totalPoints += ecoPoint;
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
