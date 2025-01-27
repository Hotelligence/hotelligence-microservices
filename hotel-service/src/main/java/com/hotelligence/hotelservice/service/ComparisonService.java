package com.hotelligence.hotelservice.service;

import com.hotelligence.hotelservice.dto.ComparisonResponse;
import com.hotelligence.hotelservice.model.Comparison;
import com.hotelligence.hotelservice.model.Hotel;
import com.hotelligence.hotelservice.repository.ComparisonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComparisonService {
    private final ComparisonRepository comparisonRepository;
    private final WebClient webClient;

    public void createComparisonList(String userId) {
        //if the user already has a comparison list, return
        if (comparisonRepository.findByUserId(userId) != null) {
            log.info("User already has a comparison list");
            return;
        }
        Comparison comparison = Comparison.builder()
                                    .userId(userId)
                                    .comparedHotels(new ArrayList<>())
                                    .build();
        comparisonRepository.save(comparison);
        log.info("Comparison list created for user: {}", userId);
    }

    public ComparisonResponse mapToComparisonResponse(Comparison comparison) {
        return ComparisonResponse.builder()
                .id(comparison.getId())
                .userId(comparison.getUserId())
                .comparedHotels(comparison.getComparedHotels())
                .build();
    }

    public List<ComparisonResponse> getAllComparisonLists() {
        return comparisonRepository.findAll().stream().map(this::mapToComparisonResponse).toList();
    }

    public ComparisonResponse getComparisonListByUserId(String userId) {
        Comparison comparison = comparisonRepository.findByUserId(userId);
        if (comparison == null) {
            log.info("User does not have a comparison list");
            return null;
        }
        return mapToComparisonResponse(comparison);
    }

    public ComparisonResponse addHotelToComparisonList(String userId, String hotelId) {
        if (comparisonRepository.findByUserId(userId) == null) {
            log.info("User does not have a comparison list");
            createComparisonList(userId);
        }

        Comparison comparisonList = comparisonRepository.findByUserId(userId);

        Hotel hotel = webClient.get()
                .uri("http://localhost:8080/api/hotels/getHotelById/" + hotelId)
                .retrieve()
                .bodyToMono(Hotel.class)
                .block();

        if (hotel == null) {
            log.info("Hotel not found");
            return null;
        }

        assert comparisonList != null;

        if (comparisonList.getComparedHotels().size() < 3) {
            comparisonList.getComparedHotels().add(hotel);
        }
        else {
            log.info("Comparison list is full");
            return mapToComparisonResponse(comparisonList);
        }

        comparisonRepository.save(comparisonList);
        log.info("Hotel added to comparison list for user: {}", userId);
        return mapToComparisonResponse(comparisonList);
    }

    public void removeHotelFromComparisonList(String userId, String hotelId) {
        Comparison comparisonList = comparisonRepository.findByUserId(userId);

        if (comparisonList == null) {
            log.info("User does not have a comparison list");
            return;
        }

        comparisonList.getComparedHotels().removeIf(hotel -> hotel.getId().equals(hotelId));
        comparisonRepository.save(comparisonList);
        log.info("Hotel removed from comparison list for user: {}", userId);
    }
}
