package com.hotelligence.reviewservice.service;

import com.hotelligence.reviewservice.dto.ReviewRequest;
import com.hotelligence.reviewservice.dto.ReviewResponse;
import com.hotelligence.reviewservice.model.Review;
import com.hotelligence.reviewservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public void writeReview(String roomId, ReviewRequest reviewRequest) {
        Review review = Review.builder()
                .hotelId(reviewRequest.getHotelId())
                .roomId(roomId)
                .userId(reviewRequest.getUserId())
                .userName(reviewRequest.getUserName())
                .cleanPoint(reviewRequest.getCleanPoint())
                .servicePoint(reviewRequest.getServicePoint())
                .staffPoint(reviewRequest.getStaffPoint())
                .facilityPoint(reviewRequest.getFacilityPoint())
                .environmentPoint(reviewRequest.getEnvironmentPoint())
                .comment(reviewRequest.getComment())
                .reviewDate(reviewRequest.getReviewDate())
                .build();


        reviewRepository.save(review);
        log.info("Review saved successfully");
    }

    public List<ReviewResponse> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();

        return reviews.stream().map(this::mapToReviewResponse).toList();
    }

    private ReviewResponse mapToReviewResponse(Review review) {
        double overallPoint = (review.getCleanPoint() + review.getServicePoint() + review.getStaffPoint() + review.getFacilityPoint() + review.getEnvironmentPoint()) / 5;

        return ReviewResponse.builder()
                .id(review.getId())
                .hotelId(review.getHotelId())
                .roomId(review.getRoomId())
                .userId(review.getUserId())
                .userName(review.getUserName())
                .cleanPoint(review.getCleanPoint())
                .servicePoint(review.getServicePoint())
                .staffPoint(review.getStaffPoint())
                .facilityPoint(review.getFacilityPoint())
                .environmentPoint(review.getEnvironmentPoint())
                .overallPoint(overallPoint)
                .pointCategory(review.getPointCategory())
                .comment(review.getComment())
                .reviewDate(review.getReviewDate())
                .build();
    }



    public List<ReviewResponse> getReviewsByHotelId(String hotelId) {
        List<Review> reviews = reviewRepository.findByHotelId(hotelId);

        return reviews.stream().map(this::mapToReviewResponse).toList();
    }

    public List<ReviewResponse> getReviewsByRoomId(String roomId) {
        List<Review> reviews = reviewRepository.findByRoomId(roomId);

        return reviews.stream().map(this::mapToReviewResponse).toList();
    }

    public Integer getReviewCountByHotelId(String hotelId) {
        return reviewRepository.countByHotelId(hotelId);
    }

    public ReviewResponse getReviewAveragePointsByHotelId(String hotelId) {
        List<Review> reviews = reviewRepository.findByHotelId(hotelId);
        double totalCleanPoint = 0;
        double totalServicePoint = 0;
        double totalStaffPoint = 0;
        double totalFacilityPoint = 0;
        double totalEnvironmentPoint = 0;
        int count = reviews.size();

        for (Review review : reviews) {
            if (review.getCleanPoint() != null) totalCleanPoint += review.getCleanPoint();
            if (review.getServicePoint() != null) totalServicePoint += review.getServicePoint();
            if (review.getStaffPoint() != null) totalStaffPoint += review.getStaffPoint();
            if (review.getFacilityPoint() != null) totalFacilityPoint += review.getFacilityPoint();
            if (review.getEnvironmentPoint() != null) totalEnvironmentPoint += review.getEnvironmentPoint();
        }

        double averageCleanPoint = totalCleanPoint / count;
        double averageServicePoint = totalServicePoint / count;
        double averageStaffPoint = totalStaffPoint / count;
        double averageFacilityPoint = totalFacilityPoint / count;
        double averageEnvironmentPoint = totalEnvironmentPoint / count;

        double averageOverallPoint = (averageCleanPoint + averageServicePoint + averageStaffPoint + averageFacilityPoint + averageEnvironmentPoint) / 5;

        return ReviewResponse.builder()
                .hotelId(hotelId)
                .cleanPoint(averageCleanPoint)
                .servicePoint(averageServicePoint)
                .staffPoint(averageStaffPoint)
                .facilityPoint(averageFacilityPoint)
                .environmentPoint(averageEnvironmentPoint)
                .overallPoint(averageOverallPoint)
                .build();
    }

}
