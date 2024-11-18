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
//        Review review = new Review();
//        review.setRoomId(roomId);
//        review.setUserId(reviewRequest.getUserId());
//        review.setUserName(reviewRequest.getUserName());
//        review.setCleanPoint(reviewRequest.getCleanPoint());
//        review.setServicePoint(reviewRequest.getServicePoint());
//        review.setStaffPoint(reviewRequest.getStaffPoint());
//        review.setFacilityPoint(reviewRequest.getFacilityPoint());
//        review.setEcoPoint(reviewRequest.getEcoPoint());
//        review.setComment(reviewRequest.getComment());
//        review.setReviewDate(reviewRequest.getReviewDate());

        Review review = Review.builder()
                .roomId(roomId)
                .userId(reviewRequest.getUserId())
                .userName(reviewRequest.getUserName())
                .cleanPoint(reviewRequest.getCleanPoint())
                .servicePoint(reviewRequest.getServicePoint())
                .staffPoint(reviewRequest.getStaffPoint())
                .facilityPoint(reviewRequest.getFacilityPoint())
                .ecofriendlyPoint(reviewRequest.getEcofriendlyPoint())
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
        return ReviewResponse.builder()
                .id(review.getId())
                .roomId(review.getRoomId())
                .userId(review.getUserId())
                .userName(review.getUserName())
                .cleanPoint(review.getCleanPoint())
                .servicePoint(review.getServicePoint())
                .staffPoint(review.getStaffPoint())
                .facilityPoint(review.getFacilityPoint())
                .ecofriendlyPoint(review.getEcofriendlyPoint())
                .overallPoint(review.getOverallPoint())
                .pointCategory(review.getPointCategory())
                .comment(review.getComment())
                .reviewDate(review.getReviewDate())
                .build();
    }

    public List<ReviewResponse> getReviewsByRoomId(String roomId) {
        List<Review> reviews = reviewRepository.findByRoomId(roomId);

        return reviews.stream().map(this::mapToReviewResponse).toList();
    }


}
