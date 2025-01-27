package com.hotelligence.hotelservice.controller;

import com.hotelligence.hotelservice.dto.ComparisonResponse;
import com.hotelligence.hotelservice.service.ComparisonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comparisons")
@RequiredArgsConstructor
public class ComparisonController {

    private final ComparisonService comparisonService;

    @GetMapping(path = "/getAllComparisons")
    @ResponseStatus(HttpStatus.OK)
    public List<ComparisonResponse> getAllComparisonLists() {
        return comparisonService.getAllComparisonLists();
    }

    @GetMapping(path = "/getComparisonListByUserId/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ComparisonResponse getComparisonListByUserId(@PathVariable("userId") String userId) {
        return comparisonService.getComparisonListByUserId(userId);
    }

    @PatchMapping(path = "/addToComparisonList/{userId}/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public ComparisonResponse addHotelToComparisonList(@PathVariable("userId") String userId, @PathVariable("hotelId") String hotelId) {
        return comparisonService.addHotelToComparisonList(userId, hotelId);
    }

    @PatchMapping(path = "/removeFromComparisonList/{userId}/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeHotelFromComparisonList(@PathVariable("userId") String userId, @PathVariable("hotelId") String hotelId) {
        comparisonService.removeHotelFromComparisonList(userId, hotelId);
    }

}
