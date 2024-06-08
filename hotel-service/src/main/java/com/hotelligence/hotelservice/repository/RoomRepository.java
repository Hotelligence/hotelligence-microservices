package com.hotelligence.hotelservice.repository;

import com.hotelligence.hotelservice.dto.RoomResponse;
import com.hotelligence.hotelservice.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface RoomRepository extends MongoRepository<Room, String> {
    @Query("{ 'availableDates': { $elemMatch: { 'from': { $lte: ?0 }, 'to': { $gte: ?1 } } } }")
    List<RoomResponse> findByAvailableDatesBetween(LocalDateTime from, LocalDateTime to);

    List<Room> findByHotelId(String hotelId);
}
