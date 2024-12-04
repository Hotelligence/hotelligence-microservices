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

    @GetMapping(path = "/getReviewsByHotelId/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ReviewResponse> getReviewsByHotelId(@PathVariable("hotelId") String hotelId){
        return reviewService.getReviewsByHotelId(hotelId);
    }

    @GetMapping(path = "/getReviewsByRoomId/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ReviewResponse> getReviewsByRoomId(@PathVariable("roomId") String roomId){
        return reviewService.getReviewsByRoomId(roomId);
    }

    @GetMapping(path = "/getReviewCountByHotelId/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public Integer getReviewCountByHotelId(@PathVariable("hotelId") String hotelId){
        return reviewService.getReviewCountByHotelId(hotelId);
    }

    @GetMapping(path = "/getReviewAveragePointsByHotelId/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public ReviewResponse getReviewAveragePointsByHotelId(@PathVariable("hotelId") String hotelId){
        return reviewService.getReviewAveragePointsByHotelId(hotelId);
    }

}


