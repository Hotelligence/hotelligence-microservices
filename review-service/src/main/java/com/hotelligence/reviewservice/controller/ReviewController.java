package com.hotelligence.reviewservice.controller;

import com.hotelligence.reviewservice.dto.ReviewRequest;
import com.hotelligence.reviewservice.dto.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.hotelligence.reviewservice.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping(path = "/writeReview/{roomId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createReview(@PathVariable("roomId") String roomId, @RequestBody ReviewRequest reviewRequest){
        reviewService.writeReview(roomId, reviewRequest);
    }

    @GetMapping(path = "/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<ReviewResponse> getAllReviews(){
        return reviewService.getAllReviews();
    }

    @GetMapping(path = "/getReviewsByRoomId/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ReviewResponse> getReviewsByRoomId(@PathVariable("roomId") String roomId){
        return reviewService.getReviewsByRoomId(roomId);
    }
}
