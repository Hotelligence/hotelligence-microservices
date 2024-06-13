package com.hotelligence.hotelservice.service;

import com.hotelligence.hotelservice.dto.RoomRequest;
import com.hotelligence.hotelservice.dto.RoomResponse;
import com.hotelligence.hotelservice.model.Hotel;
import com.hotelligence.hotelservice.repository.HotelRepository;
import com.hotelligence.hotelservice.repository.RoomRepository;
import com.hotelligence.hotelservice.model.Room;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    public void createRoom(String hotelId, RoomRequest roomRequest) {
        Room room = Room.builder()
                .hotelId(hotelId)
                .image(roomRequest.getImage())
                .roomName(roomRequest.getRoomName())
                .roomType(roomRequest.getRoomType())
                .nightlyPrice(roomRequest.getNightlyPrice())
                .numOfBeds(roomRequest.getNumOfBeds())
                .numOfGuests(roomRequest.getNumOfGuests())
                .description(roomRequest.getDescription())
                .amenities(roomRequest.getAmenities())
                .breakfastPrice(roomRequest.getBreakfastPrice())
                .breakfastFor2Price(roomRequest.getBreakfastFor2Price())
                .discount(roomRequest.getDiscount())
                .numOfRooms(roomRequest.getNumOfRooms())
                .originPrice(roomRequest.getOriginPrice())
                .taxPercentage(roomRequest.getTaxPercentage())
                .tax(roomRequest.getTax())
                .extraFee(roomRequest.getExtraFee())
                .discountPrice(roomRequest.getDiscountPrice())
                .totalPrice(roomRequest.getTotalPrice())
                .availableDates(roomRequest.getAvailableDates())
                .build();

        roomRepository.save(room);
        log.info("Room {} is saved", room.getId());
    }

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
                .numOfRooms(room.getNumOfRooms())
                .originPrice(room.getOriginPrice())
                .taxPercentage(room.getTaxPercentage())
                .tax(room.getTax())
                .extraFee(room.getExtraFee())
                .discountPrice(room.getDiscountPrice())
                .totalPrice(room.getTotalPrice())
                .availableDates(room.getAvailableDates())
                .build();
    }

//    public List<RoomResponse> searchAvailableRooms(LocalDateTime from, LocalDateTime to) {
//        return roomRepository.findByAvailableDatesBetween(from, to);
//    }

    public List<RoomResponse> getRoomsInHotel(String hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new IllegalArgumentException("Hotel with id " + hotelId + " does not exist"));

        List<Room> rooms = roomRepository.findByHotelId(hotel.getId());
        return rooms.stream().map(this::mapToRoomResponse).toList();
    }

    public RoomResponse getRoomById(String roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room with id " + roomId + " does not exist"));

        return mapToRoomResponse(room);
    }
}