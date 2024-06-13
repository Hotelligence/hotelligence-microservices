package com.hotelligence.reviewservice.repository;

import com.hotelligence.reviewservice.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String>{
    List<Review> findByRoomId(String roomId);
}
