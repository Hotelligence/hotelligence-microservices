package com.hotelligence.hotelservice.service;

import com.hotelligence.hotelservice.dto.HotelRequest;
import com.hotelligence.hotelservice.dto.HotelResponse;
import com.hotelligence.hotelservice.model.Hotel;
import com.hotelligence.hotelservice.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelService {

    private final HotelRepository hotelRepository;

    public void createHotel(HotelRequest hotelRequest) {
        Hotel hotel = Hotel.builder()
                .hotelName(hotelRequest.getHotelName())
                .address(hotelRequest.getAddress())
                .star(hotelRequest.getStar())
                .description(hotelRequest.getDescription())
                .image(hotelRequest.getImage())
                .city(hotelRequest.getCity())
                .province(hotelRequest.getProvince())
                .ratingScore(hotelRequest.getRatingScore())
                .numOfReviews(hotelRequest.getNumOfReviews())
                .discount(hotelRequest.getDiscount())
                .oldPrice(hotelRequest.getOldPrice())
                .newPrice(hotelRequest.getNewPrice())
                .totalPrice(hotelRequest.getTotalPrice())
                .build();

        hotelRepository.save(hotel);
        log.info("Hotel {} is saved", hotel.getId());
    }

    public List<HotelResponse> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();

        return hotels.stream().map(this::mapToHotelResponse).toList();
    }

    public List<HotelResponse> sortByStarDesc() {
        List<Hotel> hotels = hotelRepository.findAll();
        hotels.sort(Comparator.comparingInt(Hotel::getStar).reversed());

        return hotels.stream().map(this::mapToHotelResponse).toList();
    }

    public List<HotelResponse> sortByStarInc() {
        List<Hotel> hotels = hotelRepository.findAll();
        hotels.sort(Comparator.comparingInt(Hotel::getStar));

        return hotels.stream().map(this::mapToHotelResponse).toList();
    }

    private HotelResponse mapToHotelResponse(Hotel hotel) {
        return HotelResponse.builder()
                .id(hotel.getId())
                .hotelName(hotel.getHotelName())
                .address(hotel.getAddress())
                .star(hotel.getStar())
                .description(hotel.getDescription())
                .image(hotel.getImage())
                .city(hotel.getCity())
                .province(hotel.getProvince())
                .ratingScore(hotel.getRatingScore())
                .numOfReviews(hotel.getNumOfReviews())
                .discount(hotel.getDiscount())
                .oldPrice(hotel.getOldPrice())
                .newPrice(hotel.getNewPrice())
                .totalPrice(hotel.getTotalPrice())
                .build();
    }

    public List<HotelResponse> filterByFiveStar() {
        List<Hotel> hotels = hotelRepository.findAll();

        return hotels.stream().filter(x -> x.getStar() == 5).map(this::mapToHotelResponse).collect(Collectors.toList());
    }

    public List<HotelResponse> filterByFourStar() {
        List<Hotel> hotels = hotelRepository.findAll();

        return hotels.stream().filter(x -> x.getStar() == 4).map(this::mapToHotelResponse).collect(Collectors.toList());
    }

    public List<HotelResponse> filterByThreeStar() {
        List<Hotel> hotels = hotelRepository.findAll();

        return hotels.stream().filter(x -> x.getStar() == 3).map(this::mapToHotelResponse).collect(Collectors.toList());
    }

    public List<HotelResponse> filterByTwoStar() {
        List<Hotel> hotels = hotelRepository.findAll();

        return hotels.stream().filter(x -> x.getStar() == 2).map(this::mapToHotelResponse).collect(Collectors.toList());
    }

    public List<HotelResponse> filterByOneStar() {
        List<Hotel> hotels = hotelRepository.findAll();

        return hotels.stream().filter(x -> x.getStar() == 1).map(this::mapToHotelResponse).collect(Collectors.toList());
    }

    public List<HotelResponse> search(String query) {
        return hotelRepository.findByHotelNameContainingIgnoreCaseOrProvinceContainingIgnoreCaseOrCityContainingIgnoreCase(query, query, query);
    }

    public List<HotelResponse> sortByPriceDesc() {
        List<Hotel> hotels = hotelRepository.findAll();
        hotels.sort(Comparator.comparingDouble(Hotel::getTotalPrice).reversed());

        return hotels.stream().map(this::mapToHotelResponse).toList();
    }
}
