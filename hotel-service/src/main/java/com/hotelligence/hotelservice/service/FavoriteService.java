package com.hotelligence.hotelservice.service;

import com.hotelligence.hotelservice.dto.FavoriteResponse;
import com.hotelligence.hotelservice.model.Favorite;
import com.hotelligence.hotelservice.model.Hotel;
import com.hotelligence.hotelservice.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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

    public FavoriteResponse addHotelToFavoriteList(String userId, String hotelId) {
        if (favoriteRepository.findByUserId(userId) == null) {
            log.info("User does not have a favorite list");
            createFavoriteList(userId);
        }

        Favorite favoriteList = favoriteRepository.findByUserId(userId);

        Hotel hotel = webClient.get()
                .uri("http://localhost:8080/api/hotels/getHotelById/" + hotelId)
                .retrieve()
                .bodyToMono(Hotel.class)
                .block();

        if (hotel == null) {
            log.info("Hotel not found");
            return null;
        }

        assert favoriteList != null;
        favoriteList.getFavoriteHotels().add(hotel);
        favoriteRepository.save(favoriteList);
        log.info("Hotel added to favorite list for user: {}", userId);
        return mapToFavoriteResponse(favoriteList);
    }

    public void removeHotelFromFavoriteList(String userId, String hotelId) {
        Favorite favorite = favoriteRepository.findByUserId(userId);
        if (favorite == null) {
            log.info("User does not have a favorite list");
            return;
        }
        favorite.getFavoriteHotels().removeIf(hotel -> hotel.getId().equals(hotelId));
        favoriteRepository.save(favorite);
        log.info("Hotel removed from favorite list for user: {}", userId);

    }


}
