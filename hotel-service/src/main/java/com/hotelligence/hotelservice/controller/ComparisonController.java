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

    @PostMapping(path = "/addToComparisonList/{userId}/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public void addRoomToComparisonList(@PathVariable("userId") String userId, @PathVariable("roomId") String roomId) {
        comparisonService.addRoomToComparisonList(userId, roomId);
    }

    @PostMapping(path = "/removeFromComparisonList/{userId}/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeRoomFromComparisonList(@PathVariable("userId") String userId, @PathVariable("roomId") String roomId) {
        comparisonService.removeRoomFromComparisonList(userId, roomId);
    }

    @PostMapping(path = "/removeAllRoomsFromComparisonList/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeAllRoomsFromComparisonList(@PathVariable("userId") String userId) {
        comparisonService.removeAllRoomsFromComparisonList(userId);
    }
}
