package com.hotelligence.hotelservice.service;

import com.hotelligence.hotelservice.dto.ComparisonResponse;
import com.hotelligence.hotelservice.model.Comparison;
import com.hotelligence.hotelservice.model.Room;
import com.hotelligence.hotelservice.repository.ComparisonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
                                    .comparedRooms(new ArrayList<>())
                                    .build();
        comparisonRepository.save(comparison);
        log.info("Comparison list created for user: {}", userId);
    }

    public ComparisonResponse mapToComparisonResponse(Comparison comparison) {
        return ComparisonResponse.builder()
                .id(comparison.getId())
                .userId(comparison.getUserId())
                .comparedRooms(comparison.getComparedRooms())
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

    @Transactional
    public void addRoomToComparisonList(String userId, String roomId) {
        Comparison comparisonList = comparisonRepository.findByUserId(userId);

        if (comparisonList == null) {
            createComparisonList(userId);
            comparisonList = comparisonRepository.findByUserId(userId);
        }

        Room room = webClient.get()
                .uri("http://localhost:8080/api/rooms/getRoomById/" + roomId)
                .retrieve()
                .bodyToMono(Room.class)
                .block();

        if (room == null) {
            log.info("Room not found");
            return;
        }

//        if (!comparisonList.getComparedRooms().isEmpty() &&
//            !comparisonList.getComparedRooms().get(0).getHotelId().equals(room.getHotelId())) {
//            log.info("Room added is not in the same hotel");
//            return;
//        }

        if (comparisonList.getComparedRooms().stream().anyMatch(r -> r.getId().equals(roomId))) {
            log.info("Room is already in the comparison list");
            return;
        }

        if (comparisonList.getComparedRooms().size() >= 3) {
            log.info("Comparison list is full");
            return;
        }

        comparisonList.getComparedRooms().add(room);
        comparisonRepository.save(comparisonList);
        log.info("Room added to comparison list for user: {}", userId);
    }

    @Transactional
    public void removeRoomFromComparisonList(String userId, String roomId) {
        Comparison comparisonList = comparisonRepository.findByUserId(userId);

        if (comparisonList == null) {
            log.info("User does not have a comparison list");
            return;
        }

        boolean removed = comparisonList.getComparedRooms().removeIf(room -> room.getId().equals(roomId));
        if (removed) {
            comparisonRepository.save(comparisonList);
            log.info("Room removed from comparison list for user: {}", userId);
        } else {
            log.info("Room not found in comparison list for user: {}", userId);
        }
    }

    @Transactional
    public void removeAllRoomsFromComparisonList(String userId) {
        Comparison comparisonList = comparisonRepository.findByUserId(userId);

        if (comparisonList == null) {
            log.info("User does not have a comparison list");
            return;
        }

        if (!comparisonList.getComparedRooms().isEmpty()) {
            comparisonList.getComparedRooms().clear();
            comparisonRepository.save(comparisonList);
            log.info("All rooms removed from comparison list for user: {}", userId);
        } else {
            log.info("Comparison list is already empty for user: {}", userId);
        }
    }
}
