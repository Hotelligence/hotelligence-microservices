package com.hotelligence.bookingservice.repository;

import com.hotelligence.bookingservice.dto.BookingResponse;
import com.hotelligence.bookingservice.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<Booking, String> {

    List<Booking> findByRoomId(String roomId);

    List<Booking> findByUserId(String userId);

    List<Booking> findByHotelId(String hotelId);

//    BookingResponse findByRoomIdAndCheckedOutIsFalse (String roomId, boolean checkedOut);
}
