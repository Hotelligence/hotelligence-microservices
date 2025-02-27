package com.hotelligence.hotelservice.controller;

import com.hotelligence.hotelservice.dto.HotelRequest;
import com.hotelligence.hotelservice.dto.HotelResponse;
import com.hotelligence.hotelservice.model.Hotel;
import com.hotelligence.hotelservice.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @PostMapping(path = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createHotel(@RequestBody HotelRequest hotelRequest){
        hotelService.createHotel(hotelRequest);
    }

    @GetMapping(path = "/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<HotelResponse> getAllHotels(){
        return hotelService.getAllHotels();
    }

    @GetMapping(path = "/getHotelById/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HotelResponse getHotelById(@PathVariable("id") String hotelId){
        return hotelService.getHotelById(hotelId);
    }

    @GetMapping(path = "/searchResult")
    @ResponseStatus(HttpStatus.OK)
    public List<HotelResponse> search(@RequestParam String query,
                                      @RequestParam(required = false) String sortBy,
                                      @RequestParam(required = false) String sortOrder,
                                      @RequestParam(required = false) Integer minPrice,
                                      @RequestParam(required = false) Integer maxPrice,
                                      @RequestParam(required = false) Integer minRatingScore,
                                      @RequestParam(required = false) List<Integer> stars) {
        return hotelService.search(query, sortBy, sortOrder, minPrice, maxPrice, minRatingScore, stars);
    }

    @PatchMapping(path = "/updateHotel/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HotelResponse updateHotel(@PathVariable("id") String hotelId, @RequestBody HotelRequest hotelRequest){
        return hotelService.updateHotel(hotelId, hotelRequest);
    }

    @GetMapping(path = "/getHotelsByUserId/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<HotelResponse> getHotelsByUserId(@PathVariable("userId") String userId){
        return hotelService.getHotelsByUserId(userId);
    }

    @GetMapping(path = "/getCountByUserId/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Integer getCountByCreatedBy(@PathVariable("userId") String userId){
        return hotelService.getCountByCreatedBy(userId);
    }

    @GetMapping(path = "/getAllRoomAmenitiesByHotelId/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Hotel.RoomAmenities> getAllRoomAmenitiesByHotelId(@PathVariable("hotelId") String hotelId){
        return hotelService.getAllRoomAmenitiesByHotelId(hotelId);
    }
}