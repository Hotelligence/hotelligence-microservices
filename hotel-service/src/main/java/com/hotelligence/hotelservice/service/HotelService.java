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
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new IllegalArgumentException("Hotel with id " + hotelId + " does not exist"));
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
                .ratingScore(hotel.getRatingScore())
                .ratingCategory(hotel.getRatingCategory())
                .numOfReviews(hotel.getNumOfReviews())
                .originPrice(hotel.getOriginPrice())
                .discount(hotel.getDiscount())
                .discountPrice(hotel.getDiscountPrice())
                .taxPercentage(hotel.getTaxPercentage())
                .taxPrice(hotel.getTaxPrice())
                .extraFee(hotel.getExtraFee())
                .totalPrice(hotel.getTotalPrice())
                .build();
    }

    public List<HotelResponse> search(String query, String sortBy, String sortOrder, Integer minPrice, Integer maxPrice, Integer minRatingScore, List<Integer> stars) {
        List<HotelResponse> searchResults = hotelRepository.findByHotelNameContainingIgnoreCaseOrProvinceContainingIgnoreCaseOrCityContainingIgnoreCase(query, query, query);

        // Apply filters
        if (minPrice != null) {
            searchResults = searchResults.stream()
                    .filter(hotel -> hotel.getDiscountPrice() >= minPrice)
                    .collect(Collectors.toList());
        }
        if (maxPrice != null) {
            searchResults = searchResults.stream()
                    .filter(hotel -> hotel.getDiscountPrice() <= maxPrice)
                    .collect(Collectors.toList());
        }
        if (minRatingScore != null) {
            searchResults = searchResults.stream()
                    .filter(hotel -> hotel.getRatingScore() >= minRatingScore)
                    .collect(Collectors.toList());
        }
        if (stars != null && !stars.isEmpty()) {
            searchResults = searchResults.stream()
                    .filter(hotel -> stars.contains(hotel.getStar()))
                    .collect(Collectors.toList());
        }

        // Apply sorting
        if (sortBy != null && sortOrder != null) {
            Comparator<HotelResponse> comparator;
            switch (sortBy) {
                case "discountPrice":
                    comparator = Comparator.comparing(HotelResponse::getDiscountPrice);
                    break;
                case "ratingScore":
                    comparator = Comparator.comparing(HotelResponse::getRatingScore);
                    break;
                // Add more cases for other sort criteria
                default:
                    throw new IllegalArgumentException("Invalid sort criteria: " + sortBy);
            }
            if ("desc".equalsIgnoreCase(sortOrder)) {
                comparator = comparator.reversed();
            }
            searchResults.sort(comparator);
        }

        return searchResults;
    }
}