package com.hotelligence.hotelservice.service;

import com.hotelligence.hotelservice.dto.HotelRequest;
import com.hotelligence.hotelservice.dto.HotelResponse;
import com.hotelligence.hotelservice.model.Hotel;
import com.hotelligence.hotelservice.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelService {

    private final HotelRepository hotelRepository;

    public void createHotel(HotelRequest hotelRequest) {
        Hotel hotel = Hotel.builder()
                .name(hotelRequest.getName())
                .address(hotelRequest.getAddress())
                .star(hotelRequest.getStar())
                .description(hotelRequest.getDescription())
                .build();

        hotelRepository.save(hotel);
        log.info("Hotel {} is saved", hotel.getId());
    }

    public List<HotelResponse> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();

        return hotels.stream().map(this::mapToHotelResponse).toList();
    }

    private HotelResponse mapToHotelResponse(Hotel hotel) {
        return HotelResponse.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .address(hotel.getAddress())
                .star(hotel.getStar())
                .description(hotel.getDescription())
                .build();
    }
}
