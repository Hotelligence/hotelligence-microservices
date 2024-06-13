package com.hotelligence.hotelservice.service;

import com.hotelligence.hotelservice.dto.HotelRequest;
import com.hotelligence.hotelservice.dto.HotelResponse;
import com.hotelligence.hotelservice.dto.RoomResponse;
import com.hotelligence.hotelservice.model.Hotel;
import com.hotelligence.hotelservice.model.Room;
import com.hotelligence.hotelservice.repository.HotelRepository;
import com.hotelligence.hotelservice.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final WebClient.Builder webClient;

    public void createHotel(HotelRequest hotelRequest) {
        Hotel hotel = new Hotel();
        hotel.setHotelName(hotelRequest.getHotelName());
        hotel.setAddress(hotelRequest.getAddress());
        hotel.setStar(hotelRequest.getStar());
        hotel.setDescription(hotelRequest.getDescription());
        hotel.setImages(hotelRequest.getImages());
        hotel.setCity(hotelRequest.getCity());
        hotel.setProvince(hotelRequest.getProvince());

        hotelRepository.save(hotel);
        log.info("Hotel {} is saved", hotel.getId());
    }

    public List<HotelResponse> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();

        return hotels.stream().map(this::mapToHotelResponse).toList();
    }

    public HotelResponse getHotelById(String hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).
                orElseThrow(() -> new IllegalArgumentException("Hotel with id " + hotelId + " does not exist"));

        return mapToHotelResponse(hotel);
    }

    private HotelResponse mapToHotelResponse(Hotel hotel) {
        return HotelResponse.builder()
                .id(hotel.getId())
                .hotelName(hotel.getHotelName())
                .address(hotel.getAddress())
                .star(hotel.getStar())
                .description(hotel.getDescription())
                .images(hotel.getImages())
                .city(hotel.getCity())
                .province(hotel.getProvince())
//                .ratingScore(hotel.getRatingScore())
//                .ratingCategory(hotel.getRatingCategory())
//                .numOfReviews(hotel.getNumOfReviews())
//                .originPrice(hotel.getOriginPrice())
//                .discount(hotel.getDiscount())
//                .discountPrice(hotel.getDiscountPrice())
//                .taxPercentage(hotel.getTaxPercentage())
//                .taxPrice(hotel.getTaxPrice())
//                .extraFee(hotel.getExtraFee())
//                .totalPrice(hotel.getTotalPrice())
                .build();
    }


    public List<HotelResponse> search(String query) {
        return hotelRepository.findByHotelNameContainingIgnoreCaseOrProvinceContainingIgnoreCaseOrCityContainingIgnoreCase(query, query, query);
    }


}
