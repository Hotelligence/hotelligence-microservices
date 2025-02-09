package com.hotelligence.hotelservice.service;

import com.hotelligence.hotelservice.dto.HotelRequest;
import com.hotelligence.hotelservice.dto.HotelResponse;
import com.hotelligence.hotelservice.dto.RoomResponse;
import com.hotelligence.hotelservice.model.Hotel;
import com.hotelligence.hotelservice.repository.HotelRepository;
import com.hotelligence.reviewservice.dto.ReviewResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
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
        Hotel hotel = Hotel.builder()
                    .createdBy(hotelRequest.getCreatedBy())
                    .hotelName(hotelRequest.getHotelName())
                    .country(hotelRequest.getCountry())
                    .province(hotelRequest.getProvince())
                    .city(hotelRequest.getCity())
                    .district(hotelRequest.getDistrict())
                    .address(hotelRequest.getAddress())
                    .postalCode(hotelRequest.getPostalCode())
                    .businessType(hotelRequest.getBusinessType())
                    .phoneNumber(hotelRequest.getPhoneNumber())
                    .emailAddress(hotelRequest.getEmailAddress())
                    .star(hotelRequest.getStar())
                    .description(hotelRequest.getDescription())
                    .images(hotelRequest.getImages())
                    .build();

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
                .uri("http://localhost:8080/api/rooms/getRoomWithLowestDiscountPrice/" + hotel.getId())
                .retrieve()
                .bodyToMono(RoomResponse.class)
                .block();

        if (lowestRoom == null) {
            log.warn("No room data found for hotel id: {}", hotel.getId());
            lowestRoom = new RoomResponse(); // or handle appropriately
        }

        int roomCount = webClient.get()
                .uri("http://localhost:8080/api/rooms/getRoomCountByHotelId/" + hotel.getId())
                .retrieve()
                .bodyToMono(Integer.class)
                .block();


        int reviewCount = webClient.get()
                .uri("http://localhost:8080/api/reviews/getReviewCountByHotelId/" + hotel.getId())
                .retrieve()
                .bodyToMono(Integer.class)
                .block();

        ReviewResponse averageReview = webClient.get()
                .uri("http://localhost:8080/api/reviews/getReviewAveragePointsByHotelId/" + hotel.getId())
                .retrieve()
                .bodyToMono(ReviewResponse.class)
                .block();

        if (averageReview == null) {
            log.warn("No review data found for hotel id: {}", hotel.getId());
            averageReview = new ReviewResponse(); // or handle appropriately
        }

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
                .optionalFees(hotel.getOptionalFees())
                .amenities(hotel.getAmenities())
                .policies(hotel.getPolicies())
                .otherNames(hotel.getOtherNames())
                .roomAmenities(hotel.getRoomAmenities())
                .extraOptions(hotel.getExtraOptions())
                .roomCount(roomCount)
                .roomLowestOriginPrice(lowestRoom.getOriginPrice())
                .roomLowestDiscountPercentage(lowestRoom.getDiscountPercentage())
                .roomLowestDiscountedPrice(lowestRoom.getDiscountedPrice())
                .roomLowestTaxPercentage(lowestRoom.getTaxPercentage())
                .roomLowestTotalPrice(lowestRoom.getTotalPrice())
                .reviewCount(reviewCount)
                .reviewAverageCleanPoint(averageReview.getCleanPoint())
                .reviewAverageServicePoint(averageReview.getServicePoint())
                .reviewAverageStaffPoint(averageReview.getStaffPoint())
                .reviewAverageFacilityPoint(averageReview.getFacilityPoint())
                .reviewAverageEnvironmentPoint(averageReview.getEnvironmentPoint())
                .reviewAverageOverallPoint(averageReview.getOverallPoint())
                .reviewAveragePointCategory(averageReview.getPointCategory())
                .build();
    }

    public List<HotelResponse> search(String query, String sortBy, String sortOrder, Integer minPrice, Integer maxPrice, Integer minRatingScore, List<Integer> stars) throws IllegalArgumentException {
        List<Hotel> hotels = hotelRepository.findByHotelNameContainingIgnoreCaseOrProvinceContainingIgnoreCaseOrCityContainingIgnoreCase(query, query, query);
        List<HotelResponse> hotelResponses = hotels.stream()
                .map(this::mapToHotelResponse)
                .filter(hotelResponse -> minPrice == null || hotelResponse.getRoomLowestTotalPrice() >= minPrice)
                .filter(hotelResponse -> maxPrice == null || hotelResponse.getRoomLowestTotalPrice() <= maxPrice)
                .filter(hotelResponse -> minRatingScore == null || hotelResponse.getReviewAverageOverallPoint() >= minRatingScore)
                .filter(hotelResponse -> stars == null || stars.isEmpty() || stars.contains(hotelResponse.getStar()))
                .collect(Collectors.toList());

        if (sortBy != null) {
            switch (sortBy) {
                case "discountPrice":
                    hotelResponses.sort(Comparator.comparing(HotelResponse::getRoomLowestTotalPrice));
                    break;
                case "ratingScore":
                    hotelResponses.sort(Comparator.comparing(HotelResponse::getReviewAverageOverallPoint));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid sortBy parameter");
            }
        }

        if (sortOrder != null && sortOrder.equals("desc")) {
            Collections.reverse(hotelResponses);
        }

        return hotelResponses;
    }

    @Transactional
    public HotelResponse updateHotel(String hotelId, HotelRequest hotelRequest) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new IllegalArgumentException("Hotel with id " + hotelId + " does not exist"));

        hotel.setHotelName(hotelRequest.getHotelName());
        hotel.setCountry(hotelRequest.getCountry());
        hotel.setProvince(hotelRequest.getProvince());
        hotel.setCity(hotelRequest.getCity());
        hotel.setDistrict(hotelRequest.getDistrict());
        hotel.setAddress(hotelRequest.getAddress());
        hotel.setPostalCode(hotelRequest.getPostalCode());
        hotel.setBusinessType(hotelRequest.getBusinessType());
        hotel.setPhoneNumber(hotelRequest.getPhoneNumber());
        hotel.setEmailAddress(hotelRequest.getEmailAddress());
        hotel.setStar(hotelRequest.getStar());
        hotel.setDescription(hotelRequest.getDescription());
        hotel.setImages(hotelRequest.getImages());
        hotel.setOptionalFees(hotelRequest.getOptionalFees());
        hotel.setAmenities(hotelRequest.getAmenities());
        hotel.setPolicies(hotelRequest.getPolicies());
        hotel.setOtherNames(hotelRequest.getOtherNames());
        hotel.setRoomAmenities(hotelRequest.getRoomAmenities());
        hotel.setExtraOptions(hotelRequest.getExtraOptions());

        hotelRepository.save(hotel);
        log.info("Hotel {} is updated", hotel.getId());

        return mapToHotelResponse(hotel);
    }

    public List<HotelResponse> getHotelsByUserId(String userId) {
        return hotelRepository.findByCreatedBy(userId).stream()
                .map(this::mapToHotelResponse)
                .toList();
    }

    public Integer getCountByCreatedBy(String createdBy) {
        return hotelRepository.countByCreatedBy(createdBy);
    }

    public List<Hotel.RoomAmenities> getAllRoomAmenitiesByHotelId(String hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new IllegalArgumentException("Hotel with id " + hotelId + " does not exist"));
        return hotel.getRoomAmenities();
    }
}