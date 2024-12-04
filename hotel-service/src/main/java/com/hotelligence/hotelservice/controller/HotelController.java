package com.hotelligence.hotelservice.controller;

import com.hotelligence.hotelservice.dto.HotelRequest;
import com.hotelligence.hotelservice.dto.HotelResponse;
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
}