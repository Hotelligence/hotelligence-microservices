package com.hotelligence.hotelservice.service;

import com.hotelligence.hotelservice.dto.RoomResponse;
import com.hotelligence.hotelservice.repository.RoomRepository;
import com.hotelligence.hotelservice.model.Room;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;

    public List<RoomResponse> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream().map(this::mapToRoomResponse).toList();
    }

    private RoomResponse mapToRoomResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .hotelId(room.getHotelId())
                .image(room.getImage())
                .roomName(room.getRoomName())
                .roomType(room.getRoomType())
                .nightlyPrice(room.getNightlyPrice())
                .numOfBeds(room.getNumOfBeds())
                .numOfGuests(room.getNumOfGuests())
                .description(room.getDescription())
                .amenities(room.getAmenities())
                .breakfastPrice(room.getBreakfastPrice())
                .breakfastFor2Price(room.getBreakfastFor2Price())
                .discount(room.getDiscount())
                .oldPrice(room.getOldPrice())
                .newPrice(room.getNewPrice())
                .totalPrice(room.getTotalPrice())
                .status(room.getStatus())
                .build();
    }
}
