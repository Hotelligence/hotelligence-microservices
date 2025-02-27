package com.hotelligence.hotelservice.controller;

import com.hotelligence.hotelservice.dto.RoomRequest;
import com.hotelligence.hotelservice.dto.RoomResponse;
import com.hotelligence.hotelservice.model.Room;
import com.hotelligence.hotelservice.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping(path = "/createRoom/{hotelId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRoom(@PathVariable String hotelId, @RequestBody RoomRequest roomRequest){
        roomService.createRoom(hotelId, roomRequest);
    }

    @PatchMapping(path = "/updateRoom/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateRoom(@PathVariable("id") String roomId, @RequestBody RoomRequest roomRequest){
        roomService.updateRoom(roomId, roomRequest);
    }

    @GetMapping(path = "/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<RoomResponse> getAllRooms(){
        return roomService.getAllRooms();
    }

//    @GetMapping(path = "/searchResult")
//    @ResponseStatus(HttpStatus.OK)
//    public List<RoomResponse> searchAvailableRooms(@RequestParam LocalDateTime from, @RequestParam LocalDateTime to){
//        return roomService.searchAvailableRooms(from, to);
//    }

    @GetMapping(path = "/getRoomsInHotel/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public List<RoomResponse> getRoomsInHotel(@PathVariable("hotelId") String hotelId){
        return roomService.getRoomsInHotel(hotelId);
    }

    @GetMapping(path = "/getRoomById/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoomResponse getRoomById(@PathVariable("id") String roomId){
        return roomService.getRoomById(roomId);
    }

    @GetMapping(path = "/getRoomWithLowestDiscountPrice/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public RoomResponse getRoomWithTheLowestDiscountPrice(@PathVariable("hotelId") String hotelId){
        return roomService.getRoomWithTheLowestDiscountPrice(hotelId);
    }

    @GetMapping(path = "/getRoomCountByHotelId/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public Integer getRoomCountByHotelId(@PathVariable("hotelId") String hotelId){
        return roomService.getRoomCountByHotelId(hotelId);
    }

    @GetMapping(path = "/getAvailableRooms/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public List<RoomResponse> getAvailableRooms(@PathVariable("hotelId") String hotelId, @RequestParam LocalDateTime fromDate, @RequestParam LocalDateTime toDate){
        return roomService.getAvailableRooms(hotelId, fromDate, toDate);
    }

//    @GetMapping(path = "/getAllAmenitiesByHotelId/{hotelId}")
//    @ResponseStatus(HttpStatus.OK)
//    public List<Room.Amenities> getAllAmenitiesByHotelId(@PathVariable("hotelId") String hotelId){
//        return roomService.getAllAmenitiesByHotelId(hotelId);
//    }

    @DeleteMapping(path = "/deleteRoom/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRoom(@PathVariable("id") String roomId){
        roomService.deleteRoom(roomId);
    }
}
