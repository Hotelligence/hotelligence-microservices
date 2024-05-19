package com.hotelligence.hotelservice.repository;

import com.hotelligence.hotelservice.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RoomRepository extends MongoRepository<Room, String> {

}
