package com.hotelligence.hotelservice.controller;

import com.hotelligence.hotelservice.dto.FavoriteResponse;
import com.hotelligence.hotelservice.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @GetMapping(path = "/getAllFavoriteLists")
    @ResponseStatus(HttpStatus.OK)
    public List<FavoriteResponse> getAllFavoriteLists() {
        return favoriteService.getAllFavoriteLists();
    }

    @GetMapping(path = "/getFavoriteListByUserId/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public FavoriteResponse getFavoriteListByUserId(@PathVariable("userId") String userId) {
        return favoriteService.getFavoriteListByUserId(userId);
    }

    @PatchMapping(path = "/addToFavoriteList/{userId}/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public FavoriteResponse addHotelToFavoriteList(@PathVariable("userId") String userId, @PathVariable("hotelId") String hotelId) {
        return favoriteService.addHotelToFavoriteList(userId, hotelId);
    }

    @PatchMapping(path = "/removeFromFavoriteList/{userId}/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeHotelFromFavoriteList(@PathVariable("userId") String userId, @PathVariable("hotelId") String hotelId) {
        favoriteService.removeHotelFromFavoriteList(userId, hotelId);
    }
}
