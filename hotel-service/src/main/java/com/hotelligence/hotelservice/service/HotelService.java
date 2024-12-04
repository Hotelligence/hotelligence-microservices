package com.hotelligence.hotelservice.service;

import com.hotelligence.hotelservice.dto.HotelRequest;
import com.hotelligence.hotelservice.dto.HotelResponse;
import com.hotelligence.hotelservice.dto.RoomResponse;
import com.hotelligence.hotelservice.model.Hotel;
import com.hotelligence.hotelservice.model.Room;
import com.hotelligence.hotelservice.repository.HotelRepository;
import com.hotelligence.hotelservice.repository.RoomRepository;
import com.hotelligence.hotelservice.service.RoomService;
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
    private final WebClient webClient;

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
        RoomResponse lowestRoom = webClient.get()
                .uri("http://localhost:8080/api/rooms/getRoomWithTheLowestDiscountPrice/" + hotel.getId())
                .retrieve()
                .bodyToMono(RoomResponse.class)
                .block();

        assert lowestRoom != null;

        return HotelResponse.builder()
                .id(hotel.getId())
                .createdBy(hotel.getCreatedBy())
                .hotelName(hotel.getHotelName())
                .country(hotel.getCountry())
                .province(hotel.getProvince())
                .city(hotel.getCity())
                .district(hotel.getDistrict())
                .address(hotel.getAddress())
                .postalCode(hotel.getPostalCode())
                .businessType(hotel.getBusinessType())
                .phoneNumber(hotel.getPhoneNumber())
                .emailAddress(hotel.getEmailAddress())
                .star(hotel.getStar())
                .description(hotel.getDescription())
                .images(hotel.getImages())
                .roomLowestDiscount(lowestRoom.getDiscount())
                .roomLowestOriginPrice(lowestRoom.getOriginPrice())
                .roomLowestTaxPercentage(lowestRoom.getTaxPercentage())
                .roomLowestTax(lowestRoom.getTax())
                .roomLowestDiscountPrice(lowestRoom.getDiscountPrice())
                .roomLowestTotalPrice(lowestRoom.getTotalPrice())
                .reviewCount(getReviewCountByHotelId(hotel.getId()))
                .reviewAverageCleanPoint(getReviewAveragePointsByHotelId(hotel.getId()).getReviewAverageCleanPoint())
                .reviewAverageServicePoint(getReviewAveragePointsByHotelId(hotel.getId()).getReviewAverageServicePoint())
                .reviewAverageStaffPoint(getReviewAveragePointsByHotelId(hotel.getId()).getReviewAverageStaffPoint())
                .reviewAverageFacilityPoint(getReviewAveragePointsByHotelId(hotel.getId()).getReviewAverageFacilityPoint())
                .reviewAverageEcofriendlyPoint(getReviewAveragePointsByHotelId(hotel.getId()).getReviewAverageEcofriendlyPoint())
                .reviewAverageOverallPoint(getReviewAveragePointsByHotelId(hotel.getId()).getReviewAverageOverallPoint())
                .build();
    }

    public List<HotelResponse> search(String query, String sortBy, String sortOrder, Integer minPrice, Integer maxPrice, Integer minRatingScore, List<Integer> stars) {
        List<HotelResponse> searchResults = hotelRepository.findByHotelNameContainingIgnoreCaseOrProvinceContainingIgnoreCaseOrCityContainingIgnoreCase(query, query, query);

        // Apply filters
        if (minPrice != null) {
            searchResults = searchResults.stream()
                    .filter(hotel -> hotel.getRoomLowestDiscountPrice() >= minPrice)
                    .collect(Collectors.toList());
        }
        if (maxPrice != null) {
            searchResults = searchResults.stream()
                    .filter(hotel -> hotel.getRoomLowestDiscountPrice() <= maxPrice)
                    .collect(Collectors.toList());
        }
        if (minRatingScore != null) {
            searchResults = searchResults.stream()
                    .filter(hotel -> hotel.getReviewAverageOverallPoint() >= minRatingScore)
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
                    comparator = Comparator.comparing(HotelResponse::getRoomLowestDiscountPrice);
                    break;
                case "ratingScore":
                    comparator = Comparator.comparing(HotelResponse::getReviewAverageOverallPoint);
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

    public Integer getReviewCountByHotelId(String hotelId) {
        return webClient.get()
                .uri("http://localhost:8080/api/reviews/getReviewCountByHotelId/" + hotelId)
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
    }

    public HotelResponse getReviewAveragePointsByHotelId(String hotelId) {
        return webClient.get()
                .uri("http://localhost:8080/api/reviews/getReviewAveragePointsByHotelId/" + hotelId)
                .retrieve()
                .bodyToMono(HotelResponse.class)
                .block();
    }
}