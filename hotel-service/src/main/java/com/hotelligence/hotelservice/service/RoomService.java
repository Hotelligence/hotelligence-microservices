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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final WebClient webClient;

    public void createRoom(String hotelId, RoomRequest roomRequest) {
        Room room = Room.builder()
                .hotelId(hotelId)
                .roomName(roomRequest.getRoomName())
                .roomNumber(roomRequest.getRoomNumber())
                .roomType(roomRequest.getRoomType())
                .numOfBeds(roomRequest.getNumOfBeds())
                .bedType(roomRequest.getBedType())
                .maxAdults(roomRequest.getMaxAdults())
                .maxChildren(roomRequest.getMaxChildren())
                .images(roomRequest.getImages())
                .description(roomRequest.getDescription())
                .amenities(roomRequest.getAmenities())
                .extraOptions(roomRequest.getExtraOptions())
                .originPrice(roomRequest.getOriginPrice())
                .discountPercentage(roomRequest.getDiscountPercentage())
                .taxPercentage(roomRequest.getTaxPercentage())
                .build();

        roomRepository.save(room);
        log.info("Room {} is saved", room.getId());
    }

    @Transactional
    public void updateRoom(String roomId, RoomRequest roomRequest) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room with id " + roomId + " does not exist"));

        room.setRoomName(roomRequest.getRoomName());
        room.setRoomNumber(roomRequest.getRoomNumber());
        room.setRoomType(roomRequest.getRoomType());
        room.setNumOfBeds(roomRequest.getNumOfBeds());
        room.setBedType(roomRequest.getBedType());
        room.setMaxAdults(roomRequest.getMaxAdults());
        room.setMaxChildren(roomRequest.getMaxChildren());
        room.setImages(roomRequest.getImages());
        room.setDescription(roomRequest.getDescription());
        room.setAmenities(roomRequest.getAmenities());
        room.setExtraOptions(roomRequest.getExtraOptions());
        room.setOriginPrice(roomRequest.getOriginPrice());
        room.setDiscountPercentage(roomRequest.getDiscountPercentage());
        room.setTaxPercentage(roomRequest.getTaxPercentage());

        roomRepository.save(room);
        log.info("Room {} is updated", room.getId());
    }

    public List<RoomResponse> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();

        return rooms.stream().map(this::mapToRoomResponse).toList();
    }

    private RoomResponse mapToRoomResponse(Room room) {
        LocalDateTime checkinDate = getActiveBookingByRoomId(room.getId()) != null ? getActiveBookingByRoomId(room.getId()).getCheckinDate() : null;
        LocalDateTime checkoutDate = getActiveBookingByRoomId(room.getId()) != null ? getActiveBookingByRoomId(room.getId()).getCheckoutDate() : null;
        int discountedPrice = room.getOriginPrice() - (int) (room.getOriginPrice() * (room.getDiscountPercentage() / 100));
        int totalPrice = discountedPrice + (int) (discountedPrice * (room.getTaxPercentage() / 100));

        return RoomResponse.builder()
                .id(room.getId())
                .hotelId(room.getHotelId())
                .roomName(room.getRoomName())
                .roomNumber(room.getRoomNumber())
                .roomType(room.getRoomType())
                .numOfBeds(room.getNumOfBeds())
                .bedType(room.getBedType())
                .maxAdults(room.getMaxAdults())
                .maxChildren(room.getMaxChildren())
                .images(room.getImages())
                .description(room.getDescription())
                .amenities(room.getAmenities())
                .extraOptions(room.getExtraOptions())
                .originPrice(room.getOriginPrice())
                .discountPercentage(room.getDiscountPercentage())
                .discountedPrice(discountedPrice)
                .taxPercentage(room.getTaxPercentage())
                .totalPrice(totalPrice)
                .checkinDate(checkinDate)
                .checkoutDate(checkoutDate)
                .build();
    }

    public Integer getRoomCountByHotelId(String hotelId) {
        return roomRepository.countByHotelId(hotelId);
    }

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

    public RoomResponse getRoomWithTheLowestDiscountPrice(String hotelId) {
        return roomRepository.findByHotelId(hotelId).stream()
                .min((room1, room2) -> (int) (room1.getOriginPrice() - (room1.getOriginPrice() * (room1.getDiscountPercentage() / 100)) - room2.getOriginPrice() + (room2.getOriginPrice() * (room2.getDiscountPercentage() / 100))))
                .map(this::mapToRoomResponse)
                .orElse(null);
    }

    public RoomResponse getActiveBookingByRoomId(String roomId) {
        return webClient.get()
                .uri("http://localhost:8080/api/bookings/getActiveBookingByRoomId/" + roomId)
                .retrieve()
                .bodyToMono(RoomResponse.class)
                .block();
    }

    public List<RoomResponse> getAvailableRooms(String hotelId, LocalDateTime fromDate, LocalDateTime toDate) {
        return roomRepository.findByHotelId(hotelId).stream()
                .filter(room -> (room.getCheckinDate().isBefore(fromDate) || room.getCheckoutDate().isAfter(toDate)))
                .map(this::mapToRoomResponse)
                .toList();
    }

//    public List<Room.Amenities> getAllAmenitiesByHotelId(String hotelId) {
//        return roomRepository.findByHotelId(hotelId).stream()
//                .map(Room::getAmenities)
//                .flatMap(List::stream)
//                .toList();
//    }

    public void deleteRoom(String roomId) {
        roomRepository.deleteById(roomId);
        log.info("Room {} is deleted", roomId);
    }
}