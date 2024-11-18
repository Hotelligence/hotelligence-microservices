package com.hotelligence.hotelservice.repository;

import com.hotelligence.hotelservice.dto.HotelResponse;
import com.hotelligence.hotelservice.model.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends MongoRepository<Hotel, String> {
    List<HotelResponse> findByHotelNameContainingIgnoreCaseOrProvinceContainingIgnoreCaseOrCityContainingIgnoreCase(String hotelName, String province, String city);

//    List<HotelResponse> findByOrderByDiscountPriceDesc();
//    List<HotelResponse> findByOrderByDiscountPriceAsc();
//    List<HotelResponse> findByOrderByRatingScoreDesc();
//    List<HotelResponse> findByOrderByRatingScoreAsc();

}
