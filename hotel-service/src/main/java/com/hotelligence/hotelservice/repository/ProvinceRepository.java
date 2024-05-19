package com.hotelligence.hotelservice.repository;

import com.hotelligence.hotelservice.model.Province;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProvinceRepository extends MongoRepository<Province, String> {
}
