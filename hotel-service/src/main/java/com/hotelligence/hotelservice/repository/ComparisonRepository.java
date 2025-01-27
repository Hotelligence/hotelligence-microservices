package com.hotelligence.hotelservice.repository;

import com.hotelligence.hotelservice.model.Comparison;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ComparisonRepository extends MongoRepository<Comparison, String> {
    Comparison findByUserId(String userId);
}
