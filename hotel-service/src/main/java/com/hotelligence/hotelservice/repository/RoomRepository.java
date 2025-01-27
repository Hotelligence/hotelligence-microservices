package com.hotelligence.hotelservice.repository;

import com.hotelligence.hotelservice.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoomRepository extends MongoRepository<Room, String> {
    List<Room> findByHotelId(String hotelId);

    Room findFirstByOrderByDiscountedPriceAsc(String hotelId);

    Integer countByHotelId(String hotelId);


}