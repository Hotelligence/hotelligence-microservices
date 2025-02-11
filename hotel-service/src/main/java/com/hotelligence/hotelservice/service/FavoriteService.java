package com.hotelligence.hotelservice.service;

import com.hotelligence.hotelservice.dto.FavoriteResponse;
import com.hotelligence.hotelservice.model.Favorite;
import com.hotelligence.hotelservice.model.Hotel;
import com.hotelligence.hotelservice.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final WebClient webClient;

    public void createFavoriteList(String userId) {
        //if the user already has a favorite list, return
        if (favoriteRepository.findByUserId(userId) != null) {
            log.info("User already has a favorite list");
            return;
        }
        Favorite favorite = Favorite.builder()
                                    .userId(userId)
                                    .favoriteHotels(new ArrayList<>())
                                    .build();
        favoriteRepository.save(favorite);
        log.info("Favorite list created for user: {}", userId);
    }

    public FavoriteResponse mapToFavoriteResponse(Favorite favorite) {
        return FavoriteResponse.builder()
                .id(favorite.getId())
                .userId(favorite.getUserId())
                .favoriteHotels(favorite.getFavoriteHotels())
                .build();
    }

    public List<FavoriteResponse> getAllFavoriteLists() {
        return favoriteRepository.findAll().stream().map(this::mapToFavoriteResponse).toList();
    }

    public FavoriteResponse getFavoriteListByUserId(String userId) {
        Favorite favorite = favoriteRepository.findByUserId(userId);
        if (favorite == null) {
            log.info("User does not have a favorite list");
            return null;
        }
        return mapToFavoriteResponse(favorite);
    }

    @Transactional
    public void addHotelToFavoriteList(String userId, String hotelId) {
        Favorite favoriteList = favoriteRepository.findByUserId(userId);
        if (favoriteList == null) {
            createFavoriteList(userId);
            favoriteList = favoriteRepository.findByUserId(userId);
        }

        Hotel hotel = webClient.get()
                .uri("http://localhost:8080/api/hotels/getHotelById/" + hotelId)
                .retrieve()
                .bodyToMono(Hotel.class)
                .block();

        if (hotel == null) {
            log.info("Hotel not found");
            return;
        }

        if (favoriteList.getFavoriteHotels().stream().noneMatch(h -> h.getId().equals(hotelId))) {
            favoriteList.getFavoriteHotels().add(hotel);
            favoriteRepository.save(favoriteList);
            log.info("Hotel added to favorite list for user: {}", userId);
        } else {
            log.info("Hotel already exists in favorite list for user: {}", userId);
        }
    }

    @Transactional
    public void removeHotelFromFavoriteList(String userId, String hotelId) {
        Favorite favorite = favoriteRepository.findByUserId(userId);
        if (favorite == null) {
            log.info("User does not have a favorite list");
            return;
        }

        boolean removed = favorite.getFavoriteHotels().removeIf(hotel -> hotel.getId().equals(hotelId));
        if (removed) {
            favoriteRepository.save(favorite);
            log.info("Hotel removed from favorite list for user: {}", userId);
        } else {
            log.info("Hotel not found in favorite list for user: {}", userId);
        }
    }

    @Transactional
    public void removeAllHotelsFromFavoriteList(String userId) {
        Favorite favorite = favoriteRepository.findByUserId(userId);
        if (favorite == null) {
            log.info("User does not have a favorite list");
            return;
        }

        favorite.getFavoriteHotels().clear();
        favoriteRepository.save(favorite);
        log.info("All hotels removed from favorite list for user: {}", userId);
    }
}
