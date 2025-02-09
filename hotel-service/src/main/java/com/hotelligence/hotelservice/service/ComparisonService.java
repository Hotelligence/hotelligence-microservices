package com.hotelligence.hotelservice.service;

import com.hotelligence.hotelservice.dto.ComparisonResponse;
import com.hotelligence.hotelservice.model.Comparison;
import com.hotelligence.hotelservice.model.Room;
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

    public void addRoomToComparisonList(String userId, String roomId) {
        if (comparisonRepository.findByUserId(userId) == null) {
            log.info("User does not have a comparison list");
            createComparisonList(userId);
        }

        Comparison comparisonList = comparisonRepository.findByUserId(userId);

        Room room = webClient.get()
                .uri("http://localhost:8080/api/rooms/getRoomById/" + roomId)
                .retrieve()
                .bodyToMono(Room.class)
                .block();

        assert comparisonList != null;

        if (!comparisonList.getComparedRooms().isEmpty()) {
            assert room != null;
            if (!comparisonList.getComparedRooms().get(0).getHotelId().equals(room.getHotelId())) {
                log.info("Room added is not in the same hotel");
//                return mapToComparisonResponse(comparisonList);
            }
        }

        //check if the room is already in the comparison list
        if (comparisonList.getComparedRooms().stream().anyMatch(r -> r.getId().equals(roomId))) {
            log.info("Room is already in the comparison list");
//            return mapToComparisonResponse(comparisonList);
        }

        if (comparisonList.getComparedRooms().size() < 3) {
            comparisonList.getComparedRooms().add(room);
        }
        else {
            log.info("Comparison list is full");
//            return mapToComparisonResponse(comparisonList);
        }

        comparisonRepository.save(comparisonList);
        log.info("Room added to comparison list for user: {}", userId);
//        return mapToComparisonResponse(comparisonList);
    }

    public void removeRoomFromComparisonList(String userId, String roomId) {
        Comparison comparisonList = comparisonRepository.findByUserId(userId);

        if (comparisonList == null) {
            log.info("User does not have a comparison list");
            return;
        }

        comparisonList.getComparedRooms().removeIf(room -> room.getId().equals(roomId));
        comparisonRepository.save(comparisonList);
        log.info("Hotel removed from comparison list for user: {}", userId);
    }

    public void removeAllRoomsFromComparisonList(String userId) {
        Comparison comparisonList = comparisonRepository.findByUserId(userId);

        if (comparisonList == null) {
            log.info("User does not have a comparison list");
            return;
        }

        comparisonList.getComparedRooms().clear();
        comparisonRepository.save(comparisonList);
        log.info("All rooms removed from comparison list for user: {}", userId);
    }
}
