package com.hotelligence.hotelservice.repository;

import com.hotelligence.hotelservice.model.Favorite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends MongoRepository<Favorite, String> {
    Favorite findByUserId(String userId);
}
