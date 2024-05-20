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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createHotel(@RequestBody HotelRequest hotelRequest){
        hotelService.createHotel(hotelRequest);
    }

    @GetMapping(path = "/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<HotelResponse> getAllHotels(){
        return hotelService.getAllHotels();
    }

    @GetMapping(path = "/sortByStarDesc")
    @ResponseStatus(HttpStatus.OK)
    public List<HotelResponse> sortByStarDesc() { return hotelService.sortByStarDesc(); }

    @GetMapping(path = "/sortByStarInc")
    @ResponseStatus(HttpStatus.OK)
    public List<HotelResponse> sortByStarInc() { return hotelService.sortByStarInc(); }

    @GetMapping(path = "/filterByFiveStar")
    @ResponseStatus(HttpStatus.OK)
    public List<HotelResponse> filterByFiveStar() { return hotelService.filterByFiveStar(); }

    @GetMapping(path = "/filterByFourStar")
    @ResponseStatus(HttpStatus.OK)
    public List<HotelResponse> filterByFourStar() { return hotelService.filterByFourStar(); }

    @GetMapping(path = "/filterByThreeStar")
    @ResponseStatus(HttpStatus.OK)
    public List<HotelResponse> filterByThreeStar() { return hotelService.filterByThreeStar(); }

    @GetMapping(path = "/filterByTwoStar")
    @ResponseStatus(HttpStatus.OK)
    public List<HotelResponse> filterByTwoStar() { return hotelService.filterByTwoStar(); }

    @GetMapping(path = "/filterByOneStar")
    @ResponseStatus(HttpStatus.OK)
    public List<HotelResponse> filterByOneStar() { return hotelService.filterByOneStar(); }

    @GetMapping(path = "/searchResult")
    @ResponseStatus(HttpStatus.OK)
    public List<HotelResponse> search(@RequestParam String query){
        return hotelService.search(query);
    }
}
