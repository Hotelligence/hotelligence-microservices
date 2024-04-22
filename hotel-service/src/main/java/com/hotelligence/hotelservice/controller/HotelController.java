package com.hotelligence.hotelservice.controller;

import com.hotelligence.hotelservice.dto.HotelRequest;
import com.hotelligence.hotelservice.dto.HotelResponse;
import com.hotelligence.hotelservice.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createHotel(@RequestBody HotelRequest hotelRequest){
        hotelService.createHotel(hotelRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<HotelResponse> getAllHotels(){
        return hotelService.getAllHotels();
    }
}
