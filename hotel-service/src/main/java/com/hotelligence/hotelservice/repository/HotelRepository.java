package com.hotelligence.hotelservice.repository;

import com.hotelligence.hotelservice.model.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HotelRepository extends MongoRepository<Hotel, String> {
}
